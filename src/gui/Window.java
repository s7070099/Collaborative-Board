package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Window extends JFrame {

	private int screenWidth;
	private int screenHeight;
	
	public static WindowPaper windowPaper;
	
	public Window(String Caption, int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		setTitle(Caption);
		setBounds(0, 0, screenWidth, screenHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBackground(Color.WHITE);
		setVisible(true);
		
		windowPaper = new WindowPaper(screenWidth, screenHeight);
		add(windowPaper);
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		//g.setColor(Color.WHITE);
		//g.fillRect(0, 0, 1280, 680);
		//g.setColor(Color.BLUE);
		//g.fillRect(50,50,50,50);
		
		g.drawString("I love you", 50, 50);
		
		//g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize)); 
		g.drawLine(0, 0, 100, 100);
	}
	
}