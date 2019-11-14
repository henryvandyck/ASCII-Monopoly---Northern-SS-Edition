public class Parking extends Square
{
    public static int parkingTotal;
    
    /**
     * Constructor for a Parking object.
     * @param sprite a String representation of the appearance of this Parking
     */
    public Parking(String[] sprite)
    {
        super("Parking Lot",20, sprite);
    }
    
    /**
     * Gives the active player the current total of parking money and resets it, if the Free Parking rule is turned on
     */
    public void doItsThing()
    {
        if (getOptions()) //Option is true - they collect the money
        {
            getPList().get(getTurn()).deposit(parkingTotal);
            parkingTotal = 0;
        }
        /* 2 Options:
         * 1. Player A lands on the space. Nothing Happens
         * 2. Player A lands on the space. Receives parkingTotal. Sets parkingTotal to 0. */
    }

    /**
     * Returns the current parking money total.
     */
    public static int getParking()
    {return parkingTotal;}
    
    /**
     * Deposits a given sum into the parking total.
     * @param sum the sum to be deposited
     */
    public void deposit(int sum)
    {
        parkingTotal += sum;
    }
}
