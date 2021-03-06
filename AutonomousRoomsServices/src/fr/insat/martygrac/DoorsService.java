package fr.insat.martygrac;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("doors")
public class DoorsService {
	
	@GET
	@Path("open")
	@Produces(MediaType.TEXT_PLAIN)
	public String openDoors() {
		ApplicationState.doorsState = true;
		return "Doors opened.";
	}
	
	@GET
	@Path("close")
	@Produces(MediaType.TEXT_PLAIN)
	public String closeDoors() {
		ApplicationState.doorsState = false;
		return "Doors closed.";
	}
}
