package network;

import java.util.ArrayList;
import core.*;

public class Network {
	public static int mode = 1;
	public static Client client = new Client();
	
	public static String name;
	public static String serverIP;
	public static int serverPort;
	
	public static String ssid;
	public static int uid;
	public static String nickname;
	
	public static int paperCount;
	public static ArrayList<Paper> paper = new ArrayList<Paper>();
	
	public static int layerCount;
}
