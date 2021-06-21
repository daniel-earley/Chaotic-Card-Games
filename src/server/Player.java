package server;

import java.util.List;

public class Player {
    private String name;
    protected Deck hand;
    private boolean turn = false;

    public Player(String name) {
        this.name = name;
        this.hand = new Deck();
    }

    public Card play(int index) {
        setTurn(false);
        Card card = hand.popCard(index);
        System.out.printf("%s played %s\n", getName(), card);
        return card;
    }

    public Card viewCard(int index){
        Card card = hand.viewCard(index);
        return card;
    }

    public void pickup(Card card){
        System.out.printf("%s picked up a card\n", getName());
        this.hand.pickup(card);
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){ this.name = name; }

    public boolean getTurn() {
        return this.turn;
    }

    public void flipTurn(){
        this.turn = !this.turn;
    }

    public void setTurn(boolean turn){
        this.turn = turn;
    }

    public boolean isWin() {
        return this.hand.isEmpty();
    }

    public String toString() {
        return "\n" + getName() + ":\np: Pickup a Card\n" + hand.toString();
    }

    public String toStringAlt(){
        return "\n" + getName() + ":\ne: End Turn\n" + hand.toString();
    }

}
