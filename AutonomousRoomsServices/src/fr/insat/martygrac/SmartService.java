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

@Path("smart")
public class SmartService {
	
	public static SmartGestion smartGestion = new SmartGestion();
	
	public static Thread mThread = new Thread(smartGestion);

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
		if (ApplicationState.Temperature_int > ApplicationState.Temperature_ext && ApplicationState.Temperature_ext > 22) {
			WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/windows/open");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
			invocationBuilder.get();
		}else {
			WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/windows/close");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
			invocationBuilder.get();
		}
			
	}
	
	
	/*@GET
	@Path("start")
	@Produces(MediaType.TEXT_PLAIN)
	public int start_thread() {
		int result;
		try {
			if(mThread.isAlive()) {
				smartGestion.resume();
			}
			else{
				mThread.start();
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
	}*/
	
	

}
