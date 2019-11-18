package animallogic.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import animallogic.constants.MarkovChainConstants;
import animallogic.exception.InvalidInputException;

public class MarkovChain {

	public final static int keySize=2;
	
	
	public MarkovChain() {
		// TODO Auto-generated constructor stub
	}
	
	public static String transformText(String textToTransform, String prefix, String suffix) throws InvalidInputException{
		//textToTransfom can not be empty
		if(textToTransform == null || textToTransform.trim().isEmpty()){
			throw new InvalidInputException(MarkovChainConstants.EMPTY_TEXT_ERROR_MESSAGE);
		}
		
		//Prefix Validation
		if(prefix == null || prefix.equals("")){
			throw new InvalidInputException(MarkovChainConstants.PREFIX_MISSING_ERROR_MESSAGE);
		}
		
		//Prefix Validation
		if(suffix == null || suffix.equals("")){
			throw new InvalidInputException(MarkovChainConstants.SUFFIX_MISSING_ERROR_MESSAGE);
		}
		
		//Prefix Validation
		if(prefix.split(" ").length != keySize){
			throw new InvalidInputException("Prefix must be of "+keySize+" word(s)");
		}
		
		//Processing the New Line Character
		textToTransform = textToTransform.replace("\n", " ");
		
		//Processing the Tab Character
		textToTransform = textToTransform.replace("\t", " ");
		
		//Processing the Carriage return Character
		textToTransform = textToTransform.replace("\r", " ");
		
		String[] words= textToTransform.split(" ");
		Map<String, List<String>> dictionary = new HashMap<String, List<String>>();
		
		for(int i=0; i <= (words.length - keySize); i++){
			StringBuilder key = new StringBuilder(words[i]);
			for(int j = i+1; j < i+ keySize; j++){
				key.append(" ");
				key.append(words[j]);
			}
			
			String value = (i + keySize < words.length) ? words[i + keySize] : "";
			
			if (!dictionary.containsKey(key.toString())) {
                List<String> list = new ArrayList<String>();
                list.add(value);
                dictionary.put(key.toString(), list);
            } else {
            	dictionary.get(key.toString()).add(value);
            }
		}
		
		//Check if the prefix and suffix combination is valid
		if(!dictionary.containsKey(prefix)){
			throw new InvalidInputException(MarkovChainConstants.INVALID_PREFIX_ERROR_MESSAGE);
		}
		
		List<String> values = dictionary.get(prefix);
		
		if(!values.contains(suffix))
		{
			throw new InvalidInputException(MarkovChainConstants.INVALID_SUFFIX_ERROR_MESSAGE);
		}
		
		//Start generating the output
		int outputSize = words.length;
		
		//As the string will start from the prefix.
		List<String> output = new ArrayList<String>(Arrays.asList(prefix.split(" ")));
		//Suffix from the input will follow
		output.add(suffix);
		
		
		Random random = new Random();
		while(output.size()< outputSize){				
			StringBuilder currentPrefixBuilder = new StringBuilder();
			// Get the current prefix to use
			for(int i=(output.size()-keySize); i < output.size(); i++ ){
				if(currentPrefixBuilder.length()>0){ 
					currentPrefixBuilder.append(" ");
				}				
				currentPrefixBuilder.append(output.get(i));
			}
			
			String currentPrefix = currentPrefixBuilder.toString();
			
			List<String> suffixList = dictionary.get(currentPrefix);
			if(suffixList == null || suffixList.size()==0){
				break;
			}
			if(suffixList.size()==1){
				if(suffixList.get(0).equals("")){
					break;
				}else{
					output.add(suffixList.get(0));
				}
			}else{
				int suffixIndexToUse = random.nextInt(suffixList.size());	
				output.add(suffixList.get(suffixIndexToUse));
			}
		}
		
		/*
		 *  
		Java 7 implementation
		StringBuilder result = new StringBuilder();		
		for(String str: output){
			if(result.length()>0 && !str.equals("")){ 
				result.append(" ");
			}				
			result.append(str);
		}	
		
		return result.toString();*/
		
		String result = output.stream().collect(Collectors.joining(" "));
		
		return result;
	}
	
	public static void main(String[] args) 
    { 
  
		String[] array = {"one", "two", "three", "four"};
        String result = Arrays.stream(array).collect(Collectors.joining(" "));

        System.out.println(result);
    } 
}
