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
        //Create all rooms from XML file and set all properties
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

                if(!rooms.containsKey(parentEntry.getKey()))
                {
                    rooms.put(parentEntry.getKey(), new Room());
                }

                if(entry.getKey().equals("room_building"))
                {
                    rooms.get(parentEntry.getKey()).setBuilding(Integer.parseInt(entry.getValue()));
                }else if(entry.getKey().equals("room_name"))
                {
                    rooms.get(parentEntry.getKey()).setRoomName(entry.getValue());
                }else if(entry.getKey().equals("description"))
                {
                    rooms.get(parentEntry.getKey()).setDescription(entry.getValue());
                }else if(entry.getKey().equals("north"))
                {
                    rooms.get(parentEntry.getKey()).setNorth(Integer.parseInt(entry.getValue()));
                }else if(entry.getKey().equals("east"))
                {
                    rooms.get(parentEntry.getKey()).setEast(Integer.parseInt(entry.getValue()));
                }else if(entry.getKey().equals("south"))
                {
                    rooms.get(parentEntry.getKey()).setSouth(Integer.parseInt(entry.getValue()));
                }else if(entry.getKey().equals("west"))
                {
                    rooms.get(parentEntry.getKey()).setWest(Integer.parseInt(entry.getValue()));
                }else if(entry.getKey().equals("up"))
                {
                    rooms.get(parentEntry.getKey()).setUp(Integer.parseInt(entry.getValue()));
                }else if(entry.getKey().equals("down"))
                {
                    rooms.get(parentEntry.getKey()).setDown(Integer.parseInt(entry.getValue()));
                }
            }
        }
        
        //Create exits for all the created rooms
        Map<Integer, HashMap<String, String>> parentExitMap = xmlParser.getXMLData();
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
                    rooms.get(parentEntry.getKey()).setExit(entry.getKey(), rooms.get(entry.getValue()));
                }
            }
        }

        currentRoom = rooms.get(1);  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {   
        printWelcome();

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
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welkom bij Sherlock Holmes en de Moord in het Bordeel.");
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

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Er is geen deur!");
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
            System.out.println("Met wat stoppen?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    public static void main(String[] args)throws Exception
    {
        try{
            Game game = new Game();
            game.play();
        }catch(Exception e){
            System.err.println("Er heeft zich een ernstige fout voorgedaan waardoor het spel niet meer functioneerd.");
        }
        
    }
    
}

