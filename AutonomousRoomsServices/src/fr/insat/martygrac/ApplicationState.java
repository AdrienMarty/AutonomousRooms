package fr.insat.martygrac;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("appstate")
public class ApplicationState {
	public static int Temperature_ext = 0;
	public static int Temperature_int = 0;
	public static boolean isSomeoneHere = false;
	public static int Max_temperature = 0;
	public static int StartofTheDay;
	public static boolean windowsState;
	public static boolean doorsState;
	public static boolean lightsState;
	public static boolean alarmState;
	
	@GET
	@Path("getLastTempExt")
	@Produces(MediaType.TEXT_PLAIN)
	public static int getTemperature_ext() {
		return Temperature_ext;
	}
	
	@GET
	@Path("getLastTempInt")
	@Produces(MediaType.TEXT_PLAIN)
	public static int getTemperature_int() {
		return Temperature_int;
	}
	
	@GET
	@Path("getLastisSomeonHere")
	@Produces(MediaType.TEXT_PLAIN)
	public static boolean isSomeoneHere() {
		return isSomeoneHere;
	}
	
	
	@GET 
	@Path("/path")
	public void notifyStateChanged() {
		
	}
	
	
	
}
