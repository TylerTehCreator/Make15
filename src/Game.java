import java.util.*;

//Declaring variables to use for Game constructor.
public class Game {
    private Player player;
    private Deck deck;
    private int totalScore; //int variable for storing the total score.
    private int roundNum; //int variable for storing the current round number.
    private ArrayList<String> highScores; //Using Javas built in ArrayList method to manage highscores.
    private ArrayList<String> gameReplay; //Using Javas built in ArrayList method to manage Game replay.

    private static final String DEFAULT_HIGH_SCORE_ENTRY = "Anonymous: 0"; //Highscores slots which have not been used will appear as "Anonymouse : 0"
    private static final int DEFAULT_HIGH_SCORE_COUNT = 5; // 5 slots are available in the highscore table.

    public Game() {
        player = new Player();
        deck = new Deck();
        totalScore = 0;
        roundNum = 0;
        highScores = new ArrayList();
        gameReplay = new ArrayList();
        initializeHighScores();
    }

    //Insures highscore is reset and initialised.
    private void initializeHighScores() {
        highScores.clear(); // Clear existing entries to reset
        for (int i = 0; i < DEFAULT_HIGH_SCORE_COUNT; i++) { //Iterates from 0 to 5.
            highScores.add(DEFAULT_HIGH_SCORE_ENTRY); // Fill with default entries(Anonymous : 0)
        }
    }

    //Method for dealing the player their initial hand of 5 cards.
    public void dealInitialHand() {
        player.clearHand(); //Clears the hand for continuous rounds to be played.
        for (int i = 0; i < 5; i++) {
            Card dealtCard = deck.dealCard(); //Uses the dealCard() method to deal a card from the deck.
            player.receiveCard(dealtCard); //Player recieves the card.
            logGameEvent("Dealt card to player: " + dealtCard); //This process is logged for future viewing.
        }
    }

    //Method for resetting deck back to 56 cards for continuous rounds to be played.
    public void resetDeck() {
        deck = new Deck();
    }

    //Method for replacing picture cards in the players hand when they make 15.
    private void replacePictureCards() {
        Scanner scanner = new Scanner(System.in); //Create new scanner.
        for (Card cardToReplace : (player.getHand())) { //Iterate through players hand.
            if (cardToReplace.getRankValue() == 11 || cardToReplace.getRankValue() == 12) {//11 and 12 = Picture cards
                while (true) {
                    System.out.print("Replace " + cardToReplace +  "? (y/n):");//Asks the player if they want to replace their picture cards.
                    char option = scanner.next().charAt(0);//Asks for a char input.
                    if (option == 'y' || option == 'Y') {
                        Card replacedCard = deck.dealCard();//Gets a card from the deck to replace the picture card.
                        player.replaceCard(cardToReplace, replacedCard);//Uses replaceCard() method to remove old card and replace with new one.
                        logGameEvent("Replaced picture card " + cardToReplace + " with " + replacedCard);//Game event is logged.
                        break;
                    } else if (option == 'n' || option == 'N') {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter y or n");
                    }
                }
            }
        }
    }

    //Method to manage the game logic.
    private boolean make15Round(Card computerCard) {
        Scanner scanner = new Scanner(System.in);
        boolean made15 = false;
        boolean playedSameSuit = false;
    
        logGameEvent("Computer card: " + computerCard);//Logs card dealt to computer.
    
        while (!made15) {
            System.out.println("Select a card to play (1-5) (0 to exit)");
            int choice = scanner.nextInt();
            if (choice == 0) { //Program closes.
                logGameEvent("Player exited this round.");//Game event is logged.
                return false;
            }
            if (choice < 1 || choice > player.getSize()) { //If player chooses a number above 5, return error message.
                System.out.println("Invalid choice, please choose a card (1-5)");
                continue;
            }
    
            Card playerCard = player.getHand()[choice - 1]; //PlayerCard is assigned to a card from the players hand.
            logGameEvent("Player played: " + playerCard); //Game event is logged.
    
            if (playerCard.getRankValue() + computerCard.getRankValue() == 15) { //If playerCard + computerCard = 15, make15 is achieved.
                made15 = true;
                player.addScore(1); //Adds a point to the score.
                totalScore++;
                logGameEvent("Player scored a point with card: " + playerCard); //Game event is logged.
                System.out.println("You made 15! You score a point.");
                player.replaceCard(playerCard, deck.dealCard()); //The card that was played is replaced.
                System.out.println("Do you want to replace a picture card from your hand? (y/n)");
                while (true) { //Loop to ensure valid input.
                    char option = scanner.next().charAt(0);
                    if (option == 'y' || option == 'Y') {
                        replacePictureCards();
                        return true;
                    }
                    else if (option == 'n' || option == 'N') {
                        break;
                    }
                    else {
                        System.out.println("Invalid input. Please enter y or n");
                    }
                }
            } else if (playerCard.getSuit().equals(computerCard.getSuit())) { //If the player can't make 15, but can play a picture card.
                playedSameSuit = true;
                logGameEvent("Player played same suit: " + playerCard); //Game event is logged.
                System.out.println("You played " + playerCard + ". no points scored");
                player.replaceCard(playerCard, deck.dealCard()); //Picture card is replaced.
                return playedSameSuit;
            } else {
                System.out.println("You cannot make 15 and cannot play that card."); //Game ends when player can't make 15.
                displayTotalScore(); //Display total score.
                checkHighScores(); //Check if player achieved a new highscore.
                logGameEvent("Player couldn't make 15. Game over."); //Game event is logged.
                System.out.println("Would you like to play again? (y/n)");
                while (true) {
                    char option = scanner.next().charAt(0);
                    if (option == 'y' || option == 'Y') {
                        totalScore = 0; //Total score is reset to 0.
                        logGameEvent("Player chose to play again."); //Game event is logged.
                        System.out.println(roundNum+1); //Round number is increased.
                        play(); //New game is started with a new shuffled deck.
                        return true;
                    } else if (option == 'n' || option == 'N') {
                        logGameEvent("Player chose to end the game.");//Game event is logged.
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter y or n");
                    }
                }

            }
            viewReplay(); //Player is offered the chance to view a replay of their game.
        }
        return true;
    }

