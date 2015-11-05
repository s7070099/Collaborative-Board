package core;

import java.util.ArrayList;

public class Layer {

	public String name;
	public Boolean hidden;
	public ArrayList<Line> data;
	
	public Layer(String name, String password){
		this.name = name;
		this.hidden = false;
		this.data = new ArrayList<Line>();
	}
	
}
