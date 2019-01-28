package fr.insat.martygrac;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("lights")
public class LightsService {
	@GET
	@Path("on")
	@Produces(MediaType.TEXT_PLAIN)
	public String turnOn() {
		ApplicationState.lightsState=true;
		return "Lights on.";
	}
	
	@GET
	@Path("off")
	@Produces(MediaType.TEXT_PLAIN)
	public String turnOff() {
		ApplicationState.lightsState=false;
		return "Lights off.";
	}
}
