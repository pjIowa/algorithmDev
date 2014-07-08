
public class BallFrameTest {
	/**
	 * @param args
	 * Calls an application that allows user to press the 
	 * screen and generate randomly colored balls
	 * that bounce around the panel and it shows the
	 * number of balls running at a time
	 */
	public static void main(String args[])  
	{

	      BallFrame application = new BallFrame();
	      application.setDefaultCloseOperation(BallFrame.EXIT_ON_CLOSE);
	      application.setSize(500, 500);
	      application.setVisible(true);
	}
}