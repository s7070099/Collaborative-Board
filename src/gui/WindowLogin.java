package gui;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class WindowLogin extends JPanel{
	
	int screenWidth = 1280;
	int screenHeight = 720;
	String copyright = "Collaborative Workspace by Group 5";
	BufferedImage logo;
	JPanel panel;
	Font DIN;
	
	public WindowLogin(int screenWidth, int screenHeight){
		setLayout(null);
		setBounds(0, 0, screenWidth, screenHeight);
		setOpaque(true);
		setBackground(Color.WHITE);
		
		DIN = WindowMain.loadFont("assets/font/DIN-Regular.ttf");
		logo = WindowMain.loadGFX("assets/image/logo.png");
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(500, 0, screenWidth, screenHeight);
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		/*JTextField userInput = new JTextField();
		userInput.setBounds(0, 125, 200, 25);
		panel.add(userInput);*/
		
		final PlaceholderTextField userInput = new PlaceholderTextField("");
		userInput.setBounds(0, 125, 200, 25);
		userInput.setPlaceholder("Nickname");
		userInput.setFont(new Font("", getFont().getStyle(), 16));
		userInput.setDisabledTextColor(Color.LIGHT_GRAY);
        panel.add(userInput);
		
		/*JTextField passInput = new JTextField();
		panel.add(passInput);
		passInput.setBounds(0, 150, 200, 25);*/
        
        final PlaceholderTextField passInput = new PlaceholderTextField("");
        passInput.setBounds(0, 150, 200, 25);
        passInput.setPlaceholder("Server IP Address");
        passInput.setFont(new Font("", getFont().getStyle(), 16));
        passInput.setDisabledTextColor(Color.LIGHT_GRAY);
        panel.add(passInput);
		
		JButton loginButton = new JButton("Connect");
		panel.add(loginButton);
		loginButton.setBackground(Color.white);
		loginButton.setBounds(0, 180, 200, 40);
		
		Checkbox option1 = new Checkbox("Remember server IP");
		panel.add(option1);
		option1.setState(true);
		option1.setBounds(0,230,200,20);
		option1.setBackground(Color.WHITE);
		
		Checkbox option2 = new Checkbox("Connect automatically");
		panel.add(option2);
		option1.setState(true);
		option2.setBounds(0,250,200,20);
		option2.setBackground(Color.WHITE);
	
		JButton signup_button = new JButton("Start as Server");
		panel.add(signup_button);
		signup_button.setBackground(Color.white);
		signup_button.setBounds(0, 300, 200, 40);
		
		/*JLabel forgot_pwd = new JLabel("Forgotten Your Password?");
		panel.add(forgot_pwd);
		forgot_pwd.setBounds(25, 395, 200, 40);*/
		
		add(panel);
		setVisible(true);
	}

	public void paint(Graphics g){
		super.paint(g);
		screenWidth = getWidth();
		screenHeight = getHeight();
		panel.setBounds((screenWidth/2)-96, 256, screenWidth, screenHeight);
		
		g.drawImage(logo, (screenWidth/2)-(logo.getWidth()/2), 64, null);
		g.drawLine(0, screenHeight-32, screenWidth, screenHeight-32);
		g.drawString(copyright, (screenWidth/2)-(g.getFontMetrics().stringWidth(copyright)/2), screenHeight-12);
		/*Graphics2D g2 = (Graphics2D) g;
		Line2D line = new Line2D.Float(60, 320,260,320);
		g2.draw(line);*/
		/*Line2D line2 = new Line2D.Float(0, 430,300,430);
		g2.draw(line2);*/
	}
	
}
