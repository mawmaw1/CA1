/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

/**
 *
 * @author Magnus
 */
public class ChatServer {

    private HashMap<ClientHandler, Integer> map = new HashMap();

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private String ip;
    private int port;
    private int value = 1;

    public static void stopServer() {
        keepRunning = false;
    }

    private void runServer(String ip, int port) throws IOException {
        this.port = port;
        this.ip = ip;

        Logger.getLogger(Log.LOG_NAME).log(Level.INFO, ("Server started. Listening on: " + port + ", bound to: " + ip));
        System.out.println("Server started. Listening on: " + port + ", bound to: " + ip);

        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(port));
            System.out.println("Server started, listening on port: " + port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 9999");
            System.exit(1);
        }

        try {
            while (true) {

                Socket socket = serverSocket.accept();

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            ClientHandler handler = new ClientHandler(socket, ChatServer.this);
                            map.put(handler, value);
                            value++;
                            handler.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }

    }

    public void removeHandler(ClientHandler h) {
        map.remove(h);
    }

    public void send(String message) {
        for (ClientHandler ch : map.keySet()) {
            ch.send(message);
        }
    }

    public static void main(String[] args) throws IOException {

        try {
            Log.setLogFile("logFile.txt", "ServerLog");
            String ip = args[0]; 
            int port = Integer.parseInt(args[1]);
                       
            new ChatServer().runServer(ip, port);
        } finally {
            Log.closeLogger();
        }

    }
}
