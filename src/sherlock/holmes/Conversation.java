/**
 * Class Conversation - a conversation in an adventure game.
 *
 * This class is part of the "Sherlock Holmes" application. 
 * 
 * @author  Rico de Feijter, Marcellino van Hecke
 * @version 1.0
 */
package sherlock.holmes;

import java.util.Scanner;

public class Conversation {
    
    //Field
    private Scanner reader;
    int progress;

    /**
     * Constructor.
     */
    public Conversation()
    {
        reader = new Scanner(System.in);    
        progress = 0;
    }
    
    /**
     * Asks player to input a value
     */
    public String askQuestionInput(String question)
    {
        System.out.println(question);
        System.out.print("> ");
        String playerName = reader.nextLine().trim();
        return playerName;
    }
    
}
