/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Rico de Feijter, Marcellino van Hecke
 * @version 1.0
 */
package sherlock.holmes;

import java.util.HashMap;
import java.util.Set;

public class Room 
{
    private int building;
    private String roomName;
    private String description;
    
    private int north;
    private int east;
    private int south;
    private int west;
    
    private int up;
    private int down;
    
    private HashMap<String, Room> exits;        // stores exits of this room.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room() 
    {
        //this.description = description;
        exits = new HashMap<String, Room>();
    }
    
    /**
     * Set the parent building from room
     * @param int 
     */
    public void setBuilding(int building)
    {
        this.building = building;
    }
    
    /**
     * Set the name of the room
     * @param String 
     */
    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }
    
    /**
     * Set description for room
     * @param String 
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * Set room exit id north
     * @param int 
     */
    public void setNorth(int north)
    {
        this.north = north;
    }
    
    /**
     * Set room exit id east
     * @param int 
     */
    public void setEast(int east)
    {
        this.east = east;
    }
    
    /**
     * Set room exit id south
     * @param int 
     */
    public void setSouth(int south)
    {
        this.south = south;
    }
    
    /**
     * Set room exit id west
     * @param int 
     */
    public void setWest(int west)
    {
        this.west = west;
    }
    
    /**
     * Set room exit id up
     * @param int 
     */
    public void setUp(int up)
    {
        this.up = up;
    }
    
    /**
     * Set room exit id down
     * @param int 
     */
    public void setDown(int down)
    {
        this.down = down;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}


