/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import chatclient.ChatClient;
import chatclient.ChatObserver;
import chatserver.ChatServer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ingim
 */
public class ServerTest  implements ChatObserver{
    
    public ServerTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass(){
        String[] arr = {"localhost","9999"};
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    ChatServer.main(arr);
                } catch (IOException ex) {
                    Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
    
    
    @Test
    public void newClient() throws IOException{
        ChatClient client = new ChatClient();
        client.connect("localhost", 9999);
        client.addChatObserver(this);
        client.start();
        
        client.send("USER#Magnus");
        client.send("SEND#*#Hey");
        client.send("LOGOUT#");
        
    }
    
    
    
    @AfterClass
    public static void tearDownClass(){
        ChatServer.stopServer();
    }

    @Test
    public void send() throws IOException {
        
        
       // client.send("Hello");
      //  assertEquals("HELLO", client.receive());
    }

    @Override
    public void RecieveMessageReady(String recieveMsg) {
        System.out.println(recieveMsg);
    }

    @Override
    public void RecieveUsers(String[] recieveUsers) {
        for(int i =0;i<recieveUsers.length;i++){
            System.out.println(recieveUsers[i]);
        }
    }
}