    public void play() {
        displayHighScores(); //Player first sees the highscore table.
        resetDeck(); //Deck is reset for continuous rounds.
        dealInitialHand(); //Player is dealt their initial hand of 5.
        setRoundNum(roundNum + 1); //Round number is incremented
        System.out.println("Round: " + roundNum);
        trackRound(); // Log the round number
        while (true) {
            if (deck.isEmpty()) { //An empty deck causes the game to end.
                System.out.println("The deck is empty. Game over!");
                logGameEvent("The deck is empty. Game over!");
                break;
            }
            Card computerCard = deck.dealCard(); //Computer receives a card from the deck.
            System.out.println("Computer Card: " + computerCard);
            player.displayHand();
            if (!make15Round(computerCard)) {
                break;
            }
        }
    }

    //Method for displaying the total score.
    private void displayTotalScore() {
        System.out.println("Total Score: " + totalScore);
    }

    //Method for displaying the high scores.
    private void displayHighScores() {
        System.out.println("High Scores: " + highScores);
    }

    //Method for checking if the player achieved a new high score.
    private void checkHighScores() {
        if (isHighScore(totalScore)) {
            String playerName = getPlayerName();
            updateHighScores(playerName, totalScore);
            System.out.println("Updated High Scores:"); // Display updated scores immediately
            displayHighScores();
        }
    }

    private boolean isHighScore(int score) {
        int lowestHighScore = extractScore(highScores.get(highScores.size() - 1)); // Ensure dynamic handling of score list
        return score > lowestHighScore || (score == lowestHighScore && highScores.size() < DEFAULT_HIGH_SCORE_COUNT);
    }

    //Method for getting the players name to display in the high score table.
    private String getPlayerName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Congratulations! You got a new high score! Enter your name: ");
        return scanner.nextLine();
    }

    //Method for updating the high scores.
    private void updateHighScores(String name, int score) {
        highScores.add(formatHighScore(name, score)); // Add new entry
        highScores.sort(Comparator.comparingInt(this::extractScore).reversed()); // Sort in descending order
        trimHighScores(); // Ensure only the top 5 entries remain
    }

    //Method for ensuring players name comes first followed by their score.
    private String formatHighScore(String name, int score) {
        return name + ": " + score;
    }


    private int extractScore(String highScoreEntry) {
        String[] parts = highScoreEntry.split(": ");
        if (parts.length == 2) { // Validate format
            try {
                return Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid score format in highScores: " + highScoreEntry);
            }
        }
        return 0; // Default to 0 for invalid entries
    }

    //Method for insuring only 5 high scores can be displayed.
    private void trimHighScores() {
        while (highScores.size() > 5) {
            highScores.remove(highScores.size() - 1);
        }
    }
    
    //Method for logging a game event for replay.
    private void logGameEvent(String event) {
        gameReplay.add(event);
    }

    //Method for asking the player if they want to view a replay of their game.
    public void viewReplay() {
        boolean isViewingReplay = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to view a replay of the game? (y/n)");
        while (!isViewingReplay) {
            char option = scanner.next().charAt(0);
            if (option == 'y' || option == 'Y') {
                replayGame();
                isViewingReplay = true;
            } else if (option == 'n' || option == 'N') {
                break;
            } else {
                System.out.println("Invalid input. PLease enter y or n");
            }
        }
    }

    //Method for setting the round number.
    public void setRoundNum(int roundNum) {
            this.roundNum = roundNum;
    }

    //Method for displaying the replay of the game.
    public void replayGame() {
        System.out.println("Game Replay:");
        for (String event : gameReplay) {
            System.out.println(event);
        }
    }

    //Method for keeping track of the round number in the replay.
    private void trackRound() {
        logGameEvent("Starting Round: " + roundNum);
    }

}
