import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
/* Everything is static! */
public class Board
{
    static Square[] grid; //The set of different Squares that make up the board
    static Square[] defaultGrid;
    private static int playerAmount;
    private static int whoseTurn; //Whose turn is it currently? Very important
    static String[] centreSprite = new String[]{
        "                                                                                         ",
        "      ,--.       ,---.             /-----------------------/                             ",
        "     /    '.    /     \\           /                       /                              ",
        "            \\  ;                 /                       /               _   _           ",
        "             \\-|                /    Community Chest    /              _| | | |          ",
        "            (o o)      (/      /                       /              | | |_| |          ",
        "            /'v'     ,-'      /                       /               | |\\__, |          ",
        "    ,------/ >< \\---'        /-----------------------/                | | __/ |          ",
        "   /)     ;  --  :                                                ___ | ||___/           ",
        "      ,---| ---- |--.                                            / _ \\|_|                ",
        "     ;    | ---- |   :                                     _ __ | (_) |                  ",
        "    (|  ,-| ---- |-. |)                                   | '_ \\ \\___/                   ",
        "       | /| ---- |\\ |                                 ___ | |_) |                        ",
        "       |/ | ---- | \\|                                / _ \\| .__/                         ",
        "       \\  : ---- ;  |                          _ __ | (_) | |                            ",
        "        \\  \\ -- /  /                          | '_ \\ \\___/|_|                            ",
        "        ;   \\  /  :                       _ __| | | |                    (\\              ",
        "       /   / \\/ \\  \\           _         | '__|_| |_|                     \\\\             ",
        "      /)           (\\         | |     ___| |                               ))            ",
        "                           _  | |__  / _ \\_|                              //             ",
        "                          | | | '_ \\|  __/                         .-.   //  .-.         ",
        "                          | |_| | | |\\___                         /   \\-((=-/   \\        ",
        "                          | __|_| |_|                             \\      \\\\     /        ",
        "                      _ __| |_                                     `( ____))_ )`         ",
        "                     | '__|\\__|                                    .-'   //  '-.         ",
        "                 ___ | |                                          /     ((      \\        ",
        "          _   _ / _ \\|_|                                         |               |       ",
        "         | \\ | | (_) |             /-----------------------/      \\             /        ",
        "         |  \\| |\\___/             /                       /        \\   __-__   /         ",
        "         | . ` |                 /                       /         _)  \\   /  (_         ",
        "         | |\\  |                /        Chance         /        (((---'   '---)))       ",
        "         |_| \\_|               /                       /                                 ",
        "                              /                       /                                  ",
        "                             /-----------------------/                                   ",
        "                                                                                         "};
    
    static int freeHouses = 32; //For housing shortages
    static int freeHotels = 12;
    
    static Player p1 = null; //Will be instantiated
    static Player p2 = null;
    static Player p3 = null;
    static Player p4 = null;
    
    static boolean option = false; //Whether free parking collects the player money or not
    
    static Scanner sc = new Scanner(System.in);
    static int elimination = -1; //Used to tell if an elimination has just happened, and if so who to
    static ArrayList<Integer> recirculation = new ArrayList<Integer>(); //For when a player is eliminated by the bank. Collects properties
                                                                        //to be auctioned off among active players.
    final static boolean DEBUG = false;

