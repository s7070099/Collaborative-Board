package core;

import java.util.Scanner;

class Console extends Thread {
   public void run() {
	   Scanner input;
	   while(true){
		   input = new Scanner(System.in);
		   if(input.next().equals("/show")){
			   if(input.next().equals("paper")){
				   if(input.next().equals("1")){
					   gui.Window.windowPaper.setVisible(true);
					   continue;
				   }else{
					   gui.Window.windowPaper.setVisible(false);
					   continue;
				   }
			   }
		   }
		   System.out.println("Command not Found.");
	   }
   }
}