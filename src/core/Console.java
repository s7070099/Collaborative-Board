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
			   //load
			   System.out.println("Load");
		   }
		   
		   if(input.next().equals("/save")){
			   String filename = input.next();
			   //save
			   System.out.println("Save");
		   }
		   //อันนี้เป็นคอลโซล ไว้ ดีบัก
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