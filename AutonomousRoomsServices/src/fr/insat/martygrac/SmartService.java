package fr.insat.martygrac;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import fr.insat.martygrac.Runnables.*;
import java.lang.Thread;

@Path("smart")
public class SmartService {
	
	public static SmartGestion smartGestion = new SmartGestion();
	
	public static Thread mThread = new Thread(smartGestion);

	@GET
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
	}
	
	

}
