package core;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
			   File file1 = new File(filename);
			   FileReader fReader;
			try {
				fReader = new FileReader(file1);
				BufferedReader bufReader = new BufferedReader(fReader);
				System.out.println("Load " + filename);
			   
				
				String paper_name = bufReader.readLine(); //1.Paper name
				String paper_author = bufReader.readLine();//2.Author name
				Paper paper = new Paper(paper_name,paper_author);
				int user_qty = Integer.parseInt(bufReader.readLine());
				for(int i=0;i<user_qty;i++){
					paper.addUser(bufReader.readLine());
				}
				
				int layer_qty = Integer.parseInt(bufReader.readLine());
				
				for(int i=0;i<layer_qty;i++){
					String layer_name = bufReader.readLine();
					String layer_author = bufReader.readLine();
					Layer layer = new Layer(layer_name,layer_author);
					layer.hidden = Boolean.parseBoolean(bufReader.readLine());
					int user_number = Integer.parseInt(bufReader.readLine());
					for(int j=0;j<user_number;j++){
						layer.addUser(bufReader.readLine());
					}
					int line_qty = Integer.parseInt(bufReader.readLine());
					for(int j=0;j<line_qty;j++){
						float size = Float.parseFloat(bufReader.readLine());
						int r = Integer.parseInt(bufReader.readLine());
						int g = Integer.parseInt(bufReader.readLine());
						int b = Integer.parseInt(bufReader.readLine());
						
						int point_qty = Integer.parseInt(bufReader.readLine());
						ArrayList<Point> tuple = new ArrayList(); 
						for(int k=0;k<point_qty;k++){
							int x = Integer.parseInt(bufReader.readLine());
							int y = Integer.parseInt(bufReader.readLine());
							tuple.add(new Point(x,y));
						}
						Line line = new Line(tuple, size, new Color(r,g,b));
						layer.data.add(line);
					}
					paper.data.add(layer);
				}
				
				bufReader.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		   }
		   if(input.next().equals("/save")){
			   
			   String filename = input.next();
			   
			   Paper paper = new Paper("Test Paper", "Admin");
			   paper.data = gui.Window.windowMain.layerList;
			   paper.user.add("Mark");
			   paper.user.add("Jimmy");
			   paper.user.add("Guide");
			   paper.user.add("KuayGuide");
			   
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
					   buffer.write(i.data.size()+"");
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
				   buffer.close();
				   System.out.println("Save " + filename);
			   } catch (IOException e) {
				   e.printStackTrace();
				   System.out.println("Cannot call file writer!");
			   } 
			   //enter code here to save
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