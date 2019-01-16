package fr.insat.martygrac;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("alarm")
public class AlarmService {
	
	@GET
	@Path("trigger")
	@Produces(MediaType.TEXT_PLAIN)
	public String trigger() {
		return "Alarm triggered.";
	}
	
	@GET
	@Path("stop")
	@Produces(MediaType.TEXT_PLAIN)
	public String stop() {
		return "Alarm stopped.";
	}
}
