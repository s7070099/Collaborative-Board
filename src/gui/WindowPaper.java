package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class WindowPaper extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	
	public float cameraX = 0;
	public float cameraY = 0;
	public float cameraZoom = 0;
	
	public double mouseX = 0;
	public double mouseY = 0;
	
	public Point point;
	
	public core.Layer layer;
	
	public boolean active = false;
	
	public Graphics old;
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}

}
