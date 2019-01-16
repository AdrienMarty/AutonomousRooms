package fr.insat.martygrac;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("presence")
public class PresenceService {
	
	@GET
	@Path("isSomeoneHere")
	@Produces(MediaType.TEXT_PLAIN)
	public String isSomeoneHere() {
		return "Someone is here";
	}
	

}
