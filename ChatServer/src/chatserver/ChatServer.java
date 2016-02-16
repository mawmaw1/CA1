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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

/**
 *
 * @author Magnus
 */
public class ChatServer {

    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private String ip;
    private int port;

    public static void stopServer() {
        keepRunning = false;
    }

    private static void handleClient(Socket socket) throws IOException {
        Scanner input = new Scanner(socket.getInputStream());
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        String message = input.nextLine(); //IMPORTANT blocking call
        Logger.getLogger(Log.LOG_NAME).log(Level.INFO, String.format("Received the message: %1$S ", message));
        System.out.println(String.format("Received the message: %1$S ", message));

        while (!message.equals(ProtocolStrings.STOP)) {
            writer.println(message.toUpperCase());
            Logger.getLogger(Log.LOG_NAME).log(Level.INFO, String.format("Received the message: %1$S ", message.toUpperCase()));
            System.out.println(String.format("Received the message: %1$S ", message.toUpperCase()));
            message = input.nextLine(); //IMPORTANT blocking call
        }
        writer.println(ProtocolStrings.STOP);//Echo the stop message back to the client for a nice closedown
        socket.close();

        Logger.getLogger(Log.LOG_NAME).log(Level.INFO, ("Closed a Connection"));
        System.out.println("Closed a Connection");
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
            System.err.println("Could not listen on port: 8087");
            System.exit(1);
        }

        try {
            while (true) {

                Socket socket = serverSocket.accept();

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            handleClient(socket);
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
