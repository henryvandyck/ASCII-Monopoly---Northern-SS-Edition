import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
 
public class CommunityChance extends Square
{
    static ArrayList<String> cardNames = new ArrayList<String>(); //the names of the cards
    static ArrayList<String> cardLores = new ArrayList<String>(); //the text on the cards
    static ArrayList<Integer> cardTolls = new ArrayList<Integer>(); //explained below
    static boolean setUpVar = false;
    static ArrayList<Integer> referenceChance = new ArrayList<Integer>(); //allows for shuffling and other "deck" mechanics.
    static ArrayList<Integer> referenceChest = new ArrayList<Integer>();
 
    String cardName;
    int i;
    /**
     * The constructor for the CommunityChance class. This class makes up all Chance and Chest squares on the board.
     * It was merged into one class because they function essentially identically. 
     * The 3 parameters are fed into the Square constructor.
     * @param theName the name of the Square.
     * @param loc the location of the square on the UI Board.
     * @param sprite the collection of Strings that visually represents the Square on the UI board.
     */
    public CommunityChance(String theName, int loc, String[] sprite) throws IOException
    {
        super(theName, loc, sprite);
        cardName = theName;
         
        if (!setUpVar) //this only needs to happen once per game
        {
            setup();
            setUpVar = true;
        }
    }
 
    /**
     * Activates whenever a commChance square is landed on.
     * Draws a card for the player, and does the card's action.
     */
    public void doItsThing()
    {
        Scanner sc = new Scanner(System.in);
        
        if (getName().equals("Chance")) {i = referenceChance.get(0);}
        else {i = referenceChest.get(0);} 
        int currentToll = cardTolls.get(i);
        String line3 = "";
         
        if(currentToll > 0) 
        {
            Player.getPList().get(getTurn()).deposit(currentToll);
            line3 = "Receive $"+currentToll+".";
        }
        else if (currentToll < 0)
        {
            Player.getPList().get(getTurn()).withdraw((-1*currentToll), true);
            line3 = "You pay $"+(-1*currentToll)+".";
        } 
        
        String in = "a";
        while (in.length() != 0)
        {
            UIPrintBoard(cardNames.get(i),cardLores.get(i),line3);
            in = sc.nextLine();
        }
        
        //If currentToll = 0, the card doesn't directly relate to monetary gain/loss, but instead something else specific.
        //This series of if statements finds the correct index of the card, and does what it's supposed to do.
        //A few examples are labelled.
        if(i == 1 || i == 16) 
        {
            Player.getPList().get(getTurn()).move(0);
            Board.getGrid()[0].doItsThing(); //Advance to Go.
        }
        else if(i == 2)
        {
            if(Player.getPList().get(getTurn()).getLocation() > 23)
            { //If past location it will wrap around so he must hit go.
                Board.getGrid()[0].doItsThing();
            }
            Player.getPList().get(getTurn()).move(24);
            Board.getGrid()[24].doItsThing();
        }
        else if(i == 3)
        {
            if(Player.getPList().get(getTurn()).getLocation() > 10)
            { //If past location it will wrap around so he must hit go.
                Board.getGrid()[0].doItsThing();
            }
            Player.getPList().get(getTurn()).move(11);
            Board.getGrid()[11].doItsThing();
        }
        else if(i == 4) //Advance to the nearest utility.
        {
            if(Player.getPList().get(getTurn()).getLocation() < 12)
            {
                Player.getPList().get(getTurn()).move(12);
            }
            else if(Player.getPList().get(getTurn()).getLocation() > 26)
            {
                Board.getGrid()[0].doItsThing();
                Player.getPList().get(getTurn()).move(12);
            }
            else{
                Player.getPList().get(getTurn()).move(27);
            }
            Board.getGrid()[getPList().get(getTurn()).getLocation()].doItsThing();
        }
        else if(i == 5)
        {
            if(Player.getPList().get(getTurn()).getLocation() < 5)
            {
                Player.getPList().get(getTurn()).move(5);
            }
            else if(Player.getPList().get(getTurn()).getLocation() > 34)
            {
                getBoard()[0].doItsThing();
                Player.getPList().get(getTurn()).move(5);
            }
            else if(Player.getPList().get(getTurn()).getLocation() < 15)
            {
                getPList().get(getTurn()).move(15);
            }
            else if(Player.getPList().get(getTurn()).getLocation() < 25)
            {
                getPList().get(getTurn()).move(25);
            }
            else
            {
                getPList().get(getTurn()).move(35);
            }
            Board.getGrid()[getPList().get(getTurn()).getLocation()].doItsThing();
        }
        else if(i == 8) //Move back 3 spaces
        {
            Player.getPList().get(getTurn()).move(Player.getPList().get(getTurn()).getLocation() - 3);
            getBoard()[getPList().get(getTurn()).getLocation()].doItsThing();
        }
        else if(i == 9 || i == 21) //Go to jail
        {
            Player.getPList().get(getTurn()).toggleImprison(true);
            Player.getPList().get(getTurn()).move(10);
        }
        else if(i == 10)
        {
            Player.getPList().get(getTurn()).withdraw(Player.getPList().get(getTurn()).getHousesBuilt() *25, true);
            Player.getPList().get(getTurn()).withdraw(Player.getPList().get(getTurn()).getHotelsBuilt() *100, true);
        }
        else if(i == 12)
        {
            if(Player.getPList().get(getTurn()).getLocation() > 5)
            { //If past location it will wrap around so he must hit go.
                Board.getGrid()[0].doItsThing();
            }
            Player.getPList().get(getTurn()).move(5);
            Board.getGrid()[5].doItsThing();
        }
        else if(i == 13)
        {
            if(Player.getPList().get(getTurn()).getLocation() > 38)
            { //If past location it will wrap around so he must hit go.
                Board.getGrid()[0].doItsThing();
            }
            Player.getPList().get(getTurn()).move(39);
            Board.getGrid()[39].doItsThing();
        }
        else if(i == 14)
        {
            for(int j = 0; j < Board.getPlayerAmount(); j ++)
            {
                if(j != getTurn())
                {
                    Player.getPList().get(getTurn()).transfer(50,Player.getPList().get(j), true);
                }
            }
        }
        else if(i == 22)
        {
            for(int j = 0; i < Board.getPlayerAmount(); j ++)
            {
                if(j != getTurn())
                {
                    Player.getPList().get(j).transfer(50, Player.getPList().get(getTurn()), true);
                }
            }
        }
        else if(i == 28)
        {
            Player.getPList().get(getTurn()).withdraw(Player.getPList().get(getTurn()).getHousesBuilt() * 40, true);
            Player.getPList().get(getTurn()).withdraw(Player.getPList().get(getTurn()).getHotelsBuilt() * 115, true);
        }
        else if(i == 7 || i == 20) //Get a Get Out of Detention Free Card
        {
            Player.getPList().get(getTurn()).addFreeCard();
        }
        
        //"Take the top card and put it at the bottom of the deck"
        if (getName().equals("Chance"))
        {
            referenceChance.add(referenceChance.get(0));
            referenceChance.remove(0);
        }
        else if (getName().equals("Chest"))
        {
            referenceChest.add(referenceChest.get(0));
            referenceChest.remove(0);
        }
    }
 
