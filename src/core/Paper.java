package core;

import java.util.ArrayList;

public class Paper {
	
	public String name;
	public String author;
	public ArrayList<String> user;
	public ArrayList<Layer> data;
	
	public Paper(String name, String author){
		this.name = name;
		this.author = author;
		this.data = new ArrayList<Layer>();
	}
	
	public void addUser(String user){
		this.user.remove(user);
		this.user.add(user);
	}
	
	public void removeUser(String user){
		this.user.remove(user);
	}
	
}