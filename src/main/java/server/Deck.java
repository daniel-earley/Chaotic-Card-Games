package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The purpose of this class is to create a deck containing cards from the Card class
 * It should contain shuffling, discarding and picking up
 */

public class Deck {
    private List<Card> deckOfCards;

    /**
     * The default constructor creates an empty deck
     */
    public Deck() {
        deckOfCards = new ArrayList<>();
    }

    /**
     * This constructor creates one or more decks each containing 52 cards
     * (more decks get added to the initial deck)
     * @param numDecks - the amount of decks being created
     */
    public Deck(int numDecks){
        deckOfCards = new ArrayList<>();
        for (int x = 0; x < numDecks; x++) {
            for (int i = 1; i < 5; i++){
                for (int j = 1; j < 14; j++){
                    Card newCard = new Card(j, i);
                    pickup(newCard);
                }
            }
        }
    }

    public void shuffle (){
        int n = deckOfCards.size();
        Random rand = new Random();
        for (int i = n-1; i > 0; i--){
            int j = rand.nextInt(i);
            Collections.swap(deckOfCards, i, j);
        }
    }

    public void pickup (Card card){
        deckOfCards.add(card);
    }

    public void discard (Card card) {deckOfCards.remove(card);}

    public Card popCard (int cardIndex) {
        Card card = deckOfCards.get(cardIndex);
        discard(card);
        return card;
    }

    public Card viewCard(int index){
        return deckOfCards.get(index);
    }

    public Card top(){
        return deckOfCards.get(0);
    }

    public Card pop(){
        Card card = deckOfCards.get(0);
        deckOfCards.remove(0);
        return card;
    }

    public void push(Card card){
        deckOfCards.add(0, card);
    }

    public boolean isEmpty(){
        return deckOfCards.isEmpty();
    }

    public int getSize(){
        return deckOfCards.size();
    }

    public String toString(){
        String cards = "";
        int index = 0;
        for (Card card : deckOfCards){
            cards += index + ": " + card.toString() + "\n";
            index++;
        }
        return cards;
    }

}


