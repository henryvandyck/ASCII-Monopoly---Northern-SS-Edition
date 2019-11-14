import java.util.ArrayList;
import java.util.Scanner;
public abstract class Buyable extends Square
{
    // instance variables - replace the example below with your own
    private int cost;
    private int punishment;
    public int owner = -1;
    private int mortgageValue;
    private int buyBackValue; //Mortgage value + 10% interest
    private boolean isMortgaged = false;
    
    Scanner sc = new Scanner(System.in);
    
    /**
     * Buyable's constructor.
     * Takes in more information than Square - each Buyable has its own cost to buy it and starting rent.
     * Other variables, like mortgageValue, are directly tied to the cost.
     * @param theName the name of the Square.
     * @param theCost the cost of the Buyable.
     * @param thePunishment the rent players must face when landing on this space.
     * @param loc the location of the Square on the Board.
     * @param sprite the collection of Strings that visually represents the Square on the UI board.
     */
    public Buyable(String theName, int theCost, int thePunishment, int loc, String[] sprite)
    {
        super(theName,loc, sprite);
        cost = theCost;
        mortgageValue = cost/2;
        buyBackValue = mortgageValue+(mortgageValue/10);
        punishment = thePunishment;
    }
    
    /**
     * When you land on a Buyable, one of 3 things may happen.
     * If it is unowned, you may buy it or put it to auction.
     * If it's owned by you, nothing happens.
     * If it's owned by someone else, you are punish()ed - you pay them rent. The method of this is specific to the subclasses.
     */
    public void doItsThing() 
    {
        int currentPlayer = getTurn();
        String in = "a";
        if (currentPlayer == owner) //Current player owns this
        {
            while (in.length() != 0)
            {
                UIPrintBoard("You already own this property.","","Press Enter.");
                in = sc.nextLine();
            }
        }
        else if (owner != -1 && owner != currentPlayer) //Someone owns this, but not the player whose turn it is
        {
            if (!isMortgaged) 
            {
                while (in.length() != 0)
                {
                    UIPrintBoard("Oh no! "+ getPList().get(owner).getName() + " already owns this property.","You pay them rent.","");
                    in = sc.nextLine();
                }
                punish();
            }
            else
            {
                while (in.length() != 0)
                {
                    UIPrintBoard("This property is mortgaged, so you don't have to pay rent.","","Press enter.");
                    in = sc.nextLine();
                }
            }
        }
        else if (owner == -1) //Nobody owns this
        {
            boolean sell = false;
            for (int i = 0; i < getPList().size(); i++)
            {
                if (getPList().get(i).getBalance() > 50)
                {
                    sell = true; //Nothing will happen if NOBODY can afford the property.
                }
            }
            
            if (sell)
            {
                sell(currentPlayer);
            }
            else
            {
                UIPrintBoard("Nobody can afford this property. Maybe later.","","Press enter to continue.");
            }
        }
    }

    /**
     * Deposits some money in the mortgaging player (pNum)'s account, and then sets the property to mortgaged.
     * Also changes some information about the Sprite of the square for a visual representation of it being mortgaged in the UI.
     * @param pNum an int that describes the ID of the player mortgaging.
     */
    public boolean mortgage(int pNum) //Overriden in Property
    {
        getPList().get(pNum).deposit(mortgageValue);
        isMortgaged = true;
        if(getSprite().length == 8) // vertical square
        {
            char[] arr = getSprite()[7].toCharArray();
            arr[2] = ' ';  arr[3] = '[';   arr[4] = 'M';   arr[5] = ']';  arr[6] = ' ';  
            getSprite()[7] = new String(arr);
        }
        else // horizontal
        {
            char[] arr = getSprite()[2].toCharArray();
            arr[6] = ' ';   arr[7] = '[';   arr[8] = 'M';   arr[9] = ']';
            getSprite()[2] = new String(arr);
        }
        return true;
    }
    