    /** 
     * This is the method the player calls in order to start the game.
     * It contains the menu, guide, and settings, and it also asks players to pick their names and symbols.
     */
    public static void start() throws IOException
    {
        boolean cont = false;
        int num = 2;
        defaultGrid = Square.grid();
        
        UI.intro();
        
        while(true) // returns to the title screen when the end of this loop is reached
        {
            ArrayList<Player> list = Player.getPList();
            while (Player.getPList().size() > 0) //WIPES PLAYERLIST FOR NEW GAME
            {
                Player.getPList().remove(0);
            }
            
            grid = Square.grid();
            
            while (!cont)
            {
                northernopolyPrint();
                System.out.println("\t\t\t\t\t\t\t\tWelcome to Northernopoly! The game works with 2-4 players.");
                System.out.println("\t\t\t\t\t\t\t\tTo start the game type the number of players you want to play with.");
                System.out.println("\t\t\t\t\t\t\t\tYou can also mess around with some SETTINGS, or see a GUIDE.");
                System.out.println("\t\t\t\t\t\t\t\tTo exit the game, enter EXIT.");
                //Check validity of scanner input
                String str = sc.nextLine();
                if (Square.inputInt(str) && Integer.parseInt(str) < 5 && Integer.parseInt(str) > 1)
                {
                    num = Integer.parseInt(str);
                    playerAmount = num;
                    cont = true;
                }
                else if (str.toLowerCase().equals("settings"))
                {
                    str = "a";
                    boolean cont2 = false;
                    while (!cont2)
                    {
                        northernopolyPrint();
                        System.out.println("                                                SETTINGS:");
                        System.out.println("\n\t\t\t\t\t\t\t\tFree Parking: Do you want players landing on Free Parking");
                        System.out.println("\t\t\t\t\t\t\t\tto collect money lost to the bank? This is a common house");
                        System.out.println("\t\t\t\t\t\t\t\trule, but does not appear in the official monopoly ruleset.");
                        System.out.println("\t\t\t\t\t\t\t\tCurrently, the rule is "+option+".");
                        
                        System.out.println("\n\t\t\t\t\t\t\t\t\t\t        Type (Y)es or (N)o.");
                        System.out.println("\n\t\t\t\t\t\t\t\t\t\t        You can also CANCEL.");
                        
                        str = sc.nextLine();
                        if (str.toLowerCase().equals("y"))
                        {
                            option = true;
                            cont2 = true;
                        }
                        else if (str.toLowerCase().equals("n"))
                        {
                            option = false;
                            cont2 = true;
                        }
                        else if (str.toLowerCase().equals("cancel"))
                        {
                            cont2 = true;
                        }
                    }
                }
                else if(str.toLowerCase().equals("exit"))
                {
                    System.out.print("\f");
                    System.exit(0);
                }
                else if (str.toLowerCase().equals("guide"))
                {
                    str = "a";
                    boolean cont2 = false;
                    while (str.length() != 0)
                    {
                        northernopolyPrint();
                        System.out.println("\t\t\t\t\t\t\t\t\t\t\tGUIDE:");
                        System.out.println("\n\t\t\t\t\t\t\t\tStandard Monopoly rules apply. Monopolies (sets of properties)");
                        System.out.println("\t\t\t\t\t\t\t\tare denoted by similar borders - each monopoly has a different");
                        System.out.println("\t\t\t\t\t\t\t\tborder character.\n");
                        System.out.println("\t\t\t\t\t\t\t\tThe number in [square brackets] under the name of each property");
                        System.out.println("\t\t\t\t\t\t\t\tis its reference number. This is important when for when you want");
                        System.out.println("\t\t\t\t\t\t\t\tto mortgage, build houses on, etc. a property.\n");
                        System.out.println("\t\t\t\t\t\t\t\tPlayer \"tokens\" are shown in <angle quotes>. These display the");
                        System.out.println("\t\t\t\t\t\t\t\tcurrent position of the player represented by that token character.");
                        
                        System.out.println("\t\t\t\t\t\t\t\tThe price of a property is displayed at the bottom of its square.");
                        System.out.println("\t\t\t\t\t\t\t\tWhen owned, this is replaced by the token of the player who owns it.");
                        
                        System.out.println("\n\t\t\t\t\t\t\t\t\t\t\tPress Enter.");
                        
                        str = sc.nextLine();
                    }
                }
            }
            
            
            String in;
            char[] symbols = new char[]{'@','#','$','%'};
            boolean[] taken = new boolean[]{false,false,false,false};
            
            for (int i = 0; i < playerAmount && !DEBUG; i++)
            {
                in = "";
                cont = false;
                String name = "";
                char symbol = ' ';
                String symbolNum = "";
                while ((name.length() == 0 || name.length() > 25 || !cont))
                {
                    UI.setup("Player "+(i+1)+", what is your name?","Please keep it under 25 characters.","Input and then press enter.");
                    name = sc.nextLine();
                    boolean cont2 = false;
                    while (!cont2)
                    {
                        UI.setup("Is ["+name+"] a satisfactory name?","","Input Y or N.");
                        in = sc.nextLine();
                        if (in.toLowerCase().equals("y"))
                        { cont = true; cont2 = true; }
                        else if (in.toLowerCase().equals("n"))
                        { cont2 = true; }
                    }
                }
                
                String symbolList = "";
                for (int j = 0; j < symbols.length; j++)
                {
                    if (!taken[j]) {symbolList += "["+(j+1)+" - "+symbols[j]+"]    ";}
                    else {symbolList += "[Taken]    ";}
                    // [1 - %] or [Taken]
                }
                
                cont = false;
                while (!cont)
                {
                    UI.setup(name+", what would you like your symbol to be?",symbolList,
                                  "Input the number, and press enter.");
                    symbolNum = sc.nextLine();
                    boolean cont2 = false;
                    if (Square.inputInt(symbolNum) && Integer.parseInt(symbolNum) >= 1 && Integer.parseInt(symbolNum) < 5
                        && !taken[Integer.parseInt(symbolNum)-1])
                    {
                        while (!cont2)
                        {
                            UI.setup("Is "+symbols[Integer.parseInt(symbolNum)-1]+" a satisfactory symbol?","",
                                     "Input Y or N.");
                            in = sc.nextLine();
                            if (in.toLowerCase().equals("y"))
                            { 
                                cont = true; 
                                cont2 = true; 
                                taken[Integer.parseInt(symbolNum)-1] = true;
                            }
                            else if (in.toLowerCase().equals("n"))
                            { cont2 = true; }
                        }
                    }
                }
                symbol = symbols[Integer.parseInt(symbolNum)-1];
                if (i == 0) {p1 = new Player(name,symbol);} //the arraylist wasn't working
                else if (i == 1) {p2 = new Player(name,symbol);}
                else if (i == 2) {p3 = new Player(name,symbol);}
                else if (i == 3) {p4 = new Player(name,symbol);}
                
            }  
            
            if (DEBUG)
            {
                p1 = new Player("Bernardo",'*');
                p2 = new Player("Francisco",'%');
                p3 = new Player("Marcellus",'$');
                p4 = new Player("Horatio",'#');
                //p1.transfer(1290,p2,true);
            }
            
                                                /* End Player Setup */
            Player.getPList().add(p1);
            Player.getPList().add(p2); //Minimum of 2 players
            if (playerAmount > 2)
            {Player.getPList().add(p3);}
            if (playerAmount > 3)
            {Player.getPList().add(p4);}
            
            for(int p = 0; p < Player.getPList().size(); p++)
            {
                grid[0].getSprite()[0] = grid[0].getSprite()[0].substring(0,grid[0].getSprite()[0].indexOf("    ")) + 
                                         " <"+Character.toString(Player.getPList().get(p).getSymbol())+">" + 
                                         grid[0].getSprite()[0].substring(grid[0].getSprite()[0].indexOf("    ") + 4,
                                         grid[0].getSprite()[0].length());
            }
            
            cont = false;
            runGame();
        }
    }

    
    private static void northernopolyPrint()
    {
        System.out.println("\f\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\t\t\t\t\t\t\t\t  _   _            _   _                                       _       ");
        System.out.println("\t\t\t\t\t\t\t\t | \\ | |          | | | |                                     | |      ");
        System.out.println("\t\t\t\t\t\t\t\t |  \\| | ___  _ __| |_| |__   ___ _ __ _ __   ___  _ __   ___ | |_   _ ");
        System.out.println("\t\t\t\t\t\t\t\t | . ` |/ _ \\| '__| __| '_ \\ / _ \\ '__| '_ \\ / _ \\| '_ \\ / _ \\| | | | |");
        System.out.println("\t\t\t\t\t\t\t\t | |\\  | (_) | |  | |_| | | |  __/ |  | | | | (_) | |_) | (_) | | |_| |");
        System.out.println("\t\t\t\t\t\t\t\t |_| \\_|\\___/|_|   \\__|_| |_|\\___|_|  |_| |_|\\___/| .__/ \\___/|_|\\__, |");
        System.out.println("\t\t\t\t\t\t\t\t                                                  | |             __/ |");
        System.out.println("\t\t\t\t\t\t\t\t                                                  |_|            |___/ \n\n");
    }
    
