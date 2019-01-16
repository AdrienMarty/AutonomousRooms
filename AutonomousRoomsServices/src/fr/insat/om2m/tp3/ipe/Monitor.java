package fr.insat.om2m.tp3.ipe;

import java.io.IOException;
import java.util.List;

import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;

import fr.insat.om2m.tp3.client.Client;
import fr.insat.om2m.tp3.client.ClientInterface;
import fr.insat.om2m.tp3.client.Response;
import fr.insat.om2m.tp3.mapper.Mapper;
import fr.insat.om2m.tp3.mapper.MapperInterface;
import org.eclipse.om2m.commons.resource.Subscription;

/**
 * The monitor will retrieve information from the devices and push them to the
 * middleware
 *
 */
public class Monitor {

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
	public static final String url = "http://virtualmachine:8080/~/in-cse/";
	private static final String originator_admin = "admin:admin";

	/** Point of access of the http server part */
	public static final String SERVER_POA = "http://virtualmachine:1400/monitor";
	private static final String ORIGINATOR = "admin:admin";
	private ClientInterface client = new Client();
	private MapperInterface mapper = new Mapper();

	/**
	 * Constructor
	 */
	public Monitor() {
		
	}


	/**
	 * Creates all required resources.
	 * 
	 * @param lights
	 *            - Application ID
	 */
	public void createResources() {

		AE lamp_ae = new AE();
		lamp_ae.setAppID(APPID);
		lamp_ae.setRequestReachability(false);
		lamp_ae.getPointOfAccess().add(SERVER_POA);
		lamp_ae.getLabels().add("Type/Lamp");
		lamp_ae.getLabels().add("Location/INSA");
		lamp_ae.getLabels().add("Manufacturer/PhilipsHue");
		Container descriptor = new Container();
		descriptor.setName("DESCRIPTOR");
		Container data = new Container();
		data.setName("DATA");
		ContentInstance description_instance = new ContentInstance();
		Mapper mapper = new Mapper();
		try{
			lamp_ae.setName("Light"+1);
			Response mResponse = client.create(url,mapper.marshal(lamp_ae), originator_admin,"2");
			System.out.println(mResponse.toString());

			mResponse = client.create(url + CSE_NAME+ "/Light"+1),
					mapper.marshal(descriptor), originator_admin,"3");
			System.out.println(mResponse.toString());

			description_instance.setName("Description");
			description_instance.setContentInfo("text/plain");
			description_instance.setContent(Model.getDescriptorRep(CSE_NAME,APPID,"Living room"));
			mResponse = client.create(url+CSE_NAME+ "/Light"+1+ "/DESCRIPTOR",
					mapper.marshal(description_instance), originator_admin,"4");
			System.out.println(mResponse.toString());

			mResponse = client.create(url + CSE_NAME +"/Light"+1),
					mapper.marshal(data), originator_admin,"3");
			System.out.println(mResponse.toString());

			Subscription sub = new Subscription();
			sub.getNotificationURI().add(SERVER_POA);
			sub.setName("SUB");
			mResponse = client.create(url+CSE_NAME+ "/Light"+1+ "/DATA",
					mapper.marshal(sub), originator_admin,"23");

			System.out.println(mResponse.toString());
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
