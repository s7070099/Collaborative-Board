package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import javax.swing.JPanel;

public class WindowPaper extends JPanel {
	
	public WindowPaper(int screenWidth, int screenHeight) {
		setBounds(0, 0, screenWidth, screenHeight);
		setBackground(Color.RED);
		setVisible(true);
	}
	
	public void init() {
		//frame = new JFrame();
		
		
		
	}
	
	public void paint(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(50,50,50,50);
	}

}
