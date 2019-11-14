public class Go extends Square
{
    private int goAmount;
    
    /**
     * Constructor for a Go object.
     * @param sprite a String representation of the appearance of this Go
     */
    public Go(String[] sprite)
    {
        super("Go",0, sprite);
        goAmount = 200;
    }
    
    /**
     * Deposits the Go amount into the active Player's balance.
     */
    public void doItsThing()
    {
        getPList().get(getTurn()).deposit(goAmount);
        /* 1. Player A lands on the space.
         *      Player A is granted set goAmount. 
         * Also should work if players PASS the space.     
         */
    }
    
    /**
     * Returns the amount Go deposits when landed upon.
     */
    public int getGoAmount()
    {
        return goAmount;
    }


}
