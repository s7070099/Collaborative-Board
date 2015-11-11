package gui;

import java.awt.*;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class WindowLogin extends JFrame{
	
	public WindowLogin(){
		WindowLogin frame = new WindowLogin();
		frame.setTitle("Collaborative - login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 310, 470);
		frame.setLayout(null);
		
		//component
		JTextField user_input = new JTextField();
		getContentPane().add(user_input);
		/* Wait to add promt text in textfield*/
		user_input.setBounds(50, 125, 200, 25);
		
		JTextField pass_input = new JTextField();
		getContentPane().add(pass_input);
		/* Wait to add promt text in textfield*/
		pass_input.setBounds(50, 150, 200, 25);
		
		JButton login_button = new JButton("Login");
		getContentPane().add(login_button);
		login_button.setBackground(Color.white);
		login_button.setBounds(50, 180, 200, 40);
		
		JRadioButton radio1 = new JRadioButton("Login server automatical");
		getContentPane().add(radio1);
		radio1.setSelected(true);
		radio1.setBounds(50,230,200,20);
		
		JRadioButton radio2 = new JRadioButton("Remember server IP");
		getContentPane().add(radio2);
		radio2.setBounds(50,250,200,20);
	
		JButton signup_button = new JButton("Sign Up");
		getContentPane().add(signup_button);
		signup_button.setBackground(Color.white);
		signup_button.setBounds(50, 300, 200, 40);
		
		JLabel forgot_pwd = new JLabel("Forgotten Your Password?");
		getContentPane().add(forgot_pwd);
		forgot_pwd.setBounds(75, 395, 200, 40);
		//show
		frame.setVisible(true);
	}

	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Line2D line = new Line2D.Float(60,320,260,320);
		g2.draw(line);
		Line2D line2 = new Line2D.Float(0,430,300,430);
		g2.draw(line2);
	}
	
}
