package gui;

public class Window {

	private int screenWidth;
	private int screenHeight;
	
	private WindowPaper windowPaper;
	
	public Window(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		windowPaper = new WindowPaper(screenWidth, screenHeight);
		windowPaper.setVisible(true);

	}
	
}