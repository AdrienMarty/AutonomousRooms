package fr.insat.om2m.tp3.ipe;

import java.math.BigInteger;
import java.util.*;

import fr.insat.om2m.tp3.client.Client;
import fr.insat.om2m.tp3.client.Response;
import fr.insat.om2m.tp3.mapper.Mapper;
import org.eclipse.jetty.server.Server;


import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.Subscription;

import javax.jws.WebParam;

import static fr.insat.om2m.tp3.ipe.Monitor.SERVER_POA;
import static fr.insat.om2m.tp3.ipe.Monitor.url;


public class Main {
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
	//private static final String url = "http://192.168.1.147:8080/~/in-cse/";
	private static final String originator_admin = "admin:admin";
	public static final String EXTENSION_PRESENCE = "/presence/isSomeoneHere";
	public static final String EXTENSION_TEMPERATURE_EXT = "/temperature/getExtTemp";
	public static final String EXTENSION_TEMPERATURE_INT = "/temperature/getIntTemp";

	public static boolean isConnected(){
		synchronized (Main.class) {
			return connected;
		}
	}

	public static void setConnected(boolean state){
		synchronized (Main.class) {
			connected = state;
		}
	}

	public static void main(String[] args) throws Exception {

		Client client = new Client();
		System.out.println("Loading properties from file...");


		Monitor monitor = new Monitor();

		ArrayList<AE> app_list = new ArrayList<AE>();

		AE temp_in = new AE();
		temp_in.setAppID(APPID);
		temp_in.setName("Temp_in");
		temp_in.setRequestReachability(true);
		//temp_in.getPointOfAccess().add(SERVER_POA);
		temp_in.getLabels().add("Type/TemperatureSensor");
		temp_in.getLabels().add("Location/INSA");
		temp_in.getLabels().add("Manufacturer/");
		app_list.add(temp_in);

		AE temp_ext = new AE();
		temp_ext.setAppID(APPID);
		temp_ext.setName("Temp_ext");
		temp_ext.setRequestReachability(true);
		//temp_ext.getPointOfAccess().add(SERVER_POA);
		temp_ext.getLabels().add("Type/TemperatureSensor");
		temp_ext.getLabels().add("Location/INSA");
		temp_ext.getLabels().add("Manufacturer/");
		app_list.add(temp_ext);

		AE presence = new AE();
		presence.setAppID(APPID);
		presence.setName("Presence");
		presence.setRequestReachability(true);
		//presence.getPointOfAccess().add(SERVER_POA);
		presence.getLabels().add("Type/PresenceSensor");
		presence.getLabels().add("Location/INSA");
		presence.getLabels().add("Manufacturer/");
		app_list.add(presence);

		ArrayList<Container> cnts = new ArrayList<Container>();
		Container descriptor = new Container();
		descriptor.setName("DESCRIPTOR");
		Container data = new Container();
		data.setName("DATA");
		cnts.add(descriptor);
		cnts.add(data);

		HashMap<String, Subscription> subscriptions = new HashMap<String, Subscription>();

		Subscription sub = new Subscription();
		sub.getNotificationURI().add(SERVER_POA+EXTENSION_PRESENCE);
		sub.setName("SUB");
		sub.setNotificationContentType(BigInteger.valueOf(2));
		subscriptions.put("Presence",sub);

		Subscription sub1 = new Subscription();
		sub1.getNotificationURI().add(SERVER_POA+EXTENSION_TEMPERATURE_INT);
		sub1.setName("SUB");
		sub1.setNotificationContentType(BigInteger.valueOf(2));
		subscriptions.put("Temp_in",sub1);

		Subscription sub2 = new Subscription();
		sub2.getNotificationURI().add(SERVER_POA+EXTENSION_TEMPERATURE_EXT);
		sub2.setName("SUB");
		sub2.setNotificationContentType(BigInteger.valueOf(2));
		subscriptions.put("Temp_ext",sub2);

		monitor.createResources(app_list, cnts,subscriptions);



/*
		Server server = IpeServer.createServer();
		System.out.println("Starting the server on: " + server.getState());
		server.join();*/




		// Example: turn on a lamp 1
		//		 HueUtil.updateDevice("1", null, null, null, true);

		
		// --------------------- //
		// To launch server part //
		// --------------------- //
		
		// Creating the IPE Server on :1400/monitor
		//		Server server = IpeServer.createServer();
		//		System.out.println("Starting the server on: " + server.getState());
		//		server.join();
	}



}
