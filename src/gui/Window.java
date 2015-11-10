package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {

	private int screenWidth;
	private int screenHeight;
	
	public static WindowMain windowMain;
	public static WindowDrawTool windowDrawTool;
	
	public Window(String Caption, int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		setTitle(Caption);
		setBounds(0, 0, screenWidth, screenHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		setVisible(true);
		
		windowMain = new WindowMain(screenWidth, screenHeight);
		add(windowMain);
	}

	public void paint(Graphics g){
		//super.paint(g);
		/*bimage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_BYTE_INDEXED);
		g2d = bimage.createGraphics();

		Color transparent = new Color(0, 0, 0, 0);
	    g2d.setColor(transparent);
	    g2d.setComposite(AlphaComposite.Src);
	    g2d.fill(new Rectangle2D.Float(20, 20, 100, 20));
	    
	    g2d.setColor(new Color(255, 0, 0, 255));
	    g2d.fillRect(0, 0, 400, 400);
	    g2d.dispose();
	    Graphics2D g2d2 = (Graphics2D) g.create();
	    */
		//g.setColor(Color.WHITE);
		//g.fillRect(0, 0, 1280, 680);
		//g.setColor(Color.BLUE);
		//g.fillRect(50,50,50,50);
		
		//g.drawString("Mouse X: " + windowPaper.mouseX, 50, 50);
		//g.drawString("Mouse Y: " + windowPaper.mouseY, 50, 70);
		
		//g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize)); 
		//g.drawLine(0, 0, 100, 100);
	}

}