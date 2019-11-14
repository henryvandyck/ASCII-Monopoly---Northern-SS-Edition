import java.util.Scanner;
public class Tax extends Square
{
    private int taxToll;
    
    /**
     * Constructor for a Tax object.
     * @param theName the name of this Tax
     * @param toll    the value of this Tax's toll
     * @param loc     the location of this Tax
     * @param sprite  a String representation of the appearance of this Tax
     */
    public Tax(String theName, int toll, int loc, String[] sprite)
    {
        super(theName,loc, sprite);
        taxToll = toll;
    }
    
    public void doItsThing()
    {
        /* 1. Player A lands on the space.
         *      Player A must pay tax amount to bank.
         *      Withdraw using getToll() the desired amount */
        if (getOptions())
        {
            if (getPList().get(getTurn()).withdraw(taxToll,true))
            {
                Parking.parkingTotal+=taxToll; //change up
            }
        }
        else
        {
            getPList().get(getTurn()).withdraw(taxToll,true);
            String in = "a";
            while (in.length() != 0)
            {
                UIPrintBoard("You were fined $"+taxToll+".","","Press enter to continue.");
                in = sc.nextLine();
            }
        }
    }
    
    public int getToll()
    {
        return taxToll;
    }
}