    /** 
     * The main method, where the game loop happens.
     * Once this method is called, it will be running until someone wins and the game ends.
     */
    private static void runGame()
    {
        Scanner sc = new Scanner(System.in);
        Random rando = new Random();
        boolean gameOver = false;
        
        int roll;
        int dice1;
        int dice2; //2 dice, to simulate doubles
        int winner = -1;
        String in = "a";
        /* There are 3 major sections to the main game loop for each turn.
         * 1: The player either rolls the dice or opens one of a few optional menus: mortgage, unmortgage, build
         * 2: They roll, and whatever space they land on, that square "does its thing"
         * 3: End of the turn, same as 1 without the dice roll
         */
        
        
        while (!gameOver) //Main game loop
        {
            Player p = Player.getPList().get(getTurn()); //The player whose turn it is
            
            elimination = -1; //Nobody was just eliminated
            
            roll = 0;
            dice1 = 0;
            dice2 = 0;
            in = "a";
            
            repayDebt(); //"Checkpoints" making sure nobody should be getting out. repayDebt only activates if a player has negative money
            
            
            //We "roll" 2 dice for doubles & to simulate how it really is to play monopoly (more likely to roll certain things than others)
            dice1 = rando.nextInt(6)+1; //1-6
            dice2 = rando.nextInt(6)+1; //1-6
            if (DEBUG) {dice1 = 1; dice2 = 1;} //Helpful for testing
            
            boolean firstTurn = true;
            
            while (firstTurn || dice1 == dice2 && !p.isImprisoned())
            {
                dice1 = rando.nextInt(6)+1; //1-6
                dice2 = rando.nextInt(6)+1;
                
                firstTurn = false;
                
                while (in.length() != 0 && !p.checkOut())
                {
                    String str = "";
                    String str2 = "Press enter to roll the dice, (M)ortgage, (U)nmortgage, or (B)uild.";
                    if (p.isImprisoned()) 
                    {
                        str = "You're in jail. Doubles, a $50 fine (type BAIL), or a Get Out Of Detention Card will free you.";
                        str2 = "Press enter to roll the dice, (M)ortgage, (U)nmortgage, (B)uild, or use a (C)ard.";
                    }
                    UI.printBoard(Player.getPList().get(getTurn()).playerName+", it's your turn.",str,str2);
                    in = sc.nextLine();
                    if (in.toLowerCase().equals("m")) //mortgage
                    {
                        mortgage(true,false,getTurn());
                    }
                    else if (in.toLowerCase().equals("u")) //unmortgage
                    {
                        mortgage(false,false,getTurn());
                    }
                    else if (in.toLowerCase().equals("b")) //build
                    {
                        build();
                    }
                    else if (p.isImprisoned() && in.toLowerCase().equals("c")) //Use a get-out-of-jail-free card to escape
                    {
                        if (p.getFreeCards() > 0)
                        {
                            p.toggleImprison(false);
                            p.useFreeCard();
                            in = "a";
                            while (in.length() != 0)
                            {
                                UI.printBoard("You used a Get Out of Detention Free Card to escape.",
                                              "You have "+p.getFreeCards()+ " of them left.","Press enter.");
                                in = sc.nextLine();
                            }
                        }
                    }
                    else if (in.toLowerCase().equals("bail"))
                    {
                        if (p.getBalance() < 50) //they can't post bail.
                        {
                            in = "a";
                            while (in.length() != 0)
                            {
                                UI.printBoard("You don't have enough money to post bail.","","Press enter.");
                                in = sc.nextLine();
                            }
                        }
                        else //they can post bail, and want to
                        {
                            p.withdraw(50,false); //not forced, but it WILL go through.
                            p.toggleImprison(false); //out of jail
                            in = "a";
                            while (in.length() != 0)
                            {
                                UI.printBoard("You posted bail, and are free to go with a slap on the wrist.","",
                                              "Press enter.");
                                in = sc.nextLine();
                            }
                        }
                        in = "a"; //gives them another chance to choose between mortgage/build/roll
                    }
                }
                repayDebt();
                if (dice1 == dice2) //If you get 3 doubles in a row you go to jail.
                {
                    p.setDoubles(p.getDoubles()+1);
                }
                else //breaking the doubles streak
                {
                    p.setDoubles(0);
                }
                
                roll = dice1+dice2;
                if (p.isImprisoned()) //They're in jail, having not decided to post bail. Doubles will free them.
                {
                    String str = "";
                    if (dice1 == dice2)
                    {
                        p.setDoubles(0);
                        p.toggleImprison(false);
                        str = "You rolled doubles, and escaped!";
                    }
                    else
                    {str = "No doubles this time. Tough luck.";}
                    in = "a";
                    while (in.length() != 0)
                    {
                        UI.printBoard("Roll: "+dice1+" + "+dice2+" = "+roll, str ,"Press enter to continue.");
                        in = sc.nextLine();
                    }
                }
                
                if (!p.checkOut() && !p.isImprisoned()) //they're in the game, and not in jail.
                {
                    int currentLoc = p.getLocation();
                    if (p.getDoubles() == 3) //3 doubles in a row - jail for "speeding"
                    {
                        p.toggleImprison(true); //their imprisoned state will now be true
                        p.move(10); //Move them to jail
                    }
                    else if (currentLoc + roll > 39) // looping around to the beginning
                    {
                        p.move(currentLoc + roll - 40);
                        grid[0].doItsThing(); //Give the player the $200 from passing Go
                    }
                    else //regular roll
                    {
                        p.move(currentLoc + roll); //Move player by the amount rolled
                    }
                    in = "a";
                    
                    String str = "You advance to "+grid[p.getLocation()].getName()+".";
                    if (p.isImprisoned())
                    {str = "3 doubles in a row! You were sent to jail for cheating.";}
                    else if (!p.isImprisoned() && p.getLocation() == 10)
                    {str += " Just visiting.";}
                    
                    if (dice1 == dice2)
                    {str += " Doubles! Roll again.";}
                    
                    while (in.length() != 0)
                    {
                        UI.printBoard("Roll: "+dice1+" + "+dice2+" = "+roll, str ,"Press enter to continue.");
                        in = sc.nextLine();
                    }
                    in = "a";
                    
                    grid[p.getLocation()].doItsThing(); //doItsThing is an abstract method - every type of Square has different one
                }
                repayDebt(); //Another checkpoint
            }
            
            while (in.length() != 0 && !p.checkOut()) //Repeat of earlier
            {
                UI.printBoard("Press enter to end your turn, or:","","(M)ortgage, (U)nmortgage, or (B)uild .");
                in = sc.nextLine();
                if (in.toLowerCase().equals("m"))
                {
                    mortgage(true,false,getTurn());
                }
                else if (in.toLowerCase().equals("u"))
                {
                    mortgage(false,false,getTurn());
                }
                else if (in.toLowerCase().equals("b"))
                {
                    build();
                }
            }
            repayDebt(); 
            
            //Check if the game is over
            int gameOverResult = isGameOver();
            if (gameOverResult != -1) //someone has won
            {
                gameOver = true;
                winner = gameOverResult;
            }
            
            //If a player has just been eliminated, do that stuff
            if (elimination != -1 && !gameOver) //someone has just been eliminated, but there are still 2+ people left
            {
                for (int i = 0; i < recirculation.size(); i++) 
                { //if the "killing blow" came from the bank, there will be properties in here to auction off.
                    in = " ";
                    while (in.length() != 0)
                    {
                        UI.printBoard(Player.getPList().get(elimination).getName()+
                                      " has been eliminated; their properties will be auctioned.",
                                      "Now up for auction: "+grid[recirculation.get(i)].getName()+".","Press enter to begin.");
                        in = sc.nextLine();
                        ((Buyable)grid[recirculation.get(i)]).auction();
                    }
                }
                elimination = -1; //reset the variable
            }
            
            if (playerAmount != 1) //Rotation of the turn counter
            {
                if (whoseTurn == playerAmount-1)
                {whoseTurn = 0;}
                else 
                {whoseTurn++;}
            }
        }
        
        UI.endgame(Player.getPList().get(winner));
        // Return to title screen
    }
    
