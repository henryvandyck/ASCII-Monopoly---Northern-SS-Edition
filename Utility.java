import java.util.ArrayList;
import java.util.Random;
public class Utility extends Buyable
{
    
    /**
     * Constructor for a Utility object.
     * @param theName the name of this Utility
     * @param theCost its cost
     * @param thePunishment the value of its punishment
     * @param loc its location
     * @param sprite a String representation of the appearance of this Utility
     */
    public Utility(String theName,int theCost, int thePunishment,int loc, String[] sprite)
    {
        super(theName,theCost,thePunishment,loc, sprite); //thePunishment is 0 in Square because it's not just a set number
    }
    
    /**
     * Punishes the active Player according to this Utilitiy's attributes.
     */
    public void punish()
    {
        //Pre-condition: the space the player has landed on is owned by someone else. This is important.
        int utilitiesOwned = 0;
        ArrayList<Integer> reference = getPList().get(getOwner()).getOwnedBuyables();
        for (int i = 0; i < reference.size(); i++)
        {
            if ((reference.get(i) == 12 && !((Buyable)getBoard()[12]).getMortgage()) || 
                (reference.get(i) == 28 && !((Buyable)getBoard()[28]).getMortgage()))
            {
                utilitiesOwned++;
            }
        }
        
        Random rando = new Random();
        int payment = 0;
        if (utilitiesOwned == 1)
        {payment = 4*(rando.nextInt(6)+1);} //4x "dice roll"
        else if (utilitiesOwned == 2)
        {payment = 10*(rando.nextInt(6)+1);} //10x "dice roll"
        
        getPList().get(getTurn()).transfer( payment , getPList().get(getOwner()) , true);
    }
}
