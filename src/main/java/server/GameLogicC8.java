package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The GameLogic class is self-explanatory, this class holds all the information that is required to play the game
 */
public class GameLogicC8 {
    private static List<Player> playerList;
    private static Deck mainDeck;
    private static Deck discardPile;

    // Constructor
    public GameLogicC8(List<Player> pList){
        this.playerList = pList;
        this.mainDeck = new Deck(1);
        this.discardPile = new Deck();
    }

    /**
     * Deal cards out to each player's hand
     * @param numCards - number of cards dealt
     */
    public static void deal(int numCards) {
        for (int i = 0; i < numCards; i++) {
            for (Player player : playerList) {
                player.hand.pickup(mainDeck.pop());
            }
        }
    }

    public static Card pickup(){
        return mainDeck.pop();
    }

    public static void playCard(Card card) {
        checkSpecial(card);
        discardPile.push(card);
    }

    public static void checkText(Player player, String input) {
        if (input.matches("[pP]")){
            player.pickup(pickup());
            System.out.println(player.toStringAlt());
        } else if (input.matches("[eE]")){
            System.out.printf("%s ends their turn\n", player.getUsername());
            player.setTurn(false);
        } else {
            System.out.println("Invalid Input (Incorrect Letter)");
        }
    }

    public static void chooseOption(Player player){
        int index = 0;
        Scanner scan = new Scanner(System.in);
        System.out.printf("%s, please type in the index of the card you wish to play: ", player.getUsername());

        // Protected Inputs
        boolean played = false;
        while (!played) {
            // Checks if the input is a number or p (for pickup)
            while (!scan.hasNextInt()) {
                if (scan.hasNext("[pPeE]")){
                    checkText(player, scan.next());

                    if (!player.getTurn()){
                        break;
                    }
                } else {
                    System.out.println("Invalid Input (Not a number)");
                }
            }

            if (!player.getTurn()){
                break;
            }

            // Select a card
            index = scan.nextInt();

            // Checks if input is a valid input
            if (index < player.hand.getSize()){
                while (true) {
                    // Checks the value of the card against the top card in the discard pile
                    if (player.viewCard(index).getValue() == discardPile.top().getValue()) {
                        playCard(player.play(index));
                        played = true;
                    }

                    // Checks if the card is a wild card
                    else if (player.viewCard(index).getValue() == 8){
                        playCard(player.play(index));
                        played = true;
                    }

                    // Checks the suit of the card against the top card in the discard pile
                     else if (player.viewCard(index).getSuit().equals(discardPile.top().getSuit())) {
                        playCard(player.play(index));
                        played = true;
                    } else {
                        System.out.println("That card is currently unable to be played");
                    }
                    break;
                }

                // breaks the loop
                if (played) {
                    played = false;
                    break;
                }
            } else {
                System.out.println("Invalid Input (Number out of range)");
            }
        }
    }

    public static void checkSpecial(Card card){
        switch (card.getValue()){
            case 2 -> System.out.println("Pickup 2");
            case 8 -> wildCard();
            case 11 -> System.out.println("Skip Card");
            case 12 -> System.out.println("Pickup 5");
        }
    }

    public static void wildCard(){
        System.out.print("1: Clubs\n" +
                          "2: Spades\n" +
                          "3: Diamonds\n" +
                          "4: Hearts\n");
        System.out.println("Please choose a suit: ");

        Scanner scan = new Scanner(System.in);
        while (true) {

            while (!scan.hasNextInt()) {
                System.out.println("Invalid Input (not a number)");
                scan.next();
            }

            int index = scan.nextInt();

            if (index > 1 && index <= 4){
                discardPile.top().setSuit(index);
                switch (index) {
                    case 1 -> System.out.println("The suit has changed to Clubs");
                    case 2 -> System.out.println("The suit has changed to Spades");
                    case 3 -> System.out.println("The suit has changed to Diamonds");
                    case 4 -> System.out.println("The suit has changed to Hearts");
                }
                break;
            }

            System.out.println("Invalid Input (out of range)");
        }

    }

    public static Deck getMainDeck() {
        return mainDeck;
    }

    public static Deck getDiscardPile() {
        return discardPile;
    }

    /**
    public static void main(String[] args) {
        // Initialise
        playerList.add(new Player("Dan"));
        playerList.add(new Player("Bob"));

        // Deal the deck
        mainDeck.shuffle();
        deal(4);
        playerList.get(0).pickup(new Card(2, 2));
        playerList.get(0).pickup(new Card(8, 2));
        playerList.get(0).pickup(new Card(11, 2));
        playerList.get(0).pickup(new Card(12, 2));

        // Sets the top card of the discard pile
        discardPile.push(mainDeck.pop());

        // Game Loop (GL)
        boolean gameOver = false;
        while (gameOver == false) {

            for (Player player : playerList) {
                //print out cards
                System.out.println("Last Card in Play:\n" + discardPile.top());
                System.out.println(player);

                // Set turn
                player.flipTurn();

                if (player.getTurn()) {
                    chooseOption(player);
                }

                if (player.isWin()) {
                    System.out.printf("%s Is The Champion!\n", player.getName());
                    gameOver = true;
                    break;
                }
            }

        }

    }
     */
}
