/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

/**
 *
 * @author Magnus
 */
public class ClientHandler extends Thread {

    Scanner input;
    PrintWriter writer;
    Socket socket;
    ChatServer server;
    String clientName;

    public ClientHandler(Socket socket, ChatServer server) throws IOException {

        input = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
        this.socket = socket;
        this.server = server;

    }

    @Override
    public void run() {
        try{
            
        
        String message = ""; //= input.nextLine(); //IMPORTANT blocking call
        boolean enterName = false;

        while (!enterName) {
            writer.println("Please enter USER# and a name");
            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, String.format("Received the message: %1$S ", message));
            //System.out.println(String.format("Received the message: %1$S ", message.toUpperCase()));

            message = input.nextLine(); //IMPORTANT blocking call
            String[] parts = message.split("#");

            if (parts[0].equals("USER")) {
                enterName = true;
                clientName = parts[1];
                writer.println("Welcome " + clientName);
                server.addHandler(this);
                
            }

        }
        writer.println("Start sending messages!");

        boolean logout = false;

        while (!logout) {
            message = input.nextLine(); //IMPORTANT blocking call
            String[] parts = message.split("#");
            if (parts[0].equals(ProtocolStrings.SEND)) {

                server.send(message, clientName);

            }
            if (parts[0].equals(ProtocolStrings.LOGOUT)) {
                logout = true;
            }

            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, String.format("Received the message: %1$S ", message));
            //        System.out.println(String.format("Received the message: %1$S ", message.toUpperCase()));

        }

        Logger.getLogger(Log.LOG_NAME).log(Level.INFO, String.format("Received the message: %1$S ", message));
        writer.println(ProtocolStrings.LOGOUT);//Echo the stop message back to the client for a nice closedown

        try {
            socket.close();
            server.removeHandler(this);
        } catch (IOException ex) {
            Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, null, ex);
        }

        Logger.getLogger(Log.LOG_NAME).log(Level.INFO, ("Closed a Connection"));
        System.out.println("Closed a Connection");
        } catch (Exception e){
            try {
                socket.close();
                server.removeHandler(this);
            } catch (IOException ex) {
                Logger.getLogger(Log.LOG_NAME).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void send(String message) {
        writer.println(message);
    }

    public String getClientName() {
        return clientName;
    }

    @Override
    public String toString() {
        return clientName;
    }

}
