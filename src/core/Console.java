package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Console extends Thread {
   public void run() {
	   Scanner input;
	   while(true){
		   input = new Scanner(System.in);
		   
		   if(input.next().equals("/help")){
			   System.out.println("[HELP]");
			   System.out.println("/show paper [state] - Show or Hide Paper Window (0 or 1)");
		   }
		   
		   if(input.next().equals("/load")){
			   String filename = input.next();
			   
			   //enter code here to load
			   //gui.Window.windowMain.layerList = paper.data;
			   System.out.println("Load " + filename);
		   }
		   
		   if(input.next().equals("/save")){
			   String filename = input.next();
			   
			   Paper paper = new Paper("Test Paper", "Admin");
			   paper.data = gui.Window.windowMain.layerList;
			   paper.user.add("Mark");
			   paper.user.add("Jimmy");
			   paper.user.add("Guide");
			   
			   File file = new File(filename);
			   FileWriter fileWriter;
			   try {
				   fileWriter = new FileWriter(file);
				   BufferedWriter buffer = new BufferedWriter(fileWriter);
				   buffer.write(paper.name);
				   buffer.newLine();
				   buffer.write(paper.author);
				   buffer.newLine();
				   buffer.write(paper.user.size()+"");
				   buffer.newLine();
				   for(String i:paper.user){
					   buffer.write(i);
					   buffer.newLine();
				   }
				   buffer.write(paper.data.size()+"");
				   buffer.newLine();
				   for(Layer i:paper.data){
					   buffer.write(i.name);
					   buffer.newLine();
					   buffer.write(i.author);
					   buffer.newLine();
					   buffer.write(i.hidden+"");
					   buffer.newLine();
					   buffer.write(i.user.size()+"");
					   buffer.newLine();
					   for(String j:i.user){
						   buffer.write(j);
						   buffer.newLine();
					   }
					   buffer.write(i.user.size()+"");
					   buffer.newLine();
					   for(Line j:i.data){
						   buffer.write(j.size+"");
						   buffer.newLine();
						   buffer.write(j.color.getRed()+"");
						   buffer.newLine();
						   buffer.write(j.color.getGreen()+"");
						   buffer.newLine();
						   buffer.write(j.color.getBlue()+"");
						   buffer.newLine();
						   buffer.write(j.data.size()+"");
						   buffer.newLine();
						   for(Point k:j.data){
							   buffer.write(k.x+"");
							   buffer.newLine();
							   buffer.write(k.y+"");
							   buffer.newLine();
						   }
					   }
				   }
				   /*
				  		public String name;
						public String author;
						public Boolean hidden;
						public ArrayList<String> user;
						public ArrayList<Line> data;
				    */
				   System.out.println(paper.user.size());
				   buffer.close();
			   } catch (IOException e) {
				   e.printStackTrace();
				   System.out.println("Cannot call file writer!");
			   } 
			   //enter code here to save
			   System.out.println("Save " + filename);
		   }
		   
		   if(input.next().equals("/debug")){
			   //System.out.println(gui.Window.windowMain.buffer);
		   }
		   
		   if(input.next().equals("/tool")){
			   gui.Window.windowMain.tool = input.nextInt();
		   }

		   if(input.next().equals("/show")){
			   if(input.next().equals("paper")){
				   if(input.next().equals("1")){
					   gui.Window.windowMain.setVisible(true);
					   continue;
				   }else{
					   gui.Window.windowMain.setVisible(false);
					   continue;
				   }
			   }
		   }

		   System.out.println("Command not Found.");
	   }
   }
}