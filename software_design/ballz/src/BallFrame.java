
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @param args
 * frame to display a panel showing 
 * the motion of randomly colored balls
 * and the number of runnables at bottom
 */

public class BallFrame extends JFrame
{
   private BallPanel drawPanel;
   private ArrayList<Ball> Balls= new ArrayList<Ball>();
   private ArrayList<Color> ballColors;
   private JLabel counterLabel;
   
   //GUI design
   public BallFrame()  
   {
      super("Color Ball Threading");
      //create ball panel with dimensions
      drawPanel = new BallPanel(500, 400);
      add(drawPanel, BorderLayout.CENTER);
      //show the number of balls on screen
      counterLabel = new JLabel("",JLabel.CENTER);
      add(counterLabel, BorderLayout.SOUTH);
   }

   /* creates a ball and changes its position
    * based on its current speed. reverses 
    * direction when it hits a wall.
    * */
 
   private class Ball implements Runnable
   {
      private Ellipse2D.Double currentBall;
      private boolean ballMoving;
      private int size, refresh, speedx, speedy;          
      private Color randomColor;
  
      //initial ball conditions
      public Ball()
      {  
         //refresh is the break, in ms., from run time A to B
         refresh = 100;
         //position, speed, and size
         int startx = 200;
         int starty = 200;
         speedx = 25;
         speedy = 25;
         size = 50;
         //loop variable
         ballMoving = true;
         //random color for each ball
         Random rand = new Random();
         float red = rand.nextFloat();
         float green = rand.nextFloat();
         float blue = rand.nextFloat();
         randomColor = new Color(red, green, blue);
         //create graphics object
         currentBall = new Ellipse2D.Double(startx, starty, size, size);
      }

      public void run()
      {
    	 //keep running the task while the ball is moving
    	  while(ballMoving)
    	  {
        	 try 
        	 {
        		 Thread.sleep(refresh);
        	 }
        	 catch (InterruptedException e)
        	 {
        		 System.out.println("Woke up prematurely");
        	 }

	         //calculate new position and move ball
	          int oldx = (int) currentBall.getX();
	          int oldy = (int) currentBall.getY();
	          int newx = oldx + speedx;
	          if (newx + size > drawPanel.getWidth() || newx < 0)
	          {
	        	  speedx = -speedx;
	          }
	               
	          int newy = oldy + speedy;
	          if (newy + size > drawPanel.getHeight() || newy < 0) 
	          {
	        	  speedy = -speedy; 
	          }      
	          currentBall.setFrame(newx, newy, size, size);
	          //call paint method of panel
	          drawPanel.repaint();
         }
      }

      //drawing method for each ball
      public void draw(Graphics2D g_2d)
      {
    	 //check if ball initializes properly
         if (currentBall != null)
         {
        	g_2d.setColor(randomColor);
            g_2d.fill(currentBall);
         }
      }
      
   } // end Ball inner class


   // Panel class for the balls to move
   private class BallPanel extends JPanel  {
	  //dimensions of panel
      private int width, height;
      //initialize task executor
      private ExecutorService executor = Executors.newCachedThreadPool();
 
      public BallPanel (int w, int h)
      {
         width = w;
         height = h;
      
         // add ball when mouse is clicked
         addMouseListener(new MouseAdapter() 
         {
        	 public void mouseClicked(MouseEvent e)
        	 {
        		 //start new ball 
                 Ball nextBall = new Ball();
                 Balls.add(nextBall);
                 executor.execute(nextBall);
                 //show number of balls running
                 counterLabel.setText("Ball #: " + Balls.size());
             }
         }
          );
      }
      
      //paint every ball in arraylist when panel's paint function is called
      public void paintComponent (Graphics g)  
      {
          super.paintComponent(g);
          Graphics2D g_2d = (Graphics2D) g;
          
          for (int i = 0; i < Balls.size(); i++) 
          { 
             Balls.get(i).draw(g_2d);
          }
      }
   } // end BallPanel inner class
   
} // end BallFrame