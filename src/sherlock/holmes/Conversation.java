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
    
    /*
     * Fields
     */
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
     * Asks player a question and to input a value
     * @param String give in a question
     */
    public String askQuestionInput(String question)
    {
        System.out.println(question);
        System.out.print("> ");
        String questionAnswer = reader.nextLine().trim();
        return questionAnswer;
    }
    
    /**
     * Asks player to input a value
     */
    public String askInput()
    {
        System.out.print("> ");
        String questionAnswer = reader.nextLine().trim();
        return questionAnswer;
    }
    
}