    /**
     * Works in tandem with the mortgage method in Buyable.
     * First, assembles a list of properties eligable for the player (pNum) to mortgage.
     * Then, it displays these, and takes input.
     * This can be "forced", as in you HAVE to mortgage something, or not forced, which lets you cancel.
     * @param mode a boolean that lets the method act as both an interface for mortgaging and unmortgaging properties.
     * @param forced a boolean that tells the method whether a mortgage MUST go through or whether it is simply optional.
     * @param pNum an int that describes the ID of the player (un)mortgaging properties.
     */
    public static boolean mortgage(boolean mode, boolean forced, int pNum) //True = mortgage, false = unmortgage
    {   //return: true for worked (mortgage went through), false for didn't work
        ArrayList<Integer> mortgagable = new ArrayList<Integer>();
        
        for (int i = 0; i < 40; i++)
        {
            if (grid[i] instanceof Buyable && ((Buyable) grid[i]).getOwner() == pNum)
            {
                if (mode && !((Buyable) grid[i]).getMortgage() && grid[i] instanceof Property &&
                    ((Property) grid[i]).getHouseCount() == 0)    
                {//Isn't mortgaged, method on mortgage setting, no developments on space.
                    //This is the only scenario where you'd want to show the details of the property
                    mortgagable.add(i);
                }
                else if (mode && !(grid[i] instanceof Property) && !((Buyable) grid[i]).getMortgage())
                {
                    mortgagable.add(i);
                }
                else if (!mode && ((Buyable) grid[i]).getMortgage()
                         && ((Property)grid[i]).getBuyBackVal() <= Player.getPList().get(pNum).getBalance())
                {
                    mortgagable.add(i); //list of properties open to be unmortgaged, in this scenario
                }
            }
        }
        
        String in = "a";
        boolean cont = false;
        
        while (mortgagable.size() == 0 && in.length() != 0) //Nothing to mortgage
        {
            if (forced) {return false;} 
            String str = "";
            if (!mode) {str = "un";}
            UI.printBoard("You don't have any "+str+"mortgagable properties.","","Press enter to continue.");
            in = sc.nextLine();
            cont = true;
        }
        
        while (!cont) //The loop where they actually mortgage something
        {
            String str1;
            String str2 = "";
            int num;
            if (mode && !forced) {str1 = "Which of your properties would you like to mortgage?";}
            else if (!mode && !forced) {str1 = "Which of your properties would you like to unmortgage?";}
            else {str1 = "To repay your debt to the bank, you MUST mortgage a property.";} //mode && forced
            //(!mode && forced) will never happen
            if (!forced) {str2 = ", or CANCEL";}
            
            UI.selectProps(str1,"","Enter the corrosponding number"+str2+".",mortgagable);
            in = sc.nextLine();
            if (Square.inputInt(in) && safeToMortgage(Integer.parseInt(in),mode)) //Success!
            {
                int loc = Integer.parseInt(in);
                if (mode) {((Buyable)grid[loc]).mortgage(pNum);}
                else {((Buyable)grid[loc]).unmortgage(pNum);}
                in = "a";
                while (in.length() != 0)
                {
                    if (mode) 
                    {
                        str1 = "You mortgaged ";
                        num = ((Buyable)grid[loc]).getCost()/2;
                    }
                    else 
                    {
                        str1 = "You unmortgaged ";
                        num = ((Buyable)grid[loc]).getCost()/2;
                        num = num +(num/10);
                    }
                    UI.printBoard(str1 + grid[loc].getName()+" for $"+num+".","","Press enter to continue.");
                    in = sc.nextLine();
                }
                cont = true;
            }
            else if (in.toLowerCase().equals("cancel") && !forced) //Only acceptable if this isn't being forced
            {
                cont = true;
            }
        }
        return true; //the mortgage went through, or was cancelled.
    }
    
