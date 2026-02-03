// FILE: HANGMANPANEL.JAVA
// PURPOSE: This section of the project is responsible for visually illustrating the hangman and the various visuals on the screen. It updates based on guesses made by the user

package culminating;

import javax.swing.*;
import java.awt.*;

// Defining a public class for drawing the hangman, which extends the Jpanel to have the ability to act as a GUI panel with visuals
public class HangmanPanel extends JPanel {
	
   // Defining an integer variable that keeps track of the number of body parts the hangman has
   private int parts = 0;
   
   // The following method, setParts, updates the number of parts the hangman has to be drawn with 
   public void setParts(int p) {
	   
       parts = p;
       
       // (Researched) command that allows for the illustration of the hangman to be refreshed
       repaint();
   }
 
   @Override
   // The main drawing method that is called when the panel requires a redraw
   protected void paintComponent(Graphics g) {
	   
	   // Clearing the panel before drawing any new content
       super.paintComponent(g);
       
       
       // Setting the color of the lines to black
       // Making the line a size of 5 so it is thicker
       Graphics2D g2 = (Graphics2D) g;
       g2.setStroke(new BasicStroke(5)); 
       g.setColor(Color.BLACK);
       
       // Drawing the "gallow" on which the hangman is being hung on
       g.drawLine(50, 250, 150, 250);
       g.drawLine(100, 250, 100, 50);
       g.drawLine(100, 50, 200, 50);
       g.drawLine(200, 50, 200, 80);
       
       // Lines of codes that are responsible for drawing each of the seperate body parts on the hangman
       // Order: Head, Body, Arms, Legs
       if (parts >= 1) g.drawOval(180, 80, 40, 40); 
       if (parts >= 2) g.drawLine(200, 120, 200, 180); 
       if (parts >= 3) g.drawLine(200, 140, 170, 160); 
       if (parts >= 4) g.drawLine(200, 140, 230, 160); 
       if (parts >= 5) g.drawLine(200, 180, 170, 220); 
       if (parts >= 6) g.drawLine(200, 180, 230, 220); 
       
       // Adding eyes to the stickman if the amount of parts is greater than 5 (meaning the final guesss)
       if (parts > 5) {
           g2.setStroke(new BasicStroke(3));
           
           // Drawing the X for the left eye
           g2.drawLine(190, 90, 195, 95);
           g2.drawLine(195, 90, 190, 95);

           // Drawing the X for the right eye
           g2.drawLine(205, 90, 210, 95);
           g2.drawLine(210, 90, 205, 95);
           
   }
   }
}
 