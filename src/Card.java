
//Declaring variables to use for Card Constructor.
public class Card {
    private String suit;
    private String rank;
    private int value;

    //Constructor that creates a single card.
    public Card(String rank, String suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }

    //Returns the Suit of a card (e.g "Diamonds", "Hearts" etc.).
    public String getSuit() {
        return suit;
    }

    // Returns the value of a card (e.g 1, 2, 3, etc.).
    public int getRankValue() {
        return value;
    }

    //toString method will return something like ("1 of Hearts")
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

}
