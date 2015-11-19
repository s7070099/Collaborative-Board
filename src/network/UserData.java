package network;

import java.net.Socket;

public class UserData {
	public String name;
	public String ssid;
	public String paper;
	public String layer;
	public int mouseX;
	public int mouseY;
	public Socket s;
	
	public UserData(String name, String ssid){
		this.name = name;
		this.ssid = ssid;
		this.paper = "";
		this.layer = "";
		this.mouseX = 0;
		this.mouseY = 0;
	}
}