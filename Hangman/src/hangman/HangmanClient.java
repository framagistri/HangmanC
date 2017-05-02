/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Simone
 */
public class HangmanClient {
    
    public void requestClient() throws IOException{
        String hostName="Simone";
        
        Socket  clientSocket = new Socket(hostName, 8888);
        clientSocket.
    }
}