    /**
     * The player uses this method to build houses/hotels on spaces that adhere to a set of requirements.
     * Works in tandem with Property's build method.
     * Works similarly to the mortgage method above, at least in structure.
     */
    private static void build()
    {
        ArrayList<Integer> buildFriendly = new ArrayList<Integer>();
        for (int i = 0; i < 40; i++)
        {
            if (grid[i] instanceof Property && ((Buyable) grid[i]).getOwner() == getTurn())
            {

                int[] monoMems = ((Property) grid[i]).getMonoMems();
                /* even build rules:
                 * You're trying to build on a space which is part of a complete monopoly
                 * if this space has 2 more houses than either of the other spaces on the monopoly, don't allow it
                 */
                Property p = ((Property) grid[i]);
                if (p.getHouseCount() < 5 && !p.getMortgage() && p.monoComplete()
                    && !(p.getHouseCount() == 4 && freeHotels == 0)//they want to build a hotel, but none are left
                    && !(p.getHouseCount() < 4 && freeHouses == 0))//they want to build a house, but none are left
                {
                    //Check if building here would comply with even build
                    int[] monoHCounts = findHCounts(monoMems);
                    int mindex = findMindex(monoHCounts);
                    int maxdex = findMaxdex(monoHCounts);
                    if (monoHCounts[mindex] == p.getHouseCount())
                    { // You can only build on p if the spot with the minimum # of houses is even with it.
                        buildFriendly.add(i); //Only scenario where you'd want to show the details of the property
                    }
                }
            }
        }

        String in = "a";
        boolean cont = false;
        while (buildFriendly.size() == 0 && in.length() != 0)
        {
            UI.printBoard("You don't have anywhere to build.",
                          "Keep an eye on house/hotel shortages, and remember to build evenly!",
                          "Press enter to continue.");
            in = sc.nextLine();
            cont = true;
        }
        
        while (!cont) //Building loop
        {
            UI.selectProps("Which of your properties would you like to build on?",
                           "Keep an eye on house/hotel shortages, and remember to build evenly!",
                           "Enter the corrosponding number, or CANCEL.",buildFriendly);
            in = sc.nextLine();
            
            if (Square.inputInt(in) && isMember(Integer.parseInt(in),buildFriendly)
                && Player.getPList().get(getTurn()).getBalance() > ((Property)grid[Integer.parseInt(in)]).getHCost())
            { //More parameters for building where they want
                int loc = Integer.parseInt(in);
                Property p = ((Property)grid[loc]); //Casting!
                
                boolean cont2 = false;
                while (!cont2)
                {
                    UI.printBoard("Are you sure you want to build on "+grid[loc].getName()+" for $"+p.getHCost()+"?","","Enter (Y)es or (N)o.");
                    in = sc.nextLine();
                    if (in.toLowerCase().equals("y"))
                    {
                        p.buildHouse(); //build the house
                        Player.getPList().get(getTurn()).withdraw(((Property)grid[loc]).getHCost(),false); //withdraw money
                        in = "a";
                        
                        String str;
                        if (p.getHouseCount() == 5) //they just built a hotel
                        {
                            str = "a hotel";
                            freeHotels--;
                            freeHouses += 4;
                        }
                        else //they just built a house
                        {
                            str = p.getHouseCount() + " house(s)";
                            freeHouses--;
                        }
                        
                        while (in.length() != 0)
                        {
                            
                            UI.printBoard("You built on "+grid[loc].getName()+", spending $"+p.getHCost()+".",
                                          "This space now has "+str+".","Press enter to continue.");
                            in = sc.nextLine();
                            cont = true;
                        }
                        
                        cont2 = true;
                    }
                    else if (in.toLowerCase().equals("n"))
                    {
                        
                    }
                }
            }
            else if (in.toLowerCase().equals("cancel"))
            {
                cont = true;
            }
        }
    } // build()
    
