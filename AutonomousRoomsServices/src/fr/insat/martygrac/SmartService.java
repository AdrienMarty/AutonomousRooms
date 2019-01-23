package fr.insat.martygrac;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import fr.insat.martygrac.Runnables.*;
import java.lang.Thread;
import java.util.ArrayList;

@Path("smart")
public class SmartService {
	
	public static SmartGestion smartGestion = new SmartGestion();
	
	public static Thread timeWatcherThread = new Thread(smartGestion);

	@GET
	@Path("test")
	public void test() {
		System.out.println("Temp ext = "+ApplicationState.Temperature_ext);
		System.out.println("Temp int = "+ApplicationState.Temperature_int);
		System.out.println("Someone here = "+ApplicationState.isSomeoneHere);
	}
	
	@GET
	@Path("newTempHandler")
	public void newTempHandler() {
		Client client = ClientBuilder.newClient();
		System.out.println("Temp ext = "+ApplicationState.Temperature_ext);
		System.out.println("Temp int = "+ApplicationState.Temperature_int);
		System.out.println("Someone here = "+ApplicationState.isSomeoneHere);
		//TODO Changment du 22 en un seuil mémorisé
		if(ApplicationState.time >= ApplicationState.start_of_the_day && ApplicationState.time <= ApplicationState.end_of_the_day) {
			if (ApplicationState.Temperature_int > ApplicationState.Temperature_ext && ApplicationState.Temperature_ext > ApplicationState.temp_threshold ) {
				if (!ApplicationState.windowsState) {
					WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/windows/open");
					Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
					invocationBuilder.get();	
					ArrayList<String> var = new ArrayList<String>(); 
					var.add(ApplicationState.WINDOWS);
					ApplicationState.notifyStateChanged(var);
				}
			}else {
				if(ApplicationState.windowsState) {
					WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/windows/close");
					Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
					invocationBuilder.get();
					ArrayList<String> var = new ArrayList<String>(); 
					var.add(ApplicationState.WINDOWS);
					ApplicationState.notifyStateChanged(var);
				}
			}
		}
			
	}
	
	@GET
	@Path("newPresenceHandler")
	public void onPresenceDetected() {
		ApplicationState.updateTime();
		Client client = ClientBuilder.newClient();
		if(ApplicationState.time >= ApplicationState.start_of_the_day && ApplicationState.time <= ApplicationState.end_of_the_day) {
			if(!ApplicationState.lightsState && ApplicationState.isSomeoneHere) {
				WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/lights/on");
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
				invocationBuilder.get();	
				ArrayList<String> var = new ArrayList<String>(); 
				var.add(ApplicationState.LIGHT);
				ApplicationState.notifyStateChanged(var);
			}
			else if(ApplicationState.lightsState && !ApplicationState.isSomeoneHere) {
				WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/lights/off");
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
				invocationBuilder.get();	
				ArrayList<String> var = new ArrayList<String>(); 
				var.add(ApplicationState.LIGHT);
				ApplicationState.notifyStateChanged(var);
			}
		}else {
			if(!ApplicationState.alarmState && ApplicationState.isSomeoneHere) {
				WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/alarm/trigger");
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
				invocationBuilder.get();	
				ArrayList<String> var = new ArrayList<String>(); 
				var.add(ApplicationState.ALARM);
				ApplicationState.notifyStateChanged(var);
			}
			else if(ApplicationState.alarmState && !ApplicationState.isSomeoneHere) {
				WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/alarm/stop");
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
				invocationBuilder.get();	
				ArrayList<String> var = new ArrayList<String>(); 
				var.add(ApplicationState.ALARM);
				ApplicationState.notifyStateChanged(var);
			}
		}
	}
	
	
	@GET
	@Path("start")
	@Produces(MediaType.TEXT_PLAIN)
	public int start_thread() {
		int result;
		try {
			if(timeWatcherThread.isAlive()) {
				smartGestion.resume();
			}
			else{
				timeWatcherThread.start();
			}
			result = 0;
		}catch (Exception e) {
			e.printStackTrace();
			result=-1;
		}
		return result;
	}
	
	@GET
	@Path("stop")
	@Produces(MediaType.TEXT_PLAIN)
	public int stop_thread() {
		int result;
		try {
			smartGestion.pause();
			result=0;
		}catch (Exception e) {
			e.printStackTrace();
			result=-1;
		}
		return result;
	}
	
	

}
