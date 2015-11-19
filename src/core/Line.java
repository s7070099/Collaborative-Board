package core;

import java.awt.Color;
import java.util.ArrayList;

public class Line {

	public int id;
	public float size;
	public Color color;
	public ArrayList<Point> data;
	
	public Line(ArrayList<Point> data, float size, Color color){
		this.data = data;
		this.size = size;
		this.color = color;
	}
	
}