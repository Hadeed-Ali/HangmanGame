// FILE: SoundPlayer.java
// PURPOSE: This file is responsible for setting up and allowing for sounds to be played from the files upon a guess being made

// Declaring the package name
package culminating;

// Import statements that are necessary for working with audio files
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

// Public class, SoundPlayer, is defined to play sound files
public class SoundPlayer {
	
	// Static method playSound that plays a given sound file, no object needed
    public static void playSound(String soundFileName) {
        new Thread(() -> {
            try {
            	
            	// Loading the sound file from the sounds folder
                URL soundURL = SoundPlayer.class.getResource("/sounds/" + soundFileName);
                
                // Error prevention to return null if the sound file is unavailable
                if (soundURL == null) {
                    System.err.println("Sound file not found: /sounds/" + soundFileName);
                    return;
                }
                
                // Loading the audio system by reading the sound file and readying it to be played as an audio stream
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                
                // Listener that closes resources upon hearing that the sound is finished to preserve memory
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                        try {
                            audioStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                
                // Loading the audio and playing it outloud
                clip.open(audioStream);
                clip.start();
                
              // Catch statement that checks for any errors with obtaining/playing the audio file and printing an error statement
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error playing sound " + soundFileName + ": " + e.getMessage());
            }
            
        // Ending the thread and starting it, allowing the sound to play asynchronously 
        }).start();
    }
} 