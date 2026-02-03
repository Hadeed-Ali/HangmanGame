// FILE: WORDBANK.JAVA
// PURPOSE: This stores important information regarding the words used in the game. They are stored into categories based on difficulty and their typing

package culminating;

import java.util.*;

// Defining the class called "WordBank", which is responsible for storing/managing the words for the Hangman game
public class WordBank {
	
	// Creating a nested map with categories and maps of difficulties
    private Map<String, Map<String, List<String>>> categoryMap;
    
    // Random object to randomly select a word
    private Random rand;

    // Creating the new hashmap and using the random tool to randomly select a word
    public WordBank() {
        categoryMap = new HashMap<>();
        rand = new Random();
        
        // Calling the method which loads in all of the words, defined below
        loadWords();
    } 
    
// Creating lists for all of the words
// The words are organized by the category they are in, then followed by the difficulty through the use of a hashmap
// Longer / more complex words are in the harder categories, and each list is arranged in alphabetical order
    private void loadWords() {
    	
    	// ANIMALS - common animals found in the wild or even at home
        categoryMap.put("Animals", new HashMap<>());
        categoryMap.get("Animals").put("Easy", Arrays.asList("cat", "dog", "rat", "cow", "pig", "fish", "lion", "duck", "crab", "deer", "wolf" ));
        categoryMap.get("Animals").put("Medium", Arrays.asList("tiger", "camel", "horse", "zebra", "koala", "panda", "spider", "walrus", "turtle", "wombat", "alpaca"));
        categoryMap.get("Animals").put("Hard", Arrays.asList("giraffe", "dolphin", "gorilla", "octupus", "cheetah", "chicken", "squirrel", "reindeer", "aardvark", "anteater", "kangaroo"));

        // COUNTRIES - the various countires of the world, spanning all continents
        categoryMap.put("Country", new HashMap<>());
        categoryMap.get("Country").put("Easy", Arrays.asList("cuba", "chad", "iran", "mali", "peru", "oman", "laos", "togo", "italy", "japan", "nepal", "qatar", "spain", "syria", "chile", "china", "ghana", "haiti", "yemen"));
        categoryMap.get("Country").put("Medium", Arrays.asList("brazil", "canada", "angola", "greece", "turkey", "bhutan", "france", "jordan", "cyprus", "guyana", "norway", "ecuador", "eritrea", "albania", "algeria", "germany", "croatia", "jamaica", "estonia", "finland", "lebanon"));
        categoryMap.get("Country").put("Hard", Arrays.asList("kiribati", "liechtenstein", "luxembourg", "argentina", "afghanistan", "switzerland", "turkmenistan", "singapore", "suriname", "north-korea", "united-kingdom", "equatorial-guinea"));
        
        // FOODS - various foods ranging from snacks to entire meals
        categoryMap.put("Foods", new HashMap<>());
        categoryMap.get("Foods").put("Easy", Arrays.asList("egg", "pie", "rice", "corn", "soup", "kiwi", "cake", "bean", "taco"));
        categoryMap.get("Foods").put("Medium", Arrays.asList("sushi", "cereal", "bagel", "orange", "fajita", "bread", "bacon", "olive", "cheese", "cookie", "curry"));
        categoryMap.get("Foods").put("Hard", Arrays.asList("tiramisu", "lasagna", "blueberry", "mushroom", "spaghetti", "casserole", "croissant", "guacamole", "eggplant", "zucchini" ));
        
        // MOVIES - popular movies ranging from old classics to modern masterpieces
        categoryMap.put("Movies", new HashMap<>());
        categoryMap.get("Movies").put("Easy", Arrays.asList("up", "it", "jaws", "coco", "cars", "sing", "soul", "mulan", "shrek", "brave", "moana", "avatar"));
        categoryMap.get("Movies").put("Medium", Arrays.asList("titanic", "skyfall", "aladdin", "megamind", "deadpool", "zootopia", "parasite", "uncharted", "spiderman"));
        categoryMap.get("Movies").put("Hard", Arrays.asList("interstellar", "zombieland", "transformers", "ratatouille", "the-hunger-games", "doctor-strange", "pulp-fiction", "the-dark-knight"));

    }
    
    // Retrieve all of the category names and convert these categories into strings
    public String[] getCategories() {
        Set<String> categories = categoryMap.keySet();
        return categories.toArray(new String[0]);
    }
    
    // Returns a random word from the specificed category and difficulty using the random tool
    public Map.Entry<String, String> getRandomWord(String category, String difficulty) {
        
    	// If statement (error prevention) to ensure that the selected category actually exists
        if (!categoryMap.containsKey(category)) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }

        		// Based on the chosen category, obtain the required difficulty map that has the words for that combination of picks
                Map<String, List<String>> difficultyMap = categoryMap.get(category);
                
        // Like with the category, we do error prevention to ensure that the difficulty is valid
        if (!difficultyMap.containsKey(difficulty)) {
            throw new IllegalArgumentException("Invalid difficulty: " + difficulty + " for category: " + category);
        }

        // Selects a random word from the appropriate list
        List<String> words = difficultyMap.get(difficulty);
        String word = words.get(rand.nextInt(words.size()));

        // Returns the chosen word and category with a pair of variables (order of word, category)
        return new AbstractMap.SimpleEntry<>(word, category);
    } 
}

   
   
   
 
