package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Thread meant to receive messages from the client over the network
 * Constantly Listens for new commands
 * Takes action when a valid command is sent
 */

public class CCGServerThread extends Thread{
    // Networking
    protected PrintWriter out;
    protected BufferedReader in;

    // Function
    final Player player;

    // Constructor
    public CCGServerThread(Player player){
        super();
        this.in = player.getNetworkReader();
        this.out = player.getNetworkWriter();
        this.player = player;
    }

    @Override
    public void run(){
        // Process Commands until connection is terminated
        boolean exit = false;
        while (!exit && player.getSocket().isConnected()){
            exit = processCommand();
        }
        System.out.println(player.getUsername() + " DISCONNECTED");
    }

    /**
     * Method to break the request from the client into command and args
     * @return status of thread - true for exit, false for keep alive
     */
    private boolean processCommand(){
        String message;
        try {
            // hangs here until new message is received
            message = in.readLine();
        } catch (IOException e){
            e.printStackTrace();
            return true;
        }

        // Break the message into command and args
        StringTokenizer st = new StringTokenizer(message);
        String command = st.nextToken();
        String args = null;
        if (st.hasMoreTokens()){
            args = message.substring(command.length()+1);
        }
        return processCommand(command, args);
    }

    /**
     * Method to process the commands sent from the client
     * Current full list of commands:
     *      EXIT - closes the connection with the client and ends the thread
     *
     * @param command Command issued from the client
     * @param args Additional arguments
     * @return Thread status - true for exit, false for keep alive
     */
    private boolean processCommand(String command, String args){
        // get the username from the player. Sets the username in the associated player object
        if (command.equalsIgnoreCase(("UID"))){
            player.setUsername(args);
            synchronized (player){
                player.notifyAll();
            }
            return false;
        }
        // Ensure user has a username, if no UN then requests are invalid and server disconnects
        else if (player.getUsername() == null){
            out.println("401 PREREQUISITE UID REQUEST NOT RECEIVED. DISCONNECTING");
            System.out.println("NO UID");
            return true;
        }
        else {
            out.println("400 REQUEST NOT UNDERSTOOD");
            return false;
        }
    }
}
