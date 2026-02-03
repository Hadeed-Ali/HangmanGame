// FILE: MAIN.JAVA
// PURPOSE: This serves as the "base" of the game and where the code is launched from. It contains the main method.

package culminating;

// Importing Java swing as it is the tool required for coding visual components to the game
import javax.swing.*;

// THE MAIN CLASS AND METHOD
public class Main {
	
   public static void main(String[] args) {
	   
	   // Telling the program to run the GUI part of the code later which is responsible for windows and/or the screen
       SwingUtilities.invokeLater(() -> {
           new HangmanGUI();
       });
   }
}

 