/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

/**
 *
 * @author ingim
 */
public interface ChatObserver {
    void RecieveMessageReady(String recieveMsg);
    void RecieveUsers(String[] recieveUsers);
    
}
