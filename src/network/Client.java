package network;

import java.net.*; 
import java.io.*;

public class Client
{ 
	public boolean connect(String name, String serverIP, int serverPort){
		Socket s = null;
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
         
        BufferedReader in = null; 
        PrintWriter out = null;
        
        try
        { 
            in = new BufferedReader(new InputStreamReader(s.getInputStream())); 
            out = new PrintWriter(new OutputStreamWriter(s.getOutputStream())); 

            out.println("What is going on Server?"); 
            out.println("Please help me!"); 
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
}