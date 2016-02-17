/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import chatclient.ChatClient;
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
public class ServerTest {
    
    public ServerTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    ChatServer.main(null);
                } catch (IOException ex) {
                    Logger.getLogger(ServerTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
    
    @AfterClass
    public static void tearDownClass(){
        ChatServer.stopServer();
    }

    @Test
    public void send() throws IOException {
        ChatClient client = new ChatClient();
        client.connect("localhost", 9999);
        client.send("Hello");
      //  assertEquals("HELLO", client.receive());
    }
}
