package fr.insat.martygrac.Runnables;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.sun.org.apache.bcel.internal.generic.AALOAD;

import fr.insat.martygrac.ApplicationState;

public class SmartGestion implements Runnable {
	
	
	
	
	public volatile boolean threadSuspended = false;

	public void pause() {
		threadSuspended = true;
	}
	
	public void resume() {
		threadSuspended = false;
	}
	
	@Override
	public void run() {
		Client client = ClientBuilder.newClient();
		boolean allClosed = ApplicationState.doorsState && ApplicationState.lightsState && ApplicationState.windowsState;
		while(true) {
			try {
                Thread.sleep(5000);
                if (threadSuspended) {
                    synchronized(this) {
                        while (threadSuspended);
                            //wait();
                    }
                }
            } catch (InterruptedException e){
            	e.printStackTrace();
            }
			ApplicationState.updateTime();
			allClosed = !(ApplicationState.doorsState || ApplicationState.lightsState || ApplicationState.windowsState);
			if(ApplicationState.time >= ApplicationState.end_of_the_day && !allClosed) {
				
				WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/doors/close");
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
				invocationBuilder.get();	
				
				webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/windows/close");
				invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
				invocationBuilder.get();
				
				webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/lights/off");
				invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
				invocationBuilder.get();
				ArrayList<String> var = new ArrayList<String>(); 
				
				if(ApplicationState.isSomeoneHere) {
					webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/alarm/trigger");
					invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
					invocationBuilder.get();
					var.add(ApplicationState.ALARM);
				}
				
				allClosed = true;
				
				var.add(ApplicationState.DOORS);
				var.add(ApplicationState.WINDOWS);
				var.add(ApplicationState.LIGHT);
				ApplicationState.notifyStateChanged(var);
			}
			else if (ApplicationState.time >= ApplicationState.start_of_the_day && ApplicationState.time <= ApplicationState.end_of_the_day && !ApplicationState.doorsState) {
				WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/doors/open");
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
				invocationBuilder.get();
				ArrayList<String> var = new ArrayList<String>(); 
				var.add(ApplicationState.DOORS);
				ApplicationState.notifyStateChanged(var);
			}
			//System.out.println("Tourne");
		}
	}
}