    /**
     * Exact reverse of the above mortgage method.
     * Unmortgages the property, withdraws some money from the player's account, and changes some UI info.
     * @param pNum an int that describes the ID of the player unmortgaging properties.
     */
    public void unmortgage(int pNum)
    {
        getPList().get(pNum).withdraw(buyBackValue,false);
        isMortgaged = false;
        if(getSprite().length == 8) // vertical square
        {
            char[] arr = getSprite()[7].toCharArray();
            arr[1] = arr[0];
            arr[2] = ' ';
            arr[3] = '[';
            arr[4] = getPList().get(pNum).getSymbol();
            arr[5] = ']';
            arr[6] = ' ';  
            getSprite()[7] = new String(arr);
        }
        else // horizontal
        {
            char[] arr = getSprite()[2].toCharArray();
            arr[8] = getPList().get(pNum).getSymbol();
            getSprite()[2] = new String(arr);
        }
    }
    /**
     * An OVERLOADED version of unmortgage, for use when a mortgaged property has gone to the bank, its status needs to be flipped.
     * The bank has no balance, so no withdraw method is necessary.
     */
    public void unmortgage()
    {
        isMortgaged = false;
        if(getSprite().length == 8) // vertical square
        {
            char[] arr = getSprite()[6].toCharArray();
            arr[1] = ' ';
            arr[2] = ' ';
            arr[3] = ' ';
            getSprite()[6] = new String(arr);
        }
        else // horizontal
        {
            char[] arr = getSprite()[2].toCharArray();
            arr[2] = arr[1];
            arr[3] = arr[0];
            arr[4] = arr[1];
            arr[5] = arr[1];
            getSprite()[2] = new String(arr);
        }
    }

    /**
     * Punishes players for landing on spaces owned by other players.
     * Implemented seperately in the 3 subclasses of Buyable
     */
    public abstract void punish();

    /** 
     * Responsible for selling the property to a player, if they so desire. If they don't, it'll go to auction.
     * @param playerNum refers to the player who the buyable is being sold to
     * @param cost refers to the amount of $$ the buying player must have
     * @param buyNum refers to the location of the buyable on the 'board' 
     */
    public void sell(int playerNum) 
    {                                                              
        boolean cont = false;
        String in = "a";
        while (!cont)
        {
            if (getPList().get(playerNum).getBalance() >= cost)
            {
                UIPrintBoard("You're on "+getName()+".",
                             "Would you like to buy it for $"+cost+", or put it to auction?",
                             "Enter (B)UY, or (A)UCTION.");
                in = sc.nextLine();
                if (in.toLowerCase().equals("b"))
                {
                    getPList().get(playerNum).addBuyable(getLocation());
                    getPList().get(playerNum).withdraw(cost,false);
                    owner = playerNum;
                    if(getSprite()[0].length() == 9) // vertical square
                    {
                        char[] arr = getSprite()[7].toCharArray();
                        arr[3] = '[';
                        arr[4] = getPList().get(playerNum).getSymbol();
                        arr[5] = ']';
                        arr[6] = ' ';
                        arr[7] = arr[0];
                        getSprite()[getSprite().length-1] = new String(arr);
                    }
                    else // horizontal
                    {
                        char[] arr = getSprite()[2].toCharArray();
                        arr[7] = '[';
                        arr[8] = getPList().get(playerNum).getSymbol();
                        arr[9] = ']';
                        arr[10] = ' ';
                        arr[11] = arr[1];
                        getSprite()[getSprite().length-1] = new String(arr);
                    }
                    
                    while (in.length() != 0)
                    {
                        UIPrintBoard("You bought "+getName()+" for $"+cost+".","","Press enter to continue.");
                        in = sc.nextLine();
                    }
                    cont = true;
                }
                else if (in.toLowerCase().equals("a"))
                {
                    auction();
                    cont = true;
                }
            }
            else
            {
                boolean cont1 = false;
                while (!cont1)
                {
                    UIPrintBoard("Sorry, you can't afford this property. Do you want to mortgage something?",
                                  "If not, it will be auctioned.",
                                  "Enter (Y)ES or hit enter.");
                    in = sc.nextLine();
                    if (in.toLowerCase().equals("y"))
                    {
                        Board.mortgage(true,false,getTurn());
                        if (getPList().get(getTurn()).getBalance() >= cost)
                        {
                            cont1 = true; //They can now 
                        }
                    }
                    else if (in.length() == 0)
                    {
                        auction();
                        cont1 = true;
                        cont = true;
                    }
                }
            }
        }
    }

