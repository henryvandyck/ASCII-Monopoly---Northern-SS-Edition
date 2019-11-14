import java.util.ArrayList;
public class Player
{
    //How many of these should be private?
    String playerName;
    char symbol;
    private int playerID; //0,1,2,3
    private int balance = 1500; //Amount of $$
    private ArrayList<Integer> ownedBuyables = new ArrayList<Integer>(); //ints indicating
    private boolean[] ownedMonopolies; //which do they own?
    int housesBuilt = 0; 
    int hotelsBuilt = 0;
    private boolean imprisoned = false; //are they in jail? At the start, no.
    private int doublesCounter = 0; //number of subsequent doubles. If they get 3 in a row, they go to jail
    private boolean out = false; //Out of the game
    private int getOutOfJailFreeCards = 0; //How many get out of jail free cards the player is holding
    
    private int location = 0; //location around the board. 40 spots, 0-39. 0 is Go, 39 is "Boardwalk". Start at Go.

    
    private static int nextID = 0;
    private static ArrayList<Player> playerList = new ArrayList<Player>();
    
    /**
     * Constructor for a Player object.
     * @param name the player's name
     * @param symbol a char representation of the player's icon to appear in their token
     */
    public Player(String name,char theSymbol)
    {
        playerID = nextID;
        nextID++;
        symbol = theSymbol;
        playerName = name;
        ownedMonopolies = new boolean[]{false,false,false,false,false,false,false,false}; //some setup
    }
    
