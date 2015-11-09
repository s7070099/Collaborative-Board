package core;

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