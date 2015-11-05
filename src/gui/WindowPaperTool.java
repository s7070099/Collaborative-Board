package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JInternalFrame;

public class WindowPaperTool extends JInternalFrame {

	public WindowPaperTool() {
		setBounds(0, 0, 100, 100);
	}
	
	public void paint(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(150,150,50,50);
	}

}
