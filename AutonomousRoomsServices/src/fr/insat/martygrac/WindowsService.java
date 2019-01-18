package fr.insat.martygrac;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("windows")
public class WindowsService {
	
	@GET
	@Path("open")
	@Produces(MediaType.TEXT_PLAIN)
	public String open() {
		System.out.println("Windows opened");
		return "Windows opened.";
	}
	
	@GET
	@Path("close")
	@Produces(MediaType.TEXT_PLAIN)
	public String close() {
		System.out.println("Widowns closed");
		return "Windows closed.";
	}
}
