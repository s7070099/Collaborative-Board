package network;

import java.net.*;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.*;

import java.awt.Color;
import java.io.*;

public class Client
{ 
	public boolean connected = true;
	Socket s = null;
	BufferedReader in = null; 
    public PrintWriter out = null;
    
    class getPassword extends JDialog {
    	public getPassword(){
    		
    	}
    }
	
	public boolean connect(String name, String serverIP, int serverPort){
		connected = true;
		try { 
            s = new Socket(serverIP, serverPort); 
        } catch(UnknownHostException uhe) { 
            System.out.println("Unknown Host:" + serverIP); 
            s = null;
        } 
        catch(IOException ioe) 
        { 
            System.out.println("Cant connect to server at "+ serverPort +". Make sure it is running."); 
            s = null; 
        } 
         
        if(s == null) 
            return false;
        
        try
        { 
            in = new BufferedReader(new InputStreamReader(s.getInputStream())); 
            out = new PrintWriter(new OutputStreamWriter(s.getOutputStream())); 
            
            out.println("connect");
            out.println(Network.nickname);
            out.println(Network.ssid);
            out.flush();
            
            long myNextOutputTime = System.currentTimeMillis() + 30000;
            
            String cmd = null;
            while(connected){
            	try {
            		cmd = in.readLine();
                    /*if (cmd == null)
                    {
                        System.out.println("Client Disconnected!");
                        connected = false;
                    }*/
                    if(cmd.equals("connect")){
                    	String ssid = in.readLine();
                    	Network.serverCaption = in.readLine();
                    	try {
                			File file = new File("user.key");
                			if(file.exists()){
                				FileWriter fWriter = new FileWriter(file);
                				BufferedWriter buffer = new BufferedWriter(fWriter);
                				buffer.write(ssid);
                				buffer.close();
                			}
                		} catch (IOException e) {
                			e.printStackTrace();
                			ssid = "";
                		}
                    	out.println("getlist");
                    	out.flush();
                    }else if(cmd.equals("sv_full")){
                    	JOptionPane.showMessageDialog (null, "server is full !", "ERROR", JOptionPane.ERROR_MESSAGE);
                    	CollaborativeBoard.window.windowLogin.panel.setVisible(false);
                    	CollaborativeBoard.window.windowLogin.panelConnecting.setVisible(true);
                    }else if(cmd.equals("getlist")){
                    	Network.serverCaption = in.readLine();
                    	Network.paperCount = Integer.parseInt(in.readLine());
                    	
                    	Network.paper = new ArrayList<Paper>();
                    	for(int i=0; i<Network.paperCount; i++){
                    		Paper tmpPaper = new Paper("", "", "");
                    		int userCount = Integer.parseInt(in.readLine());
                    		System.out.println(userCount);
                    		for(int j=0; j<userCount; j++){
                    			tmpPaper.onlineUser.add(in.readLine());
                    		}
                    		tmpPaper.name = in.readLine();
                    		tmpPaper.author = in.readLine();
                    		tmpPaper.password = in.readLine();
                    		Network.paper.add(tmpPaper);
                    		tmpPaper = null;
                    		System.gc();
                    	}
                    	if(CollaborativeBoard.window.windowSelectPaper != null){
                    		CollaborativeBoard.window.windowSelectPaper.refreshPaper();
                    	}else{
                    		CollaborativeBoard.window.goToSelectPaper();
                    	}
                    }else if(cmd.equals("getpaper")){
                    	Network.paperName = in.readLine();
                    	Network.paperAuthor = in.readLine();
                    	int layerCount = Integer.parseInt(in.readLine());
                    	Network.layerList = new ArrayList<Layer>();
                    	for(int i=0; i<layerCount; i++){
                    		Layer layer = new Layer(in.readLine(), in.readLine());
                    		int lineCount = Integer.parseInt(in.readLine());
                    		ArrayList<Line> lineList = new ArrayList<Line>();
                    		for(int j=0; j<lineCount; j++){
                    			int id = Integer.parseInt(in.readLine());
                    			float size = Float.parseFloat(in.readLine());
                    			int r = Integer.parseInt(in.readLine());
                    			int g = Integer.parseInt(in.readLine());
                    			int b = Integer.parseInt(in.readLine());
                    			int pointCount = Integer.parseInt(in.readLine());
                    			ArrayList<Point> point = new ArrayList<Point>();
                    			for(int k=0; k<pointCount; k++){
                    				int x = Integer.parseInt(in.readLine());
                    				int y = Integer.parseInt(in.readLine());
                    				point.add(new Point(x, y));
                    			}
                    			Line line = new Line(point, size, new Color(r, g, b));
                    			line.id = id;
                    			line.data = point;
                    			lineList.add(line);
                    		}
                    		layer.data = lineList;
                    		Network.layerList.add(layer);
                    	}
                    	CollaborativeBoard.window.goToPaper();
                    }else if(cmd.equals("getpassword")){
                    	new getPassword();
                    }else if(cmd.equals("paper_ename")){
                    	CollaborativeBoard.window.windowSelectPaper.lock = false;
                    }else if(cmd.equals("addpaper")){
                    	CollaborativeBoard.window.goToPaper();
                    }else if(cmd.equals("exitpaper")){
                    	Network.layerList = null;
                    	System.gc();
                    	out.println("getlist");
                    	out.flush();
                    }else if(cmd.equals("lineindex")){
                    	if(CollaborativeBoard.window.windowMain != null){
                    		CollaborativeBoard.window.windowMain.lineIndex = Integer.parseInt(in.readLine());
                    	}
                    }
                }
                catch(/*java.net.SocketTimeoutException*/ Exception e)
                {
                	e.printStackTrace();
                    System.out.println("Timed out trying to read from socket");
                }
            	
            	if (connected && (System.currentTimeMillis() - myNextOutputTime > 0))
                {
                    out.println("ping");
                    myNextOutputTime += 30000;
                }
            }
        } 
        catch(IOException ioe) 
        { 
            System.out.println("Exception during communication. Server probably closed connection."); 
        } 
        finally
        {
            try
            { 
                out.close(); 
                in.close(); 
                s.close(); 
            } 
            catch(Exception e) 
            { 
                e.printStackTrace(); 
            }                
        }
        return true;
	}
	
	public boolean disconnnect(){
		try
        { 
            out.close(); 
            in.close(); 
            s.close(); 
        } 
        catch(Exception e) 
        { 
            e.printStackTrace(); 
        }   
		connected = false;
		return true;
	}
}