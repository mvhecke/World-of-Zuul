/**
 *  This class is the player class. This class is used to set the player
 * his name. The name is used to congratulate the user with it's completed
 * quest. It is also added to add feature functions.
 * 
 * @author  Rico de Feijter, Marcellino van Hecke
 * @version 1.0
 */
package sherlock.holmes;

import java.util.ArrayList;

public class Player {
    
    /*
     * Fields
     */
    private String playerName;
    private int currentRoomID;
    private Room currentRoom;
    
    private boolean teaTime;
    private int playerBeamerLocation;
    
    private ArrayList<Item> inventory = new ArrayList<Item>();
    
    /**
     * Set player name
     * @param String set playerName
     */
    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }
    
    /**
     * Return player name
     * @return String
     */
    public String getPlayerName()
    {
        return playerName;
    }
    
    /**
     * Set the current room ID where the player is
     * @param int The current room ID 
     */
    public void setCurrentRoomID(int currentRoomID)
    {
        this.currentRoomID = currentRoomID;
    }
    
    /**
     * Return the current room ID where the player is
     * @return int
     */
    public int getCurrentRoomID()
    {
        return currentRoomID;
    }
    
    /**
     * Set the players current room
     * @param Room The current room object 
     */
    public void setCurrentRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }
    
    /**
     * Return the current room object the player is in
     * @return Room The current room object
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * Set the teatime true/false
     */
    public void setTeaTime(boolean tea)
    {
        this.teaTime = tea;
    }
    
    /**
     * Return wether or not it is tea time
     * @return boolean Tea time true/false
     */
    public boolean getTeaTime()
    {
        return teaTime;
    }
    
    /**
     * Same the location of the room the player is currently in
     * @param int The room ID 
     */
    public void setPlayerBeamerLocation(int location)
    {
        this.playerBeamerLocation = location;
    }
    
    /**
     * Return the saved room ID
     * @return int The room ID
     */
    public int getPlayerBeamerLocation()
    {
        return playerBeamerLocation;
    }
    
    /**
     * Add an item to the inventory
     * @param Item The given item object.
     */
    public void addItem(Item item)
    {
        inventory.add(item);
    }
    
    /**
     * Remove a specified item from the inventory
     * @param int Specified item id
     */
    public void removeItem(int item)
    {
        inventory.remove(item);
    }
    
    /**
     * Return inventory item
     * @param int
     */
    public Item getInventoryItem(int item)
    {
        return inventory.get(item);
    }
    
    /**
     * Return the size of the item inventory
     * @return int
     */
    public int getInventorySize()
    {
        return inventory.size();
    }
    
    /**
     * Return the full inventory list
     * @return ArrayList
     */
    public ArrayList getInventory()
    {
        return inventory;
    }
    
}
