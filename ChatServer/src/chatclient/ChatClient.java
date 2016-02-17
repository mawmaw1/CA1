/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

/**
 *
 * @author ingim
 */
public class ChatClient extends Thread{
    
    Socket socket;
    private int port = 9999;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    private List<ChatObserver> observers = new ArrayList();

    public void addChatObserver(ChatObserver eo) {
        observers.add(eo);
    }

    public void connect(String address, int port) throws UnknownHostException, IOException {
        this.port = port;
        serverAddress = InetAddress.getByName(address);
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
    }

    public void send(String msg) {
        output.println(msg);
    }

    public String receive() {

        String msg = input.nextLine();

        if (msg.equals(ProtocolStrings.LOGOUT)) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ChatObserver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return msg;

    }

    public void notifyObservers(String res) {
        for (ChatObserver observer : observers) {
            observer.RecieveMessageReady(res);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                notifyObservers(receive());
                System.in.read();
            } catch (UnknownHostException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}