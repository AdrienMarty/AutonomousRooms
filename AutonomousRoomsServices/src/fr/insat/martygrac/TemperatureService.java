package fr.insat.martygrac;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;

import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.resource.*;
import org.eclipse.om2m.commons.resource.Notification.NotificationEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import fr.insat.om2m.tp3.mapper.Mapper;
import fr.insat.om2m.tp3.util.NotificationUtil;
import fr.insat.om2m.tp3.util.NotificationUtil.NotObixContentException;
import obix.Obj;
import obix.io.ObixDecoder;

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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Response;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

@Path("temperature")
public class TemperatureService {
	
	@POST
	@Path("getExtTemp")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces (MediaType.TEXT_PLAIN)
	public String getOutdoor(String body)  {
		//System.out.println(body);
		Pattern p = Pattern.compile("<con>([^<]+)</con>"); 
		Matcher m = p.matcher(body);
		while(m.find())
		{
			ApplicationState.Temperature_ext = Integer.valueOf(m.group(1));
		}
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/smart/newTempHandler");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
		invocationBuilder.get();
		
		client = ClientBuilder.newClient();
		webTarget = client.target("http://mac_maxime:1880/data/Temp_ext");
		invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
		javax.ws.rs.core.Response resp = invocationBuilder.post(Entity.entity(String.valueOf(ApplicationState.Temperature_ext), MediaType.TEXT_PLAIN));
		System.out.println(resp.toString());
		return "OK";
	}
	
	@POST
	@Path("getIntTemp")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces (MediaType.TEXT_PLAIN)
	public String getIndoor(String body) {
		//System.out.println(body);
		Pattern p = Pattern.compile("<con>([^<]+)</con>"); 
		Matcher m = p.matcher(body);
		while(m.find())
		{
			ApplicationState.Temperature_int = Integer.valueOf(m.group(1));
		}
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/AutonomousRoomsServices/smart/newTempHandler");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
		invocationBuilder.get();
		
		
		client = ClientBuilder.newClient();
		webTarget = client.target("http://mac_maxime:1880/data/Temp_int");
		invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
		javax.ws.rs.core.Response resp = invocationBuilder.post(Entity.entity(String.valueOf(ApplicationState.Temperature_int), MediaType.TEXT_PLAIN));
		System.out.println(resp.toString());
		return "OK";
	}
}
