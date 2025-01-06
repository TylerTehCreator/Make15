
//Declaring variables to use in Player constructor.
public class Player {
    private Card[] hand;
    private int score;
    private int size;

    //Constructor which allows 5 cards to be stored in the hand array, as that is the maximum amount a player can hold in their hand.
    public Player() {
        hand = new Card[5];
        score = 0;
        size = 0;
    }

    //Adds a card to the players hand.
    public void receiveCard(Card card) {
        if (size < hand.length) { //Checks if the current size of the hand is less than the available slots(5).
            hand[size++] = card; //Increments the size by 1 and adds a card to the players hand.
        }
        else {
            System.out.println("You already hold the maximum amount of cards!");
        }
    }

    //Displays the players hand using a for loop.
    public void displayHand() {
        System.out.println("\nYour hand:");
        for (int i = 0; i < size; i++) {
            System.out.println(i+1 + ": " + hand[i].toString());
        }
    }

    public void addScore(int points) {
        score += points;
    }

    //Returns the players hand.
    public Card[] getHand() {
        return hand;
    }

    //Returns the size of the players hand(Should always be 5).
    public int getSize() {
        return size;
    }

    //Removes a specified card from the hand and shifts the remaining cards up by one so the new card enters the array at the bottom of the hand.
    public void removeCard(Card card) {
        for (int i = 0; i < size; i++) {//Iterates from 0 to size of hand(5)
            if (hand[i].equals(card)) { //Iterates through the hand and searches for specified card.
                for (int j = i; j < size - 1; j++) {
                    hand[j] = hand[j + 1];//Cards in the hand are shifted up in the array to maintain order.
                }
                hand[--size] = null;//Size of array is decremented and specified card is set to null to maintain clean memory management.
                return;
            }
        }
        throw new IllegalArgumentException("Card not found in hand.");//If card is not found in array, and Exception is thrown.
    }

    //Removes a card and replaces it with a new card.
    public void replaceCard(Card oldCard, Card newCard) {
        removeCard(oldCard);//Specified card is first removed as the hand can only hold 5 cards, thus reducing it to 4.
        receiveCard(newCard);//Adds a specified card to replace the old one, thus returning the hand to its original size of 5.
    }

    //Sets the size of the hand to 0, thus clearing the hand.
    public void clearHand() {
        size = 0;
    }

}
