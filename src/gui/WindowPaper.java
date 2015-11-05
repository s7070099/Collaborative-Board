package gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowPaper extends JFrame {
	
	private JPanel contentPane;
	
	public WindowPaper(int screenWidth, int screenHeight) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, screenWidth, screenHeight);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
	}
	
	public void paint(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(50,50,50,50);
	}

}
