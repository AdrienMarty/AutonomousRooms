package fr.insat.martygrac;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("userinputs")
public class UserInputsHandler {
	
	
	@GET
	@Path("getMaxTemp")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMaxTemp() {
		return String.valueOf(ApplicationState.temp_threshold);
	}
	
	@GET
	@Path("getMinTime")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMinTime() {
		return String.valueOf(ApplicationState.start_of_the_day);
	}
	
	@GET
	@Path("getMaxTime")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMaxTime() {
		return String.valueOf(ApplicationState.end_of_the_day);
	}
	
	@POST
	@Path("setMaxTemp")
	@Consumes(MediaType.TEXT_PLAIN)
	public String setMaxTemp(String body) {
		ApplicationState.temp_threshold = Integer.valueOf(body);
		System.out.println("Temperature threshold changed to: "+ ApplicationState.temp_threshold);
		return "OK";
	}
	
	@POST
	@Path("setMinTime")
	@Consumes(MediaType.TEXT_PLAIN)
	public String setMinTime(String body) {
		ApplicationState.start_of_the_day = Integer.valueOf(body);
		System.out.println("Start of the day changed to: "+ ApplicationState.start_of_the_day);
		return "OK";
	}
	
	@POST
	@Path("setMaxTime")
	@Consumes(MediaType.TEXT_PLAIN)
	public String setMaxTime(String body) {
		ApplicationState.end_of_the_day = Integer.valueOf(body);
		System.out.println("End of the day changed to: "+ ApplicationState.end_of_the_day);
		return "OK";
	}

	
}
