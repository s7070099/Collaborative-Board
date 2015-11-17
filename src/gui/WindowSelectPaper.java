package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class WindowSelectPaper extends JPanel {
	int screenWidth = 1280;
	int screenHeight = 720;
	
	JPanel panel;
	
	public WindowSelectPaper(){
		setLayout(null);
		setBounds(0, 0, screenWidth, screenHeight);
		setOpaque(true);
		setBackground(Color.WHITE);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, screenWidth, screenHeight);
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		panel.setVisible(true);
		
		add(panel);
		setVisible(true);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		screenWidth = getWidth()+16;
		screenHeight = getHeight()+38;
	}
}
