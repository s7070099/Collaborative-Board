package network;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import core.*;

public class Network {
	public static int mode = 1;
	public static Client client = new Client();
	public static Server server = new Server();
	
	public static String name;
	public static String serverIP;
	public static int serverPort = 9999;
	public static int serverPaper = 32;
	public static int serverUser = 12;
	public static String serverDirectory = "save/default";
	
	public static String ssid;
	public static int uid;
	public static String nickname;
	
	public static int paperCount;
	public static ArrayList<Paper> paper = new ArrayList<Paper>();
	
	public static int layerCount;
	
	public static String cmdHeader(){
		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("y-MM-dd hh:mm:ss");
		return "["+formatter.format(now.getTime())+"] ";
	}
	
	public static void print(String text){
		if(CollaborativeBoard.window.windowServerConsole != null){
			CollaborativeBoard.window.windowServerConsole.output.append(cmdHeader()+text+"\n");
		}
	}
	
}