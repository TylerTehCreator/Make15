import java.util.Random;

//Declaring variables to use for Deck constructor.
public class Deck {
    private Card[] deck; //Array of Cards to store Cards in the Deck class.
    private int amount;
    private boolean isInitialized = false;

    public void isInitialized() {
        if (!isInitialized) {
            throw new SecurityException("Deck is not initialized");
        }
    }

    public Deck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};//Array for storing the suits in the deck.
        String[] ranks = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};//Array for storing the ranks in the deck.
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 11, 11, 12};//Array for storing the integer values of the ranks array (Jack, Queen, King = 11. Ace = 12)
        int size = suits.length * ranks.length;//Multiplying length of suits array(4) by length of ranks array(14) to create the size of the deck(56)
        deck = new Card[size];//Creating an array of type card which will hold 56 cards to form a deck.
        amount = 0;//int variable to check how many cards are currently in the deck.
        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < suits.length; j++) {
                deck[amount++] = new Card(ranks[i], suits[j], values[i]);//for loop which generates a new card 56 times with suits, ranks and values taken from their respective arrays.
            }
        }
        isInitialized = true;
        isInitialized();
        shuffle();
    }

    public void getDeck() {
        for (int i = 0; i < amount; i++) {
            System.out.println(i+1 + ": " + deck[i]);
        }
    }

    // Fisher Yates Algorithm used to create a shuffle method.
    private void shuffle() {
        Random random = new Random();
        for (int x = amount-1; x > 0; x--) { //Loop through the deck
            int y = random.nextInt(x+1);//y index becomes a number between 0 and x
            Card temp = deck[x]; //Store x variable temporarily.
            deck[x] = deck[y]; //Swap empty x variable with y variable.
            deck[y] = temp; //Swap empty y variable with temp(x) variable.
        }
    }

    //Returns the size of the Deck, a full deck will return '56'.
    public int size() {
        return amount;
    }

    public Card dealCard() {
        if (isEmpty()) { //First checks if array is empty, returning null if it is.
            return null;
        }
        else return deck[--amount];//Returns a card at last valid index of the cards array and decreases the amount of cards in the array.
    }

    public void clearDeck() {
        amount = 0;
    }

    //Checks if Deck is empty.
    public boolean isEmpty() {
        return amount == 0;
    }
}
