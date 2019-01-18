package fr.insat.martygrac;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("userinputs")
public class UserInputsHandler {
	
	
	@GET
	@Path("getMinTemp")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMinTemp() {
		return "32";
	}
	
	@GET
	@Path("getMaxTemp")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMaxTemp() {
		return "12";
	}
	
	@GET
	@Path("getMinTime")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMinTime() {
		return "32";
	}
	
	@GET
	@Path("getMaxTime")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMaxTime() {
		return "12";
	}

	
}
