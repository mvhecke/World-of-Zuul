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
    
    private String playerName;
    private boolean teaTime;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    
    /**
     * Constructor
     */
    public void Player()
    {
    }
    
    /**
     * Set player name
     * @param String
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
     * @return 
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
