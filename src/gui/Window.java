package gui;

import java.awt.EventQueue;

public class Window {

	private int screenWidth;
	private int screenHeight;
	
	private WindowPaper windowPaper;
	
	public Window(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowPaper windowPaper = new WindowPaper(screenWidth, screenHeight);
					windowPaper.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	
}