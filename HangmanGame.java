// FILE: HANGMANGAME.JAVA
// PURPOSE: This part of the code is responsible for all of the various in game functions that are required for the game to run

package culminating;
import java.util.*;

// Setting up the various classes and variables within the code
public class HangmanGame {
	
   // Reference to the wordbank class in order to obtain words and their categories
   private WordBank wordBank;
   
   // Current word being guessed by the user
   private String word;
   
   // The category the word is in; used for hints
   private String category;
   
   // Array representing the current state of the word (____)
   private char[] currentDisplay;
   
   // Set of the letters already guessed / inputted by the user
   private Set<Character> guessedLetters;
   
   // Integer values that represent the number of wrong guesses made by the user and ther maximum number of guesses for the word
   private int wrongGuesses, maxGuesses;
   
   // Integer that stores the score accumulated by the user
   private int score = 0;
   
   // Integer that stores the numeric value of the number of hints left
   private int hintsLeft;
   
   // String that represents the current difficulty level of the game
   private String difficulty = "Easy";
   
   // Constructor that initializes a new game with a new word and new # of guesses
   public HangmanGame(WordBank wordBank, String difficulty, String category) {
	   this.wordBank = wordBank;
       this.difficulty = difficulty;
       this.category = category;
       initGame();
       
   }
   
   
   // INIT METHOD
   public void initGame() {
	   
	   // Obtaining a random word and its specific category
	   Map.Entry<String, String> entry = wordBank.getRandomWord(category, difficulty); 
       
       // Store the word in uppercase letters
       this.word = entry.getKey().toUpperCase();
       
       // Initializing the display with ____ that show the length of the word
       this.category = entry.getValue();
       this.currentDisplay = new char[word.length()];
       
       for (int i = 0; i < word.length(); i++) {
    	    char c = word.charAt(i);
    	    currentDisplay[i] = (Character.isLetter(c)) ? '_' : c; 
       }
       
       
       // Reset the guessed letters, number of incorrect guesses, and the number of allowed guesses for the new round
       this.guessedLetters = new HashSet<>();
       this.wrongGuesses = 0;
       this.maxGuesses = 6;
       
       
       // Set the numbre of hints available for the user based on the selected difficulty
       this.hintsLeft = switch (difficulty) {
           case "Easy" -> 3;
           case "Medium" -> 2;
           default -> 1;
       };
   }
   
   // METHOD FOR GUESSING LETTERS
   public boolean guessLetter(char c) {
	    c = Character.toUpperCase(c);

	    // Return false if letter was already guessed
	    if (guessedLetters.contains(c)) {
	        return false;
	    }

	    // Adding the letter to the list of guessed letters
	    guessedLetters.add(c);
	    
	    // Setting the correct variable to initialize as false
	    boolean correct = false;

	    // Check if letter exists in the word
	    for (int i = 0; i < word.length(); i++) {
	        if (word.charAt(i) == c) {
	            currentDisplay[i] = c;
	            correct = true;
	            SoundPlayer.playSound("gamesound1.wav"); 
	        }
	    }

	    
	    // Only increment wrongGuesses if the guess was incorrect
	    if (!correct) {
	        wrongGuesses++;
	        SoundPlayer.playSound("gamesound2.wav");
	       
	      // If the correct word is guessed, increment score and streak by one
	    } else if (isWordGuessed()) {
	        score++;
	        streak++;
	    }
	    
	    // Returning correct
	    return correct;
	} 
   
   
   // METHOD TO DETERMINE IF THE GAME IS OVER
   // The game is considered to be over if the number of wrong guesses it as or exceeds the maximum number of allowed guesses
   public boolean isGameOver() {
       return isWordGuessed() || wrongGuesses >= maxGuesses;
   }
   
   // METHOD TO DETERMINE IF THE WORD IS GUESSED
   // Runs through each space and checks to see if it contains a letter or still is a underscore
   // if even one spot is left, then it is false. Otherwise, it marks the word as true
   public boolean isWordGuessed() {
       for (char c : currentDisplay) if (c == '_') return false;
       return true;
   }
   
   // METHOD TO OBTAIN THE WORD'S DISPLAY
   public String getWordDisplay() {
       return String.valueOf(currentDisplay);
   }
   
   // METHOD TO RETURN THE FULL WORD TO THE USER
   public String getFullWord() {
       return word;
   }
   
   // METHOD TO RETURN THE NUMBER OF WRONG GUESSES
   public int getWrongGuesses() {
	   
       return wrongGuesses;
   }
   
   // METHOD TO RETURNT THE GUESSES REMAINING
   // Determined by subtracting the maximum number of guesses by the number of wrong guesses
   public int getGuessesLeft() {
       return maxGuesses - wrongGuesses;
   }
   
   // METHOD TO OBTAIN A HINT
   public String getHint() {
	   	
	    // Printing that no hints are left if the hint count is at or below 0
	    if (hintsLeft <= 0) return "No hints left.";
	    hintsLeft--;

	    // Looping through each letter of the word and obtaining the correct letter at that position in the full word
	    for (int i = 0; i < word.length(); i++) {
	        if (currentDisplay[i] == '_') {
	            char hintChar = word.charAt(i);
	            
	            // Simulates the user guessing the letter rather than automatically filling it
	            // By simulating a guess for an automatically correct letter, it counts as a letter being revealed
	            guessLetter(hintChar);  
	            break;
	        }
	    }

	    // Returning the string that  aletter has been revealed
	    return "A letter has been revealed!";
	}
      
   
   // METHOD TO RESET THE GAME
   public void resetGame() {
       initGame();
   }
   
   
   // METHOD TO RETURN THE SCORE
   public int getScore() {
       return score;
   }
   
   
   // METHOD TO RETURN THE NUMBER OF HINTS LEFT
   public int getHintsLeft() {
       return hintsLeft;
   }
   
   
   // METHOD TO RETURN THE CURRENT DIFFICULTY SELECTED
   public String getDifficulty() {
       return difficulty;
   }
   
   
   // METHOD TO CHANGE THE DIFFICULTY WITHIN THE GAME
   public void changeDifficulty() {
	    difficulty = switch (difficulty) {
	        case "Easy" -> "Medium";
	        case "Medium" -> "Hard";
	        default -> "Easy";
	    };
	    // Remove the initGame() call here since restartGame() will handle it
	} 
   
   
   // Creating an integer streak, which we initialize to start at 0
   private int streak = 0;
   
   
   // METHOD TO OBTAIN THE STREAK
   // Returns the streak variable
   public int getStreak() {
	    return streak;
   }
   
   
   // METHOD TO CHANGE CATEGORY 
   public void changeCategory(String newCategory) {
	   this.category = newCategory;
	   
	   // Restart the game with the new chosen category
	   initGame();  
	    }
   
// Updating the score variable of the game
public void setScore(int score) {
    this.score = score; // Allows us to restore score after restart
}


// Updating the streak variable of the game
public void setStreak(int streak) {
    this.streak = streak; // Allows us to restore streak after restart
} 

}
  