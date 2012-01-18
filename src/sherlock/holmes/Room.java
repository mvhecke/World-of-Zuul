/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Sherlock Holmes" application. 
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Rico de Feijter, Marcellino van Hecke
 * @version 1.0
 */
package sherlock.holmes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Room 
{
    /*
     * Fields
     */
    private int roomID;
    private int buildingID;
    private String roomName;
    private String description;
    private int itemRequirement;

    private HashMap<String, Room> exits;// stores exits of this room.
    private ArrayList<Item> items;

    /**
     * Constructor
     */
    public Room() 
    {
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
    }
    
    /**
     * Set the ID of the room
     * @param int Room ID 
     */
    public void setRoomID(int roomID)
    {
        this.roomID = roomID;
    }
    
    /**
     * Return the ID of the room
     * @return int Room ID
     */
    public int getRoomID()
    {
        return roomID;
    }
    
    /**
     * Set the parent building from room
     * @param int set buildingID
     */
    public void setBuildingID(int buildingID)
    {
        this.buildingID = buildingID;
    }
    
    /*
     * Return buildingID
     * @return int buildingID
     */
    public int getBuildingID()
    {
        return buildingID;
    }
    
    /**
     * Set the name of the room
     * @param String roomName
     */
    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }
    
    /**
     * Return the name of the room
     * @return String roomName
     */
    public String getRoomName()
    {
        return roomName;
    }
    
    /**
     * Set description for room
     * @param String description
     */
    public void setDescription(String description)
    {
        this.description = description;
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
     * return the description
     * @return String
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Set the required item ID to access a room
     * @param int The required item ID 
     */
    public void setItemRequirement(int itemRequirement)
    {
        this.itemRequirement = itemRequirement;
    }
    
    /**
     * Return the required item ID to access a room
     * @return int The required item ID 
     */
    public int getItemRequirement()
    {
        return itemRequirement;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     */
    public String getLongDescription(ArrayList playerInventory)
    {
        return "Je bent nu in de " + roomName + ".\n" + "De uitgangen zijn:\n" + getExitsString(playerInventory);
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return ArrayList
     */
    public ArrayList getExits()
    {
        ArrayList<Room> arrayExits = new ArrayList<Room>();

        for (Entry<String, Room> entry : exits.entrySet())
        {
            arrayExits.add(entry.getValue());
        }
        
        return arrayExits;
    }
    
    /*
     * Check if a room is closed. The player must have a quest item in his inventory to open a locked room
     * @param Arraylist
     */
    public String getExitsString(ArrayList inventory)
    {
        ArrayList playerInventory = inventory;
        ArrayList<Room> lockedRooms = new ArrayList<Room>();
        String exitString = "";
        
        //Loop through all exits
        for (Entry<String, Room> entry : exits.entrySet())
        {
            int ItemRequirement = entry.getValue().getItemRequirement();
            
            //Check if room has an item requirement
            if(ItemRequirement > 0)
            {
                if(playerInventory.size() > 0)
                {
                    //Check if there are any quest items in possession
                    for (Iterator inventoryIterator = playerInventory.iterator(); inventoryIterator.hasNext();)
                    {
                        Item inventoryItem = (Item) inventoryIterator.next();
                        
                        //If matches add it to locked rooms
                        if(ItemRequirement == inventoryItem.getItemID())
                        {
                            lockedRooms.add(entry.getValue());
                        }
                    }
                }else
                {
                    //If no inventory size guaranteed locked room
                    lockedRooms.add(entry.getValue());
                }
            }
            
        }
        
        //Loop through al exits
        for (Entry<String, Room> entry : exits.entrySet())
        {
            if(lockedRooms.size() > 0)
            {
                int ItemRequirement = entry.getValue().getItemRequirement();
                
                if(ItemRequirement > 0)
                {
                    int currentRoomID = entry.getValue().getRoomID();

                    if(playerInventory.size() > 0)
                    {
                            for (Iterator lockedIterator = lockedRooms.iterator(); lockedIterator.hasNext();)
                            {
                                Room lockedRoom = (Room) lockedIterator.next();

                                if(currentRoomID == lockedRoom.getRoomID())
                                {
                                    exitString += entry.getKey() + " = " + entry.getValue().getRoomName() + " (Gesloten), "; 
                                }else
                                {
                                    exitString += entry.getKey() + " = " + entry.getValue().getRoomName() + ", "; 
                                }
                            }
                    }else
                    {
                        exitString += entry.getKey() + " = " + entry.getValue().getRoomName() + " (Gesloten), ";
                    }
                }else
                {
                    exitString += entry.getKey() + " = " + entry.getValue().getRoomName() + ", ";
                }
            }else
            {
                exitString += entry.getKey() + " = " + entry.getValue().getRoomName() + ", ";
            }
        }
        
        exitString = exitString.substring(0, exitString.lastIndexOf(","));
        
        return exitString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return room
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Assign item to room
     * @param Item Insert an item object 
     */
    public void setItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * Return an item object from specified ID
     * @param int Item ID
     * @return Item
     */
    public Item getItem(int item)
    {
        return items.get(item);
    }
    
    /**
     * Return items
     * @return ArrayList
     */
    public ArrayList getItems()
    {
        return items;
    }
    
    /**
     * Return amount of items in room
     * @return int
     */
    public int getItemsAmount()
    {
        return items.size();
    }
}


