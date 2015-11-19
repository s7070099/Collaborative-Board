package core;

import java.util.ArrayList;

public class Paper {
	
	public String name;
	public String author;
	public String password;
	public ArrayList<String> user;
	public ArrayList<Layer> data;
	public ArrayList<String> onlineUser;
	
	public Paper(String name, String author, String password){
		this.name = name;
		this.author = author;
		this.password = password;
		this.data = new ArrayList<Layer>();
		this.data.add(new Layer("Default", ""));
		this.user = new ArrayList<String>();
		this.onlineUser = new ArrayList<String>();
	}
	
	public void addUser(String user){
		this.user.remove(user);
		this.user.add(user);
	}
	
	public void removeUser(String user){
		this.user.remove(user);
	}
	
}