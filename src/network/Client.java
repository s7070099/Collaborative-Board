package network;

import java.net.*; 
import java.io.*;

public class Client
{ 
	public boolean connect(String name, String serverIP, int serverPort){
		Socket s = null;
		try { 
            s = new Socket(serverIP, 11111); 
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
         
        BufferedReader in = null; 
        PrintWriter out = null;
        
         
        try
        { 
            in = new BufferedReader(new InputStreamReader(s.getInputStream())); 
            out = new PrintWriter(new OutputStreamWriter(s.getOutputStream())); 

            out.println("What is going on Server?"); 
            out.flush(); 

            System.out.println(in.readLine()); 
             
            out.println("Quit"); 
            out.flush(); 
            
            while(true){
            	
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
                // Close the input and output streams 
                out.close(); 
                in.close(); 
                // Close the socket before quitting 
                s.close(); 
            } 
            catch(Exception e) 
            { 
                e.printStackTrace(); 
            }                
        }  
        return true;
	}
    /*public static void main(String[] args)
    { 
        // First parameter has to be Server host name or IP address 
        if(args.length == 0) 
        { 
            System.out.println("Usage : SocketClient <serverName>"); 
            return; 
        } 
        
        Socket s = null; 
         
        // Create the socket connection to the MultiThreadedSocketServer port 11111 
        try
        { 
            s = new Socket(args[0], 11111); 
        }        
        catch(UnknownHostException uhe) 
        { 
            // Server Host unreachable 
            System.out.println("Unknown Host :" + args[0]); 
            s = null; 
        } 
        catch(IOException ioe) 
        { 
            // Cannot connect to port on given server host 
            System.out.println("Cant connect to server at 11111. Make sure it is running."); 
            s = null; 
        } 
         
        if(s == null) 
            System.exit(-1); 
         
        BufferedReader in = null; 
        PrintWriter out = null; 
         
        try
        { 
            // Create the streams to send and receive information 
            in = new BufferedReader(new InputStreamReader(s.getInputStream())); 
            out = new PrintWriter(new OutputStreamWriter(s.getOutputStream())); 
             
            // Since this is the client, we will initiate the talking. 
            // Send a string data and flush 
            out.println("What is going on Server?"); 
            out.flush(); 
            // Receive the reply. 
            System.out.println(in.readLine()); 
             
            // Send the special string to tell server to quit. 
            out.println("Quit"); 
            out.flush(); 
        } 
        catch(IOException ioe) 
        { 
            System.out.println("Exception during communication. Server probably closed connection."); 
        } 
        finally
        { 
            try
            { 
                // Close the input and output streams 
                out.close(); 
                in.close(); 
                // Close the socket before quitting 
                s.close(); 
            } 
            catch(Exception e) 
            { 
                e.printStackTrace(); 
            }                
        }        
    } */
}