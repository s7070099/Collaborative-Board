package network;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
 import core.*;
 
public class Server {
	ArrayList<Paper> paperList = new ArrayList<Paper>();
	ArrayList<UserData> userData = new ArrayList<UserData>();
	ArrayList<ClientServiceThread> clientList = new ArrayList<ClientServiceThread>();
	ServerSocket myServerSocket;
    public boolean ServerOn = true;
    
    public void print(String text){
    	Network.print(text);
    }
    
    public boolean stop(){
    	for(ClientServiceThread i:clientList){
    		i.m_bRunThread = false;
    	}
    	ServerOn = false;
    	try { 
            myServerSocket.close(); 
            print("Server Stopped"); 
        } catch(Exception ioe) { 
        	print("Problem stopping server socket"); 
        	return false;
        }
    	return true;
    }
 
    public boolean start(){
    	ServerOn = true;
        try { 
            myServerSocket = new ServerSocket(Network.serverPort);
            myServerSocket.setReuseAddress(true);
            print("Start server successfully !");
        } catch(IOException ioe) { 
        	print("Could not create server socket on port "+Network.serverPort+". Quitting."); 
            return false;
        } 
        
        while(ServerOn) {                        
            try { 
            	Socket clientSocket = myServerSocket.accept();
            	//clientSocket.setSoTimeout(1000);
                ClientServiceThread cliThread = new ClientServiceThread(clientSocket);
                cliThread.start(); 
                clientList.add(cliThread);
            } catch(IOException ioe) { 
            	print("Exception encountered on accept. Ignoring. Stack Trace :"); 
                ioe.printStackTrace();
            } 
        }
        return stop();
    }

    class ClientServiceThread extends Thread { 
        Socket myClientSocket;
        BufferedReader in = null; 
        PrintWriter out = null; 
        public boolean m_bRunThread = true;
        
        String name = "";
        String ssid = "";
        String paperName = "";
        String layerName = "";
        int lineIndex = 0;
 
        public ClientServiceThread(){ 
            super(); 
        } 
 
        ClientServiceThread(Socket s){ 
            myClientSocket = s; 
        } 
        
        public int getID(){
        	int uid = -1;
        	for(int i=0; i<userData.size(); i++){
        		if(userData.get(i).name.equals(name)){
        			uid = i;
        		}
        	}
        	return uid;
        }
        
        public ArrayList<String> getPaperOnline(int paperID){
        	ArrayList<String> user = new ArrayList<String>();
        	for(int i=0; i<userData.size(); i++){
        		if(userData.get(i).paper.equals(paperList.get(paperID).name)) user.add(userData.get(i).name);
        		//print(userData.get(i).paper +" = " +paperList.get(paperID).name);
        	}
        	return user;
        }
        
        public boolean checkName(String name){
        	for(int i=0; i<userData.size(); i++){
        		if(userData.get(i).name.equals(name)) return false;
        	}
        	return true;
        }
        
        public int onlineUser(){
        	int count = 0;
        	for(int i=0; i<userData.size(); i++){
        		if(userData.get(i).paper != "") count++;
        	}
        	return count;
        }
        
        public String ssidToName(String ssid){
        	String result = "";
        	for(int i=0; i<userData.size(); i++){
        		if(userData.get(i).ssid.equals(ssid)) result = userData.get(i).name;
        	}
        	return result;
        }
        
        public String nameToSSID(String name){
        	String result = "";
        	for(int i=0; i<userData.size(); i++){
        		if(userData.get(i).name.equals(name)) result = userData.get(i).ssid;
        	}
        	return result;
        }
        
        public int nameToID(String name){    
        	int count = 0;
        	for(int i=0; i<userData.size(); i++){
        		if(userData.get(i).name.equals(name)) {
        			break;
        		}else{
        			count++;
        		}
        	}
        	return count;
        }
        
        public int paperNameToID(String paperName){
        	int result = -1;
        	for(int i=0; i<paperList.size(); i++){
        		if(paperList.get(i).name.equals(paperName)) result = i;
        	}
        	return result;
        }
        
        public int layerNameToID(int paperID, String layerName){
        	int result = -1;
        	ArrayList<Layer> layer = paperList.get(paperID).data;
        	for(int i=0; i<layer.size(); i++){
        		//print(layer.get(i).name);
        		if(layer.get(i).name.equals(layerName)) result = i;
        	}
        	return result;
        }     
        
