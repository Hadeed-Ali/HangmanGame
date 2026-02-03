// FILE: BackgroundPanel.java
// PURPOSE: This file sets up the background images and adds them to the background based off the images stored in the files

package culminating;

// Importing java libraries for creating GUI components and for drawing graphics/images
import javax.swing.*;
import java.awt.*;

// Defining a custom class, BackgroundPanel, that extends JPanel
public class BackgroundPanel extends JPanel {
	
	// Stores the image to be drawn in the background a a variable
    private Image backgroundImage;

    // Method that is called upon the creation of a new backgroundpanel
    public BackgroundPanel(String imagePath) {
        setBackgroundImage(imagePath);
    }

    // Method to set the background image of the game
    public void setBackgroundImage(String imagePath) {
    	
    	// Error prevention to ensure that the file name is valid, otherwise returning null
        if (imagePath != null && !imagePath.isEmpty()) {
            backgroundImage = new ImageIcon(getClass().getResource("/images/" + imagePath)).getImage();
        } else {
            backgroundImage = null;
        }
        
        // Repaint is called to refresh the background
        repaint();
    }

    // The core drawing method and method (paintComponent)
    @Override
    protected void paintComponent(Graphics g) {
    	
    	// Clear the panel of the previous image and replace it with the new one
        super.paintComponent(g);
        
        // The background image is drawn to scale so that it fits the dedicated width and heigth of the panel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
} 