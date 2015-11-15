package core;

import javax.swing.JFrame;

public class CollaborativeBoard {
	
	public static String AppName = "";//"Collaborative Workspace";
	public static String AppVersion = "";//"1.0.0";
	
	public static String Nickname = "nickname";
	public static String ServerIPDefault = "127.0.0.1";
	public static int ServerPortDefault = 7777;
	
	public static void main(String[] args) {
		
		new gui.Window(AppName + " " + AppVersion, 1280, 720);
		//new network.Client("127.0.0.1", 7777);
		new Console().start();
		
	}

}