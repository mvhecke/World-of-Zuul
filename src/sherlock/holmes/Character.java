/**
 * Class Character - a character in an adventure game.
 *
 * This class is part of the "Sherlock Holmes" application. 
 * 
 * @author  Rico de Feijter, Marcellino van Hecke
 * @version 1.0
 */
package sherlock.holmes;

public class Character {
    
    /**
     * Fields
     */
    private String name;
    private Room room;
    
    /**
     * Constructor
     * @param String fill in a name
     * @param Room set the room
     */
    public Character(String name, Room room) {
        this.name = name;
        this.room = room;
    }
   
    /**
     * @param String fill in a name
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Get the name of the character
     * @return String
     */
    public String getName(){
        return name;
    }
    
    /**
     * Set the room
     * @param Room set Room
     */
    public void setRoom(Room room){
        this.room = room;
    }

    /**
     * Get room
     * @return Room
     */
    public Room getRoom(){
        return room;
    }
    
    
}