    /**
     * Called when the player is in debt, to earn them some money.
     * pNum refers to the player selling the properties.
     * type refers to the mode of the method:
     *      If type == 2, they were taken out by the bank, and their sold properties will go up for auction
     *      If type == 1, they were taken out by a player, and their sold properties will go directly to them.
     * returns true or false depending on whether a sale was successful.
     * @param pNum an int that describes the ID of the player selling their properties.
     * @param type an int that tells the program where sold properties go after they are sold.
     */
    public static boolean sellProps(int pNum, int type) //false if there were none more to sell
    {
        ArrayList<Integer> sellable = new ArrayList<Integer>();
        Player p = Player.getPList().get(pNum);
        for (int i = 0; i < 40; i++) //WHICH PROPERTIES ARE VIABLE TO BE SOLD?
        {
            if (grid[i] instanceof Buyable && ((Buyable) grid[i]).getOwner() == pNum)
            { //It's a buyable, owned by the player.
                Buyable b = (Buyable)grid[i];
                if (!(b instanceof Property)) //it's a railroad or utility
                {
                    sellable.add(i); //railroads and utilities are sellable whenever
                }
                
                else if (b instanceof Property && !((Property)b).monoComplete())
                {
                    sellable.add(i); //it's a property, but not part of a complete monopoly.
                }
                
                else if (b instanceof Property && ((Property)b).monoComplete()) //part of a complete monopoly
                {
                    int[] monoMems = ((Property)b).getMonoMems();
                    boolean viable = true;
                    for (int j = 0; j < monoMems.length; j++)
                    {
                        if (((Property)(grid[monoMems[j]])).getHouseCount() != 0) //there is development here
                        {
                            viable = false;
                        }
                    }
                    
                    if (viable) //part of a complete monopoly, but that monopoly hasn't been
                    {           //developed on yet, so it's ok to sell.
                        sellable.add(i);
                        for (int j = 0; j < monoMems.length; j++)
                        { //The monopoly is no longer complete.
                            ((Property)(grid[monoMems[j]])).monopolyComplete = false; 
                        }
                    } 
                }
            }
        }
        
        if (sellable.size() == 0) //nothing to sell :(
        {
            String in = " ";
            while (in.length() != 0)
            {
                UI.selectProps("You have nothing you can sell.",
                               "Reminder: You can only sell properties from non-developed monopolies.",
                               "Press enter, and type something else.",sellable);
                in = sc.nextLine();
            }
            return false;
        }
        else //Something to sell! :)
        {
            boolean cont = false;
            String in = " ";
            while (!cont) //Selling loop
            {
                UI.selectProps("Which of your properties would you like to sell?","",
                               "Enter the corrosponding number, or CANCEL.",sellable);
                in = sc.nextLine();
                if (Square.inputInt(in) && isMember(Integer.parseInt(in),sellable))
                {
                    int prop = Integer.parseInt(in);
                    boolean cont2 = false;
                    int cost = ((Buyable)grid[prop]).getCost();
                    if (((Buyable)grid[Integer.parseInt(in)]).getMortgage()) //it's mortgaged.
                    {
                        cost /= 2; //a sold property is worth half of it's original value when mortgaged
                    }
                    while (!cont2) //Selling a property is a big deal, so it's nice to give the player a chance to make sure they want to
                    {
                        UI.selectProps("Are you sure you want to sell "+grid[prop].getName()+" for $"+cost+"?",
                                       "","Enter Y or N.",sellable);
                        in = sc.nextLine();
                        if (in.toLowerCase().equals("y"))
                        {
                            p.deposit(cost); //gives them the money
                            p.getOwned().remove(new Integer(prop));//removes first instance of the property's number.
                            if (type == 2) //bank
                            {
                                ((Buyable)grid[prop]).owner = -1; //returned to circulation
                                ((Buyable)grid[prop]).unmortgage(); //unmortgaged
                                recirculation.add(prop); //added to the list of props that will be auctioned later
                                if(grid[prop].getSprite()[0].length() == 9) // vertical square
                                {
                                    grid[prop].getSprite()[7] = defaultGrid[prop].getSprite()[7];
                                }
                                else // horizontal
                                {
                                    grid[prop].getSprite()[2] = defaultGrid[prop].getSprite()[2];
                                }
                                
                            }
                            else if (type == 1) //another player
                            {
                                int owedID = ((Buyable)grid[p.getLocation()]).owner;
                                ((Buyable)grid[prop]).owner = ((Buyable)grid[p.getLocation()]).owner;
                                //Transfers ownership of the sold property from one player to the other. It'll stay (un)mortgaged.
                                Player.getPList().get(owedID).getOwned().add(prop); //adds it to the others' list
                                
                                //It'll still be mortgaged, though.
                            }
                            cont = true;
                            cont2 = true;
                        }
                        else if (in.toLowerCase().equals("n"))
                        {
                            cont2 = true;
                        }
                    }
                }
                else if (in.toLowerCase().equals("cancel"))
                {
                    cont = true;
                }
            }
        }
        return true;
    }
    
    /**
     * Another method called when the player is in debt, needing to make some money by selling what they own.
     * pNum refers to the player selling houses.
     * type is the mode of the method - same as in the above method.
     * @param pNum an int that describes the ID of the player selling their houses.
     * @param type an int that tells the program where the money from sold houses goes.
     */
    public static boolean sellHouses(int pNum, int type)
    {
        ArrayList<Integer> sellable = new ArrayList<Integer>();
        Player p = Player.getPList().get(pNum);
        for (int i = 0; i < 40; i++) //WHICH PROPERTIES CAN WE SELL HOUSES FROM?
        {
            if (grid[i] instanceof Property && ((Buyable)grid[i]).owner == pNum &&
                ((Property)grid[i]).monoComplete() && ((Property)grid[i]).getHouseCount() > 0)
            { //it's a property, owned by the player, part of a complete monopoly, and there ARE houses there.
                sellable.add(i); 
            } 
        }
        
        if (sellable.size() == 0) //no houses built to sell off :(
        {
            String in = " ";
            while (in.length() != 0)
            {
                UI.selectProps("You have no properties with houses to sell.","",
                               "Press enter, and type something else.",sellable);
                in = sc.nextLine();
            }
            return false;
        }
        else //there's something to sell.
        {
            boolean cont = false;
            while (!cont)
            {
                boolean cont2 = false;
                String in = " ";
                while (!cont2)
                {
                    UI.selectProps("Choose a property to sell from. Keep in mind, housing shortages",
                                   "and even building rules may cause you to sell more than intended!",
                                   "Type a number, or CANCEL.",sellable);
                    in = sc.nextLine();
                    if (Square.inputInt(in))
                    {
                        int prop = Integer.parseInt(in);
                        if (prop >= 0 && prop < 40 && grid[prop] instanceof Property && 
                            ((Property)grid[prop]).getHouseCount() > 0)
                        { //they've inputted a number from 0-39, it's tied to a property, and that property has houses on it
                            p.deposit(evenBuildSystem(prop)); //See below
                        }
                    }
                    else if (in.toLowerCase().equals("cancel"))
                    {
                        cont2 = true;
                        cont = true;
                    }
                }
            }
        }
        return true;
    }
    
