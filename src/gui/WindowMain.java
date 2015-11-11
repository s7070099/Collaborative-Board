package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import core.*;

public class WindowMain extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	
	public String paperName = "UNNAMED PAPER";
	
	public int screenWidth = 1280;
	public int screenHeight = 720;
	
	public double cameraX = 0;
	public double cameraY = 0;
	public double cameraZoom = 0;
	
	public int mousePressX = 0;
	public int mousePressY = 0;
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
	int buttonToolCount = 5;
	BufferedImage buttonToolGFX[] = new BufferedImage[16];
	
	int panelLayerPosX = 0;
	int panelLayerPosY = 0;
	int panelLayerSizeX = 192;
	int panelLayerSizeY = 64;
	int panelLayerScroll = 0;
	BufferedImage panelLayerBuffer = new BufferedImage(128, 512, BufferedImage.TYPE_INT_ARGB);
	
	public void clearMem(Object a){
		a = null;
		System.gc();
	}
	
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
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        
        buttonToolGFX[0] = loadGFX("assets/image/icon_pencil.png");
        buttonToolGFX[1] = loadGFX("assets/image/icon_line.png");
        //buttonToolGFX[2] = loadGFX("assets/image/icon_linecont.png");
        buttonToolGFX[2] = loadGFX("assets/image/icon_rectangle.png");
        buttonToolGFX[3] = loadGFX("assets/image/icon_circle.png");
        //buttonToolGFX[4] = loadGFX("assets/image/icon_eraser.png");
        buttonToolGFX[4] = loadGFX("assets/image/icon_eraserplus.png");
        
        buttonToolGFX[5] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[6] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[7] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[8] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[9] = loadGFX("assets/image/tool_button_0.png");
        //buttonToolGFX[11] = loadGFX("assets/image/tool_button_0.png");
        //buttonToolGFX[12] = loadGFX("assets/image/tool_button_0.png");
        
        Graphics2D g2d = panelLayerBuffer.createGraphics();
		g2d.setColor(new Color(0, 0, 0, 0));
		g2d.setComposite(AlphaComposite.Src);
		g2d.fill(new Rectangle2D.Float(20, 20, 100, 20));
		g2d = null;
		System.gc();
        
        addLayer("Default1", core.CollaborativeBoard.Nickname);
        addLayer("Default2", core.CollaborativeBoard.Nickname);
        addLayer("Default3", core.CollaborativeBoard.Nickname);
        addLayer("Default4", core.CollaborativeBoard.Nickname);
        addLayer("Default5", core.CollaborativeBoard.Nickname);
        addLayer("Default6", core.CollaborativeBoard.Nickname);
        addLayer("Default7", core.CollaborativeBoard.Nickname);
        /*JButton button = new JButton("Click Me");
        button.setBounds(5, 5, 50, 30);
        add(button);*/
        layerList.get(0).user.add("FakeUser1");
        layerList.get(0).user.add("FakeUser2");
        layerList.get(0).user.add("FakeUser3");
	}
	
	public int addLayer(String name, String author){
		BufferedImage buffer = new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = buffer.createGraphics();
		g2d.setColor(new Color(0, 0, 0, 0));
		g2d.setComposite(AlphaComposite.Src);
		g2d.fill(new Rectangle2D.Float(0, 00, 500, 500));
		g2d = null;
		System.gc();
		layerBuffer.add(buffer);
		layerList.add(new Layer(name, author));
		return layerList.size()-1;
	}
	
	public void refreshPanelLayer(){
		panelLayerBuffer = null;
		System.gc();
		
		panelLayerBuffer = new BufferedImage(panelLayerSizeX, 512, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = panelLayerBuffer.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		for(int i=0; i<layerList.size(); i++){
			g2d.setColor(Color.BLACK);
			g2d.drawString(layerList.get(i).name, 0, panelLayerScroll + 10 + ((i) * panelLayerSizeY));
		}
		g2d.dispose();
		g2d = null;
		System.gc();
	}
	
	public void refreshBuffer(int layerIndex){
		layerBuffer.set(layerIndex, new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_ARGB));
		Graphics2D g2d = layerBuffer.get(index).createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		for(Line i:layerList.get(layerIndex).data){
			Line tmpline = i;
			ArrayList<Point> tmpdata = i.data;
			
			int xPoly[] = new int[tmpdata.size()];
			int yPoly[] = new int[tmpdata.size()];
			for(int j=0; j<tmpdata.size(); j++){
				xPoly[j] = tmpdata.get(j).x;
				yPoly[j] = tmpdata.get(j).y;
			}
			g2d.setStroke(new BasicStroke(tmpline.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.setColor(new Color(tmpline.color.getRed(), tmpline.color.getGreen(), tmpline.color.getBlue(), 255));
			g2d.drawPolyline(xPoly, yPoly, xPoly.length);
		}
		g2d.dispose();
		g2d = null;
		System.gc();
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
		g2d = null;
		System.gc();
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
		g2d = null;
		System.gc();
	}

	public void paint(Graphics g){
		super.paint(g);
		screenWidth = getWidth();
		screenHeight = getHeight();
		
		int offsetX = (int)cameraX;
		int offsetY = (int)cameraY;
		int sizeX = (int)4096;
		int sizeY = (int)4096;
		for(int i=0; i<layerBuffer.size(); i++){
			g.drawImage(layerBuffer.get(i), offsetX, offsetY, sizeX, sizeY, null);
			if(i == index){
				if(penRecord == true){
					getLine(g, new Line(pointList, toolSize, toolColor));
				}
			}
		}
		
		panelToolPosition = (screenHeight/2) - ((buttonToolCount * 64)/2);
		
		for(int i=0; i<buttonToolCount; i++){
			g.drawImage(buttonToolGFX[i], 0, panelToolPosition + (i * 64), null);
		}
		
		refreshPanelLayer();
		panelLayerPosX = screenWidth-panelLayerSizeX;
		panelLayerPosY = 48;
		g.drawImage(panelLayerBuffer, panelLayerPosX, panelLayerPosY, null);
		
		g.setColor(Color.BLACK);
		g.drawString("Mouse X: " + mouseX, 30, 50);
		g.drawString("Mouse Y: " + mouseY, 30, 70);
		
		g.drawOval(mouseX, mouseY, (int)toolSize, (int)toolSize);
		
		g = null;
		System.gc();
	}
	
	public boolean mouseLeft(MouseEvent e){
		return e.getModifiers() == InputEvent.BUTTON1_MASK;
	}
	
	public boolean ifDrawTool(){
		return (tool >= 0 && tool <= 5);
	}
	
	public boolean mouseCheck(int x1, int y1, int x2, int y2){
		return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
	}
	
	public double getDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
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
		mousePressX = e.getX();
		mousePressY = e.getY();
		
		int selectTool = -1;
		for(int i=0; i<=buttonToolCount; i++){
			if(mouseCheck(0, panelToolPosition + (i * 64), 64, panelToolPosition + ((i+1) * 64))){
				selectTool = i;
				tool = selectTool;
			}
		}
		System.out.println(selectTool);
		if(ifDrawTool() && selectTool == -1 && mouseLeft(e)){
			pointList = null;
			System.gc();
			pointList = new ArrayList<Point>();
			penRecord = true;
			System.out.println("Started " + penRecord);
			
			if(tool == 1){
				pointList.add(new Point(mouseX, mouseY));
				pointList.add(new Point(mouseX, mouseY));
			}
			if(tool == 2){
				pointList.add(new Point(mouseX, mouseY));
				pointList.add(new Point(mouseX, mouseY));
				pointList.add(new Point(mouseX, mouseY));
				pointList.add(new Point(mouseX, mouseY));
				pointList.add(new Point(mouseX, mouseY));
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(ifDrawTool() && penRecord == true){
			if(pointList.size() == 1){
				pointList.add(new Point(mouseX, mouseY));
			}else if(pointList.size() == 0){
				pointList.add(new Point(mouseX, mouseY));
				pointList.add(new Point(mouseX, mouseY));
			}
			
			Line line = new Line(pointList, toolSize, toolColor);
			layerList.get(index).data.add(line);
			penRecord = false;
			
			applyLine(index, line);
			System.out.println("Ended " + penRecord + "(Buffer "+ layerBuffer.size() +", Use "+ index +")");
			pointList = null;
			line = null;
			System.gc();
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
				case 2:
					pointList.get(1).x = mouseX;
					pointList.get(2).x = mouseX;
					pointList.get(2).y = mouseY;
					pointList.get(3).y = mouseY;
					break;
				case 3:
					pointList = null;
					System.gc();
					pointList = new ArrayList<Point>();
					//double rad = getDistance(mousePressX, mousePressY, mouseX, mouseY);
					int p = 5;
					int count = 360/p;
			        double cx = mousePressX;//+(mouseX-mousePressX);
			        double cy = mousePressY;//+(mouseY-mousePressY);
			        double r = Math.max(1, getDistance((int)mousePressX, (int)mousePressY, (int)mouseX, (int)mouseY));
			        for(int theta = 0; theta < 360+p; theta+=p)
			        {
			        	//r += 1;
			        	/*if(theta > theta/4){
			        		
			        	}*/
			        	
			            int x = (int)(cx + r * Math.cos(Math.toRadians(theta)));
			            int y = (int)(cy + r * Math.sin(Math.toRadians(theta)));
			            pointList.add(new Point(x, y));
			        }
					break;
				case 5:
					ArrayList<Integer> removeLineID = new ArrayList<Integer>();
					ArrayList<Integer> removePointOffset = new ArrayList<Integer>();
					
					for(int i=0; i<layerList.get(index).data.size(); i++){
						Line line = layerList.get(index).data.get(i);
						for(int j=0; j<line.data.size(); j++){
							if(getDistance(mouseX, mouseY,(int)line.data.get(j).x, (int)line.data.get(j).y) < 2.5f){
								removeLineID.add(i);
								removePointOffset.add(j);
							}
						}
						line = null;
						System.gc();
					}

					if(removeLineID.size() > 0){
						for(int i=0; i<removeLineID.size(); i++){
							Line line = layerList.get(index).data.get(i);
							if(line.data.size() > 1){
								List<Point> pointList1 = line.data.subList(0, removePointOffset.get(i)-1);
								List<Point> pointList2 = line.data.subList(removePointOffset.get(i)+1, line.data.size()-1);
								System.out.println("0-"+(removePointOffset.get(i)-1)+" "+(removePointOffset.get(i)+1)+"-"+(line.data.size()-1));
								ArrayList<Point> newLine1 = new ArrayList<Point>();
								ArrayList<Point> newLine2 = new ArrayList<Point>();
								for(Point j:pointList1){
									newLine1.add(j);
								}
								for(Point j:pointList2){
									newLine2.add(j);
								}
								layerList.get(index).data.add(new Line(newLine1, line.size, line.color));
								layerList.get(index).data.add(new Line(newLine2, line.size, line.color));
								newLine1 = null;
								newLine2 = null;
								pointList1 = null;
								pointList2 = null;
								System.gc();
							}
							layerList.get(index).data.remove(i);
							line = null;
							System.gc();
						}
						refreshBuffer(index);
					}
					removeLineID = null;
					removePointOffset = null;
					System.gc();
					break;
				case 4:
					ArrayList<Line> removeBuffer = new ArrayList<Line>();
					for(Line i:layerList.get(index).data){
						for(Point j:i.data){
							if(getDistance(mouseX, mouseY,(int)j.x, (int)j.y) < 2.5f){
								removeBuffer.add(i);
							}
						}
					}
					if(removeBuffer.size() > 0){
						for(Line i:removeBuffer){
							layerList.get(index).data.remove(i);
						}
						refreshBuffer(index);
					}
					removeBuffer = null;
					System.gc();
					break;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		updateMousePosition(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println(e.getWheelRotation());
		panelLayerScroll -= e.getWheelRotation() * 4;
		repaint();
	}

}