    /**
     * Moves the player to a new location on the board by changing the value of location and editing Board.grid
     * @param destination the index of Board.grid that the player is moving to
     */
    public void move(int destination)
    {
        if(destination == 10) // detention
        {
            for(int row = 0; row < Board.grid[location].getSprite().length; row++) // delete token from old square
            {
                if(Board.grid[location].getSprite()[row].indexOf("<"+Character.toString(symbol)+">") >= 0)
                {
                    Board.grid[location].getSprite()[row] = Board.grid[location].getSprite()[row].replace("<"+Character.toString(symbol)+">","   ");
                    break;
                }
            }
            location = destination % 40;
            for(int row = 0; !imprisoned && row < Board.grid[location].getSprite().length; row++) // add token to new square
            {
                if (Board.grid[location].getSprite()[row].indexOf("    ") >= 0 && Board.grid[location].getSprite()[row].indexOf("    ") < 5)
                {
                    Board.grid[location].getSprite()[row] = Board.grid[location].getSprite()[row].substring(0,Board.grid[location].getSprite()[row].indexOf("    ")) + 
                        " <" + Character.toString(symbol) + ">" + Board.grid[location].getSprite()[row].substring(Board.grid[location].getSprite()[row].indexOf("    ") + 4,
                        Board.grid[location].getSprite()[row].length());
                    
                    break;
                } //this needs something that refers to whether the player is imprisoned or not
            }
            for(int row = 0; imprisoned && row < Board.grid[location].getSprite().length; row++) // add token to new square
            {
                if (Board.grid[location].getSprite()[row].substring(6).indexOf("    ") >= 0)
                {
                    Board.grid[location].getSprite()[row] = Board.grid[location].getSprite()[row].substring(0,6+Board.grid[location].getSprite()[row].substring(6).indexOf("    ")) + 
                        " <" + Character.toString(symbol) + ">" + Board.grid[location].getSprite()[row].substring(6).substring(Board.grid[location].getSprite()[row].substring(6).indexOf("    ") + 4,
                        Board.grid[location].getSprite()[row].substring(6).length());
                    
                    break;
                } //this needs something that refers to whether the player is imprisoned or not
            }
        }
        else
        {
            for(int row = 0; row < Board.grid[location].getSprite().length; row++) // delete token from old square
            {
                Board.grid[location].getSprite()[row] = Board.grid[location].getSprite()[row].replace("<"+Character.toString(symbol)+">","   ");
            }
            location = destination % 40;
            if(destination < 10 || destination > 20 && destination < 30) // top or bottom side of the board
            {
                for(int row = 0; row < Board.grid[location].getSprite().length; row++) // add token to new square
                {
                    if(Board.grid[location].getSprite()[row].indexOf("   ") >= 0)
                    {
                        Board.grid[location].getSprite()[row] = Board.grid[location].getSprite()[row].substring(0,Board.grid[location].getSprite()[row].indexOf("   ")) + 
                            "<" + Character.toString(symbol) + ">" + Board.grid[location].getSprite()[row].substring(Board.grid[location].getSprite()[row].indexOf("   ")+3,
                            Board.grid[location].getSprite()[row].length());
                        break;
                    }
                }
            }
            else
            {
                for(int again = 0; again < Board.grid[location].getSprite().length; again++) // add token to new square
                {
                    if(Board.grid[location].getSprite()[again].indexOf("   ") >= 0)
                    {
                        Board.grid[location].getSprite()[again] = Board.grid[location].getSprite()[again].substring(0,Board.grid[location].getSprite()[again].indexOf("   ")) + 
                            "<" + Character.toString(symbol) + ">" + Board.grid[location].getSprite()[again].substring(Board.grid[location].getSprite()[again].indexOf("   ")+3,
                            Board.grid[location].getSprite()[again].length());
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * Returns the ArrayList of Buyables that this Player owns.
     */
    public ArrayList<Integer> getOwnedBuyables()
    {
        return ownedBuyables; 
    }
    
    /**
     * Adds a Buyable to this Player,
     * @param buyable the Buyable to be added
     */
    public void addBuyable(int buyable)
    {
        int i = 0;
        for (i = 0; i < ownedBuyables.size() && ownedBuyables.get(i) < buyable; i++){} //Puts the new buyable in a position so it's sorted
        ownedBuyables.add(i,buyable);
    }
    
    /**
     * Sets the value of doublesCounter to a given amount,
     * @param amount the amount doublesCounter is to be set to
     */
    public void setDoubles(int amount)
    {
        doublesCounter = amount;
    }
    
    /**
     * Deposits a given amount to this Player's balance.
     * @param amount the amount to be deposited
     */
    public void deposit(int amount)
    {
        balance += amount;
    }
    
    /**
     * Withdraws a given amount from this Player's balance. Returns true if the transaction goes through,
     * and false otherwise.
     * @param amount the amount to be withdrawn
     * @param forced whether this transaction should occur regardless of this Player's current balance
     */
    public boolean withdraw(int amount, boolean forced) //false if it doesn't work
    {
        if (balance >= amount || forced)
        {
            balance -= amount;
            return true;
        }
        else //if they can't pay, but they need to.
        {
            //What if this is a tax and they need to now mortgage something to pay it?
            return false;
        }
    }
    
    /**
     * Transfers a given amount from this Player to another Player. Returns true if the transaction goes through,
     * and false otherwise.
     * @param amount the amount to be withdrawn
     * @param other  the Player to receive the transfer
     * @param forced whether this transaction should occur regardless of this Player's current balance
     */
    public boolean transfer(int amount, Player other, boolean forced)
    {
        if(balance >= amount)
        {
            withdraw(amount,forced);
            other.deposit(amount);
            return true;
        }
        else if (forced)
        {
            withdraw(amount,forced);
            other.deposit(amount);
            return false;
        }
        return false;
    }
    
    /**
     * Sets this player to be eliminated.
     */
    public void eliminate()
    {
        out = true;
    }
    
    /**
     * Sets imprisoned to the given boolean value.
     * @param on the value imprisoned is to be set to
     */
    public void toggleImprison(boolean on)
    {
        if (on) {imprisoned = true;}
        else {imprisoned = false;}
    }
    
    /**
     * Adds a get out of jail free card to this Player.
     */
    public void addFreeCard()
    {getOutOfJailFreeCards++;}
    
    /**
     * Removes a get out of jail free card from this Player.
     */
    public void useFreeCard()
    {getOutOfJailFreeCards--;}
    
    /*
     * Getter methods for this Player's instance variables.
     */
    public int getBalance()                     
    {return balance;}
    public String getName()
    {return playerName;}
    public int getID()
    {return playerID;}
    public char getSymbol()
    {return symbol;}
    public static ArrayList<Player> getPList()  
    {return playerList;}
    public ArrayList<Integer> getOwned()  
    {return ownedBuyables;}
    public boolean checkOut()                   
    {return out;}
    public int getLocation()                    
    {return location;}
    public int getHousesBuilt()
    {return housesBuilt;}
    public int getHotelsBuilt()
    {return hotelsBuilt;}
    public int getDoubles()
    {return doublesCounter;}
    public boolean isImprisoned()
    {return imprisoned;}
    public int getFreeCards()
    {return getOutOfJailFreeCards;}
}
