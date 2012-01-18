/**
 * Class Item - a item in an adventure game.
 *
 * This class is used to process all items used
 * in the game.
 * 
 * @author  Rico de Feijter, Marcellino van Hecke
 * @version 1.0
 */
package sherlock.holmes;

public class Item {
    
    /*
     * Fields
     */
    private int itemID;
    private int itemRoom;
    private String itemName;
    private int itemValue;
    
    /**
     * Set the item ID
     * @param int set item
     */
    public void setItemID(int itemID)
    {
        this.itemID = itemID;
    }
    
    /**
     * Return the itemID
     * @return int
     */
    public int getItemID()
    {
        return itemID;
    }
    
    /**
     * Set the location of the item based on the roomID
     * @param int set itemRoom
     */
    public void setItemRoom(int itemRoom)
    {
        this.itemRoom = itemRoom;
    }
    
    /**
     * Return the roomID of the item
     * @return int
     */
    public int getItemRoom()
    {
        return itemRoom;
    }
    
    /**
     * Set the item name
     * @param String set itemName
     */
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    
    /**
     * Return the name of the item
     * @return String
     */
    public String getItemName()
    {
        return itemName;
    }
    
    /**
     * Set the value of the item
     * 0 is uselass, 1 is quest item
     * @param int 
     */
    public void setItemValue(int itemValue)
    {
        this.itemValue = itemValue;
    }
    
    /**
     * Return the value of the item
     * @return int
     */
    public int getItemValue()
    {
        return itemValue;
    }
}
