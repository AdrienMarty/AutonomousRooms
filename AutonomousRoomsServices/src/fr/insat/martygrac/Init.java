package fr.insat.martygrac;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.Subscription;


import fr.insat.om2m.tp3.client.Response;
import fr.insat.om2m.tp3.ipe.Model;
import fr.insat.om2m.tp3.ipe.Monitor;
import fr.insat.om2m.tp3.mapper.Mapper;

@Path("init")
public class Init {
	String APP_ID = "Autonomous_rooms";
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public int init_services() {
		Monitor monitor = new Monitor();
		monitor.createResources();
		return 0;
	}
}
