package fr.insat.martygrac;

import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.Subscription;

import fr.insat.om2m.tp3.client.Client;
import fr.insat.om2m.tp3.client.Response;
import fr.insat.om2m.tp3.ipe.Model;
import fr.insat.om2m.tp3.ipe.Monitor;
import fr.insat.om2m.tp3.mapper.Mapper;

@Path("init")
public class Init {
	String APP_ID = "Autonomous_rooms";
	/** Csebase id */
	public final static String CSEID = "in-cse";
	/** CseBase Name */
	public final static String CSE_NAME = "in-name";
	/** Generic create method name */
	public final static String METHOD = "CREATE";
	/** Data container id */
	public final static String DATA = "DATA";
	/** Descriptor container id */
	public final static String DESC = "DESCRIPTOR";
	/** Indicate if the program is connected to Hue bridge */
	private static boolean connected = false;

	public final static String APPID = "HUE-app";
	public static final String url = "http://192.168.1.15:8080/~/in-cse/";
	private static final String originator_admin = "admin:admin";

	/** Point of access of the http server part */
	public static final String SERVER_POA = "http://localhost:8080/AutonomousRoomServices/";
	public static final String EXTENSION_PRESENCE = "/presence/isSomeoneHere";
	public static final String EXTENSION_TEMPERATURE_EXT = "/temperature/getExtTemp";
	public static final String EXTENSION_TEMPERATURE_INT = "/temperature/getIntTemp";
	
	private static final String ORIGINATOR = "admin:admin";
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public int init_services() {
		Client client = new Client();
		Mapper mapper = new Mapper();
		Response mResponse;
		try {
			Subscription sub = new Subscription();
			sub.getNotificationURI().add(SERVER_POA+EXTENSION_PRESENCE);
			sub.setName("SUB");
			mResponse = client.create(url + CSE_NAME + "/DATA",
					mapper.marshal(sub), originator_admin,"23");
			Subscription sub1 = new Subscription();
			sub.getNotificationURI().add(SERVER_POA+EXTENSION_TEMPERATURE_INT);
			sub.setName("SUB");
			mResponse = client.create(url + CSE_NAME + "/DATA",
					mapper.marshal(sub1), originator_admin,"23");
			Subscription sub2 = new Subscription();
			sub.getNotificationURI().add(SERVER_POA+EXTENSION_TEMPERATURE_EXT);
			sub.setName("SUB");
			mResponse = client.create(url + CSE_NAME + "/DATA",
					mapper.marshal(sub2), originator_admin,"23");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
