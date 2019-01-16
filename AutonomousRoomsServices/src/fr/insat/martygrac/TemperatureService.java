package fr.insat.martygrac;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("temperature")
public class TemperatureService {
	
	@GET
	@Path("getExtTemp")
	@Produces(MediaType.TEXT_PLAIN)
	public String getOutdoor() {
		return "Outdoor Temperature : 12";
	}
	
	@GET
	@Path("getIntTemp")
	@Produces(MediaType.TEXT_PLAIN)
	public String getIndoor() {
		return "Indoor Temperature : 24";
	}
}
