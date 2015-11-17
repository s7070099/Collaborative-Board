package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import core.CollaborativeBoard;
import core.Layer;
import core.Line;
import core.Paper;
import core.Point;
import network.*;

public class WindowLogin extends JPanel{
	
	int screenWidth = 1280;
	int screenHeight = 720;
	String copyright = "Collaborative Workspace by Group 5";
	BufferedImage logo;
	JPanel panel;
	JPanel panelConnecting;
	JPanel panelServerConfig;
	Font DIN;
	
	boolean autologin = false;
	boolean remember_svip = true;
	
	int svPort = 9999;
	int svPaper = 32;
	int svUser = 12;
	String svDirectory = "save/default";
	boolean remember_svconfig = true;
	
	Thread threadA;
	
	public WindowLogin(){
		setLayout(null);
		setBounds(0, 0, screenWidth, screenHeight);
		setOpaque(true);
		setBackground(Color.WHITE);
		
		DIN = WindowMain.loadFont("assets/font/DIN-Regular.ttf");
		logo = WindowMain.loadGFX("assets/image/logo.png");
		
		String oldName = "";
		String oldServerIP = "";
		try {
			File file = new File("user.config");
			if(file.exists()){
				FileReader fReader = new FileReader(file);
				BufferedReader bufReader = new BufferedReader(fReader);
	
				oldName = bufReader.readLine();
				oldServerIP = bufReader.readLine();
				remember_svip = bufReader.readLine().equals("1");
				autologin = bufReader.readLine().equals("1");
				
				if(remember_svip == false){
					oldName = "";
					oldServerIP = "";
				}
				
				bufReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			File file = new File("server.config");
			if(file.exists()){
				FileReader fReader = new FileReader(file);
				BufferedReader bufReader = new BufferedReader(fReader);
				
				svPort = Integer.parseInt(bufReader.readLine());
				svPaper = Integer.parseInt(bufReader.readLine());
				svUser = Integer.parseInt(bufReader.readLine());
				svDirectory = bufReader.readLine();
				remember_svconfig = bufReader.readLine().equals("1");
				
				bufReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(500, 0, screenWidth, screenHeight);
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		
		panelConnecting = new JPanel();
		panelConnecting.setLayout(null);
		panelConnecting.setBounds(500, 256, screenWidth, screenHeight);
		panelConnecting.setOpaque(true);
		panelConnecting.setBackground(Color.WHITE);
		panelConnecting.setVisible(false);

		panelServerConfig = new JPanel();
		panelServerConfig.setLayout(null);
		panelServerConfig.setBounds(500, 256, screenWidth, screenHeight);
		panelServerConfig.setOpaque(true);
		panelServerConfig.setBackground(Color.WHITE);
		panelServerConfig.setVisible(false);
		
		/*JTextField userInput = new JTextField();
		userInput.setBounds(0, 125, 200, 25);
		panel.add(userInput);
		
		JTextField passInput = new JTextField();
		panel.add(passInput);
		passInput.setBounds(0, 150, 200, 25);*/
		
		final PlaceholderTextField userInput = new PlaceholderTextField("");
		userInput.setBounds(0, 125, 200, 25);
		userInput.setPlaceholder("Nickname");
		userInput.setFont(new Font("", getFont().getStyle(), 16));
		userInput.setDisabledTextColor(Color.LIGHT_GRAY);
		userInput.setText(oldName);
        panel.add(userInput);
        
        final PlaceholderTextField passInput = new PlaceholderTextField("");
        passInput.setBounds(0, 150, 200, 25);
        passInput.setPlaceholder("Server IP Address");
        passInput.setFont(new Font("", getFont().getStyle(), 16));
        passInput.setDisabledTextColor(Color.LIGHT_GRAY);
        passInput.setText(oldServerIP);
        panel.add(passInput);
        
        final PlaceholderTextField portInput = new PlaceholderTextField("");
        portInput.setBounds(0, 125, 200, 25);
        portInput.setPlaceholder("Server Port (Default: 9999)");
        portInput.setFont(new Font("", getFont().getStyle(), 16));
        portInput.setDisabledTextColor(Color.LIGHT_GRAY);
        portInput.setText(svPort+"");
        panelServerConfig.add(portInput);
        
        final PlaceholderTextField maxPaperInput = new PlaceholderTextField("");
        maxPaperInput.setBounds(0, 150, 200, 25);
        maxPaperInput.setPlaceholder("Maximum Paper");
        maxPaperInput.setFont(new Font("", getFont().getStyle(), 16));
        maxPaperInput.setDisabledTextColor(Color.LIGHT_GRAY);
        maxPaperInput.setText(svPaper+"");
        panelServerConfig.add(maxPaperInput);
        
        final PlaceholderTextField maxUserInput = new PlaceholderTextField("");
        maxUserInput.setBounds(0, 175, 200, 25);
        maxUserInput.setPlaceholder("Maximum User");
        maxUserInput.setFont(new Font("", getFont().getStyle(), 16));
        maxUserInput.setDisabledTextColor(Color.LIGHT_GRAY);
        maxUserInput.setText(svUser+"");
        panelServerConfig.add(maxUserInput);
        
        final PlaceholderTextField fileInput = new PlaceholderTextField("");
        //fileInput.setEditable(false);
        fileInput.setBounds(0, 200, 168, 25);
        fileInput.setPlaceholder("Save file Directory");
        fileInput.setFont(new Font("", getFont().getStyle(), 16));
        fileInput.setDisabledTextColor(Color.LIGHT_GRAY);
        fileInput.setText(svDirectory+"");
        panelServerConfig.add(fileInput);
        
        JButton selectButton = new JButton("...");
        selectButton.setBackground(Color.white);
        selectButton.setBounds(168, 200, 32, 25);
        panelServerConfig.add(selectButton);
        
        selectButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e)
			 {
				 JFileChooser fChooser = new JFileChooser();
				 fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				 fChooser.setCurrentDirectory(new File("save"));
				 fChooser.setSelectedFile(new File(""));
				 int result = fChooser.showSaveDialog(null);
				 if (result == JFileChooser.APPROVE_OPTION) {
				     File selectedFile = fChooser.getSelectedFile();
				     fileInput.setText(selectedFile.getAbsolutePath());
				 }
			 }
		});
        
        Checkbox option3 = new Checkbox("Remember server settings.");
		option3.setState(true);
		option3.setBounds(0, 230, 260, 20);
		option3.setBackground(Color.WHITE);
		option3.setState(remember_svconfig);
		panelServerConfig.add(option3);

		JButton cancelServerButton = new JButton("Cancel");
        cancelServerButton.setBackground(Color.white);
        cancelServerButton.setBounds(0, 255, 200, 40);
        panelServerConfig.add(cancelServerButton);
        
        cancelServerButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e)
			 {
				 panel.setVisible(true);
				 panelServerConfig.setVisible(false);
			 }
		});
        
		JButton startServerbutton = new JButton("Start Server");
		startServerbutton.setBackground(Color.white);
		startServerbutton.setBounds(0, 300, 200, 40);
		panelServerConfig.add(startServerbutton);
		
		startServerbutton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e)
			 {
				 if(portInput.getText().length() != 0) 
					 svPort = Integer.parseInt(portInput.getText());
				 if(maxPaperInput.getText().length() != 0) 
					 svPaper = Integer.parseInt(maxPaperInput.getText());
				 if(maxUserInput.getText().length() != 0) 
					 svUser = Integer.parseInt(maxUserInput.getText());
				 String svDirectory = fileInput.getText();

				 int errorcode = 0;
				 String info = null;
				 
				 if(svPort <= 0) svPort = 9999;
				 if(svPaper <= 0) svPaper = 32;
				 if(svUser <= 0) svUser = 12;
				 
				 File tmpFile = new File(svDirectory);
				 if(!tmpFile.exists()){
					 tmpFile.mkdirs(); 
					if(!tmpFile.exists()){
						errorcode = 1;
						info = "cannot create save directory !";
					}
				 }
				 
				 Network.serverPort = svPort;
				 Network.serverPaper = svPaper;
				 Network.serverUser = svUser;
				 Network.serverDirectory = svDirectory;
				 
				 if(errorcode == 0){
					 Network.mode = 2;
					 CollaborativeBoard.window.goToServerConsole();
					 
					 threadA = new Thread(new Runnable(){
				            public void run(){
				            	panel.setVisible(true);
				            	panelConnecting.setVisible(false);
				            	Network.server.start();
				            }
				     }, "Server");
					 threadA.start();
				 }else{
					 JOptionPane.showMessageDialog (null, info, "ERROR", JOptionPane.ERROR_MESSAGE);
				 }
			 }
		});
        
        JLabel label1 = new JLabel("Connect to Server...", SwingConstants.CENTER);
        label1.setBounds(0, 125, 200, 25);
        panelConnecting.add(label1);
		
        JLabel label2 = new JLabel("", SwingConstants.CENTER);
        label2.setBounds(0, 150, 200, 25);
        panelConnecting.add(label2);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.white);
        cancelButton.setBounds(0, 180, 200, 40);
        panelConnecting.add(cancelButton);
		
		
		JButton loginButton = new JButton("Connect");
		panel.add(loginButton);
		loginButton.setBackground(Color.white);
		loginButton.setBounds(0, 180, 200, 40);
		
		loginButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){
				 int errorcode = 0;
				 String info = null;
				 String name = userInput.getText();
				 String serverip = passInput.getText()+":0";
				 
				 if(name.length() == 0){
					 errorcode = 1;
					 info = "please input nickname.";
				 }else if(!name.matches("[A-Za-z0-9]+")){
					 errorcode = 1;
					 info = "please use character A-Z, a-z, 0-9 (no space).";
				 }else if(serverip.length() == 0){
					 errorcode = 1;
					 info = "please input server ip.";
				 }
				 
				 if(errorcode == 0){
					 Network.mode = 1;
					 
					 threadA = new Thread(new Runnable(){
				            public void run(){
				            	String serverIPArray[] = serverip.split(":"); 
				        		String serverip = serverIPArray[0];
				        		int serverPort = Integer.parseInt(serverIPArray[1]);
				        		if(serverPort == 0) serverPort = 9999;
				        		
				        		label2.setText(serverip+":"+serverPort);
				            	
				            	if(Network.client.connect(name, serverip, serverPort)){
				            		CollaborativeBoard.window.goToList();
				            	}else{
				            		JOptionPane.showMessageDialog (null, "cannot connect to server !", "ERROR", JOptionPane.ERROR_MESSAGE);
				            	}
				            	panel.setVisible(true);
				            	panelConnecting.setVisible(false);
				            }
				     }, "Client");
					 threadA.start();
					 
					 panel.setVisible(false);
					 panelConnecting.setVisible(true);
				 }else{
					 JOptionPane.showMessageDialog (null, info, "ERROR", JOptionPane.ERROR_MESSAGE);
				 }
			 }
		});
		
		cancelButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e)
			 {
				 threadA.stop();
				 panel.setVisible(true);
				 panelConnecting.setVisible(false);
			 }
		});

		
		Checkbox option1 = new Checkbox("Remember nickname and server IP");
		panel.add(option1);
		option1.setState(true);
		option1.setBounds(0,230,260,20);
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
		
		signup_button.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e)
			 {
				 panel.setVisible(false);
				 panelServerConfig.setVisible(true);
			 }
		});

		add(panel);
		add(panelConnecting);
		add(panelServerConfig);
		setVisible(true);
	}

	public void paint(Graphics g){
		super.paint(g);
		screenWidth = getWidth();
		screenHeight = getHeight();
		panel.setBounds((screenWidth/2)-96, 256, screenWidth, screenHeight);
		panelConnecting.setBounds((screenWidth/2)-96, 256, screenWidth, screenHeight);
		panelServerConfig.setBounds((screenWidth/2)-96, 256, screenWidth, screenHeight);
		
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
