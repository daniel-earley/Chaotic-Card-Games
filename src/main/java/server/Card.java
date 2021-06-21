package server;

public class Card {
    private int value;
    private String suit;

    public Card(int val, String suit) {
        this.value = val;
        this.suit = suit;
    }

    public Card(int val, int suit){
        this.value = val;
        setSuit(suit);
    }

//    Setters

    public void setValue(int val){
        this.value = val;
    }

    public void setSuit(String suit){
        this.suit = suit;
    }

    public void setSuit(int suit){
        switch (suit) {
            case 1 -> this.suit = "Clubs";
            case 2 -> this.suit = "Spades";
            case 3 -> this.suit = "Diamonds";
            case 4 -> this.suit = "Hearts";
        }
    }

    //    Getters
    public int getValue(){
        return this.value;
    }

    public String getSuit(){
        return this.suit;
    }

    //    ToString
    public String toString(){
        String name = "";

        switch (value){
            case 1 -> name = "Ace";
            case 11 -> name = "Jack";
            case 12 -> name = "Queen";
            case 13 -> name = "King";
        }

        if (name.equals("")) {
            return getValue() + " of " + getSuit();
        } else {
            return name + " of " + getSuit();
        }
    }
}
