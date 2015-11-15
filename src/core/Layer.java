package core;

import java.util.ArrayList;

public class Layer {

	public String name;
	public String author;
	public Boolean hidden;
	public ArrayList<String> user;
	public ArrayList<Line> data;
	public int onlineUser;
	
	public Layer(String name, String author){
		this.name = name;
		this.author = author;
		this.hidden = false;
		this.user = new ArrayList<String>();
		this.data = new ArrayList<Line>();
	}
	
	public void addUser(String user){
		this.user.remove(user);
		this.user.add(user);
	}
	
	public void removeUser(String user){
		this.user.remove(user);
	}
	
}
