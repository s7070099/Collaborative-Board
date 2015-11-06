package core;

import java.util.Scanner;

class Console extends Thread {
	   public void run() {
		   Scanner input = new Scanner(System.in);
		   if(input.next().equals("/show")){
			   if(input.next().equals("paper")){
				   if(input.next().equals("1")){
					   gui.Window.windowPaper.setVisible(true);
				   }else{
					   gui.Window.windowPaper.setVisible(false);
				   }
			   }
		   }
	   }
}