package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import core.*;
import network.Network;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class WindowSelectPaper extends JPanel implements MouseWheelListener {
	int screenWidth = 1280;
	int screenHeight = 720;
	
	ArrayList<Paper> paperList = new ArrayList<Paper>();
	
	Font DIN;
	BufferedImage captionGFX;
	BufferedImage buttonGFX;
	BufferedImage captionMenuGFX[] = new BufferedImage[2];
	BufferedImage menuGFX[] = new BufferedImage[2];
	
	JPanel panel, refreshPaperButton, addPaperButton;
	JScrollPane scrollPane;
	
	int maxScroll = 0;
	int currentScroll = 0;
	
	public boolean lock = false;
	
	class SubWindowAddPaper extends JDialog {
		public SubWindowAddPaper(){
			setTitle("Add Paper");
			setBounds(0, 0, 400, 103);
			
			JPanel panel = new JPanel();
			panel.setBounds(0, 0, getWidth(), getHeight());
			panel.setLayout(null);
			panel.setOpaque(true);
			panel.setBackground(Color.WHITE);
			
			final PlaceholderTextField nameInput = new PlaceholderTextField("");
			nameInput.setBounds(0, 0, 400, 25);
			nameInput.setPlaceholder("Paper Name");
			nameInput.setFont(DIN.deriveFont(16f));
			nameInput.setDisabledTextColor(Color.LIGHT_GRAY);
			panel.add(nameInput);
	        
	        final PlaceholderTextField passInput = new PlaceholderTextField("");
	        passInput.setBounds(0, 25, 400, 25);
	        passInput.setPlaceholder("Password");
	        passInput.setFont(DIN.deriveFont(16f));
	        passInput.setDisabledTextColor(Color.LIGHT_GRAY);
	        panel.add(passInput);
	        
	        JButton button1 = new JButton("Add");
	        button1.setBounds(0, 50, 200, 25);
	        button1.setBackground(Color.white);
	        panel.add(button1);
	        
	        button1.addActionListener(new ActionListener(){
				 public void actionPerformed(ActionEvent e)
				 {
					 String name = nameInput.getText();
					 String pass = passInput.getText();
					 if(name.length() != 0){
						 Network.paperName = name;
						 Network.client.out.println("addpaper");
						 Network.client.out.println(name);
						 Network.client.out.println(pass);
			        	 Network.client.out.flush();
			        	 dispose();
					 }else{
						 JOptionPane.showMessageDialog (null, "please input paper name.", "ERROR", JOptionPane.ERROR_MESSAGE);
					 }
				 }
			});
	        
	        JButton button2 = new JButton("Cancel");
	        button2.setBounds(200, 50, 200, 25);
	        button2.setBackground(Color.white);
	        panel.add(button2);
	        
	        button2.addActionListener(new ActionListener(){
				 public void actionPerformed(ActionEvent e)
				 {
					 lock = false;
					 dispose();
				 }
			});
	        
	        add(panel);
	        
			Image icon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
			setIconImage(null);
	        
	        setLocationRelativeTo(CollaborativeBoard.window);
	        setResizable(false);
	        setModal(true);
	        setVisible(true);
		}
	}
	
	class ButtonAddPaper extends JPanel {
		public ButtonAddPaper(){
			setBackground(Color.WHITE);
			addMouseListener(new MouseAdapter() { 
				public void mousePressed(MouseEvent me) {
					if(lock == false){
						lock = true;
						new SubWindowAddPaper();
					}
				} 
		    });
		}
	}
	
	class ButtonGoLogin extends JPanel {
		public ButtonGoLogin(){
			setBackground(Color.WHITE);
			addMouseListener(new MouseAdapter() { 
				public void mousePressed(MouseEvent me) {
					if(Network.client.disconnnect()){
						Network.clientThread.stop();
						CollaborativeBoard.window.goToLogin();
					}
				} 
		    });
		}
	}
	
	class ButtonRefresh extends JPanel {
		public ButtonRefresh(){
			setBackground(Color.WHITE);
			addMouseListener(new MouseAdapter() { 
				public void mousePressed(MouseEvent me) {
					if(lock == false){
						lock = true;
						Network.client.out.println("getlist");
						Network.client.out.flush();
					}
				} 
		    });
		}
	}
	
	class ButtonPaper extends JPanel {
		String name;
		String author;
		ArrayList<String> user;
		int userSize;
		
		public ButtonPaper(int paperID){
			setBackground(Color.WHITE);
			addMouseListener(new MouseAdapter() { 
		          public void mousePressed(MouseEvent me) { 
		        	  //System.out.println("paper "+ paperID);
		        	  if(lock == false){
		        		  Network.paperID = paperID;
			        	  Network.client.out.println("getpaper");
			        	  Network.client.out.println(paperID);
			        	  Network.client.out.println("");
			        	  Network.client.out.flush();
			        	  lock = true;
		        	  }
		          } 
		    });
			name = paperList.get(paperID).name;
			author = paperList.get(paperID).author;
			user = paperList.get(paperID).user;
			userSize = user.size();
		}
		
		public void paint(Graphics g){
			super.paint(g);
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			
			g.drawImage(buttonGFX, 0, 0, null);
			g.setColor(new Color(230, 230, 230));
			g.drawLine(getWidth()-12-64, getHeight()-49, getWidth()-12-64, getHeight()-14);
			g.setColor(new Color(183, 183, 183));
			g.drawLine(7, getHeight()-49, getWidth()-12, getHeight()-49);
			g.setColor(Color.WHITE);
			g.fillRect(7, 7, 288, 162);
			g.setColor(Color.BLACK);
			g.setFont(DIN.deriveFont(16f));
			g.drawString(name, 12, getHeight()-49+18);
			g.setFont(DIN.deriveFont(11f));
			g.drawString(author, 12, getHeight()-49+32);
			g.drawImage(menuGFX[0], getWidth()-12-64, getHeight()-49+6, null);
			g.setFont(DIN.deriveFont(22f));
			g.drawString(userSize+"", getWidth()-30-(g.getFontMetrics().stringWidth(userSize+"")/2), getHeight()-22);
		}
	}
	
	public void refreshPaper(){
		paperList = Network.paper;
		
		panel.removeAll();
		int x = 0;
		int y = 0;
		int sizeX = 288+18;
		int sizeY = 162+36+20;
		for(int i=0; i<paperList.size(); i++){
			ButtonPaper b1 = new ButtonPaper(i);
			b1.setBounds(22+((sizeX)*x), 24+(sizeY)*y, sizeX, sizeY);
			panel.add(b1);
			x++;
			if(x == 4){ x = 0; y++; }
		}
		maxScroll = ((y+1) * sizeY) + 24;
		repaint();
		lock = false;
	}
	
	public WindowSelectPaper(){
		setLayout(null);
		setBounds(0, 0, screenWidth, screenHeight);
		setOpaque(true);
		setBackground(Color.WHITE);
		
		addMouseWheelListener(this);
		
		DIN = WindowMain.loadFont("assets/font/DIN-Regular.ttf");
		captionGFX = WindowMain.loadGFX("assets/image/caption.png");
		buttonGFX = WindowMain.loadGFX("assets/image/paper.png");
		menuGFX[0] = WindowMain.loadGFX("assets/image/layer_adduser.png");
		
		refreshPaperButton = new JPanel();
		addPaperButton = new JPanel();

		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 16, screenWidth, 10000);
		panel.setOpaque(true);
		panel.setBackground(Color.WHITE);
		panel.setVisible(true);
		
		ButtonAddPaper button1 = new ButtonAddPaper();
		button1.setBounds(screenWidth-64, 0, 64, 32);
		add(button1);
		
		ButtonGoLogin button2 = new ButtonGoLogin();
		button2.setBounds(0, 0, 48, 32);
		add(button2);
		
		ButtonRefresh button3 = new ButtonRefresh();
		button3.setBounds(screenWidth-128, 0, 64, 32);
		add(button3);
		
		/*for(int i=0; i<39; i++){
			paperList.add(new Paper("paper" + (i+1), "boss", "555"));
		}*/
		refreshPaper();

		add(panel);
		setVisible(true);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		screenWidth = getWidth()+16;
		screenHeight = getHeight()+38;
		
		panel.setBounds(0, 16-currentScroll, screenWidth, maxScroll);
		
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g.drawImage(captionGFX, 0, 0, null);
		g.setFont(DIN.deriveFont(22f));
		g.drawString("<    " + Network.serverCaption, 12, 20);
		g.setFont(DIN.deriveFont(36f));
		g.drawString("+", screenWidth-48, 24);
		g.setColor(new Color(183, 183, 183));
		//g.drawLine(screenWidth-64, 0, screenWidth-64, 31);
		/*g.setColor(toolColor);
		g.fillRect(2, panelToolPosition+2, 44, 44);
		g.setColor(new Color(184, 184, 184, 255));
		g.drawRect(2, panelToolPosition+2, 43, 43);
		g.setColor(new Color(0, 0, 0, 255));
		int tmpToolSize = Math.min(25, Math.max(1, (int)(toolSize)));
		g.fillOval(24 - (tmpToolSize/2), panelToolPosition - 4 + 48 + 24 - (tmpToolSize/2), tmpToolSize, tmpToolSize);
		g.setFont(DIN.deriveFont(10f));
		g.drawString(toolSize+"", 24 - (g.getFontMetrics().stringWidth(toolSize+"")/2), panelToolPosition + 92);
		*/
		
		g = null;
		System.gc();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		currentScroll += e.getWheelRotation() * 32;
		currentScroll = Math.max(Math.min(currentScroll, maxScroll-screenHeight+64), 0);
		repaint();
	}
}
