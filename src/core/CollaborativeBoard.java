package core;

import javax.swing.JFrame;

public class CollaborativeBoard {
	
	private static String AppName = "Collaborative Board";
	private static String AppVersion = "1.0.0";
	
	private static String ServerIPDefault = "127.0.0.1";
	private static int ServerPortDefault = 7777;
	
	public static void main(String[] args) {
		
		new gui.Window(AppName + " " + AppVersion, 1280, 720);
		new network.Client("127.0.0.1", 7777);
		new Console().start();
	}

}