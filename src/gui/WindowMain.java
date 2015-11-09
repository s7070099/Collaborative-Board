package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import core.*;

public class WindowMain extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	
	public float cameraX = 0;
	public float cameraY = 0;
	public float cameraZoom = 0;
	
	public int mouseX = 0;
	public int mouseY = 0;
	
	public int tool = 0;
	public float toolSize = 2f;
	public Color toolColor = Color.BLACK;
	
	public boolean penRecord = false;
	
	public Point point;
	public Line line;
	public Layer layer;
	
	public ArrayList<Point> pointData;
	public ArrayList<Line> lineData = new ArrayList<Line>();
	public ArrayList<Layer> layerData;
	
	public boolean active = false;
	BufferedImage buffer;
	
	public WindowMain(int screenWidth, int screenHeight) {
		setBounds(0, 0, screenWidth, screenHeight);
		setOpaque(true);
		setBackground(Color.WHITE);
		setVisible(true);
		
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        
        buffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffer.createGraphics();

		Color transparent = new Color(0, 0, 0, 0);
		g2d.setColor(transparent);
		g2d.setComposite(AlphaComposite.Src);
		g2d.fill(new Rectangle2D.Float(20, 20, 100, 20));
	    
		/*g2d.setColor(new Color(255, 0, 0, 255));
		g2d.fillRect(0, 0, 400, 400);*/
		
	}
	
	public void applyLine(BufferedImage buffer, Line lineData){
		Line tmpline = lineData;
		ArrayList<Point> tmpdata = lineData.data;
		
		int xPoly[] = new int[tmpdata.size()];
		int yPoly[] = new int[tmpdata.size()];
		for(int j=0; j<tmpdata.size(); j++){
			xPoly[j] = tmpdata.get(j).x;
			yPoly[j] = tmpdata.get(j).y;
		}
		
		Graphics2D g2d = buffer.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setStroke(new BasicStroke(tmpline.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setColor(new Color(tmpline.color.getRed(), tmpline.color.getGreen(), tmpline.color.getBlue(), 255));
		g2d.drawPolyline(xPoly, yPoly, xPoly.length);
		g2d.dispose();
	}
	
	public void getLine(Graphics g, Line lineData){
		Line tmpline = lineData;
		ArrayList<Point> tmpdata = lineData.data;
		
		int xPoly[] = new int[tmpdata.size()];
		int yPoly[] = new int[tmpdata.size()];
		for(int j=0; j<tmpdata.size(); j++){
			xPoly[j] = tmpdata.get(j).x;
			yPoly[j] = tmpdata.get(j).y;
		}
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setStroke(new BasicStroke(tmpline.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setColor(tmpline.color);
		g2d.drawPolyline(xPoly, yPoly, xPoly.length);
		g2d.dispose();
	}
	
	/*
	public void getLine(Graphics g, Line lineData){
		Line tmpline = lineData;
		ArrayList<Point> tmpdata = lineData.data;
		
		int xPoly[] = new int[tmpdata.size()];
		int yPoly[] = new int[tmpdata.size()];
		for(int j=0; j<tmpdata.size(); j++){
			xPoly[j] = tmpdata.get(j).x;
			yPoly[j] = tmpdata.get(j).y;
		}
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setStroke(new BasicStroke(tmpline.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setColor(tmpline.color);
		g2d.drawPolyline(xPoly, yPoly, xPoly.length);
		g2d.dispose();
	
		g2d = buffer.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setStroke(new BasicStroke(tmpline.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setColor(new Color(tmpline.color.getRed(), tmpline.color.getGreen(), tmpline.color.getBlue(), 255));
		g2d.drawPolyline(xPoly, yPoly, xPoly.length);
		g2d.dispose();
	}
	*/

	public void paint(Graphics g){
		super.paint(g);
		
		
		Graphics2D g2d2 = (Graphics2D) g;
		g2d2.drawImage(buffer, 0, 0, null);
		
		g.drawString("Mouse X: " + mouseX, 30, 50);
		g.drawString("Mouse Y: " + mouseY, 30, 70);
		
		if(penRecord == true){
			getLine(g, new Line(pointData, toolSize, toolColor));
		}
		//g2d2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		/*g2d2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);*/
		
		//float scale = 0.7f;
		//g2d.drawImage(buffer, (int)(buffer.getWidth()*scale/2), (int)(buffer.getHeight()*scale/2), buffer.getWidth()*scale, buffer.getHeight()*scale, null);
		//g2d2.dispose();
		

		
		
		
		
		/*for(int i=0; i<lineData.size(); i++){
			Line tmpline = lineData.get(i);
			ArrayList<Point> tmpdata = tmpline.data;
			drawLayer(g, tmpline);
		}*/
		
		/*if(gLoad == true){
			old = g;
			gLoad = false;
		}*/
		//g = update;
		/*
		if(gSave == true){
			g = update;
			gSave = false;
		}
		*/
		//g.setColor(Color.WHITE);
		//g.fillRect(0, 0, 1280, 680);
		//g.setColor(Color.BLUE);
		//g.fillRect(50,50,50,50);
		//old = g;
		
		
		/*if(penRecord == true){
			//System.out.println("Drawing..");
			for(int i=0; i<pointData.size()-1; i++){
				g.drawLine(pointData.get(i).x, pointData.get(i).y, pointData.get(i+1).x, pointData.get(i+1).y);
			}
			drawLayer(g, new Line(pointData, toolSize, toolColor));
		}*/
		
		//for(int i=0; i<lineData.size(); i++){
		//	Line tmpline = lineData.get(i);
		//	ArrayList<Point> tmpdata = tmpline.data;
			
			//drawLayer(g, tmpline);
			
			/*OLD DRAWING
			for(int j=0; j<tmpline.size()-1; j++){
				Point tmppoint = tmpline.get(j);
				Point tmppoint2 = tmpline.get(j+1);
				
				g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2d.drawLine(tmppoint.x, tmppoint.y, tmppoint2.x, tmppoint2.y);
				g2d.dispose();
				
				
				
			}
			*/
			
			
			//GOOD DRAWING
			/*int xPoly[] = new int[tmpdata.size()];
			int yPoly[] = new int[tmpdata.size()];
			for(int j=0; j<tmpdata.size(); j++){
				xPoly[j] = tmpdata.get(j).x;
				yPoly[j] = tmpdata.get(j).y;
				//System.out.println(xPoly[j] + " " + yPoly[j]);
			}
			
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setStroke(new BasicStroke(tmpline.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.setColor(tmpline.color);
			g2d.drawPolyline(xPoly, yPoly, xPoly.length);
			g2d.dispose();*/
			
		//}
	}
	
	public void active() {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("keyTyped: "+e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("keyPressed: "+e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("keyReleased: "+e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pointData = new ArrayList<Point>();
		penRecord = true;
		System.out.println("Started " + penRecord);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Line line = new Line(pointData, toolSize, toolColor);
		lineData.add(line);
		penRecord = false;
		
		applyLine(buffer, line);
		System.out.println("Ended " + penRecord);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	public void updateMousePosition(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		updateMousePosition(e);
		
		if(penRecord == true){
			pointData.add(new Point(mouseX, mouseY));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		updateMousePosition(e);
	}

}
