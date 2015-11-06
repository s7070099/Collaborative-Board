/*package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JInternalFrame;

public class WindowPaperTool extends JInternalFrame {

	public WindowPaperTool() {
		setBounds(0, 0, 100, 100);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void paint(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(150,150,50,50);
	}

}
*/

package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowPaperTool extends JFrame {
	
	private JPanel contentPanel;
	private JPanel contentPanel2;
	
	public WindowPaperTool() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 200, 200);
		setUndecorated(true);
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.WHITE);
		contentPanel2 = new JPanel();
		contentPanel2.setBackground(Color.BLACK);
		setContentPane(contentPanel);
		setContentPane(contentPanel2);
		
		Point point = super.getLocation();
		setLocation(point.x, point.y);
		
	}
	
	public void paint(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(50,50,50,50);
	}

}