    /** 
     * Called by sellHouses() (above) - given an input of a property, figures out how to remove houses over
     * the entire monopoly to ensure the even build rule is kept in place. Then, returns the amount of money
     * that the player should be compensated.
     * Even Build: On a monopoly, each property's house count must be within 1 of each other. (minimum + 1 == maximum)
     * 
     * @param prop refers to the index of the property that this method is looking at
     */
    public static int evenBuildSystem(int prop) 
    {
        Property p = ((Property) grid[prop]);
        int[] monoMems = ((Property) grid[prop]).getMonoMems(); //Monopoly Members of the property in question
        
        int owed = 0; //the amount owed to the player
        int fundPer = p.getHCost()/2; //you get 0.5x the cost for each.
        
        int[] memHCounts = findHCounts(monoMems);     
        int mindex = findMindex(memHCounts); //min amnt of houses
        int maxdex = findMaxdex(memHCounts); //max amnt of houses
        
        if (memHCounts[mindex] == 5) //All spaces have hotels
        {
            if (freeHouses < 4) //Wipes the slate clean. Not enough houses to swap out.
            { 
                for (int i = 0; i < monoMems.length; i++)
                {
                    freeHotels++; //destroy the hotel
                    for (int j = 0; j < 5; j++) //from hotel (5) to nothing (0) for each property
                    {
                        ((Property) grid[monoMems[i]]).destroyHouse();
                        owed += fundPer;
                    }
                }
            }
            else
            {
                p.destroyHouse();
                freeHotels++;
                freeHouses -= 4; //Swap out a hotel for 4 houses
                owed += fundPer;
            }
        }
        else if (memHCounts[maxdex] < 5) //None of the spaces have hotels
        {
            p.destroyHouse();
            freeHouses++;
            owed += fundPer;
        }
        else //there's a mix of houses and hotels
        {
            if (p.getHouseCount() == 5) //p is a hotel
            {
                if (freeHouses < 4) //A repeat of above
                {
                    freeHotels++;
                    for (int i = 0; i < 5; i++) //tear down everything
                    {
                        p.destroyHouse();
                        owed += fundPer;
                    }
                }
                else
                {
                    p.destroyHouse();
                    freeHotels++;
                    freeHouses -= 4;
                    owed += fundPer;
                }
            }
            else //it's a (set of) house(s)
            {
                p.destroyHouse();
                freeHouses++;
                owed += fundPer;
            }
        }
        memHCounts = findHCounts(monoMems); //An update to this array      
        mindex = findMindex(memHCounts);
        maxdex = findMaxdex(memHCounts);
        while (memHCounts[findMindex(findHCounts(monoMems))] + 1 < memHCounts[findMaxdex(findHCounts(monoMems))])
        { //This runs until this monopoly adheres to the even build rule
            for (int i = 0; i < monoMems.length; i++)
            {
                Property pCurrent = ((Property) grid[monoMems[i]]);
                while (memHCounts[findMindex(memHCounts)] + 1 < pCurrent.getHouseCount()
                       && memHCounts[findMindex(memHCounts)] != pCurrent.getHouseCount()) //?
                { //Some of this property's developments need to be destroyed to fit with even building rules
                    if (pCurrent.getHouseCount() == 5) //it's a hotel. Repeat of above code
                    {
                        if (freeHouses < 4)
                        {
                            freeHotels++;
                            for (int j = 0; j < 5; j++) //knock it all down
                            {
                                pCurrent.destroyHouse();
                                owed += fundPer;
                            }
                        }
                        else
                        {
                            pCurrent.destroyHouse();
                            freeHotels++;
                            freeHouses -= 4;
                            owed += fundPer;
                        }
                    }
                    else //it's a (set of) house(s)
                    {
                        pCurrent.destroyHouse();
                        freeHouses++;
                        owed += fundPer;
                    }
                    memHCounts = findHCounts(monoMems); //Update this list    
                }
            }
        }
        
        return owed; //When everything in the monopoly is good, return this amount.
    }
    /**
     * Accepts an array (referring to properties) and returns an array with each of their house counts.
     * @param monoMems an array containing the set of property indexes that make up a monopoly.
     */
    private static int[] findHCounts(int[] monoMems)
    {
        if (monoMems.length == 2)
        {
            return new int[] {((Property) grid[monoMems[0]]).getHouseCount(),
                              ((Property) grid[monoMems[1]]).getHouseCount()};
        }
        else //length == 3
        {
            return new int[] {((Property) grid[monoMems[0]]).getHouseCount(),
                              ((Property) grid[monoMems[1]]).getHouseCount(),
                              ((Property) grid[monoMems[2]]).getHouseCount()};
        }
    }
    /**
     * Accepts an array (arr), returns the index of the minimum value in it.
     * @param arr the array of which the minimum value index is returned.
     */
    private static int findMindex(int[] arr)
    {
        int min = arr[0];
        int mindex = 0;
        for (int i = 1; i < arr.length; i++)
        {
            if (arr[i] < min)
            {
                min = arr[i];
                mindex = i;
            }
        }
        return mindex;
    }
    /**
     * Accepts an array (arr), returns the index of the maximum value in it.
     * @param arr the array of which the maximum value index is returned.
     */
    private static int findMaxdex(int[] arr)
    {
        int max = arr[0];
        int maxdex = 0;
        for (int i = 1; i < arr.length; i++)
        {
            if (arr[i] > max)
            {
                max = arr[i];
                maxdex = i;
            }
        }
        return maxdex;
    }
    
    
    /**
     * A collection of "getter methods".
     */
    public static int getTurn()
    {
        return whoseTurn;
    }
    public static Square[] getGrid()
    {
        return grid;
    }
    public static boolean getOptions()
    {
        return option;
    }
    public static int getPlayerAmount()
    {
        return playerAmount;
    }
    