    /**
     * This setup method uses File I/O to read a file full of the cardNames, cardLores, and cardTolls, and fills out the previously
     * instantiated ArrayLists with them.
     * File I/O was used because many of the cardLores are quite long, and there are 32 cards in total, so it would have been
     * impractical to hard code it.
     */
    private void setup() throws IOException
    {
        File cardsFile = new File("commChanceCards.txt");
        Scanner sc = new Scanner(cardsFile);
        for (int i = 0; i < (16*2); i++) //Loads up the arrays with information. Chance cards are 0-15. Chest 16-32.
        {
            cardNames.add(sc.nextLine());
            cardLores.add(sc.nextLine());
            cardTolls.add(Integer.parseInt(sc.nextLine()));
        }
        int j = 0;
        for (j = 0; j < 16; j++)
        {referenceChance.add(j);}
        for (j = 16; j < 32; j++)
        {referenceChest.add(j);}
        shuffle(referenceChance); //shuffles the order of the cards in the "deck"
        shuffle(referenceChest);
    }
    
    /**
     * The Fisher–Yates shuffle technique.
     * Used for shuffling the reference ArrayLists.
     * @param arr the array to be shuffled.
     */ 
    static void shuffle(ArrayList<Integer> arr)
    {
        Random rando = new Random();
        for (int i = arr.size() - 1; i > 0; i--)
        {
            int index = rando.nextInt(i + 1);
            // Simple swap
            int a = arr.get(index);
            arr.set(index,arr.get(i));
            arr.set(i,a);
        }
    }
}