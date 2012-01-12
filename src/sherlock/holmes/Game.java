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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private HashMap<Integer, Room> rooms;
    
    private XMLParser xmlParser;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() throws Exception
    {
        parser = new Parser();
        
        rooms = new HashMap<Integer, Room>();
        
        xmlParser = new XMLParser();
        xmlParser.setFilename("../rooms.xml");
        xmlParser.runXMLConvert();
        
        createRooms();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Map<Integer, HashMap<String, String>> parentMap = xmlParser.getXMLData();
        Iterator<Map.Entry<Integer, HashMap<String, String>>> parentXML = parentMap.entrySet().iterator();

        while (parentXML.hasNext())
        {
            Map.Entry<Integer, HashMap<String, String>> parentEntry = parentXML.next();

            Map<String, String> map = parentEntry.getValue();
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();

            while (entries.hasNext())
            {
                Map.Entry<String, String> entry = entries.next();
                
                if(entry.getKey().equals("description"))
                {
                    rooms.put(parentEntry.getKey(), new Room(entry.getKey()));
                }
            }
        }
        
        Room outside, theatre, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        //Print all XML Junk
        Map<Integer, HashMap<String, String>> parentMap = xmlParser.getXMLData();
        Iterator<Map.Entry<Integer, HashMap<String, String>>> parentXML = parentMap.entrySet().iterator();

        while (parentXML.hasNext())
        {
            Map.Entry<Integer, HashMap<String, String>> parentEntry = parentXML.next();

            System.out.println("Parent = " + parentEntry.getKey());

            Map<String, String> map = parentEntry.getValue();
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();

            while (entries.hasNext())
            {
                Map.Entry<String, String> entry = entries.next();

                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            System.out.println();

        }
        
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
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
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
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
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
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
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
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
    }
    
}

