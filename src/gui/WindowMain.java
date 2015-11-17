package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import core.*;

public class WindowMain extends JPanel implements ComponentListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	
	public String paperName = "UNNAMED PAPER";
	
	public boolean pageShift = false;
	public boolean keyShift = false;
	
	public int screenWidth = 1280;
	public int screenHeight = 720;
	public int screenHeightMid = 360;
	
	public int cameraX = 0;
	public int cameraY = 0;
	public double cameraZoom = 0;
	
	public int cameraPressX = 0;
	public int cameraPressY = 0;
	
	public int mousePressX = 0;
	public int mousePressY = 0;
	public int mousePressRX = 0;
	public int mousePressRY = 0;
	public int mouseX = 0;
	public int mouseY = 0;
	public int mouseRX = 0;
	public int mouseRY = 0;
	
	public int tool = 2;
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
	
	BufferedImage nullBuffer = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
	BufferedImage captionGFX;

	int panelToolPosition = 0;
	int buttonToolSize = 0;
	int buttonToolCount = 7;
	BufferedImage buttonToolGFX[] = new BufferedImage[buttonToolCount * 2];
	BufferedImage panelToolGFX[] = new BufferedImage[16];
	
	int panelLayerMaxShow = 3;
	int panelLayerPosX = 0;
	int panelLayerPosY = 0;
	int panelLayerSizeX = 192;
	int panelLayerSizeY = 128;
	int panelLayerScroll = 0;
	int panelButtonPosY = 0;
	BufferedImage panelLayerGFX[] = new BufferedImage[16];
	BufferedImage panelLayerBuffer = new BufferedImage(128, 512, BufferedImage.TYPE_INT_ARGB);
	int panelSlotPos[] = new int[panelLayerMaxShow+3];
	int panelSlotID[] = new int[panelLayerMaxShow+3];
	
	BufferedImage colorPanelGFX;
	BufferedImage sizePanelGFX;
	BufferedImage colorPanelBuffer;
	BufferedImage sizePanelBuffer;
	
	public int subTool = 0;
	boolean canPickMinor = false;
	boolean canPickMajor = false;
	double colorDirection = 353.5006408691406;
	Point colorMajorPick = new Point(180, 93);
	Point colorPick = new Point(57, 57+91);
	Color colorMajor = Color.RED;
	Color colorMinor = Color.BLACK;
	public Color colorMacro[] = new Color[48];
	
	boolean canDrag = false;
	int sizeDragValue = 0;
	float sizeMacro[] = {0.7f, 1, 1.5f, 2, 2.5f, 3, 4, 5, 6, 7, 8, 10, 12, 15, 17, 20, 25, 30, 40, 50, 60, 70, 80, 1000};
	
	Font DIN;
	
	public void updateAll(){
		refreshPanelLayer();
		repaint();
		validate();
	}
	
	public void clearMem(Object a){
		a = null;
		System.gc();
	}
	
	public static BufferedImage loadGFX(String filePath){
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
	
	public static Font loadFont(String filename){
		Font customFont = null;
		try {
            //create the font to use. Specify the size!
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File(filename));//.deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(FontFormatException e)
        {
            e.printStackTrace();
        }
		return customFont;
	}
	
	public WindowMain() {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		setBounds(0, 0, screenWidth, screenHeight);
		setOpaque(true);
		setBackground(Color.WHITE);
		setVisible(true);
		
		addKeyListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);
        setFocusable(true);
        
        
        captionGFX = loadGFX("assets/image/caption.png");
        
        buttonToolGFX[0] = loadGFX("assets/image/icon_none.png");
        buttonToolGFX[1] = loadGFX("assets/image/icon_none.png");
        buttonToolGFX[2] = loadGFX("assets/image/icon_pencil.png");
        buttonToolGFX[3] = loadGFX("assets/image/icon_line.png");
        buttonToolGFX[4] = loadGFX("assets/image/icon_rectangle.png");
        buttonToolGFX[5] = loadGFX("assets/image/icon_circle.png");
        buttonToolGFX[6] = loadGFX("assets/image/icon_eraser.png");
        
        buttonToolGFX[7] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[8] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[9] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[10] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[11] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[12] = loadGFX("assets/image/tool_button_0.png");
        buttonToolGFX[13] = loadGFX("assets/image/tool_button_0.png");
        
        panelToolGFX[0] = loadGFX("assets/image/tool.png");
        
        panelLayerGFX[0] = loadGFX("assets/image/layer.png");
        panelLayerGFX[1] = loadGFX("assets/image/layer_visible.png");
        panelLayerGFX[2] = loadGFX("assets/image/layer_adduser.png");
        panelLayerGFX[3] = loadGFX("assets/image/layer_up.png");
        panelLayerGFX[4] = loadGFX("assets/image/layer_down.png");
        panelLayerGFX[5] = loadGFX("assets/image/layer_delete.png");
        panelLayerGFX[6] = loadGFX("assets/image/layer_hidden.png");
        
        colorPanelGFX = loadGFX("assets/image/color.png");
        sizePanelGFX = loadGFX("assets/image/size.png");
        for(int i=0; i<colorMacro.length; i++){
        	colorMacro[i] = Color.WHITE;
        }
        
        DIN = loadFont("assets/font/DIN-Regular.ttf");
        
        Graphics2D g2d = panelLayerBuffer.createGraphics();
		g2d.setColor(new Color(0, 0, 0, 0));
		g2d.setComposite(AlphaComposite.Src);
		g2d.fill(new Rectangle2D.Float(20, 20, 100, 20));
		g2d = null;
		System.gc();
        
        addLayer("Default", core.CollaborativeBoard.Nickname);
        /*addLayer("Layer2", core.CollaborativeBoard.Nickname);
        addLayer("Layer3", core.CollaborativeBoard.Nickname);
        addLayer("Layer4", core.CollaborativeBoard.Nickname);*/
        /*addLayer("Default5", core.CollaborativeBoard.Nickname);
        addLayer("Default6", core.CollaborativeBoard.Nickname);
        addLayer("Default7", core.CollaborativeBoard.Nickname);*/
        /*JButton button = new JButton("Click Me");
        button.setBounds(5, 5, 50, 30);
        add(button);*/
        layerList.get(0).user.add("FakeUser1");
        layerList.get(0).user.add("FakeUser2");
        layerList.get(0).user.add("FakeUser3");
        
        //updateAll();
	}
	
	public int addLayer(String name, String author){
		BufferedImage buffer = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
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
		panelLayerBuffer = new BufferedImage(panelLayerSizeX, panelLayerMaxShow * panelLayerSizeY, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = panelLayerBuffer.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		int tmpSlotCount = 1;
		for(int i=0; i<layerList.size(); i++){
			int yStart = -panelLayerScroll + ((i) * panelLayerSizeY);
			int yEnd = yStart + panelLayerSizeY;
			if(index != i){
				g2d.setColor(new Color(230, 230, 255, 255));
				g2d.fillRect(0, yStart+1, panelLayerSizeX, panelLayerSizeY);
			}else{
				panelButtonPosY = yStart+103;
			}
			
			g2d.setColor(Color.BLACK);
			g2d.setFont(DIN.deriveFont(14f));
			g2d.drawString(layerList.get(i).name, 4, 14 + yStart);
			g2d.setColor(Color.GRAY);
			g2d.setFont(DIN.deriveFont(12f).deriveFont(Font.BOLD));//new Font("Verdana", Font.BOLD, 12));
			g2d.drawString(layerList.get(i).author, 4, 27 + yStart);
			//BufferedImage tmp = (BufferedImage) layerBuffer.get(i).getScaledInstance(1280, 720, Image.SCALE_SMOOTH);
			g2d.setColor(new Color(255, 255, 255, 255));
			g2d.fillRect(0, yStart+31, panelLayerSizeX, 72);
			g2d.drawImage(layerBuffer.get(i), 0, yStart+31, panelLayerSizeX, yStart+103, 0, 0, screenWidth, screenHeight, null);

			g2d.setColor(new Color(230, 230, 230, 255));
			for(int j=1; j<=5; j++){
				g2d.drawImage(panelLayerGFX[j], 36 * (j-1), yStart+103, null);
				g2d.drawLine(j*36, yStart+103, j*36, yEnd);
			}
			if(layerList.get(i).hidden){ g2d.drawImage(panelLayerGFX[6], 0, yStart+103, null); }
			
			g2d.drawLine(0, yStart+31, panelLayerSizeX, yStart+31);
			g2d.drawLine(0, yStart+103, panelLayerSizeX, yStart+103);
			
			g2d.setColor(new Color(184, 184, 184, 255));
			g2d.drawLine(0, yEnd, panelLayerSizeX, yEnd);
			
			if(yEnd > 0  && tmpSlotCount < panelLayerMaxShow+2){ 
				panelSlotPos[tmpSlotCount] = panelLayerPosY+yEnd-7;
				panelSlotID[tmpSlotCount] = i;
				tmpSlotCount++;
			}
			//tmp = null;
		}
		panelSlotPos[0] = 0;
		panelSlotID[0] = 0;
		panelSlotPos[panelLayerMaxShow+2] = panelLayerMaxShow * panelLayerSizeY;
		panelSlotID[panelLayerMaxShow+2] = 0;
		g2d.dispose();
		g2d = null;
		System.gc();
	}
	
	public void refreshBuffer(int layerIndex){
		layerBuffer.set(layerIndex, new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB));
		//if(layerList.get(layerIndex).hidden) return;
		Graphics2D g2d = layerBuffer.get(index).createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		for(Line i:layerList.get(layerIndex).data){
			Line tmpline = i;
			ArrayList<Point> tmpdata = i.data;
			
			int xPoly[] = new int[tmpdata.size()];
			int yPoly[] = new int[tmpdata.size()];
			int ignoreCount = 0;
			for(int j=0; j<tmpdata.size(); j++){
				int x = tmpdata.get(j).x;
				int y = tmpdata.get(j).y;
				//*if(x >= -cameraX && y >= -cameraY && x <= screenWidth - cameraX && y <= screenHeight + cameraY && x != 0 && y != 0){
					xPoly[j] = x + cameraX;
					yPoly[j] = y + cameraY;
				//}else{
				//	ignoreCount++;
				//}
			}
			g2d.setStroke(new BasicStroke(tmpline.size, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g2d.setColor(new Color(tmpline.color.getRed(), tmpline.color.getGreen(), tmpline.color.getBlue(), 255));
			g2d.drawPolyline(xPoly, yPoly, xPoly.length-ignoreCount);
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
			xPoly[j] = tmpdata.get(j).x + cameraX;
			yPoly[j] = tmpdata.get(j).y + cameraY;
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

	public void refreshColorPanel(){
		colorPanelBuffer = new BufferedImage(206, 351, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = colorPanelBuffer.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.drawImage(colorPanelGFX, 0, 0, null);
		//g2d.setPaint(new GradientPaint(0, 57, new, 0, 57+92, Color.BLACK));
        //g2d.fill(new Rectangle(57, 57, 92, 92));
		
        //g2d.setPaint(new GradientPaint(57+46, 57+46, new Color(colorMajor.getRed(), colorMajor.getGreen(), colorMajor.getBlue(), 0), 57+92, 57, colorMajor));
        g2d.setPaint(new GradientPaint(57, 0, Color.WHITE, 57+92, 0, colorMajor));
        g2d.fill(new Rectangle(57, 57, 92, 92));
        g2d.setPaint(new GradientPaint(0, 57, new Color(0, 0, 0, 0), 0, 57+92, Color.BLACK));
        g2d.fill(new Rectangle(57, 57, 92, 92));
        g2d.setColor(new Color(230, 230, 230));
        g2d.drawRect(56, 56, 93, 93);
        
        int offsetCPX = 15;
        int offsetCPY = 206;//250;
        int offsetCPSX = 22;
        int offsetCPSY = 22;
        for(int j=0; j<6; j++){
        	for(int i=0; i<8; i++){
        		g2d.setColor(colorMacro[i+(j*8)]);
        		g2d.fillRect(offsetCPX + (i*offsetCPSX), offsetCPY + (j*offsetCPSY), offsetCPSX - 2, offsetCPSY - 2);
        		g2d.setColor(new Color(230, 230, 230));
        		g2d.drawRect(offsetCPX + (i*offsetCPSX), offsetCPY + (j*offsetCPSY), offsetCPSX - 2, offsetCPSY - 2);
        	}
        }
        
        g2d.drawRect(colorPick.x-2, colorPick.y-2, 3, 3);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(colorPick.x-3, colorPick.y-3, 5, 5);
        
        g2d.setColor(new Color(230, 230, 230));
        g2d.drawRect(colorMajorPick.x-2, colorMajorPick.y-2, 3, 3);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(colorMajorPick.x-3, colorMajorPick.y-3, 5, 5);
        
        colorMajor = new Color(colorPanelBuffer.getRGB(colorMajorPick.x, colorMajorPick.y));
        colorMinor = new Color(colorPanelBuffer.getRGB(colorPick.x, colorPick.y));
		toolColor = colorMinor;
 		g2d.dispose();
		g2d = null;
		System.gc();
	}
	
	public void refreshSizePanel(){
		sizePanelBuffer = new BufferedImage(206, 351, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = sizePanelBuffer.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.drawImage(sizePanelGFX, 0, 0, null);
		
		g2d.setColor(new Color(0, 0, 0, 255));
		for(int j=0; j<6; j++){
			for(int i=0; i<4; i++){
				float tmpSize = sizeMacro[(j*4)+i];
				int tmpToolSize = Math.min(25, Math.max(1, (int)(tmpSize)));
				
				g2d.fillOval(7+24+ (48*i) - (tmpToolSize/2), 49+24 + (48*j) - (tmpToolSize/2), tmpToolSize, tmpToolSize);
				g2d.setFont(DIN.deriveFont(10f));
				g2d.drawString(tmpSize+"", 7+24 + (48*i) - (g2d.getFontMetrics().stringWidth(tmpSize+"")/2), 4+92+ (48*j));
			}
		}
		
		g2d.setColor(new Color(230, 230, 230));
		g2d.drawLine(7, 7+48, 198, 7+48);
		for(int i=0; i<6; i++){
			g2d.drawLine(7, 7+48+(i*48), 198, 7+48+(i*48));
		}
		for(int i=0; i<4; i++){
			g2d.drawLine(7+(i*48), 7+48, 7+(i*48), 7+48+(6*48));
		}
		
		g2d.drawRect(7+4, 7+24 - 2, 180, 4);
		g2d.setColor(new Color(60, 60, 60));
		g2d.fillRect(7+5, 7+25 - 2, sizeDragValue+1, 3);
		g2d.fillRect(7+4+sizeDragValue+1, 7+24 - 7, 4, 14);
		
		g2d.dispose();
		g2d = null;
		System.gc();
	}

	public void paint(Graphics g){
		super.paint(g);
		screenWidth = getWidth()+20;
		screenHeight = getHeight();
		screenHeightMid = screenHeight/2;
		//System.out.println(screenWidth+", "+screenHeight);
		
		int offsetX = (int)cameraX;
		int offsetY = (int)cameraY;
		int sizeX = (int)1920;
		int sizeY = (int)1080;
		for(int i=0; i<layerBuffer.size(); i++){
			if(layerList.get(i).hidden){
				g.drawImage(nullBuffer, 0, 0, null);
			}else{
				g.drawImage(layerBuffer.get(i), 0, 0, sizeX, sizeY, null);
			}
			if(i == index){
				if(penRecord == true && ifDrawTool()){
					getLine(g, new Line(pointList, toolSize, toolColor));
				}
			}
		}
		
		panelToolPosition = screenHeightMid - ((buttonToolCount * 48)/2);
		
		g.drawImage(panelToolGFX[0], 0, panelToolPosition-17, null);
		for(int i=0; i<buttonToolCount; i++){
			g.drawImage(buttonToolGFX[i], 0, panelToolPosition + (i * 48), 48, 48, null);
		}
		
		panelLayerPosX = screenWidth-panelLayerSizeX;
		panelLayerPosY = screenHeightMid-((panelLayerSizeY * panelLayerMaxShow)/2);
		
		//g.drawImage(panelLayerGFX[0], panelLayerPosX-36, panelLayerPosY-24, null);
		g.drawImage(panelLayerGFX[0], panelLayerPosX-36, panelLayerPosY-16, null);
		g.drawImage(panelLayerBuffer, panelLayerPosX-7, panelLayerPosY, null);
		
		g.setColor(new Color(184, 184, 184, 255));
		
		int tmp = panelLayerPosY + (panelLayerSizeY * panelLayerMaxShow);
		g.drawLine(panelLayerPosX-7, tmp, panelLayerPosX+panelLayerSizeX, tmp);
		g.setColor(Color.BLACK);
		g.setFont(DIN.deriveFont(48f));
		g.drawString("+", panelLayerPosX+64, tmp+36);
		//g.drawRect(panelLayerPosX-7, tmp, panelLayerPosX+panelLayerSizeX, 44);
		//g.drawImage(,,);
		//g.setColor(Color.BLACK);
		//g.drawRect(panelLayerPosX, panelLayerPosY, panelLayerSizeX, panelLayerSizeY * panelLayerMaxShow);
		
		/*g.setColor(Color.BLACK);
		g.drawString("Mouse X: " + mouseX, 30, 50);
		g.drawString("Mouse Y: " + mouseY, 30, 70);*/
		
		if(subTool == 1){
			g.drawImage(colorPanelBuffer, 52, panelLayerPosY+16, null);
		}else if(subTool == 2){
			g.drawImage(sizePanelBuffer, 52, panelLayerPosY+16, null);
		}
		
		if(tool == 6){
			g.drawOval(mouseX - 6, mouseY - 6, 12, 12);
		}else{
			g.drawOval(mouseX - (int)toolSize/2, mouseY - (int)toolSize/2, (int)toolSize, (int)toolSize);
		}
		
		g.drawImage(captionGFX, 0, 0, null);
		
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g.setColor(toolColor);
		g.fillRect(2, panelToolPosition+2, 44, 44);
		g.setColor(new Color(184, 184, 184, 255));
		g.drawRect(2, panelToolPosition+2, 43, 43);
		g.setColor(new Color(0, 0, 0, 255));
		int tmpToolSize = Math.min(25, Math.max(1, (int)(toolSize)));
		g.fillOval(24 - (tmpToolSize/2), panelToolPosition - 4 + 48 + 24 - (tmpToolSize/2), tmpToolSize, tmpToolSize);
		g.setFont(DIN.deriveFont(10f));
		g.drawString(toolSize+"", 24 - (g.getFontMetrics().stringWidth(toolSize+"")/2), panelToolPosition + 92);
		g.setFont(DIN.deriveFont(22f));
		g.drawString("<    " + paperName + " / " + layerList.get(index).name, 12, 20);
		
		g = null;
		System.gc();
	}
	
	public boolean mouseLeft(MouseEvent e){
		return e.getModifiers() == InputEvent.BUTTON1_MASK;
	}
	
	public boolean mouseRight(MouseEvent e){
		return e.getModifiers() == InputEvent.BUTTON2_MASK;
	}
	
	public boolean mouseMiddle(MouseEvent e){
		return e.getModifiers() == InputEvent.BUTTON3_MASK;
	}
	
	public boolean ifDrawTool(){
		return (tool >= 2 && tool <= 5);
	}
	
	public boolean mouseCheck(int x1, int y1, int x2, int y2){
		return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
	}
	
	public double getDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	public double getAngle(int x1, int y1, int x2, int y2) {
	    float angle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));

	    if(angle < 0){
	        angle += 360;
	    }
	    return angle;
	}
	
	public boolean onSubPanel(){
		return mouseCheck(52, panelLayerPosY+16, 52+7+192, panelLayerPosY+16+337);
	}
	
	public boolean onLayerPanel(){
		return mouseCheck(panelLayerPosX-7, panelLayerPosY, screenWidth, panelLayerPosY+(panelLayerSizeY*panelLayerMaxShow));
	}
	
	public void colorPick() {
		colorPick.x = Math.max(0, Math.min(91, mouseX-52-57)) + 57;
		colorPick.y = Math.max(0, Math.min(91, mouseY-panelLayerPosY-57-16)) + 57;
		System.out.println(colorMinor);
	}
	
	public void colorMajorPick(){
		colorDirection = getAngle(52+57+46, panelLayerPosY+16+57+46, mouseX, mouseY);//+90)%360;
		double angle = colorDirection * Math.PI / 180;
		colorMajorPick.x = (int) (52+52/*+46*/ + (77 * Math.cos(angle)));
		colorMajorPick.y = (int) (52+50 + (77 * Math.sin(angle)));
		System.out.println(colorDirection+":: " + colorMajorPick.x + ", " + colorMajorPick.y);
	}
	
	public void sizeDrag(){
		sizeDragValue = Math.max(0, Math.min(180, mouseX-52-7-4));
		toolSize = Math.max((sizeDragValue*100)/180, 0.7f);
		//System.out.println(sizeDragValue);
	}
	
	public void addMacroColor(){
		for(int i=46; i>=0; i--){
			colorMacro[i+1] = colorMacro[i];
		}
		colorMacro[0] = colorMinor;
		refreshColorPanel();
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println("keyTyped: "+e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("keyPressed: "+e);
		if(e.getKeyCode() == 16){
			keyShift = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("keyReleased: "+e);
		if(e.getKeyCode() == 16){
			keyShift = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressX = e.getX();
		mousePressY = e.getY();
		cameraPressX = cameraX;
		cameraPressY = cameraY;
		mousePressRX = mousePressX + cameraX;
		mousePressRY = mousePressY + cameraY;
		
		if(mouseCheck(0, 0, 48, 32)){
			CollaborativeBoard.window.goToList();
			System.out.println("Exit");
			return;
		}
		
		int selectTool = -1;
		for(int i=0; i<buttonToolCount; i++){
			if(mouseCheck(0, panelToolPosition + (i * 48), 48, panelToolPosition + ((i+1) * 48))){
				selectTool = i;
				if(i > 1){
					tool = selectTool;
					//System.out.println("Change tool to " + tool);
				}else{
					if(subTool-1 != selectTool){
						subTool = i+1;
						if(subTool == 1){ refreshColorPanel(); }
						if(subTool == 2){ refreshSizePanel(); }
						repaint();
					}else{
						subTool = 0;
						repaint();
					}
					//System.out.println("Call panel " + subTool);
				}
				return;
			}
		}
		
		if(selectTool > 1){
			subTool = 0;
		}

		if(onSubPanel() && subTool != 0){
			if(subTool == 1){
				if(mouseCheck(52+57, panelLayerPosY+16+57, 52+57+92, panelLayerPosY+16+57+92)){
					canPickMinor = true;
					colorPick();
				}
				double mouseRange = getDistance(52+57+46,  panelLayerPosY+16+57+46, mouseX, mouseY);System.out.println(mouseRange);
				if(mouseRange > 70 && mouseRange < 87){
					canPickMajor = true;
					colorMajorPick();
				}
			}
			if(subTool == 2){
				if(mouseCheck(52+7, panelLayerPosY+16+7, 52+7+192, panelLayerPosY+16+7+48)){
					canDrag = true;
					sizeDrag();
					refreshSizePanel();
					repaint();
				}
				for(int j=0; j<6; j++){
					for(int i=0; i<4; i++){
						if(mouseCheck(52+7+(48*i), panelLayerPosY+49+24 + (48*j),52+7+48+(48*(i+1)), panelLayerPosY+49+48+ (48*(j+1)))){
							toolSize = sizeMacro[(j*4)+i];
							refreshSizePanel();
							repaint();
						}
						/*int tmpToolSize = Math.min(25, Math.max(1, (int)(tmpSize)));
						g2d.fillOval(7+24+ (48*i) - (tmpToolSize/2), 49+24 + (48*j) - (tmpToolSize/2), tmpToolSize, tmpToolSize);
						g2d.setFont(DIN.deriveFont(10f));
						g2d.drawString(tmpSize+"", 7+24 + (48*i) - (g2d.getFontMetrics().stringWidth(tmpSize+"")/2), 4+92+ (48*j));*/
					}
				}
			}
			selectTool = -2;
		}
		
		int tmpValue = panelLayerPosY + (panelLayerSizeY * panelLayerMaxShow);
		if(mouseCheck(panelLayerPosX-7, tmpValue, panelLayerPosX+panelLayerSizeX, tmpValue+44)){
			addLayer(core.CollaborativeBoard.Nickname + "'s Layer", core.CollaborativeBoard.Nickname);
		}
		//g.drawLine(panelLayerPosX-7, tmp, panelLayerPosX+panelLayerSizeX, tmp)
		
		if(onLayerPanel()){//System.out.println("asd");
			for(int i=0; i<Math.min(layerList.size(), panelLayerMaxShow+2); i++){
				if(mouseY > (7+panelSlotPos[i]) && mouseY < 7+panelSlotPos[i+1]){
					index = panelSlotID[i+1];
					//System.out.println((7+panelSlotPos[i]) + " < " + mouseY + " < " +(7+panelSlotPos[i+1]));
				}
			}
		
			for(int i=0; i<5; i++){
				int tmpX = panelLayerPosX - 7;
				int tmpY = panelLayerPosY + panelButtonPosY;
				if(mouseCheck(tmpX+(i*36), tmpY, tmpX+((i+1)*36)-1, tmpY+36)){
					//System.out.println(i);
					switch(i){
						case 0:
							layerList.get(index).hidden = !layerList.get(index).hidden;
							System.out.println(layerList.get(index).hidden);
							refreshBuffer(index);
							break;
						case 1:
							
							break;
						case 2:
							if(index > 0){
								int tmpLayerID = index - 1;
								Layer tmpLayer = layerList.get(index);
								layerList.set(index, layerList.get(tmpLayerID));
								layerList.set(tmpLayerID, tmpLayer);
									
								BufferedImage tmpLayerBuffer = layerBuffer.get(index);
								layerBuffer.set(index, layerBuffer.get(tmpLayerID));
								layerBuffer.set(tmpLayerID, tmpLayerBuffer);
								
								index = tmpLayerID;
							}
							break;
						case 3:
							int tmpLayerID = layerList.size()-1;
							if(index < tmpLayerID){
								tmpLayerID = index + 1;
								Layer tmpLayer = layerList.get(index);
								layerList.set(index, layerList.get(tmpLayerID));
								layerList.set(tmpLayerID, tmpLayer);
									
								BufferedImage tmpLayerBuffer = layerBuffer.get(index);
								layerBuffer.set(index, layerBuffer.get(tmpLayerID));
								layerBuffer.set(tmpLayerID, tmpLayerBuffer);
								
								index = tmpLayerID;
							}
							break;
							
							
						case 4:
							if(layerList.size() > 1){
								layerList.remove(index);
								layerBuffer.remove(index);
								if(index > 0){ 
									index = index-1;
								}
							}
							break;
					}
				}
			}
		}
		
		//System.out.println(selectTool + ", penrecord " + penRecord);
		if(selectTool == -1 && mouseLeft(e)){
			//System.out.println("Start with " + selectTool);
			pointList = null;
			System.gc();
			pointList = new ArrayList<Point>();
			penRecord = true;
			if(ifDrawTool()){
				//System.out.println("Draw Tool Confirm >> " + tool);
				//System.out.println("Started " + penRecord);
				
				if(tool == 3){
					pointList.add(new Point(mouseX, mouseY));
					pointList.add(new Point(mouseX, mouseY));
				}
				if(tool == 4){
					pointList.add(new Point(mouseX, mouseY));
					pointList.add(new Point(mouseX, mouseY));
					pointList.add(new Point(mouseX, mouseY));
					pointList.add(new Point(mouseX, mouseY));
					pointList.add(new Point(mouseX, mouseY));
				}
			}
			//System.out.println(pointList.size());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(canPickMinor || canPickMajor){
			addMacroColor();
		}
		canDrag = false;
		canPickMinor = false;
		canPickMajor = false;
		
		if(penRecord == true){
			if(ifDrawTool()){
				if(pointList.size() == 1){
					pointList.add(new Point(mouseX, mouseY));
				}else if(pointList.size() == 0){
					pointList.add(new Point(mouseX, mouseY));
					pointList.add(new Point(mouseX, mouseY));
				}
				
				ArrayList<Point> tmpLine = new ArrayList<Point>();
				for(Point i:pointList){
					tmpLine.add(new Point(i.x - cameraX, i.y - cameraY));
				}
				
				Line line = new Line(tmpLine, toolSize, toolColor);
				layerList.get(index).data.add(line);
				
				applyLine(index, line);
				//System.out.println("Ended " + penRecord + "(Buffer "+ layerBuffer.size() +", Use "+ index +")");
				pointList = null;
				line = null;
				System.gc();
			}
			refreshPanelLayer();
			repaint();
			penRecord = false;
		}
		if(pageShift){
			for(int i=0; i<layerList.size(); i++){
				refreshBuffer(index);
			}
			//refreshPanelLayer();
			//repaint();
			pageShift = false;
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
		mouseRX = mouseX + cameraX;
		mouseRY = mouseY + cameraY;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		updateMousePosition(e);
		
		if(canDrag){
			sizeDrag();
			refreshSizePanel();
			repaint();
		}
		
		if(canPickMinor){
			colorPick();
			refreshColorPanel();
			repaint();
		}
		
		if(canPickMajor){
			colorMajorPick();
			refreshColorPanel();
			repaint();
		}

		if(keyShift){
			pageShift = true;
			cameraX = cameraPressX-mousePressX+mouseX;
			cameraY = cameraPressY-mousePressY+mouseY;
			refreshBuffer(index);
			refreshPanelLayer();
			repaint();
			//System.out.println(cameraX +", "+cameraY);
			return;
		}
		
		if(penRecord == true){
			switch(tool){
				case 2: 
					pointList.add(new Point(mouseX, mouseY));
					break;
				case 3:
					pointList.get(1).x = mouseX;
					pointList.get(1).y = mouseY;
					break;
				case 4:
					pointList.get(1).x = mouseX;
					pointList.get(2).x = mouseX;
					pointList.get(2).y = mouseY;
					pointList.get(3).y = mouseY;
					break;
				case 5:
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
				case 7:
					/*ArrayList<Integer> removeLineID = new ArrayList<Integer>();
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
								//System.out.println("0-"+(removePointOffset.get(i)-1)+" "+(removePointOffset.get(i)+1)+"-"+(line.data.size()-1));
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
					System.gc();*/
					break;
				case 6:
					ArrayList<Line> removeBuffer = new ArrayList<Line>();
					for(Line i:layerList.get(index).data){
						for(Point j:i.data){
							if(getDistance(mouseX, mouseY,(int)j.x + cameraX, (int)j.y + cameraY) < 12.5f){
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
		//System.out.println(e.getWheelRotation());
		panelLayerScroll -= e.getWheelRotation() * 16;
		//System.out.println(layerList.size()-panelLayerMaxShow);
		panelLayerScroll = Math.max(Math.min(panelLayerScroll, panelLayerSizeY * Math.max(layerList.size()-panelLayerMaxShow, 0)), 0);
		refreshPanelLayer();
		repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
		/*refreshPanelLayer();
		invalidate();*/
		//updateAll();
		System.out.println("resized");
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
