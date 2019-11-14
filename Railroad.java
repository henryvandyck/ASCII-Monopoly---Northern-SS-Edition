import java.util.ArrayList;
public class Railroad extends Buyable
{
    int[] railList = new int[] {5,15,25,35};
    
    /**
     * Constructor for a Railroad object.
     * @param theName the name of this Railroad
     * @param theCost its cost
     * @param thePunishment the value of its punishment
     * @param loc its location
     * @param sprite a String representation of the appearance of this Railroad
     */
    public Railroad(String theName,int theCost,int thePunishment,int loc, String[] sprite)
    {
        super(theName,theCost,25,loc, sprite); //25 is initial punishment - with 1 railroad. Necessary?
    }

    public void punish()
    {
        //Pre-condition: the space the player has landed on is owned by someone else. This is important.
        int railsOwned = 0;
        ArrayList<Integer> reference = getPList().get(getOwner()).getOwnedBuyables();
        for (int i = 0; i < reference.size(); i++)
        {
            for (int j = 0; j < railList.length; j++)
            {
                if (reference.get(i) == railList[j] && !((Buyable)getBoard()[reference.get(i)]).getMortgage())
                { //they own the railroad, and it's not mortgaged.
                    railsOwned++;
                }
            }
            //railList is a list of location indexes of railroads, cycle through it.
            //If the player that owns this railroad owns more, bump up the railsOwned tally appropriately
        }
        //Afterwards, do some calculations for how much the current player owes
        //place it in the balance of another player
        int payment = (int)Math.round((Math.pow(2,railsOwned-1))) * 25; //check this works
        /* 1 Railroad - $25    2RR - $50   3RR - $100   4RR - $200 */
        
        getPList().get(getTurn()).transfer( payment , getPList().get(getOwner()) , true);
    }
}
