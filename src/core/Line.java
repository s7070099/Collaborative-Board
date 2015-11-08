package core;

import java.awt.Color;
import java.util.ArrayList;

public class Line {

	public ArrayList<Point> data;
	public float size;
	public Color color;
	
	public Line(ArrayList<Point> data, float size, Color color){
		this.data = data;
		this.size = size;
		this.color = color;
	}
	
}