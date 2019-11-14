public class Property extends Buyable
{
    private int houseCost;
    private int numHouse; //1-4: houses. 5: hotel
    public boolean monopolyComplete = false; //By default
    private int[] monopolyMembers;
    private int[] rents;
    private String monopolyName;
    // 0: Nothing 1: Monopoly  2: One house 3: Two houses 4: Three houses  5: Four Houses  6: Hotel
    
    /**
     * Constructor for a Property object.
     * @param theName the name of this Property
     * @param theCost its cost
     * @param thePunishment the value of its punishment
     * @param hCost the cost of a house on this Property
     * @param monoMems an int array of indices of Board.grid which are members of this Property's monopoly
     * @param propRents an int array of this Property's rents at varying levels of development
     * @param loc this Property's location
     * @param sprite a String representation of the appearance of this Utility
     */
    public Property(String theName, int theCost, int thePunishment, int hCost, int[] monoMems,
                    int[] propRents, int loc, String[] sprite)
    {
        super(theName,theCost,thePunishment,loc, sprite);
        houseCost = hCost;
        monopolyMembers = monoMems;
        rents = propRents;
        numHouse = 0;
        monopolyComplete = false;
        if (loc==1||loc==3){monopolyName = "Basement";}
        if (loc==6||loc==8||loc==9){monopolyName = "Math Department";}
        if (loc==11||loc==13||loc==14){monopolyName = "Arts Department";}
        if (loc==16||loc==18||loc==19){monopolyName = "History Department";}
        if (loc==21||loc==23||loc==24){monopolyName = "Athletics";}
        if (loc==26||loc==27||loc==29){monopolyName = "Science Department";}
        if (loc==31||loc==32||loc==34){monopolyName = "Student Resources";}
        if (loc==37||loc==39){monopolyName = "Prestige";}
    }
    
    /**
     * Runs and returns the superclass' mortgage method, unless the property has at least one house,
     * in which case it returns false.
     * @param pNum the number of the active Player
     */
    @Override
    public boolean mortgage(int pNum)
    {
        if (numHouse > 0) 
        {
            return false;
        }
        else
        {
            return(super.mortgage(pNum));
        }
    }

    /**
     * Punishes the active Player who has landed on this Property.
     */
    public void punish()
    {
        if (monopolyComplete)
        {
            int index = 1+numHouse;
            getPList().get(getTurn()).transfer(rents[index],getPList().get(getOwner()),true);
            //If they don't have enough funds for the transfer, they'll repay it in Board.repayDebt();
            //if (!getPList().get(getTurn()).transfer(rents[index],getPList().get(getOwner()),true))
            // {
                // mortgage(true,true,getTurn());
            // }
        }
        else
        {
            getPList().get(getTurn()).transfer(rents[0],getPList().get(getOwner()),true);
            //transfer x amount of money from current player to owner
        }
    }
    
    /**
     * Sells this Property to another player.
     */
    @Override
    public void sell(int playerNum) //Tasked with seeing if a monopoly was completed this turn
    {
        super.sell(playerNum);
        boolean monoComplete = true;
        for (int i = 0; i < monopolyMembers.length; i++)
        {
            if (getBoard()[monopolyMembers[i]] instanceof Property)
            {
                Property p = (Property)getBoard()[monopolyMembers[i]];
                if (p.getOwner() != playerNum) //they don't own this part of the monopoly yet
                {
                    monoComplete = false;
                }
            }
        }
        //If monoComplete is still true at this point, they have all the spaces in the monopoly.
        if (monoComplete)
        {
            for (int i = 0; i < monopolyMembers.length; i++)
            {
                if (getBoard()[monopolyMembers[i]] instanceof Property)
                {
                    ((Property)getBoard()[monopolyMembers[i]]).monopolyComplete = true;
                }
            }
            monopolyComplete = true; //may be redundant
            String in = "a";
            while (in.length() != 0)
            {
                UIPrintBoard("You've completed the "+monopolyName+" monopoly!",
                             "Rents here have doubled, and you can now build houses.","Press enter to continue.");
                in = sc.nextLine();
            }
        }
    }
    
    /**
     * Builds a house on this Property, or builds a hotel if there are already four houses.
     */
    public void buildHouse()
    {
        if (getPList().get(getTurn()).getBalance() > houseCost && numHouse < 5 &&
            ((numHouse < 4 && Board.freeHouses > 0) || (numHouse == 4 && Board.freeHotels > 0)))
        {
            if (numHouse == 4) // They're about to build a hotel
            {
                getPList().get(getTurn()).hotelsBuilt++;
                getPList().get(getTurn()).housesBuilt -= 4;
                if(getSprite().length == 8) // Vertical square
                {
                    char[] arr = getSprite()[6].toCharArray();
                    arr[4] = ' ';
                    arr[5] = '[';
                    arr[6] = 'H';
                    arr[7] = ']';
                    getSprite()[6] = new String(arr);
                }
                else // Horizontal
                {
                    char[] arr = getSprite()[2].toCharArray();
                    arr[11] = '[';
                    arr[12] = 'H';
                    arr[13] = ']';
                    arr[14] = ' ';
                    getSprite()[2] = new String(arr);
                }
            }
            else //numHouse < 4
            {
                getPList().get(getTurn()).housesBuilt++;
                String numHouseStr = Integer.toString(numHouse+1);
                if(getSprite().length == 8) // Vertical square
                {
                    char[] arr = getSprite()[6].toCharArray();
                    arr[4] = '[';
                    arr[5] = numHouseStr.charAt(0);
                    arr[6] = 'h';
                    arr[7] = ']';
                    getSprite()[6] = new String(arr);
                }
                else // Horizontal
                {
                    char[] arr = getSprite()[2].toCharArray();
                    arr[11] = '[';
                    arr[12] = numHouseStr.charAt(0);
                    arr[13] = 'h';
                    arr[14] = ']';
                    getSprite()[2] = new String(arr);
                }
            }
            numHouse++;
        }
    }
    
    /**
     * Destroyes a house or hotel on this Property. Builds four houses if a house was destroyed.
     */
    public void destroyHouse()
    {
        if (numHouse > 0)
        {
            if (numHouse == 5) //They're about to destroy a hotel
            {
                getPList().get(getTurn()).hotelsBuilt--;
                getPList().get(getTurn()).housesBuilt += 4;
                if(getSprite().length == 8) // Vertical square
                {
                    char[] arr = getSprite()[6].toCharArray();
                    arr[4] = '[';
                    arr[5] = '4';
                    arr[6] = 'h';
                    arr[7] = ']';
                    getSprite()[6] = new String(arr);
                }
                else // Horizontal
                {
                    char[] arr = getSprite()[2].toCharArray();
                    arr[11] = '[';
                    arr[12] = '4';
                    arr[13] = 'h';
                    arr[14] = ']';
                    getSprite()[2] = new String(arr);
                }
            }
            else //numHouse < 4
            {
                getPList().get(getTurn()).housesBuilt--;
                if(getSprite().length == 8 && numHouse > 0) // Vertical square
                {
                    char[] arr = getSprite()[6].toCharArray();
                    arr[4] = '[';
                    arr[5] = (char)(numHouse - 1);
                    arr[6] = 'h';
                    arr[7] = ']';
                    getSprite()[6] = new String(arr);
                }
                else if(numHouse > 0)// Horizontal
                {
                    char[] arr = getSprite()[2].toCharArray();
                    arr[11] = '[';
                    arr[12] = (char)(numHouse - 1);
                    arr[13] = 'h';
                    arr[14] = ']';
                    getSprite()[2] = new String(arr);
                }
                else if(getSprite().length == 8) // Vertical and no houses
                {
                    char[] arr = getSprite()[6].toCharArray();
                    arr[4] = ' ';
                    arr[5] = ' ';
                    arr[6] = ' ';
                    arr[7] = ' ';
                    getSprite()[6] = new String(arr);
                }
                else // Horizontal and no houses
                {
                    char[] arr = getSprite()[2].toCharArray();
                    arr[11] = ' ';
                    arr[12] = ' ';
                    arr[13] = ' ';
                    arr[14] = ' ';
                    getSprite()[2] = new String(arr);
                }
            }
            numHouse--;
        }
    }
    
    /*
     * Getter methods for this Property's instace variables.
     */
    public int getHouseCount()
    {
        return numHouse;
    }
    public boolean monoComplete()
    {
        return monopolyComplete;
    }
    public int getHCost()
    {
        return houseCost;
    }
    public int[] getMonoMems()
    {
        return monopolyMembers;
    }
    public String getMonopolyName()
    {
        return monopolyName;
    }
}
