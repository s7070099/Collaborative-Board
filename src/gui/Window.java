package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {

	private int screenWidth;
	private int screenHeight;
	
	private WindowPaper windowPaper;
	
	public Window(String Caption, int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		setTitle(Caption);
		setBounds(0, 0, screenWidth, screenHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		windowPaper = new WindowPaper(screenWidth, screenHeight);
	}
	
}