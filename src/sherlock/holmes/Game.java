/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Rico de Feijter, Marcellino van Hecke
 * @version 1.0
 */
package sherlock.holmes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private ArrayList<Room> visitedRooms;
    
    private XMLParser roomsXML;
    private XMLParser itemsXML;
    
    private HashMap<Integer, Room> rooms;
    private HashMap<Integer, Item> items;
    
    private Player player;
    private Conversation conversation;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() throws Exception
    {
        parser = new Parser();

        player = new Player(); //Create player
        
        rooms = new HashMap<Integer, Room>();//Create room storage
        visitedRooms = new ArrayList<Room>();//Create visited rooms storage
        
        //Get XML content for the rooms
        roomsXML = new XMLParser();
        roomsXML.setFilename("../rooms.xml");
        roomsXML.runXMLConvert();
        
        //Store all room data
        createRooms();
        
        //Create item storage
        items = new HashMap<Integer, Item>();
        
        //Get XML content for the items
        itemsXML = new XMLParser();
        itemsXML.setFilename("../items.xml");
        itemsXML.runXMLConvert();
        
        createItems();
        
        conversation = new Conversation();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        //Create all rooms from XML file and set all properties
        Map<Integer, HashMap<String, String>> parentMap = roomsXML.getXMLData();
        Iterator<Map.Entry<Integer, HashMap<String, String>>> parentXML = parentMap.entrySet().iterator();

        while (parentXML.hasNext())
        {
            Map.Entry<Integer, HashMap<String, String>> parentEntry = parentXML.next();

            Map<String, String> map = parentEntry.getValue();
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();

            while (entries.hasNext())
            {
                Map.Entry<String, String> entry = entries.next();

                //Create rom object if not already exists
                if(!rooms.containsKey(parentEntry.getKey()))
                {
                    rooms.put(parentEntry.getKey(), new Room());
                }

                if(entry.getKey().equals("room_building"))
                {
                    //Set building ID where room is located
                    rooms.get(parentEntry.getKey()).setBuildingID(Integer.parseInt(entry.getValue()));
                }else if(entry.getKey().equals("room_name"))
                {
                    //Set room name
                    rooms.get(parentEntry.getKey()).setRoomName(entry.getValue());
                }else if(entry.getKey().equals("description"))
                {
                    //Set description for room
                    rooms.get(parentEntry.getKey()).setDescription(entry.getValue());
                }
            }
        }
        
        //Create exits for all the created rooms
        Map<Integer, HashMap<String, String>> parentExitMap = roomsXML.getXMLData();
        Iterator<Map.Entry<Integer, HashMap<String, String>>> parentExitXML = parentExitMap.entrySet().iterator();

        while (parentExitXML.hasNext())
        {
            Map.Entry<Integer, HashMap<String, String>> parentEntry = parentExitXML.next();

            Map<String, String> map = parentEntry.getValue();
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();

            while (entries.hasNext())
            {
                Map.Entry<String, String> entry = entries.next();
                
                if(entry.getKey().equals("north") || entry.getKey().equals("east") || entry.getKey().equals("south") || entry.getKey().equals("west") || entry.getKey().equals("up") || entry.getKey().equals("down"))
                {
                    String direction = "";
                    
                    if(entry.getKey().equals("north"))
                    {
                        direction = "noord";
                    }else if(entry.getKey().equals("east"))
                    {
                        direction = "oost";
                    }else if(entry.getKey().equals("south"))
                    {
                        direction = "zuid";
                    }else if(entry.getKey().equals("west"))
                    {
                        direction = "west";
                    }else if(entry.getKey().equals("up"))
                    {
                        direction = "boven";
                    }else if(entry.getKey().equals("down"))
                    {
                        direction = "beneden";
                    }
                    
                    //Get room object to set as exit
                    Room exit = rooms.get(Integer.parseInt(entry.getValue()));
                    
                    //Set exits for a room
                    rooms.get(parentEntry.getKey()).setExit(direction, exit);
                }
            }
        }

        currentRoom = rooms.get(1); //Starts the game in the first room
    }
    
    /**
     * Create all items and set al properties
     */
    public void createItems()
    {
        //Create all items from XML file and set all properties
        Map<Integer, HashMap<String, String>> parentMap = itemsXML.getXMLData();
        Iterator<Map.Entry<Integer, HashMap<String, String>>> parentXML = parentMap.entrySet().iterator();
        
        int itemRoomID = 0;
        
        while (parentXML.hasNext())
        {
            Map.Entry<Integer, HashMap<String, String>> parentEntry = parentXML.next();

            Map<String, String> map = parentEntry.getValue();
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            
            Item currentItem = new Item();
            
            while (entries.hasNext())
            {
                Map.Entry<String, String> entry = entries.next();
                
                //Create item object if not already exists
                if(currentItem.getItemID() != 0)
                {
                    currentItem.setItemID(parentEntry.getKey());
                }

                if(entry.getKey().equals("item_room"))
                {
                    //Set room ID where item is located
                    itemRoomID = Integer.parseInt(entry.getValue());
                }else if(entry.getKey().equals("item_name"))
                {
                    //Set item name
                    currentItem.setItemName(entry.getValue());
                }else if(entry.getKey().equals("item_value"))
                {
                    //Set item value
                    currentItem.setItemValue(Integer.parseInt(entry.getValue()));
                    
                    //Add item object to room
                    rooms.get(itemRoomID).setItem(currentItem);
                }
            }
        }
    }
    
    /**
     * Sets player name
     */
    public void setPlayerName()
    {  
        player.setPlayerName(conversation.askQuestionInput("Hoe heet jij?"));
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        setPlayerName();
        printWelcome(player.getPlayerName());

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Het lukte gewoon niet om het spel af te maken?");
        System.out.println("Bedankt voor het spelen. Geef maar toe!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome(String playername)
    {
        System.out.println();
        System.out.println("Welkom " + playername + " bij Sherlock Holmes en de Moord in het Bordeel.");
        System.out.println();
        System.out.println("Het is buiten slecht weer(London natuurlijk) en je krijgt een bericht van een");
        System.out.println("koerier. Er is een moord gepleegd in het lokale bordeel om de hoek! Aan jou");
        System.out.println("als Sherlock Holmes, de detective, om deze moord op te lossen. Om te starten");
        System.out.println("moet je eerst naar het politie bureau toe gaan.");
        System.out.println();
        System.out.println("Type 'help' als je hulp nodig hebt.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("Ik weet niet wat je bedoelt...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("ga")) {
            goRoom(command);
        }else if (commandWord.equals("kijk")) {
            lookInRoom();
        }
        else if (commandWord.equals("stop")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Je hebt om hulp gevraagd? En dat voor een detective?");
        System.out.println("Jij stelt ook niet veel voor als je hulp nodig hebt..");
        System.out.println();
        System.out.println("Je commando woorden zijn:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Waar naar toe?");
            return;
        }
        
        if(command.getSecondWord().equals("terug"))
        {
            Room lastRoom = null;
            
            if(visitedRooms.size() >= 1)
            {
                lastRoom = visitedRooms.get(visitedRooms.size()-1);
            }
            
            if(lastRoom == currentRoom || lastRoom == null)
            {
                System.out.println("Je kunt niet verder terug gaan!");
            }else
            {
                currentRoom = lastRoom;
                
                //Remove last room from ArrayList
                visitedRooms.remove(visitedRooms.size()-1);
                
                System.out.println(currentRoom.getLongDescription());
            }
            
        }else
        {
            String direction = command.getSecondWord();

            // Try to leave current room.
            Room nextRoom = currentRoom.getExit(direction);

            if (nextRoom == null) {
                System.out.println("Er is geen deur!");
            }
            else {
                visitedRooms.add(currentRoom); //Add current room to visited rooms
                currentRoom = nextRoom; //Make next room current room
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
    
    /**
     * Look for items in the current room
     */
    public void lookInRoom()
    {
        String printString = "";
        
        for (Iterator it = currentRoom.getItems().iterator(); it.hasNext();) {
            Item roomItem = (Item) it.next();
            
            printString += roomItem.getItemName() + ", ";
        }
        
        //Cut of last comma
        printString = printString.substring(0, printString.lastIndexOf(","));
        
        System.out.println(printString);
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Met wat stoppen?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    public static void main(String[] args)throws Exception
    {
        
            Game game = new Game();
            game.play();
        try{     
        }catch(Exception e){
            System.err.println("Er heeft zich een ernstige fout voorgedaan waardoor het spel niet meer functioneerd.");
        }
        
    }
    
}

