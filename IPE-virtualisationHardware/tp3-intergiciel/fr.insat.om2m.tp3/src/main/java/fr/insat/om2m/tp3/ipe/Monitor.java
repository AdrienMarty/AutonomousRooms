package fr.insat.om2m.tp3.ipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.philips.lighting.model.PHLight;
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
	public static final String url = "http://mac_maxime:8080/~/in-cse/";
	private static final String originator_admin = "admin:admin";

	/** Point of access of the http server part */
	//public static final String SERVER_POA = "http://mac_maxime:1400/monitor";
	public static final String SERVER_POA = "http://192.168.43.214:8080/AutonomousRoomsServices";
	private static final String ORIGINATOR = "admin:admin";

	private ClientInterface client = new Client();
	private MapperInterface mapper = new Mapper();

	/**
	 * Constructor
	 */
	public Monitor() {
	}

	public void pushState(String lampId, String state, String color, int hue) {
		ContentInstance dataInstance = new ContentInstance();
		dataInstance.setContent(Model.getDataRep("Home", state, color, hue));
		dataInstance.setContentInfo("application/obix:0");
		try {
			Response mResponse = client.create(url+CSE_NAME+ "/Light"+ lampId +  "/DATA",
					mapper.marshal(dataInstance), ORIGINATOR, "4");
			System.out.println(mResponse.toString());
		} catch (IOException e) {
			System.err.println("Error creating the content instance");
			e.printStackTrace();
		}
	}

	/**
	 * Creates all required resources.
	 *
	 * @param application_entity
	 *            - Application ID
	 *
	 * @param cnt
	 *
	 */
	public void createResources(ArrayList<AE> application_entitys, ArrayList<Container> cnts, HashMap<String, Subscription> subscriptions) {

		/*AE lamp_ae = new AE();
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
		ContentInstance description_instance = new ContentInstance();*/
		Mapper mapper = new Mapper();
		try{
			for (AE application_entity : application_entitys){
				Response mResponse = client.create(url,mapper.marshal(application_entity), originator_admin,"2");
				System.out.println(mResponse.toString());
				for (Container cont : cnts){
					//System.out.println(mapper.marshal(cont));
				/*mResponse = client.create(url+CSE_NAME+"/"+application_entity.getAppName() + "/",
						mapper.marshal(cont), originator_admin,"3");*/
					mResponse = client.create(url+CSE_NAME+"/"+application_entity.getName(),
							mapper.marshal(cont), originator_admin,"3");
					System.out.println(mResponse.toString());
				}
				mResponse = client.create(url + CSE_NAME+ "/" +application_entity.getName() + "/DATA",
						mapper.marshal(subscriptions.get(application_entity.getName())), originator_admin,"23");
				System.out.println(mResponse.toString());

			}


			/*mResponse = client.create(url+CSE_NAME+ "/Light"+l.getIdentifier() + "/DESCRIPTOR",
					mapper.marshal(description_instance), originator_admin,"4");
			System.out.println(mResponse.toString());

			mResponse = client.create(url + CSE_NAME +"/Light"+l.getIdentifier(),
					mapper.marshal(data), originator_admin,"3");
			System.out.println(mResponse.toString());*/

			/*Subscription sub = new Subscription();
			sub.getNotificationURI().add(SERVER_POA);
			sub.setName("SUB");
			mResponse = client.create(url+CSE_NAME+ "/Light"+l.getIdentifier() + "/DATA",
					mapper.marshal(sub), originator_admin,"23");*/
		}catch (Exception e){
			e.printStackTrace();
		}

	}

}
