/**
 *  This class is the player class. This class is used to set the player
 * his name. The name is used to congratulate the user with it's completed
 * quest. It is also added to add feature functions.
 * 
 * @author  Rico de Feijter, Marcellino van Hecke
 * @version 1.0
 */
package sherlock.holmes;

public class Player {
    
    private String playerName;
    private Item items;
    
    public void Player(String playerName)
    {
        this.playerName = playerName;
    }
    
    /**
     * Set player name
     */
    public void setPlayerName()
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
    
}
