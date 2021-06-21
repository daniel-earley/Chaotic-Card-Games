package server;

import java.io.IOException;
import java.util.*;

/**
 * Main Server Class: Handles the flow of the program
 *
 * Connects MIN_PLAYERS before a game can start
 */
public class CCGServer {
    // Game Information
    private static final int MAX_PLAYERS = 4;
    private static final int MIN_PLAYERS = 2;
    protected final List<Player> players = new ArrayList<>();

    // Constructor
    public CCGServer(){
        try {
            // Start Connection Thread
            Runnable runnable = new ConnectClientThread(MAX_PLAYERS, players);
            Thread connect = new Thread(runnable);
            connect.start();

            // Wait until there are enough players
            synchronized (players){
                while (players.size() < MIN_PLAYERS){
                    players.wait();
                }
            }

            // Game Shit
            System.out.println("Enough players for a game");
            GameLogicC8 game = new GameLogicC8(players);
            ArrayList<String> newMsgs = new ArrayList<>();

//            // Game Loop
//            //Initialise Decks
//            Deck mainDeck = new Deck(1);
//            Deck discardPile = new Deck();
//
//            // Deal the deck
//            mainDeck.shuffle();
//            game.deal(mainDeck, 5);
//
//            // Sets the top card of the discard pile
//            discardPile.push(mainDeck.pop());
//
//            // Game Loop (GL)
//            boolean gameOver = false;
//            while (gameOver == false) {
//
//                for (Player player : players) {
//                    //print out cards
//                    System.out.println("Last Card in Play:\n" + discardPile.top());
//                    System.out.println(player);
//
//                    // Set turn
//                    player.flipTurn();
//
//                    if (player.getTurn()) {
//                        chooseOption(player);
//                    }
//
//                    if (player.isWin()) {
//                        System.out.printf("%s Is The Champion!\n", player.getName());
//                        gameOver = true;
//                        break;
//                    }
//                }
//
//            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
