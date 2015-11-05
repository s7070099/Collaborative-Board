package core;

import java.util.ArrayList;

public class Paper {
	
	public String name;
	public String password;
	
	public ArrayList<Layer> data;
	
	public Paper(String name, String password){
		this.name = name;
		this.password = password;
		
		this.data = new ArrayList<Layer>();
	}
	
}
