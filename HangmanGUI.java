// FILE: HANGMANGUI.JAVA
// PURPOSE: This controls all of the visual aspects of the game, presenting the graphics for the user 

package culminating;

// Importing various tools that are used in java to create graphical user interface
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;


// The public class for this section of the code, HangmanGUI, which extends the Jframe function
public class HangmanGUI extends JFrame {
	
   // Stores the list of words and their categories
   private WordBank wordBank;
   
   // The various bits of logic for the game itself and its running
   private HangmanGame game;
   
   // The custom panel for drawing the hangman and its stand
   private HangmanPanel hangmanPanel;
   
   // The panel with the various buttons that are responsible for helping the user guess letters
   private JPanel letterButtonsPanel;
   
   // Labels that display game information
   private JLabel wordLabel, statusLabel, hintLabel, scoreLabel, streakLabel, guessesLabel;
   
   // Buttons that are needed for user input
   private JButton hintButton, restartButton, difficultyButton;
   
   // The category combobox
   private JComboBox<String> categoryComboBox; 
   
   // The background panel that is updated to be an image
   private BackgroundPanel backgroundPanel; 
   
   // Various other important buttons and labels, particularly the ones that control the time
   private JButton timerButton;
   private Timer gameTimer;
   
   // The time remaining, defaulted at 30 seconds
   private int timeRemaining = 30; 
   
   // Whether or not timer mode is active (defaulted to false)
   private boolean timedMode = false;
   
   // The timer label itself
   private JLabel timerLabel;
 
   // Method that adds a space between the _ so that the length of the word can be found easily
   private String formatWordDisplay(String wordDisplay) {
	    return wordDisplay.replace("", " ").trim();
	} 
   
