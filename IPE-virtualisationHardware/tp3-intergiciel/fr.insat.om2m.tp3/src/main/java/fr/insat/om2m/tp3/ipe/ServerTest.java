package fr.insat.om2m.tp3.ipe;

import fr.insat.om2m.tp3.client.Client;
import fr.insat.om2m.tp3.client.Response;
import fr.insat.om2m.tp3.mapper.Mapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.om2m.commons.resource.Subscription;

import static fr.insat.om2m.tp3.ipe.Monitor.SERVER_POA;
import static fr.insat.om2m.tp3.ipe.Monitor.url;

public class ServerTest {
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

        Server server = IpeServer.createServer();
        System.out.println("Starting the server on: " + server.getState());
        server.join();
    }
}
