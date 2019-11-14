public class Jail extends Square
{
    /**
     * Constructor for a Jail object. All the actual Jail mechanics are implemented in Board, in the main game loop.
     * @param sprite a String representation of the appearance of this Jail
     */
    public Jail(String[] sprite)
    {
        super("Detention",10, sprite);
    }
    
    /**
     * Performs Jail's action, which is nothing.
     */
    public void doItsThing()
    {
        /* 1. Player A lands on the space.
         *      Nothing happens. */
    }
}
