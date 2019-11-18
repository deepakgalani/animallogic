package animallogic;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import animallogic.constants.MarkovChainConstants;
import animallogic.exception.InvalidInputException;
import animallogic.util.MarkovChain;

public class TestMarkovChain {

	public TestMarkovChain() {
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void setUp() throws Exception {
	}
	
	@Test(expected=InvalidInputException.class)
	public void testAllNullInputs() throws InvalidInputException {
		try
		{
			String result = MarkovChain.transformText(null, null, null);
			System.out.println("Result: "+result);
			fail("An InvalidInputException was expected");
		} catch(InvalidInputException ex){
			System.out.println("Exception Message: "+ex.getMessage());
			if(ex.getMessage().equals(MarkovChainConstants.EMPTY_TEXT_ERROR_MESSAGE)){
				throw ex;
			}
			fail("Expected Invalid Input Exception with message:"+MarkovChainConstants.EMPTY_TEXT_ERROR_MESSAGE+" but received: "+ex.getMessage());
		}
	}
	
	@Test(expected=InvalidInputException.class)
	public void testNullPrefix() throws InvalidInputException {
		try
		{
			String result = MarkovChain.transformText("now he is gone she said he is gone for good but now he", 
					null, "she");
			System.out.println("Result: "+result);
			fail("An InvalidInputException was expected");
		} catch(InvalidInputException ex){
			System.out.println("Exception Message: "+ex.getMessage());
			if(ex.getMessage().equals(MarkovChainConstants.PREFIX_MISSING_ERROR_MESSAGE)){
				throw ex;
			}
			fail("Expected Invalid Input Exception with message:"+MarkovChainConstants.PREFIX_MISSING_ERROR_MESSAGE+" but received: "+ex.getMessage());
		}
	}
	
	@Test(expected=InvalidInputException.class)
	public void testNullSuffix() throws InvalidInputException {
		try
		{
			String result = MarkovChain.transformText("now he is gone she said he is gone for good but now he", 
					"gone", null);
			System.out.println("Result: "+result);
			fail("An InvalidInputException was expected");
		} catch(InvalidInputException ex){
			System.out.println("Exception Message: "+ex.getMessage());
			if(ex.getMessage().equals(MarkovChainConstants.SUFFIX_MISSING_ERROR_MESSAGE)){
				throw ex;
			}
			fail("Expected Invalid Input Exception with message:"+MarkovChainConstants.SUFFIX_MISSING_ERROR_MESSAGE+" but received: "+ex.getMessage());
		}
	}

	@Test(expected=InvalidInputException.class)
	public void testInValidPrefixWordCount() throws InvalidInputException {
		try
		{
			String result = MarkovChain.transformText("now he is gone she said he is gone for good but now he", 
					"gone", "she");
			System.out.println("Result: "+result);
			fail("An InvalidInputException was expected");
		} catch(InvalidInputException ex){
			System.out.println("Exception Message: "+ex.getMessage());
			if(ex.getMessage().equals("Prefix must be of "+MarkovChain.keySize+" word(s)")){
				throw ex;
			}
			fail("Expected Invalid Input Exception with message:Prefix must be of "+MarkovChain.keySize+" word(s) but received: "+ex.getMessage());
		}
	}
	
	@Test(expected=InvalidInputException.class)
	public void testInValidPrefix() throws InvalidInputException {
		try
		{
			String result = MarkovChain.transformText("now he is gone she said he is gone for good but now he", 
					"gone with", "she");
			System.out.println("Result: "+result);
			fail("An InvalidInputException was expected");
		} catch(InvalidInputException ex){
			System.out.println("Exception Message: "+ex.getMessage());
			if(ex.getMessage().equals(MarkovChainConstants.INVALID_PREFIX_ERROR_MESSAGE)){
				throw ex;
			}
			fail("Expected Invalid Input Exception with message:"+MarkovChainConstants.INVALID_PREFIX_ERROR_MESSAGE+" but received: "+ex.getMessage());
		}
	}
	
	@Test(expected=InvalidInputException.class)
	public void testInValidSuffix() throws InvalidInputException {
		try
		{
			String result = MarkovChain.transformText("now he is gone she said he is gone for good but now he",
					"gone for", "she");
			System.out.println("Result: "+result);
			fail("An InvalidInputException was expected");
		} catch(InvalidInputException ex){
			System.out.println("Exception Message: "+ex.getMessage());
			if(ex.getMessage().equals(MarkovChainConstants.INVALID_SUFFIX_ERROR_MESSAGE)){
				throw ex;
			}
			fail("Expected Invalid Input Exception with message:"+MarkovChainConstants.INVALID_SUFFIX_ERROR_MESSAGE+" but received: "+ex.getMessage());
		}
	}
	
	@Test(expected=InvalidInputException.class)
	public void testEmptyText() throws InvalidInputException {
		try
		{
			String result = MarkovChain.transformText("",
					"gone for", "she");
			System.out.println("Result: "+result);			
		} catch(InvalidInputException ex){
			System.out.println("Exception Message: "+ex.getMessage());
			if(ex.getMessage().equals(MarkovChainConstants.EMPTY_TEXT_ERROR_MESSAGE)){
				throw ex;
			}
			fail("Expected Invalid Input Exception with message:"+MarkovChainConstants.EMPTY_TEXT_ERROR_MESSAGE+" but received: "+ex.getMessage());
		}
	}
	
	@Test
	public void testValidInputs(){
		try
		{
			String result = MarkovChain.transformText("now he is gone she said he is gone for good but now he",
					"gone for", "good");
			System.out.println("Result: "+result);			
		} catch(InvalidInputException ex){
			ex.printStackTrace();			
			fail("Exception  "+ex.getMessage());
		}
	}
	
	@Test
	public void testExactResults(){
		try
		{
			String result = MarkovChain.transformText("now he is gone she said he is gone for good 2 months",
					"good 2", "months");
			System.out.println("Result: "+result);			
			Assert.assertTrue("good 2 months".equals(result));
		} catch(InvalidInputException ex){
			ex.printStackTrace();			
			fail("Exception  "+ex.getMessage());
		}
	}
	
	
	@Test
	public void testEscapeCharactersResults() throws InvalidInputException{
		try
		{
			String result = MarkovChain.transformText(" but now he went	 \\ \n\r	@@@@@############",
					"but now", "he");
			System.out.println("Result: "+result);			
		} catch(InvalidInputException ex){
			System.out.println("Exception Message: "+ex.getMessage());
			fail("Expection "+ex.getMessage());
		}
	}
	
	
	@Test
	public void testLargeTextResults(){
		try
		{
			String inputText = ""; 
			URL url = getClass().getResource("large_text_file_input.txt");
			inputText = new String(Files.readAllBytes(Paths.get(url.toURI())));
			String result = MarkovChain.transformText(inputText, "very tired", "of");
			System.out.println("Result: "+result);						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(InvalidInputException ex){
			System.out.println("Exception Message: "+ex.getMessage());
			ex.printStackTrace();
			fail("Expection occured ");
		} catch (URISyntaxException ex) {
			ex.printStackTrace();
			System.out.println("Exception Message: "+ex.getMessage());			
			fail("Expection occured ");
		}
	}
	
	@Test
	public void testBeginingOfTheResult(){
		try
		{
			String result = MarkovChain.transformText("now he is gone she said he is gone for good but now he",
					"is gone", "she");
			System.out.println("Result: "+result);			
			Assert.assertTrue(result.startsWith("is gone she"));
		} catch(InvalidInputException ex){
			ex.printStackTrace();			
			fail("Exception  "+ex.getMessage());
		}
	}
	
	@Test
	public void testMinimumOccurencesInResult(){
		try
		{
			String result = MarkovChain.transformText("With Markov Chain I want to test minimum occurrences, I want atleast 2, Lets test what I want",
					"I want", "to");
			System.out.println("Result: "+result);		
			Pattern p = Pattern.compile("I want");
			Matcher m = p.matcher( result );
			int counter=0;
			while (m.find()) {
			    counter++;
			}
			
			System.out.println("counter: "+counter);
			Assert.assertTrue(counter >= 2);
		} catch(InvalidInputException ex){
			ex.printStackTrace();			
			fail("Exception  "+ex.getMessage());
		}
	}
	
	@Test
	public void testLessOrEqualOccurencesInResult(){
		try
		{
			String result = MarkovChain.transformText("With Markov Chain I want to test exact occurrences of I want so I want exactly 3",
					"I want", "to");
			System.out.println("Result: "+result);		
			Pattern p = Pattern.compile("I want");
			Matcher m = p.matcher( result );
			int counter=0;
			while (m.find()) {
			    counter++;
			}
			
			System.out.println("counter: "+counter);
			Assert.assertTrue(counter <= 4);
		} catch(InvalidInputException ex){
			ex.printStackTrace();			
			fail("Exception  "+ex.getMessage());
		}
	}
	
	@Test
	public void testEndsWithInResult(){
		try
		{
			String result = MarkovChain.transformText("This time around I will repeat this time around but whatever happens before I want to see if I can test the ends with scenario",
					"happens before", "I");
			System.out.println("Result: "+result);		
			Assert.assertTrue(result.endsWith("the ends with scenario"));
		} catch(InvalidInputException ex){
			ex.printStackTrace();			
			fail("Exception  "+ex.getMessage());
		}
	}
}
