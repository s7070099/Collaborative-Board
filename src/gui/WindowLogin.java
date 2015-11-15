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
		frame.setBounds(0, 0, 1280, 720);
		frame.setLayout(null);
		
		//component
		JTextField user_input = new JTextField("Username");
		getContentPane().add(user_input);
		user_input.setBounds(600, 375, 200, 25);
		
		JTextField pass_input = new JTextField("Server IP");
		getContentPane().add(pass_input);
		pass_input.setBounds(600, 400, 200, 25);
		
		JButton login_button = new JButton("Login");
		getContentPane().add(login_button);
		login_button.setBackground(Color.white);
		login_button.setBounds(600, 430, 200, 40);
		
		JRadioButton radio1 = new JRadioButton("Login server automatical");
		getContentPane().add(radio1);
		radio1.setSelected(true);
		radio1.setBounds(600,480,200,20);
		
		JRadioButton radio2 = new JRadioButton("Remember server IP");
		getContentPane().add(radio2);
		radio2.setBounds(600,500,200,20);
	
		JButton signup_button = new JButton("Sign Up");
		getContentPane().add(signup_button);
		signup_button.setBackground(Color.white);
		signup_button.setBounds(600, 550, 200, 40);
		
		JLabel forgot_pwd = new JLabel("Forgotten Your Password?");
		getContentPane().add(forgot_pwd);
		forgot_pwd.setBounds(620, 645, 200, 40);
		//show
		frame.setVisible(true);
	}

	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Line2D line = new Line2D.Float(610,570,810,570);
		g2.draw(line);
		Line2D line2 = new Line2D.Float(0,680,1280,680);
		g2.draw(line2);
	}
	
}