        public int getLineIndex(int paperID, int layerID){
        	int count = 0;
        	for(int i=0; i<paperList.get(paperID).data.get(layerID).data.size(); i++){
        		if(paperList.get(paperID).data.get(layerID).data.get(i).id == lineIndex) {
        			break;
        		}else{
        			count++;
        		}
        	}
        	//print(count+"");
        	return count;
        }
 
        public void run(){ 
            print("incomming connection " + myClientSocket.getInetAddress().getHostAddress()/*.getHostName()*/ + ":" + myClientSocket.getPort() + "..."); 
 
            try{                                
                in = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream())); 
                out = new PrintWriter(new OutputStreamWriter(myClientSocket.getOutputStream())); 

                while(m_bRunThread) {   
                	try {
                		
	                    String cmd = in.readLine();
	                    print("[Raw Packet: " + cmd + "]");
	                    if(cmd.equals("connect")){
	                    	name = in.readLine();
	                    	ssid = in.readLine();
	                    	
	                    	if(onlineUser() == Network.serverUser){
	                    		out.println("sv_full");
	                    		out.flush();
	                    		m_bRunThread = false;
	                    	}
	                    	
	                    	int uid = -1;
	                    	for(int i=0; i<userData.size(); i++){
	                    		if(userData.get(i).ssid.equals(ssid)){
	                    			uid = i;
	                    		}
	                    	}
	                    	
	                    	if(uid == -1){
	                    		ssid = UUID.randomUUID().toString();
	                    		print("server generate a new hash [" + ssid + "] for user " + name + " !");
	                    		UserData ud = new UserData(name, ssid);
	                    		//ud.s = myClientSocket;
	                    		userData.add(ud);
	                    	}
	                    	out.println("connect");
	                    	out.println(ssid);
	                    	out.println(Network.serverCaption);
	                    	out.flush();
	                    	
	                    	print("user "+ name +" is connected.");
	                    }else if(cmd.equals("getlist")){
	                    	int paperCount = paperList.size();
	                    	out.println("getlist");
	                    	out.println(Network.serverCaption);
	                    	out.println(paperCount);
	                    	for(int i=0; i<paperCount; i++){
	                    		ArrayList<String> tmpUser = getPaperOnline(i);
	                    		out.println(tmpUser.size());
	                    		for(String j:tmpUser){
	                    			out.println(j);
	                    		}
	                    		out.println(paperList.get(i).name);
	                    		out.println(ssidToName(paperList.get(i).author));
	                    		out.println(paperList.get(i).password);
	                    	}
	                    	out.flush();
	                    	print("user "+ name +" get paper list.");
	                    }else if(cmd.equals("getpaper")){
	                    	int paperID = Integer.parseInt(in.readLine());
	                    	String password = in.readLine();
	                    	Paper paper = paperList.get(paperID);
	                    	if(paper.password.equals(password) || paper.author.equals(ssid)){;
	                        	out.println("getpaper");
	                        	out.println(paper.name);
	                        	out.println(ssidToName(ssidToName(paper.author)));
	                        	out.println(paper.data.size());
	                        	for(int i=0; i<paper.data.size(); i++){
	                        		Layer layer = paper.data.get(i);
	                        		out.println(layer.name);
	                        		out.println(ssidToName(layer.author));
	                        		out.println(layer.data.size());
	                        		for(int j=0; j<layer.data.size(); j++){
	                        			Line line = layer.data.get(j);
	                        			out.println(line.id);
	                        			out.println(line.size);
		                        		out.println(line.color.getRed());
		                        		out.println(line.color.getGreen());
		                        		out.println(line.color.getBlue());
		                        		out.println(line.data.size());
		                        		for(int k=0; k<line.data.size(); k++){
		                        			Point point = line.data.get(k);
		                        			out.println(point.x);
			                        		out.println(point.y);
		                        		}
	                        		}
	                        	}
	                        	out.flush();
	                        	paperName = paperList.get(paperID).name;
	                        	layerName = "Default";
	                        	print("user " + name + " is get paper.");
	                    	}else{
	                    		out.println("getpassword");
		                    	out.flush();
		                    	print("require password from " + name);
	                    	}
	                    }else if(cmd.equals("addpaper")){
	                    	String tmpName = in.readLine();
	                    	String password = in.readLine();
	                    	int tmpSize = paperList.size();
	                    	boolean canAdd = true;
	                    	for(int i=0; i<tmpSize; i++){
	                    		if(paperList.get(i).name.equals(tmpName)) canAdd = false;
	                    	}
	                    	if(canAdd){
		                    	paperList.add(new Paper(tmpName, ssid, password));
		                    	print("user "+ name +" add paper "+tmpName+" with password " + password + " Successfully.");
		                    	out.println("addpaper");
		                    	out.flush();
		                    	paperName = tmpName;
		                    	layerName = "Default";
		                    	userData.get(nameToID(name)).paper = paperName;
	                    	}else{
	                    		print("user "+ name +" add paper "+tmpName+" but "+tmpName+" is Already !");
	                    		out.println("paper_ename");
		                    	out.flush();
	                    	}
	                    }else if(cmd.equals("exitpaper")){
	                    	//broadcast to another.
	                    	out.println("exitpaper");
	                    	out.flush();
	                    }else if(cmd.equals("al")){
	                    	float size = Float.parseFloat(in.readLine());
	                    	int r = Integer.parseInt(in.readLine());
	                    	int g = Integer.parseInt(in.readLine());
	                    	int b = Integer.parseInt(in.readLine());
	                    	
	                    	int paperID = paperNameToID(paperName);
	                    	int layerID = layerNameToID(paperID, layerName);
	                    	
	                    	Line line = new Line(new ArrayList<Point>(), size, new Color(r, g, b));
	                    	
	                    	lineIndex = paperList.get(paperID).data.get(layerID).lineIndex;
	                    	paperList.get(paperID).data.get(layerID).lineIndex++;
	                    	line.id = lineIndex;
	                    	
	                    	paperList.get(paperID).data.get(layerID).data.add(line);
	                    	
	                    	out.println("lineindex");
	                    	out.println(lineIndex);
	                    	out.flush();
	                    	
	                    	/*for(int i=0; i<userData.size(); i++){
	                    		if(userData.get(i).paper.equals(paperList.get(paperID).name) && userData.get(i).name != name){
	                    			PrintWriter out2 = new PrintWriter(new OutputStreamWriter(userData.get(i).s.getOutputStream()));
	                    			out2.println("al");
	                    			out2.println(size);
	                    			out2.println(r);
	                    			out2.println(g);
	                    			out2.println(b);
	                    			out2.flush();
	                    			out2.close();
	                    		}
	                    	}*/
	                    }else if(cmd.equals("ap")){
	                    	int x = Integer.parseInt(in.readLine());
	                    	int y = Integer.parseInt(in.readLine());
	                    	
	                    	int paperID = paperNameToID(paperName);
	                    	int layerID = layerNameToID(paperID, layerName);
	                    	
	                    	Point point = new Point(x, y);
	                    	
	                    	int realIndex = getLineIndex(paperID, layerID);

	                    	paperList.get(paperID).data.get(layerID).data.get(realIndex).data.add(point);
	                    	
	                    	/*for(int i=0; i<userData.size(); i++){
	                    		if(userData.get(i).paper.equals(paperList.get(paperID).name) && userData.get(i).name != name){
	                    			PrintWriter out2 = new PrintWriter(new OutputStreamWriter(userData.get(i).s.getOutputStream()));
	                    			out2.println("ap");
	                    			out2.println(x);
	                    			out2.println(y);
	                    			out2.flush();
	                    			out2.close();
	                    		}
	                    	}*/
	                    }else if(cmd.equals("rm")){
	                    	int lineID = Integer.parseInt(in.readLine());
	                    	
	                    	int paperID = paperNameToID(paperName);
	                    	int layerID = layerNameToID(paperID, layerName);
	                    	
	                    	int realIndex = getLineIndex(paperID, layerID);
	                    	
	                    	paperList.get(paperID).data.get(layerID).data.get(realIndex).data.remove(lineID);
	                    }
                	}catch(java.net.SocketTimeoutException e)
                    {
                        System.out.println("Timed out trying to read from socket");
                    }
                } 
            }catch(Exception e){ 
                e.printStackTrace(); 
            }finally{ 
                try{                    
                    in.close(); 
                    out.close(); 
                    myClientSocket.close(); 
                    print("...Stopped"); 
                }catch(IOException ioe){ 
                    ioe.printStackTrace(); 
                } 
            } 
        } 
    } 
}