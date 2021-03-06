package fr.insat.martygrac;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Path("presence")
public class PresenceService {
	
	@POST
	@Path("isSomeoneHere")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String isSomeoneHere(String body) {
		//System.out.println(body);
		Pattern p = Pattern.compile("<con>([^<]+)</con>"); 
		Matcher m = p.matcher(body);
		while(m.find())
		{
			ApplicationState.isSomeoneHere = Boolean.valueOf(m.group(1));
		}
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/smart/newPresenceHandler");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
		invocationBuilder.get();
		
		client = ClientBuilder.newClient();
		webTarget = client.target("http://mac_maxime:1880/data/Presence");
		invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
		javax.ws.rs.core.Response resp = invocationBuilder.post(Entity.entity(String.valueOf(ApplicationState.isSomeoneHere), MediaType.TEXT_PLAIN));
		System.out.println(resp.toString());
		
		return "OK";
	}

}