   // THE MAIN HANGMANGUI METHOD TO SETUP THE GAME AND ITS CORE VISUALS
   public HangmanGUI() {
	  
	   // Setting the title of the videogame
       setTitle("--- Hangman Game ---");
       
       // Setting the size of the window
       setSize(1200, 600);
       
       // Setting the closing functionality of the window
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       // Adding an action listener that allows for the window to be closed
       addWindowListener(new WindowAdapter() {
    	    @Override
    	    public void windowClosing(WindowEvent e) {
    	        if (gameTimer != null) {
    	            gameTimer.stop();
    	        }
    	    }
       });
   
      
       // Using the border organization layout
       setLayout(new BorderLayout());
       setResizable(false);
       
       // Setting the default difficulty to easy and the default category to animals (upon launching the game)
       String difficulty = "Easy";
       String category = "Animals";
       
       // Assigning the word bank to a variable and using it to start a game
       wordBank = new WordBank(); 
       game = new HangmanGame(wordBank, difficulty, category); 
       
       // THE NORTHERN PANEL OF THE GAME
       JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
       topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 3)); 
       topPanel.setBackground(Color.WHITE);
       topPanel.setOpaque(true); 
       
       // Creating labels for the score, streak, # of remaining guesses, the game status, and remaining title
       // Each of these labels share the same font and text size
       scoreLabel = new JLabel("Score: 0");
       scoreLabel.setFont(new Font("Arial", Font.BOLD, 18)); 
       
       // Label for the user's current streak
       streakLabel = new JLabel("Streak: 0");
       streakLabel.setFont(new Font("Arial", Font.BOLD, 18)); 
       
       // Label for the # of remaining guesses
       guessesLabel = new JLabel("Guesses Left: " + game.getGuessesLeft());
       guessesLabel.setFont(new Font("Arial", Font.BOLD, 18));
       
       // Label for the current status of the game
       statusLabel = new JLabel("In Progress");
       statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
       
       // Label for the time remaining
       timerLabel = new JLabel("Time: --");
       timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
       
       // Adding the various labels defined just now to the northern section of the screen so the user can easily see it
       topPanel.add(scoreLabel);
       topPanel.add(streakLabel);
       topPanel.add(guessesLabel);
       topPanel.add(timerLabel); 
       topPanel.add(statusLabel);
       
       // Adding the top panel to the northern part of the screen via border layout
       add(topPanel, BorderLayout.NORTH);
       
       // THE CENTRAL PANEL OF THE GAME
       
       // Creating the background panel, setting it as opaque, and adding it to the center of the game
       backgroundPanel = new BackgroundPanel("animals.jpg");   
       backgroundPanel.setBackground(new Color(255, 235, 180 )); 
       backgroundPanel.setOpaque(true);  
       backgroundPanel.setLayout(null);
       add(backgroundPanel, BorderLayout.CENTER);
       
       // Creating the hangman panel and setting it as not opaque
       hangmanPanel = new HangmanPanel();
       hangmanPanel.setBackground(new Color(255, 255, 255)); 
       hangmanPanel.setOpaque(false);  
       
       // Creating the word label by displaying the word as it is being guessed
       wordLabel = new JLabel(formatWordDisplay(game.getWordDisplay()), SwingConstants.CENTER); 

       // Setting the bounds of the hangman panel and adding it to the background
       hangmanPanel.setBounds(35, 80, 300, 300 );
       backgroundPanel.add(hangmanPanel);

       // Create and position word label to upper right
       wordLabel = new JLabel(formatWordDisplay(game.getWordDisplay()));
       wordLabel.setFont(new Font("Arial", Font.BOLD, 35));
       wordLabel.setBounds(400, 200, 500, 50); // Move right + top;  
       backgroundPanel.add(wordLabel); 
       
       // Creating a grid of buttons (letters) with a 3 x 9 format (vertical - horizontal)
       letterButtonsPanel = new JPanel(new GridLayout(3, 9));
       letterButtonsPanel.setBackground(Color.WHITE);
       letterButtonsPanel.setOpaque(true); 
       
       // For loop that runs through each character/letter of the standard english alphabet
       for (char c = 'A'; c <= 'Z'; c++) {
    	   
    	   // Creating a button for each letter
           JButton btn = new JButton(String.valueOf(c));
           
           // Calling the letter guess method everytime a button is pressed
           btn.addActionListener(e -> handleLetterGuess(btn));
           letterButtonsPanel.add(btn);
       }
       
       // Adding this panel of buttons to the southern section of the display (SOUTH)
       add(letterButtonsPanel, BorderLayout.SOUTH);
       
       // Creating a grid of buttons that follow a 3 by 1 layout (vertical - horizontal)
       JPanel westPanel = new JPanel(new GridLayout(5, 1));
       westPanel.setBackground(Color.WHITE);
       westPanel.setOpaque(true);  
       
       // Creating the hint, restart, and difficulty buttons sequentially that call their respective methods to determine their numeric values
       // Each of these buttons can be pressed when needed by the user and call on different methods in HangmanGame
       hintButton = new JButton("Hint (" + game.getHintsLeft() + ")");
       restartButton = new JButton("Restart");
       difficultyButton = new JButton("Difficulty: " + game.getDifficulty());
       
       // Creating a timer button that displays the time if pressed and adding an action listener to it
       timerButton = new JButton("Enable Timer (30s)");
       timerButton.addActionListener(e -> toggleTimerMode());
       
       // Create category dropdown with categories from wordBank (assuming it has a method getCategories())
       String[] categories = wordBank.getCategories(); // getCategories() should return String[] or List<String>
       categoryComboBox = new JComboBox<>(categories);
       categoryComboBox.setSelectedIndex(0);  // default select first category
       categoryComboBox.addActionListener(e -> changeCategory());
       
       // Incorporating action listeners into each button that call on their respective methods upon being pressed to fulfill their roles
       hintButton.addActionListener(e -> showHint());
       restartButton.addActionListener(e -> restartGame());
       difficultyButton.addActionListener(e -> changeDifficulty());
       
       // Adding each of these buttons to the western panel / left side of the window, where they can easily be accessed
       westPanel.add(difficultyButton);
       westPanel.add(categoryComboBox);
       westPanel.add(hintButton);
       westPanel.add(restartButton);
       westPanel.add(timerButton); 
       add(westPanel, BorderLayout.WEST);
       
       // Creating an empty label on the right side of the screen that displays a hint
       hintLabel = new JLabel("", SwingConstants.CENTER);
       hintLabel.setBounds(400, 350, 400, 30);  // same X as wordLabel, Y is below wordLabel
       backgroundPanel.add(hintLabel); 
       setVisible(true);
   }
   
   // METHOD TO HANDLE GUESSING OF LETTERS FROM THE USER
   private void handleLetterGuess(JButton btn) {
	   
	    // Extracting the letter from the clicked button's text
	    String letter = btn.getText();

	    // Chceking if the guessed letter is within the word, returning true or false based on the outcome
	    boolean correct = game.guessLetter(letter.charAt(0));

	    // Setting the core features of the buttons
	    btn.setOpaque(true);
	    btn.setContentAreaFilled(true);
	    btn.setBorderPainted(false);  
	    
	    // Turning the background of the button to green or red based on whether or not the guess was correct
	    btn.setBackground(correct ? Color.GREEN : Color.RED);
	    
	    // Having the text of the button being white for improved constrast
	    btn.setForeground(Color.WHITE);
	    
	    // Refreshing the display to make the change instantaneous
	    btn.repaint();  

	    // Remmoving action listeners so that the buttons cannot be clicked again for an action to occur
	    for (ActionListener al : btn.getActionListeners()) {
	        btn.removeActionListener(al);
	    }

	    // Incorporating a timer to dim the button to gray (unusable) ater ~1000ms
	    Timer timer = new Timer(1000, e -> {
	        btn.setBackground(Color.GRAY);
	        btn.setForeground(Color.DARK_GRAY);
	        btn.repaint();
	        ((Timer) e.getSource()).stop();
	    });
	    
	    // Ensuring that the timer runs only one time and does repeate
	    timer.setRepeats(false);
	    timer.start();

	    // Calling the method to update the game state
	    updateGameState();
	}  
   
   // METHOD TO UPDATE THE STATE OF THE GAME
   private void updateGameState() {
	  
		    if (game.isGameOver()) {
		        if (timedMode) {
		            gameTimer.stop();
		        }
		    }
	  
	   // Updating the displayed word on the screen
       wordLabel.setText(game.getWordDisplay());
       
       // Updating the current number of guesses left for the user
       guessesLabel.setText("Guesses Left: " + game.getGuessesLeft());
       
       // Update the drawing of the stickman with his new parts
       hangmanPanel.setParts(game.getWrongGuesses());
       
       // Updating the number of hints left for the user
       hintButton.setText("Hint (" + game.getHintsLeft() + ")");
       
       
       // If the game has been completed, disable buttons, show word, and display an ending message depending on a win or loss
       if (game.isGameOver()) {
           disableAllButtons();
           
           if (game.isWordGuessed()) {
               statusLabel.setText("You Win!");
           } else {
               statusLabel.setText("The word was: " + game.getFullWord());
               wordLabel.setText(formatWordDisplay(game.getFullWord())); 
           }
           
           
           // Validating and refreshing the status label to reflect the change
           statusLabel.revalidate();
           statusLabel.repaint();
          
       }
       
       // Updating the new score count and refreshing the graphics on the screen
       scoreLabel.setText("Score: " + game.getScore());
       streakLabel.setText("Streak: " + game.getStreak()); 
       
       // Full game refresh with repaint
       repaint();
       
       // Updating the format/display of the word
       wordLabel.setText(formatWordDisplay(game.getWordDisplay())); 
       
   }
   
   // METHOD TO DISABLE ALL BUTTONS
   private void disableAllButtons() {
	   
	   // For each button, set it being enabled to false so that it can no longer be pressed
       for (Component c : letterButtonsPanel.getComponents()) {
           c.setEnabled(false);
       }
   }
   
   // METHOD TO DISPLAY HINTS FOR THE USER
   private void showHint() {
	   
	   // Assigning the string variable hint by using the getHint method in a different section of the code
       String hint = game.getHint();
       
       // Displaying the hint
       hintLabel.setFont(new Font("Arial", Font.BOLD, 18));
       hintLabel.setText("Hint: " + hint);
       hintLabel.setBackground(new Color(255, 255, 255)); 
       hintLabel.setOpaque(false); 
       
       // Updating the game state to show this new hint
       updateGameState();
   }
   
   // METHOD TO RESTART THE GAME AND RESET ALL NECESSARY ELEMENTS
   private void restartGame() {
	   
	   // Based on whether or not timer mode is active, it will eiter restart the 30 second timer or stop the timer completely
	   if (timedMode) {
	        startTimer(); 
	    } else {
	        stopTimer(); 
	    }
	 
	   // Setting the variables of current score and current streak to the current scores and streaks in the game to preserve them
	   int currentScore = game.getScore();
	   int currentStreak = game.getStreak();
	   
	    // Obtains the selected category and difficulty from the game state
	    String selectedCategory = (String) categoryComboBox.getSelectedItem();
	    String difficulty = game.getDifficulty();

	    // Creating a new Hangman Game with the same parameters from the previous code
	    game = new HangmanGame(wordBank, difficulty, selectedCategory);
	    
	    // Restoring the score and streak to their previous values
	    game.setScore(currentScore); 
	    game.setStreak(currentStreak); 

	    // Resetting all the labels to their typical forms prior to any letters being guessed
	    // For most of these labels, it means resetting them to have their maximum values (Ex: Guesses Left = 6)
	    // Any labels that change DURING the game are reset here
	    wordLabel.setText(formatWordDisplay(game.getWordDisplay()));
	    guessesLabel.setText("Guesses Left: " + game.getGuessesLeft());
	    statusLabel.setText("In Progress");
	    hintLabel.setText("");
	    scoreLabel.setText("Score: " + game.getScore());
	    streakLabel.setText("Streak: " + game.getStreak());
	    hintButton.setText("Hint (" + game.getHintsLeft() + ")");
	    difficultyButton.setText("Difficulty: " + difficulty);

	    // Resetting the hangman drawing so only the gavel is present
	    hangmanPanel.setParts(0); 

	    // Removing all existing buttons
	    letterButtonsPanel.removeAll();
	    
	    // Iterating through each character to recreate all of the buttons with action listeners
	    for (char c = 'A'; c <= 'Z'; c++) {
	        JButton btn = new JButton(String.valueOf(c));
	        btn.addActionListener(e -> handleLetterGuess(btn));
	        letterButtonsPanel.add(btn);
	    }

	    // Refreshing the panel tp repaint and showcase all of the new buttons
	    letterButtonsPanel.revalidate();
	    letterButtonsPanel.repaint();
	    
	    // Completing a full UI refresh
	    repaint();
	} 
	   
   // METHOD TO CHANGE THE DIFFICULTY OF THE GAME
   private void changeDifficulty() {
	    String currentCategory = (String) categoryComboBox.getSelectedItem();
	    
	    // Changing the difficulty and restarting the game to reflect the change in the UI
	    game.changeDifficulty();
	    restartGame();
	    
	    // Ensure the category stays the same after difficulty change
	    categoryComboBox.setSelectedItem(currentCategory);
	} 
   
   // METHOD TO CHANGE THE CATEGORY OF THE MATCH
   private void changeCategory() {
	    String selectedCategory = (String) categoryComboBox.getSelectedItem();
	    if (selectedCategory != null) {
	        game.changeCategory(selectedCategory);

	        // Based on the selected category, change the background image to a different file that corresponds to the category
            // Set to lower case to ensure no case-sensitive errors
	        switch (selectedCategory.toLowerCase()) {
	            case "animals":
	                backgroundPanel.setBackgroundImage("animals.jpg");
	                break;
	            case "country":
	                backgroundPanel.setBackgroundImage("countries.jpg");
	                break;
	            case "foods":
	                backgroundPanel.setBackgroundImage("food.jpg");
	                break;
	            case "movies":
	                backgroundPanel.setBackgroundImage("movies.jpg");
	                break;
	                
	            // If there is no case, set it to null 
	            default:
	                backgroundPanel.setBackgroundImage(null); 
	                break;
	        }

	        // Restart the game to update the background
	        restartGame();
	    }
	} 
   	
   
   	// METHOD TO TOGGLE BETWEEN TIMED AND UNTIMED MODES
    private void toggleTimerMode() {
        timedMode = !timedMode;
        
        // Setting the text of the button to either the time that would be allocated or to disable the active timer
        timerButton.setText(timedMode ? "Disable Timer" : "Enable Timer (30s)");
        
        
        // Depending on whether or not timed mode is on, either start or stop the current timer
        if (timedMode) {
            startTimer();
        } else {
            stopTimer();
        }
    }

    // METHOD TO START A 30 SECOND TIMER THAT DECREASES OVERTIME
    private void startTimer() {
    	
    	// The initial time remaining is set to 30 serconds
        timeRemaining = 30; 
        updateTimerDisplay();
        
        // If a timer was already going it, stop it to make room for the new one
        if (gameTimer != null) {
            gameTimer.stop();
        }
        
        // Creating a new timer that fires every second or 1000ms, decreasing the time remainining at the same pace as in real life
        gameTimer = new Timer(1000, e -> {
            timeRemaining--;
            updateTimerDisplay();
            
            // If the timer hits zero or falls below it, update the time to be expired
            if (timeRemaining <= 0) {
                timeExpired();
            }
        });
        
        // Starting the timer upon the pressing of the button
        gameTimer.start();
    }

    // METHOD THE STOP THE RUNNING TIMER AND UPDATE THE DISPLAY ACCORDINGLY
    private void stopTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        
        // Setting the label to Time: -- to indicate that all time has elapsed
        timerLabel.setText("Time: --");
    }

    // METHOD TO UPDATE THE TIMER DISPLAY
    private void updateTimerDisplay() {
    	
    	// As the timer is running, update the label at the top of the screen to showcase the current time remaining
        timerLabel.setText("Time: " + timeRemaining);
    }

    // METHOD TO HANDLE WHEN THE TIMER EXPIRES
    private void timeExpired() {
    	
    	// Stops the timer
        gameTimer.stop();
        
        // Changes the label to indicate that the timer has ended and the correct word
        statusLabel.setText("Time's Up! The word was: " + game.getFullWord());
        
        // Adding the correct word to a label by retrieving it via a different method
        wordLabel.setText(formatWordDisplay(game.getFullWord()));
        
        // Disabling all buttons to prevent the user from guessing any further
        disableAllButtons();
    } 
   }
