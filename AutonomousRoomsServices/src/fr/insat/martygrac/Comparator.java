package fr.insat.martygrac;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("comparator")
public class Comparator {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getLongueur() {
		return "Bijour";
	}
	
	@GET
	@Path("longueur/{chaine}")
	@Produces(MediaType.TEXT_PLAIN)
	public int getLongueurChaine(@PathParam("chaine") String chaine) {
		return chaine.length();
	}
	
	@POST
	@Path("test")
	@Consumes(MediaType.APPLICATION_JSON)
	public String affiche(String chaine) {
		return chaine;
	}
	
}
 
