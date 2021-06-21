package server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Player {
    // Player Properties
    private String username;
    protected Deck hand;
    private boolean turn = false;

    // Connection Info
    public CCGServerThread ccgServerThread;
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    // Constructor
    public Player(Socket socket) throws IOException{
        // Setup output streams
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Start listener thread
        this.ccgServerThread = new CCGServerThread(this);
        this.socket = socket;
    }

    // Communication Functions

    public synchronized void sendPlayerNames(List<Player> newPlayers) {
        StringBuilder msg = new StringBuilder("PLAYERNAMES ");
        for (Player player: newPlayers) {
            msg.append(player.getUsername()).append(" ");
        }
        out.println(msg);
    }

    // Game Functions

    public Card play(int index) {
        setTurn(false);
        Card card = hand.popCard(index);
        System.out.printf("%s played %s\n", getUsername(), card);
        return card;
    }

    public Card viewCard(int index){
        Card card = hand.viewCard(index);
        return card;
    }

    public void pickup(Card card){
        System.out.printf("%s picked up a card\n", getUsername());
        this.hand.pickup(card);
    }

    // Getters and Setters
    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){ this.username = username; }

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
        return "\n" + getUsername() + ":\np: Pickup a Card\n" + hand.toString();
    }

    public String toStringAlt(){
        return "\n" + getUsername() + ":\ne: End Turn\n" + hand.toString();
    }

    public BufferedReader getNetworkReader() { return in; }

    public PrintWriter getNetworkWriter() { return out; }

    public Socket getSocket() { return socket; }
}
