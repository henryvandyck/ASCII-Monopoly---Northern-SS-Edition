public class GoToJail extends Square
{
    /**
     * Constructor for objects of class GoToJail.
     * @param sprite a String representation of the appearance of this GoToJail
     */
    public GoToJail(String[] sprite)
    {
        super("Go To Detention", 30, sprite); //is this a good way of doing these 1-time-use classes
    }
    
    /**
     * Sends the active player to jail.
     */
    public void doItsThing()
    {
        //this will run if a player has landed on this space
        //send the player to location index 
        getPList().get(getTurn()).toggleImprison(true);
        getPList().get(getTurn()).move(10);
        //badabing badaboom
    }
}
