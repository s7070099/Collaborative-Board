package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import core.*;

public class WindowMain extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
	
	public int screenWidth = 1000;
	public int screenHeight = 1000;
	
	public float cameraX = 0;
	public float cameraY = 0;
	public float cameraZoom = 0;
	
	public int mouseX = 0;
	public int mouseY = 0;
	
	public int tool = 0;
	public float toolSize = 2f;
	public Color toolColor = Color.BLACK;
	
	public boolean penRecord = false;
	public int index = 0;
	
	public Point point;
	public Line line;
	public Layer layer;
	
	public ArrayList<Point> pointList;
	public ArrayList<Line> lineList = new ArrayList<Line>();
	public ArrayList<Layer> layerList = new ArrayList<Layer>();
	public ArrayList<BufferedImage> layerBuffer = new ArrayList<BufferedImage>();

	int panelToolPosition = 0;
	int buttonToolSize = 0;
	int buttonToolCount = 7;
	BufferedImage buttonToolGFX[] = new BufferedImage[16];
	
	int panelLayerPosition = 0;
	
	public BufferedImage loadGFX(String filePath){
		BufferedImage buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		try
        {
			buffer = ImageIO.read(new File(filePath));
        } 
        catch (IOException e)
        {
        	System.out.println("Load GFX (" + filePath + ") Failed!");
        }
		return buffer;
	}
	
	public WindowMain(int screenWidth, int screenHeight) {
		setBounds(0, 0, screenWidth, screenHeight);
		setOpaque(true);
		setBackground(Color.WHITE);
		setVisible(true);
		
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        
        buttonToolGFX[0] = loadGFX("assets/image/icon_pencil.png");
        buttonToolGFX[1] = loadGFX("assets/image/icon_line.png");
        buttonToolGFX[2] = loadGFX("assets/image/icon_linecont.png");
        buttonToolGFX[3] = loadGFX("assets/image/icon_rectangle.png");
        buttonToolGFX[4] = loadGFX("assets/image/icon_circle.png");
        buttonToolGFX[5] = loadGFX("assets/image/icon_eraser.png");
        buttonToolGFX[6] = loadGFX("assets/image/icon_eraserplus.png");
        
        buttonToolGFX[7] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[8] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[9] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[10] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[11] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[12] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[13] = loadGFX("assets/image/tool_button_0.png");
        
        addLayer("Default", core.CollaborativeBoard.Nickname);
        /*JButton button = new JButton("Click Me");
        button.setBounds(5, 5, 50, 30);
        add(button);*/
	}
	
	public int addLayer(String name, String author){
		BufferedImage buffer = new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = buffer.createGraphics();
		g2d.setColor(new Color(0, 0, 0, 0));
		g2d.setComposite(AlphaComposite.Src);
		g2d.fill(new Rectangle2D.Float(20, 20, 100, 20));
		layerBuffer.add(buffer);
		layerList.add(new Layer(name, author));
		return layerList.size()-1;
	}
	
	public void refreshLine(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		for(int i=0; i<layerBuffer.size()-1; i++){
			g2d.drawImage(layerBuffer.get(i), 0, 0, null);
		}
	}
	
	public void applyLine(int layerID, Line lineData){
		Line tmpline = lineData;
		ArrayList<Point> tmpdata = lineData.data;
		
		int xPoly[] = new int[tmpdata.size()];
		int yPoly[] = new int[tmpdata.size()];
		for(int j=0; j<tmpdata.size(); j++){
			xPoly[j] = tmpdata.get(j).x;
			yPoly[j] = tmpdata.get(j).y;
		}

		Graphics2D g2d = layerBuffer.get(layerID).createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setStroke(new BasicStroke(tmpline.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setColor(new Color(tmpline.color.getRed(), tmpline.color.getGreen(), tmpline.color.getBlue(), 255));
		g2d.drawPolyline(xPoly, yPoly, xPoly.length);
		g2d.dispose();
	}
	
	public void getLine(Graphics g, Line lineData){
		Line tmpline = lineData;
		ArrayList<Point> tmpdata = lineData.data;
		
		int xPoly[] = new int[tmpdata.size()];
		int yPoly[] = new int[tmpdata.size()];
		for(int j=0; j<tmpdata.size(); j++){
			xPoly[j] = tmpdata.get(j).x;
			yPoly[j] = tmpdata.get(j).y;
		}
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setStroke(new BasicStroke(tmpline.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2d.setColor(tmpline.color);
		g2d.drawPolyline(xPoly, yPoly, xPoly.length);
		g2d.dispose();
	}
	
	boolean refresh = false;

	public void paint(Graphics g){
		super.paint(g);
		screenWidth = getWidth();
		screenHeight = getHeight();
		//System.out.println(screenWidth + "," + screenHeight);
		
		if(refresh == true){
			refreshLine(g);
			refresh = false;
		}
		
		for(int i=0; i<layerBuffer.size(); i++){
			g.drawImage(layerBuffer.get(i), 0, 0, null);
		}
		
		if(penRecord == true){
			getLine(g, new Line(pointList, toolSize, toolColor));
		}
		
		panelToolPosition = (screenHeight/2) - ((buttonToolCount * 64)/2);
		
		for(int i=0; i<buttonToolCount; i++){
			g.drawImage(buttonToolGFX[i], 0, panelToolPosition + (i * 64), null);
		}
		
		g.setColor(Color.BLACK);
		g.drawString("Mouse X: " + mouseX, 30, 50);
		g.drawString("Mouse Y: " + mouseY, 30, 70);
		
		g.drawOval(mouseX, mouseY, (int)toolSize, (int)toolSize);
	}
	
	public boolean ifDrawTool(){
		return (tool >= 0 && tool <= 4);
	}
	
	public boolean mouseCheck(int x1, int y1, int x2, int y2){
		return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
	}
	
	public void active() {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("keyTyped: "+e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("keyPressed: "+e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("keyReleased: "+e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int selectTool = -1;
		for(int i=0; i<=buttonToolCount; i++){
			if(mouseCheck(0, panelToolPosition + (i * 64), 64, panelToolPosition + ((i+1) * 64))){
				selectTool = i;
				tool = selectTool;
			}
		}
		System.out.println(selectTool);
		if(ifDrawTool() && selectTool == -1){
			pointList = new ArrayList<Point>();
			penRecord = true;
			System.out.println("Started " + penRecord);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(ifDrawTool() && penRecord == true){
			Line line = new Line(pointList, toolSize, toolColor);
			layerList.get(index).data.add(line);
			penRecord = false;
			
			applyLine(index, line);
			System.out.println("Ended " + penRecord + "(Buffer "+ layerBuffer.size() +", Use "+ index +")");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	public void updateMousePosition(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		updateMousePosition(e);
		
		if(penRecord == true){
			switch(tool){
				case 0: 
					pointList.add(new Point(mouseX, mouseY));
					break;
				case 1:
					pointList.get(1).x = mouseX;
					pointList.get(1).y = mouseY;
					break;
			}
			if(tool == 0){
				
			}
			if(tool == 1){
				
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		updateMousePosition(e);
	}

}
