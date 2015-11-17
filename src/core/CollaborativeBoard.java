package core;

import javax.swing.JFrame;
import gui.*;

public class CollaborativeBoard {
	
	public static String AppName = "";//"Collaborative Workspace";
	public static String AppVersion = "";//"1.0.0";
	
	public static String Nickname = "nickname";
	public static String ServerIPDefault = "127.0.0.1";
	public static int ServerPortDefault = 7777;

	public static Window window;
	
	public static void main(String[] args) {
		
		/*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	window = new Window(AppName + " " + AppVersion, 1280, 720);
            }
		});*/
		window = new Window(AppName + " " + AppVersion, 1280, 720);
		//new network.Client("127.0.0.1", 7777);
		//new Console().start();
		
	}

}