    /**
     * Checks if a property adheres to certain parameters that would allow it to be mortgaged.
     * @param prop refers to the index of the property that will be checked
     * @param mode a boolean that lets the method act as both an interface for mortgaging and unmortgaging properties.
     */
    private static boolean safeToMortgage(int prop, boolean mode)
    {
        ArrayList<Integer> owned = Player.getPList().get(getTurn()).getOwned();
        for (int i = 0; i < owned.size(); i++)
        {
            if (owned.get(i) == prop)
            {
                if (grid[prop] instanceof Property)
                {
                    Property p = (Property)grid[prop];
                    if (mode && (p.getHouseCount() > 0 || p.getMortgage()))
                    {
                        return false;
                    }
                    else if (!mode && !p.getMortgage())
                    {
                        return false;
                    }
                }
                else //must be either a utility or railroad
                {
                    Buyable b = (Buyable)grid[prop];
                    if (b.getMortgage())
                    {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    /**
     * Given an int and an ArrayList of Integer, checks if the int is featured in the ArrayList
     * @param num the number that the method is trying to locate in the ArrayList
     * @param arr the ArrayList that is searched.
     */
    private static boolean isMember(int num, ArrayList<Integer> arr)
    {
        for (int i = 0; i < arr.size(); i++) //replace with binary search? We need a sorting/searching alg. somewhere
        {
            if (arr.get(i) == num)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Activates when a player's balance strays into the negative. This happens mostly when they land on a property and don't
     * have enough money to pay the other player the demanded rent.
     * This method traps the player until they've either crossed into the positive again, or done everything in their power
     * to cross into the positive, but have failed. In that case, they are eliminated.
     */
    private static boolean repayDebt() 
    {
        for (int i = 0; i < Player.getPList().size(); i++) //check whether every player is bankrupt.
        {
            Player p = Player.getPList().get(i);
            int type = 2;
            if (p.getBalance() < 0 && grid[p.getLocation()] instanceof Property
                && ((Property)(grid[p.getLocation()])).getOwner() != -1 //nobody owns the space
                && ((Property)(grid[p.getLocation()])).getOwner() != p.getID()) //the player doesn't own the space
            {
                type = 1; //type 1 means they owe this money to another player
            }             //(since they went under after landing on another person's space)
            else if (p.getBalance() < 0)
            {
                type = 2; //type 2 means they owe the bank. Properties are reset, money disappears.
            }
            
            String in = "";
            boolean allIsLost = false; //All 3 (or a certain combo) of result booleans have resulted false. They will be eliminated
            boolean resultM = true;
            boolean resultS = true;
            boolean resultH = true;
            
            while (p.getBalance() < 0 && !p.checkOut())
            {
                in = "a";
                UI.printBoard(p.getName()+", you are in debt. If you can't pass $0, you'll be out of the game.",
                "You can (M)ortgage or even (S)ell properties, and manage houses/hotels (H).",
                "Enter one of these 3 options.");
                
                in = sc.nextLine();
                boolean cont = false;
                if (in.toLowerCase().equals("m")) //mortgage ANY mortgagable properties.
                {    
                     boolean result = mortgage(true,false,i);//if this returns f, it means there's nothing to mortgage
                     if (!resultH && !result) {resultM = false;}
                }
                else if (in.toLowerCase().equals("s")) //sell sellable properties
                {    
                     boolean result = sellProps(i,type);
                     if (!resultH && !result) 
                     {
                         resultS = false; //If you can't sell anything, you can't mortgage anything, either.
                         resultM = false;
                     }
                }
                else if (in.toLowerCase().equals("h")) //sell houses and hotels
                {    
                     boolean result = sellHouses(i,type);
                     if (!result) {resultH = false;}
                }
                
                if (!resultM && !resultS && !resultH) //all methods of gaining money have failed to get them enough
                {allIsLost = true;}
                
                if (allIsLost)
                {
                    p.eliminate(); //Gone from the game. They will be skipped over in turns
                    if (type == 1 && p.getBalance() < 0)
                    {
                        int owner = ((Property)(grid[p.getLocation()])).owner;
                        Player.getPList().get(owner).deposit(p.getBalance());
                        //this last line will actually lower the owed person's balance. It makes sure they only
                        //get in cash what the owing player was able to pay for before being eliminated.
                    }
                    elimination = i; //tells the program that someone was just eliminated, and who.
                    String input = " ";
                    
                    while (input.length() != 0)
                    {
                        UI.printBoard(p.getName()+" has gone bankrupt, and was eliminated from the game.","",
                                  "Press enter to continue.");
                        input = sc.nextLine();
                    }
                }
                
            }
        }
        
        
        return false;
    }
    
    /**
     * Checks if the game is over.
     * If not, returns -1. If so, returns the ID of the winner.
     */
    public static int isGameOver()
    {
        int amount = Player.getPList().size();
        int player = -1;
        for (int i = 0; i < Player.getPList().size(); i++) //check whether every player is bankrupt.
        {
            if (Player.getPList().get(i).checkOut())
            {
                amount--;
            }
            else
            {
                player = i; //last player that was registered as still in. If there's only 1, this'll be the
            }               //number of the only player who's still in (meaning, the winner)
        }
        if (amount == 1)
        {
            return player; //the winner
        }
        else
        {
            return -1; 
        }
    }
}
