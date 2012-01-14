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
     * Constructor - initialise the name and the room.
     */
    public Character(String name, Room room) {
        this.name = name;
        this.room = room;
    }
   
    /**
     * Set the name of the character
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Get name of the character
     */
    public String getName(){
        return name;
    }
    
    /**
     * Set room
     */
    public void setRoom(Room room){
        this.room = room;
    }

    /**
     * Get room
     */
    public Room getRoom(){
        return room;
    }
    
    
}