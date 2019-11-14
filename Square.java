import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.NumberFormatException;
public abstract class Square
{
    private String name;
    private int location;
    private String[] sprite;
    
    public Scanner sc = new Scanner(System.in);

    /**
     * Constructor for a Square object.
     * @param theName name of this Square
     * @param loc this Square's location on the board
     * @param theSprite a String array representation of this Square's visual appearance
     */
    public Square (String theName, int loc, String[] theSprite)
    {
        name = theName;
        location = loc;
        sprite = theSprite;
    }

    /**
     * Perfoms this Square's unique action.
     */
    public abstract void doItsThing();

                /* UI Link Methods */
    /**
     * Allows child classes not to have a dependency on UI
     * @param str1 the first line of information to be printed beside the board
     * @param str2 the second line of such information
     * @param str3 the third line of such information
     */
    public static void UIPrintBoard(String str1, String str2, String str3)
    {
        UI.printBoard(str1,str2,str3);
    }

                /* Input Try/Catch */
    /**
     * Checks if the inputted String is an integer.
     * @param str the String that's checked.
     */
    public static boolean inputInt(String str)
    {
        try
        {
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

                /* 'Get' methods that fetch stuff from Board (plus this Square's name & location)*/
    public static ArrayList<Player> getPList() 
    {return Player.getPList();}
    public static int getTurn()
    {return Board.getTurn();}
    public static Square[] getBoard()
    {return Board.getGrid();}
    public static boolean getOptions()
    {return Board.option;}
    public static int getPlayerAmount()
    {return Board.getPlayerAmount();}
    public String getName()
    {return name;}
    public int getLocation()
    {return location;}
    public String[] getSprite()
    {return sprite;}
    public void mortgage(boolean mode,boolean forced,int pNum)
    {Board.mortgage(mode,forced,pNum);}

    /**
     * Initializes the grid
     */
    public static Square[] grid() throws IOException
    {
        //Makes all squares how they should be
        Square[] grid = new Square[40];
        grid[0] = new Go(new String[] {"                  ","    ____   ___    ","   /      /   \\   ","   | --|  |   |   ","   \\___|  \\___/   ","                  ","     /--------/   ",
                            "     \\--------\\   "});
        grid[1] = new Property("Cafeteria",60,2,50,new int[]{1,3},new int[]{2,4,10,30,90,160,250},1, new String[] {"CAFETERIA","@  [1]  @","@       @","@       @",
                                "@       @","@       @","@       @","@@ $60 @@"}); //4 not 400
        grid[2] = new CommunityChance("Chest",2, new String[] {" !  !  ! ","  CHEST  ","!       !","         ","!       !","         ","!       !"," !  !  ! "});
        grid[3] = new Property("SAC Office",60,4,50,new int[]{1,3},new int[]{4,8,20,60,180,320,450},3, new String[]{"@@ SAC @@","@ OFFICE ","@  [3]  @","@       @","@       @",
                                "@       @","@       @","@@ $60 @@"});
        grid[4] = new Tax("School Fees",200,4,new String[] {". SCHOOL.", ".  FEES .",".       .",".       .",".       .",".       .",".       ."," Pay $200"});
        grid[5] = new Railroad("Yonge Bus",200,25,5, new String[]{"YONGE BUS","   [5]   ","X       X","         ","X       X","         ","X       X","   $200  "});
        grid[6] = new Property("Banjevic Blvd",100,6,50,new int[]{6,8,9},new int[]{6,12,30,90,270,400,550},6, new String[]{" BANJEVIC","BOULEVARD","%  [6]  %","%       %",
                                "%       %","%       %","%       %","%% $100 %"});
        grid[7] = new CommunityChance("Chance",7,new String[]{" ?  ?  ? "," CHANCE  ","?       ?","         ","?       ?","         ","?       ?"," ?  ?  ? "});
        grid[8] = new Property("Dodgson's Hangar",100,6,50,new int[]{6,8,9},new int[]{6,12,30,90,270,400,550},8, new String[] {"DODGSON'S","% HANGAR ","%  [8]  %","%       %",
                                "%       %","%       %","%       %","%% $100 %"});
        grid[9] = new Property("Jones Junction",120,8,50,new int[]{6,8,9},new int[]{8,16,40,100,300,450,600},9, new String[] {"% JONES %"," JUNCTION","%  [9]  %","%       %",
                                "%       %","%       %","%       %","%% $120 %"});
        
        grid[10] = new Jail(new String[] {"     |            ","     |  DETENTION ","  J  |            ","  U  |            ","  S  |            ","  T  |            ",
                            "     -------------","      VISITING    "});
        grid[11] = new Property("Theatre Throughway",140,10,100,new int[]{11,13,14},new int[]{10,20,50,150,450,625,750},11,new String[]{"THEATRE THROUGHWAY","*      [11]      *",
                                "****** $140 ******"});
        grid[12] = new Utility("Boiler Room",150,0,12,new String[]{"~   BOILER ROOM  ~","~      [12]      ~","~  ~   $150   ~  ~"});
        grid[13] = new Property("Art Avenue",140,10,100,new int[]{11,13,14},new int[]{10,20,50,150,450,625,750},13, new String[]{"*** ART AVENUE ***","*      [13]      *",
                                "****** $140 ******"});
        grid[14] = new Property("Music Meadows",160,12,100,new int[]{11,13,14},new int[]{12,24,60,180,500,700,900},14,new String[]{"** MUSIC MEADOWS *","*      [14]      *",
                                "****** $160 ******"});
        grid[15] = new Railroad("Broadway Bus",200,25,15,new String[]{"X  BROADWAY BUS  X","       [15]       ","X  X   $200   X  X"});
        grid[16] = new Property("Dahlke Drive",180,14,100,new int[]{16,18,19},new int[]{14,28,70,200,550,750,950},16,new String[]{"&& DAHLKE DRIVE &&","&      [16]      &",
                                "&&&&&& $180 &&&&&&"});
        grid[17] = new CommunityChance("Chest",17, new String[]{" !  ! CHEST  !  ! ","!                !"," !  !  !  !  !  ! "});
        grid[18] = new Property("Leishman Lane",180,14,100,new int[]{16,18,19},
                                new int[]{14,28,70,200,550,750,950},18,new String[]{"&& LEISHMAN LANE &","&      [18]      &","&&&&&& $180 &&&&&&"});
        grid[19] = new Property("Dingwall Mall",200,16,100,new int[]{16,18,19},new int[]{16,32,80,220,600,800,1000},19,
                                new String[]{"&& DINGWALL MALL &","&      [19]      &","&&&&&& $200 &&&&&&"});
        
        grid[20] = new Parking(new String[]{"                  ","      PARKING     ","        LOT       ","                  ","     ______       ","    /|_||_\\`.__   ",
                                "   (   _    _ _\\  ","   =`-(_)--(_)-'  "});
        grid[21] = new Property("Lower Gyms",220,18,150,new int[]{21,23,24},new int[]{18,36,90,250,700,875,1050},21,new String[]{"^ LOWER ^","^  GYMS ^","^  [21] ^",
                                    "^       ^","^       ^","^       ^","^       ^","^^ $220 ^"});
        grid[22] = new CommunityChance("Chance",22,new String[]{" ?  ?  ? "," CHANCE  ","?       ?","         ","?       ?","         ","?       ?"," ?  ?  ? "});
        grid[23] = new Property("Upper Gym",220,18,150,new int[]{21,23,24},new int[]{18,36,90,250,700,875,1050},23,new String[]{"^ UPPER ^","^  GYM  ^","^  [23] ^",
                                "^       ^","^       ^","^       ^","^       ^","^^ $220 ^"});
        grid[24] = new Property("Field",240,20,150,new int[]{21,23,24},new int[]{20,40,100,300,750,925,1100},24,new String[]{"^ FIELD ^","^  [24] ^","^       ^","^       ^",
                                    "^       ^","^       ^","^       ^","^^ $240 ^"});
        grid[25] = new Railroad("Eglinton Bus",200,25,25,new String[]{" EGLINTON","   BUS   ","X  [25] X","         ","X       X","         ","X       X","   $200  "});
        grid[26] = new Property("Ross Crossing",260,22,150,new int[]{26,27,29},new int[]{22,44,110,330,800,975,1150},26,
                                new String[]{"#  ROSS #"," CROSSING","#  [26] #","#       #","#       #","#       #","#       #","## $260 #"});
        grid[27] = new Property("Bairdwalk",260,22,150,new int[]{26,27,29},new int[]{22,44,110,330,800,975,1150},27,new String[]{"BAIRDWALK","#  [27] #","#       #","#       #",
                                    "#       #","#       #","#       #","## $260 #"});
        grid[28] = new Utility("Swimming Pool",150,0,28,new String[]{" SWIMMING","   POOL  ","~  [28] ~","         ","~       ~","         ","~       ~","   $150  "});
        grid[29] = new Property("Kim Korner",280,24,150,new int[]{26,27,29},new int[]{24,48,120,360,850,1025,1200},29,new String[]{"## KIM ##","# KORNER ","#  [29] #",
                                "#       #","#       #","#       #","#       #","## $280 #"});
        grid[30] = new GoToJail(new String[]{"                  ","                  ","       GO TO      ","     DETENTION    ",
                                "                  ","                  ","                  ","                  "});
        grid[31] = new Property("Guidance",300,26,200,new int[]{31,32,34},new int[]{26,52,130,390,900,1100,1275},31,new String[]{"++++ GUIDANCE ++++","+      [31]      +",
                                "++++++ $300 ++++++"});
        grid[32] = new Property("VP Office",300,26,200,new int[]{31,32,34},new int[]{26,52,130,390,900,1100,1275},32, new String[]{"++++ VP OFFICE +++","+      [32]      +",
                                "++++++ $300 ++++++"});
        grid[33] = new CommunityChance("Chest",33,new String[]{" !  ! CHEST  !  ! ","!                !"," !  !  !  !  !  ! "});
        grid[34] = new Property("Library",320,28,200,new int[]{31,32,34},new int[]{28,56,150,450,1000,1200,1400},34,new String[]{"+++++ LIBRARY ++++","+      [34]      +",
                                "++++++ $320 ++++++"});
        grid[35] = new Railroad("Roehampton Bus",200,25,35, new String[]{"X ROEHAMPTON BUS X","       [35]       ","X  X   $200   X  X"});
        grid[36] = new CommunityChance("Chance",36,new String[]{" ?  ? CHANCE ?  ? ","?                ?"," ?  ?  ?  ?  ?  ? "});
        grid[37] = new Property("Auditorium",350,35,200,new int[]{37,39},new int[]{35,70,175,500,1100,1300,1500},37,new String[]{"=== AUDITORIUM ===","=      [37]      =",
                                "====== $350 ======"});
        grid[38] = new Tax("Luxury Fees",75,38, new String[]{".   LUXURY FEES  .",".                .","..... Pay $75 ...."});
        grid[39] = new Property("Jamieson's Class",400,50,200,new int[]{37,39},new int[]{50,100,200,600,1400,1700,2000},39,new String[]{" JAMIESON'S CLASS ","=      [39]      =",
                                "====== $400 ======"});

        return grid;
    }
}