package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import core.*;

public class WindowPaper extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	
	public float cameraX = 0;
	public float cameraY = 0;
	public float cameraZoom = 0;
	
	public int mouseX = 0;
	public int mouseY = 0;
	
	public boolean penRecord = false;
	public float penSize = 0.5f;
	public Color penColor = Color.BLACK;
	
	public Point point;
	public Line line;
	public Layer layer;
	
	public ArrayList<Point> pointData;
	public ArrayList<Line> lineData = new ArrayList<Line>();
	public ArrayList<Layer> layerData;
	
	public boolean active = false;
	
	public WindowPaper(int screenWidth, int screenHeight) {
		//setting
		setBounds(0, 0, screenWidth, screenHeight);
		setOpaque(true);
		setBackground(Color.WHITE);
		setVisible(true);
		
		//keyboard and mouse
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
	}

	public void paint(Graphics g){
		super.paint(g);
		//g.setColor(Color.WHITE);
		//g.fillRect(0, 0, 1280, 680);
		//g.setColor(Color.BLUE);
		//g.fillRect(50,50,50,50);
		//old = g;
		g.drawString("Mouse X: " + mouseX, 30, 50);
		g.drawString("Mouse Y: " + mouseY, 30, 70);
		
		if(penRecord == true){
			//System.out.println("Drawing..");
			for(int i=0; i<pointData.size()-1; i++){
				g.drawLine(pointData.get(i).x, pointData.get(i).y, pointData.get(i+1).x, pointData.get(i+1).y);
			}
		}
		
		for(int i=0; i<lineData.size(); i++){
			ArrayList<Point> tmpline = lineData.get(i).data;
			for(int j=0; j<tmpline.size()-1; j++){
				Point tmppoint = tmpline.get(j);
				Point tmppoint2 = tmpline.get(j+1);
				
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2d.drawLine(tmppoint.x, tmppoint.y, tmppoint2.x, tmppoint2.y);
				g2d.dispose();
			}
		}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pointData = new ArrayList<Point>();
		penRecord = true;
		System.out.println("Started " + penRecord);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		lineData.add(new Line(pointData));
		penRecord = false;
		System.out.println("Ended " + penRecord);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		/*mouseX = e.getX();
		mouseY = e.getY();
		repaint();*/
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		/*mouseX = e.getX();
		mouseY = e.getY();*/
		/*mouseX = e.getX();
		mouseY = e.getY();
		repaint();*/
	}
	
	public void updateMousePosition(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();
		//System.out.println(mouseX + " " + mouseY + " " + penRecord);
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		updateMousePosition(e);
		
		if(penRecord == true){
			pointData.add(new Point(mouseX, mouseY, 0.5f));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		updateMousePosition(e);
	}

}