    /**
     * If a player lands on a property but chooses not to buy it, it will go to auction.
     * All players will have a chance at purchasing the property.
     */
    public void auction() 
    {
        boolean[] optedIn = new boolean[] {false,false,false,false}; //whether each player is bidding in the auction.
        int howManyOptedIn = 0;
        boolean autoSold = false;
        for (int i = 0; i < getPList().size(); i++)
        {
            if (!getPList().get(i).checkOut() && getPList().get(i).getBalance() > 10)
            { //if they're still in the game and have enough money to initially buy in
                optedIn[i] = true;
                howManyOptedIn++;
            }
        }
        
        if (howManyOptedIn == 1) //Only 1 person can buy the property
        {
            int i = 0;
            for (int j = 0; j < 4; j++)
            {
                if (optedIn[j]) {i = j;}
            }
            boolean cont = false;
            String input = "a";
            while (!cont)
            {
                UIPrintBoard(getPList().get(i).getName()+" is the only one who can afford "+getName()+".",
                         "Would you like to buy it for $"+getCost()+"?","Enter Y or N.");
                input = sc.nextLine();
                if (input.toLowerCase().equals("y"))
                {
                    while (input.length() != 0)
                    {
                        getPList().get(i).withdraw(getCost(),false); //Withdraw the amount they bid from their balance
                        getPList().get(i).addBuyable( getPList().get(i).getLocation());
                        if(getSprite()[0].length() == 9)
                        {
                            char[] arr = getSprite()[7].toCharArray();
                            arr[3] = '[';
                            arr[4] = getPList().get(i).getSymbol();
                            arr[5] = ']';
                            arr[6] = ' ';
                            arr[7] = arr[0];
                            getSprite()[getSprite().length-1] = new String(arr);
                        }
                        else
                        {
                            char[] arr = getSprite()[2].toCharArray();
                            arr[7] = '[';
                            arr[8] = getPList().get(i).getSymbol();
                            arr[9] = ']';
                            arr[10] = ' ';
                            arr[11] = arr[1];
                            getSprite()[getSprite().length-1] = new String(arr);
                        }
                        UIPrintBoard(getPList().get(i).getName()+" bought "+getName()+" for "+getCost()+".",
                         "","Press enter to continue.");
                        input = sc.nextLine();
                    }
                    cont = true;
                }
                else if (input.toLowerCase().equals("n")) //if they refuse, the property is not sold to anyone.
                {
                    cont = true;
                }
            }
            autoSold = true;
        }
        
        int count = getTurn();
        int pureCount = 0;
        String str1, str2, str3;
        String input = "";
        int auctionPrice = 0; //This updates. This is just the starting number.
        boolean cont0 = false;
        
        while (!cont0 && !autoSold) //more than 1 person is competing for the property
        {
            boolean cont = false;
            if (optedIn[count] && getPList().get(count).getBalance() <= auctionPrice + 10)
            {
                UIPrintBoard("",getPList().get(count).playerName + " cannot afford to join the auction.","");
                String in = sc.nextLine();
                optedIn[count] = false;
                howManyOptedIn--;
                cont = true;
            }
            if (optedIn[count]) //If this player is still in the auction
            {
                while (!cont)
                {
                    str1 = "PROPERTY: "+getName().toUpperCase();
                    str2 = getPList().get(count).playerName + ", would you like to bid?";
                    str3 = "Type YES or NO.";
                    UIPrintBoard(str1,str2,str3); //prints it in the UI
                    input = sc.nextLine().toLowerCase();
                    boolean cont2 = false;
                    if (input.equals("yes"))
                    {
                        while (!cont2)
                        {
                            UIPrintBoard("The current price is "+auctionPrice+". What would you like to bid?",
                                      "Type an integer divisible by 10. Minimum raise: $10","You can also CANCEL.");
                            input = sc.nextLine().toLowerCase();
                            if (Square.inputInt(input)
                                && Integer.parseInt(input) <= getPList().get(count).getBalance()
                                && Integer.parseInt(input) - 10 >= auctionPrice
                                && Integer.parseInt(input) % 10 == 0)
                            { //is a valid number, affordable to the player, at least a $10 raise, divisible by $10
                                auctionPrice = Integer.parseInt(input);
                                cont = true;
                                cont2 = true;
                            }
                            else if (input.equals("cancel"))
                            {
                                cont2 = true;
                            }
                        }
                    }
                    else if (input.equals("no"))
                    {
                        optedIn[count] = false; //player has withdrawn their chance to bid
                        howManyOptedIn--;
                        cont = true;
                    }
                }
            }
            if (count == getPList().size()-1) //Player turn cycle
            {count = 0;}
            else
            {count++;}
            pureCount++;
            input = "a";
            if (howManyOptedIn == 0 && pureCount < Board.getPlayerAmount()) //If nobody bids in round one, it isn't sold to anyone.
            {
                while (input.length() != 0)
                {
                    UIPrintBoard("Nobody bid, so the property was not sold.","","Press enter to continue.");
                    input = sc.nextLine();
                }
                cont0 = true;
                autoSold = true;
            }
            else if (pureCount > getPList().size() && howManyOptedIn == 1)
            {
                cont0 = true;
            }

            else if (howManyOptedIn == 1 && pureCount >= Board.getPlayerAmount())
            {
                cont0 = true;
            }
        } //The while loop stops when only 1 person is still opted in, so they won the auction (but at what cost)
        
        for (int i = 0; i < 4 && !autoSold; i++) //Finds the person who won and gives the property to them
        {
            if (optedIn[i]) //will only be true for one of the maximum four
            {
                getPList().get(i).withdraw(auctionPrice,true); //Withdraw the amount they bid from their balance
                getPList().get(i).addBuyable( getPList().get(getTurn()).getLocation() );
                boolean monoComplete = true;
                int[] monopolyMembers = new int[3]; 
                if (this instanceof Property)
                {
                    monopolyMembers = ((Property)this).getMonoMems();
                    for (int j = 0; j < monopolyMembers.length; j++)
                    {
                        if (getBoard()[monopolyMembers[j]] instanceof Property)
                        {
                            Property p = (Property)getBoard()[monopolyMembers[j]];
                            if (p.getOwner() != i) //they don't own this part of the monopoly yet
                            {
                                monoComplete = false;
                            }
                        }
                    }
                }
                owner = i;
                // Add the buyable to this player's set that corrosponds to the player whose turn it is' location
                input = "a";
                while (input.length() != 0)
                {
                    UIPrintBoard("",getPList().get(i).playerName+" won the auction, buying "+getName()
                               +" for $"+auctionPrice+".","Hit enter to continue.");
                    if(getSprite()[0].length() == 9)
                    {
                        char[] arr = getSprite()[7].toCharArray();
                        arr[3] = '[';
                        arr[4] = getPList().get(i).getSymbol();
                        arr[5] = ']';
                        arr[6] = ' ';
                        arr[7] = arr[0];
                        getSprite()[getSprite().length-1] = new String(arr);
                    }
                    else
                    {
                        char[] arr = getSprite()[2].toCharArray();
                        arr[7] = '[';
                        arr[8] = getPList().get(i).getSymbol();
                        arr[9] = ']';
                        arr[10] = ' ';
                        arr[11] = arr[1];
                        getSprite()[getSprite().length-1] = new String(arr);
                    }
                    input = sc.nextLine();
                }
                
                if (this instanceof Property)
                {
                    if (monoComplete)
                    {
                        for (int j = 0; j < monopolyMembers.length; j++)
                        {
                            if (getBoard()[monopolyMembers[i]] instanceof Property)
                            {
                                ((Property)getBoard()[monopolyMembers[j]]).monopolyComplete = true;
                            }
                        }
                        monoComplete = true; //may be redundant
                        String in = "a";
                        while (in.length() != 0)
                        {
                            UIPrintBoard("You've completed the "+((Property)this).getMonopolyName()+" monopoly!",
                                         "Rents here have doubled, and you can now build houses.","Press enter to continue.");
                            in = sc.nextLine();
                        }
                    }
                }
            }
        }
    }

    /**
     * "Getter" methods for Buyable's instance variables.
     */
    public int getOwner()
    {
        return owner;
    }
    public boolean getMortgage()
    {
        return isMortgaged;
    }
    public int getCost()
    {
        return cost;
    }
    public int getBuyBackVal()
    {
        return buyBackValue;
    }
}