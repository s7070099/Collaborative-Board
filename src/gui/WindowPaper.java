package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class WindowPaper extends JPanel implements KeyListener {
	
	public float cameraX = 0;
	public float cameraY = 0;
	public float cameraZoom = 0;
	
	public boolean active = false;
	
	public WindowPaper(int screenWidth, int screenHeight) {
		setBounds(0, 0, screenWidth, screenHeight);
		setOpaque(true);
		setBackground(Color.WHITE);
		setVisible(true);
		
		
		//for keyboard
        addKeyListener(this);
        setFocusable(true);
		
	}

	public void paint(Graphics g){
		super.paint(g);
		//g.setColor(Color.WHITE);
		//g.fillRect(0, 0, 1280, 680);
		//g.setColor(Color.BLUE);
		//g.fillRect(50,50,50,50);
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

}
