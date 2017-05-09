/*
 * Code used in the "Software Engineering" course.
 *
 * Copyright 2017 by Claudio Cusano (claudio.cusano@unipv.it)
 * Dept of Electrical, Computer and Biomedical Engineering,
 * University of Pavia.
 */
package console;

import hangman.Game;
import hangman.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simone
 */
public class OnlinePlayer extends Player{

    private Socket mysocket;
    
    public OnlinePlayer(Socket mysocket){
        this.mysocket = mysocket;
    }
    
    @Override
    public char chooseLetter(Game game) {
        while (true) {
            BufferedReader inFromClient = null;
            try {
                System.out.print("Inserisci una lettera: ");
                String line = null;
                while(true){
                    inFromClient = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
                    line = inFromClient.readLine();
                    System.out.println(line);
                    if(line!=null)
                        break;
                }
                if (line.length() == 1 && Character.isLetter(line.charAt(0))) {
                    return line.charAt(0);
                } else {
                    System.out.println("Lettera non valida.");
                }
            } catch (IOException ex) {
                Logger.getLogger(OnlinePlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
    

    @Override
    public void update(Game game) {
                switch(game.getResult()) {
            case FAILED:
                printBanner("Hai perso!  La parola da indovinare era '" +
                            game.getSecretWord() + "'");
                break;
            case SOLVED:
                printBanner("Hai indovinato!   (" + game.getSecretWord() + ")");
                break;
            case OPEN:
                int rem = Game.MAX_FAILED_ATTEMPTS - game.countFailedAttempts();
                System.out.print("\n" + rem + " tentativi rimasti\n");
                System.out.println(this.gameRepresentation(game));
                System.out.println(game.getKnownLetters());
                break;
        }
    }
    
    private String gameRepresentation(Game game) {
        int a = game.countFailedAttempts();
        
        String s = "   ___________\n  /       |   \n  |       ";
        s += (a == 0 ? "\n" : "O\n");
        s += "  |     " + (a == 0 ? "\n" : (a < 5
                ? "  +\n"
                : (a == 5 ? "--+\n" : "--+--\n")));
        s += "  |       " + (a < 2 ? "\n" : "|\n");
        s += "  |      " + (a < 3 ? "\n" : (a == 3 ? "/\n" : "/ \\\n"));
        s += "  |\n================\n";
        return s;
    }
    
    private void printBanner(String message) {
        System.out.println("");
        for (int i = 0; i < 80; i++)
            System.out.print("*");
        System.out.println("\n***  " + message);
        for (int i = 0; i < 80; i++)
            System.out.print("*");
        System.out.println("\n");
    }
    
}
