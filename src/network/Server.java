package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
 
 
public class Server {
 
	ServerSocket myServerSocket;
    public boolean ServerOn = true;
    
    public void print(String text){
    	Network.print(text);
    }
 
    public boolean start() 
    {
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
                ClientServiceThread cliThread = new ClientServiceThread(clientSocket);
                cliThread.start(); 
            } catch(IOException ioe) { 
            	print("Exception encountered on accept. Ignoring. Stack Trace :"); 
                ioe.printStackTrace(); 
            } 
        }
 
        try { 
            myServerSocket.close(); 
            print("Server Stopped"); 
        } catch(Exception ioe) { 
        	print("Problem stopping server socket"); 
        	return false;
        } 
        return true;
    } 

    class ClientServiceThread extends Thread { 
        Socket myClientSocket;
        boolean m_bRunThread = true; 
 
        public ClientServiceThread(){ 
            super(); 
        } 
 
        ClientServiceThread(Socket s){ 
            myClientSocket = s; 
        } 
 
        public void run(){ 
            BufferedReader in = null; 
            PrintWriter out = null; 
            print("Incomming Connection " + myClientSocket.getInetAddress().getHostName() + "..."); 
 
            try
            {                                
                in = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream())); 
                out = new PrintWriter(new OutputStreamWriter(myClientSocket.getOutputStream())); 
 
                while(m_bRunThread) 
                {                 
                    String clientCommand = in.readLine(); 
                    print("Client Says :" + clientCommand);
 
                    if(!ServerOn) 
                    { 
                    	print("Server has already stopped"); 
                        out.println("Server has already stopped"); 
                        out.flush(); 
                        m_bRunThread = false;   
 
                    } 
 
                    if(clientCommand.equalsIgnoreCase("quit")) { 
                        m_bRunThread = false;   
                        print("Stopping client thread for client : "); 
                    } else if(clientCommand.equalsIgnoreCase("end")) { 
                        m_bRunThread = false;   
                        print("Stopping client thread for client : "); 
                        //ServerOn = false;
                    } else {
                        out.println("Server Says : " + clientCommand); 
                        out.flush(); 
                    }
                } 
            } 
            catch(Exception e) 
            { 
                e.printStackTrace(); 
            } 
            finally
            { 
                try
                {                    
                    in.close(); 
                    out.close(); 
                    myClientSocket.close(); 
                    print("...Stopped"); 
                } 
                catch(IOException ioe) 
                { 
                    ioe.printStackTrace(); 
                } 
            } 
        } 
    } 
}