package fr.insat.martygrac;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
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
	public static int time;
	public static int start_of_the_day=8;
	public static int end_of_the_day=19;
	public static int temp_threshold = 22;
	
	public static final String WINDOWS = "window";
	public static final String ALARM = "alarm";
	public static final String LIGHT = "light";
	public static final String DOORS = "door";
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
	
	public static void updateTime() {
		Date date = new Date();
		time = date.getHours();
	}
	
	public static void notifyStateChanged(ArrayList<String> state) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://mac_maxime:1880/actionMade");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
		String result ="{";
		for(String s : state) {
			switch(s) {
				case WINDOWS: result += "\"window\":\""+windowsState+"\""; 
							break;
				case ALARM: result += "\"alarm\":\""+alarmState+"\""; 
							break;
				case DOORS: result += "\"door\":\""+doorsState+"\""; 
							break;
				case LIGHT: result += "\"light\":\""+lightsState+"\""; 
							break;
				default:
			}
			if (s != state.get(state.size()-1)) {
				result+=",";
			}
		}
		result += "}";
		System.out.println(result);
		javax.ws.rs.core.Response resp = invocationBuilder.post(Entity.entity(String.valueOf(result), MediaType.APPLICATION_JSON));
		System.out.println(resp.toString());
	}
	
	
	
}
