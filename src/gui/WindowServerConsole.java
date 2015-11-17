package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import core.CollaborativeBoard;
import network.Network;
import network.Server;

public class WindowServerConsole extends JPanel {
	int screenWidth = 1280;
	int screenHeight = 720;
	
	public JTextArea output;
	
	JPanel panel;
	JTextField command;
	JButton stopButton;
	JScrollPane scrollPane;
	
	public WindowServerConsole() {
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
		
		output = new JTextArea();
		output.setBounds(0, 0, screenWidth, screenHeight-70);
		panel.add(output);
		
		scrollPane = new JScrollPane(output);
		scrollPane.setBounds(0, 0, screenWidth-15, screenHeight-70);
		panel.add(scrollPane);

		command = new JTextField();
		command.setBounds(0, screenHeight-70, screenWidth-128, 32);
		panel.add(command);
		
		stopButton = new JButton("Stop Server");
		stopButton.setBounds(screenWidth-132, screenHeight-70, 116, 32);
		panel.add(stopButton);
		
		stopButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e)
			 {
				 Network.server.ServerOn = false;
				 CollaborativeBoard.window.goToLogin2();
			 }
		});
		
		//for(int i=0; i<1000; i++) output.append(i+"\n");
		output.append(CollaborativeBoard.AppName + " " + CollaborativeBoard.AppVersion+" Dedicated Server\n\n");
		add(panel);
		setVisible(true);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		screenWidth = getWidth()+16;
		screenHeight = getHeight()+38;
		
		panel.setBounds(0, 0, screenWidth, screenHeight);
		output.setBounds(0, 0, screenWidth, screenHeight-70);
		scrollPane.setBounds(0, 0, screenWidth-15, screenHeight-70);
		command.setBounds(0, screenHeight-70, screenWidth-128, 32);
		stopButton.setBounds(screenWidth-132, screenHeight-70, 116, 32);
	}
	
}