package animallogic.service;

import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import animallogic.exception.InvalidInputException;
import animallogic.request.MarkovChainRequest;
import animallogic.util.MarkovChain;


@Path("/")
public class FileTransformationService {

	
	public FileTransformationService() {
	}
	
	@GET
	@Path("/check")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkMethod(){
		Map<String, Object> outputMap = new TreeMap<String, Object>();
	    outputMap.put("response", "{'server':'I am running'}");
	    return Response.ok(outputMap).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/markovchain")
	@Produces(MediaType.APPLICATION_JSON)
	public Response markovChain(MarkovChainRequest mcr){		
		try {
			Map<String, Object> outputMap = new TreeMap<String, Object>();
			// Transform the file using the Markov Chain Algorithm
			String result = MarkovChain.transformText(mcr.getFile(), mcr.getPrefix(), mcr.getSuffix()); 
			outputMap.put("text", result);	   
			return Response.ok(outputMap).build();
		} catch (InvalidInputException e) {
			System.out.println("In Exception invalid input");
			e.printStackTrace();
			Map<String, Object> outputMap = new TreeMap<String, Object>();
		    outputMap.put("ErrorMessage", e.getMessage());
			return Response.status(400).entity(outputMap).type(MediaType.APPLICATION_JSON).build();
		}			
		catch (Exception e) {
			System.out.println("In the Exception class handling");
			e.printStackTrace();
			return Response.serverError().build();
		}			
	}	
}
