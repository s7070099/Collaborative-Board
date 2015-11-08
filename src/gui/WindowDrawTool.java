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
import javax.swing.JButton;
import javax.swing.JTextField;

public class WindowDrawTool extends JPanel {
	private JTextField textField;
	
	public WindowDrawTool() {
		setBounds(0, 0, 40, 300);
		setOpaque(false);
		setBackground(Color.BLACK);
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		add(btnNewButton);
		setVisible(true);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		/*setUndecorated(true);
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.WHITE);
		contentPanel2 = new JPanel();
		contentPanel2.setBackground(Color.BLACK);
		setContentPane(contentPanel);
		setContentPane(contentPanel2);*/
		
		/*Point point = super.getLocation();
		setLocation(point.x, point.y);*/
		
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.BLUE);
		g.fillRect(50,50,50,50);
	}

}
