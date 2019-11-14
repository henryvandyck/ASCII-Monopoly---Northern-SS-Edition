import java.util.ArrayList;
import java.util.Scanner;
public class UI
{
    private static int[][] squaresOrder = new int[][] {{20,21,22,23,24,25,26,27,28,29,30},{19,31},{18,32},{17,33},
                                                    {16,34},{15,35},{14,36},{13,37},{12,38},{11,39},{10,9,8,7,6,5,4,3,2,1,0}};
     
                                                    /**
     * Prints the Northernopoly board and all relevant information to the terminal.
     * @param str1 the first line of information to be printed beside the board
     * @param str2 the second line of such information
     * @param str3 the third line of such information
     */
    public static void printBoard(String str1, String str2, String str3)
    {
        System.out.print("\f");
        ArrayList<String> info = new ArrayList<String>();
        ArrayList<Player> PList = Player.getPList();
        int numSplit = 0;
        if(str1.length() > 50)
        {
            String[] arr = cutString(str1);
            info.add(arr[0]);
            info.add(arr[1]);
            numSplit++;
        }
        else    info.add(str1);
        if(str2.length() > 50)
        {
            String[] arr = cutString(str2);
            info.add(arr[0]);
            info.add(arr[1]);
            numSplit++;
        }
        else    info.add(str2);
        if(str3.length() > 50)
        {
            String[] arr = cutString(str3);
            info.add(arr[0]);
            info.add(arr[1]);
            numSplit++;
        }
        else    info.add(str3);
        for(int n = 3 - numSplit; n > 0; n--)
        {
            info.add("");
        }
 
        info.add("|------------------------------------|"); // 38 long
        // info.add("|                                    |");
        for(int i = 0; i < PList.size(); i++)
        {
            String inOut = " ";
            if (Board.getTurn() == PList.get(i).getID())
            {inOut = "*";}
            if(!PList.get(i).checkOut())
            {
                info.add("{"+inOut+"} "+"["+PList.get(i).getSymbol()+"]  "+PList.get(i).getName());
                info.add("Balance: $" + PList.get(i).getBalance());
                info.add("Properties: " + PList.get(i).getOwned().size());
                info.add("Get out of Detention Free Cards: " +  PList.get(i).getFreeCards());
                info.add("");
            }
            else
            {
                info.add("{ } "+"["+PList.get(i).getSymbol()+"]  "+PList.get(i).getName());
                info.add("Bankrupt");
                info.add("");
                info.add("");
                info.add("");
            }
        }
        info.add("Free Houses: " + Board.freeHouses);
        info.add("Free Hotels: " + Board.freeHotels);
        for(int row = 0; row < 55; row++)
        {
            if(row != 0)    System.out.println();
            indent();
            if(row == 0 || row == 9 || row == 45 || row == 54)
            {
                System.out.print("---------------------------------------------------------------------------------------------------------------------------------");
                if(row == 45 && Board.getPlayerAmount() == 4)
                {
                    indent();System.out.print("|                                    |");
                }
            }
            else if(row < 9 || row > 45)
            {
                System.out.print("|");
                  
                for(int i = 0; i < squaresOrder[0].length; i++)
                {
                    System.out.print(Board.grid[squaresOrder[10 * (row / 45)][i]].getSprite()[row % 45 - 1] + "|");
                }
                 
                if(row > 45 && row < 19 + info.size())
                {
                    indent();
                    if(info.get(row-19).length() == 38)     System.out.print(info.get(row-19));
                    else
                    {
                        System.out.print("| " + info.get(row-19));
                        for(int x = 0; x < 35 - info.get(row-19).length(); x++)     System.out.print(" ");
                        System.out.print("|");
                    }
                }
                if(row == 19 + info.size())
                {
                    indent();
                    System.out.print("|------------------------------------|");
                }
            }
            else
            {
                if(row % 4 == 1)
                {
                    System.out.print("--------------------");
                    System.out.print(Board.centreSprite[row - 10]);
                    System.out.print("--------------------");
                }
                else
                {
                    System.out.print("|" + Board.grid[squaresOrder[(row - 10) / 4 + 1][0]].getSprite()[(row - 10) % 4] + "|");
                    System.out.print(Board.centreSprite[row - 10] + "|");
                    System.out.print(Board.grid[squaresOrder[(row - 10) / 4 + 1][1]].getSprite()[(row - 10) % 4] + "|");
                }
                  
                if(row >= 19 && row < 25)
                {
                    indent();
                    System.out.print(info.get(row-19));
                }
                else if(row >= 25 && row < 19 + info.size())
                {
                    indent();
                    if(info.get(row-19).length() == 38)     System.out.print(info.get(row-19));
                    else
                    {
                        System.out.print("| " + info.get(row-19));
                        for(int x = 0; x < 35 - info.get(row-19).length(); x++)     System.out.print(" ");
                        System.out.print("|");
                    }
                }
                if(row == 19 + info.size())
                {
                    indent();
                    System.out.print("|------------------------------------|");
                }
            }
        }
    }
      
    /**
     * prints three lines of output to the terminal, with proper spacing
     * @param str1 the first line of information to be printed
     * @param str2 the second line of such information
     * @param str3 the third line of such information
     */
    public static void setup(String str1, String str2, String str3)
    {
        //Different window open with an auction UI
        System.out.println("\f");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("                                                                           "+str1);
        System.out.println("                                                                           "+str2);
        System.out.println("                                                                           "+str3);
    }
    
    /**
     * prints three lines of output to the terminal, with proper spacing
     * @param str1 the first line of information to be printed
     * @param str2 the second line of such information
     * @param str3 the third line of such information
     */
    public static void endgame(Player winner)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("\f");
        String plural = "ies";
        if(winner.getOwned().size() == 1) plural = "y";
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("                                                                           With $" + winner.getBalance() + " and " + winner.getOwned().size() + " propert" + plural + " to their name...");
        System.out.println("                                                                           " + winner.getName() + " has won the game! Congratulations!");
        System.out.println("                                                                           Press enter to return to the title screen.");
        sc.nextLine();
    }
 
    /**
     * To be used when choosing a property to mortgage, build, or perform some other action on. Prints the board and relevant information
     * to the terminal while only dispaying information for those Squares which the active player owns and can currently mortgage.
     * @param str1 the first line of information to be printed beside the board
     * @param str2 the second line of such information
     * @param str3 the third line of such information
     * @param arr an ArrayList<Integer> of indices of Board.grid which point to Squares that the player owns and can mortgage / build on / sell, etc.
     */
    public static void selectProps(String str1, String str2, String str3, ArrayList<Integer> arr) //Works
    {
        //Shows which spaces on the board are mortgage-able
        //Print the board with only the squares listed in mortgagable 'highlighted'
        System.out.print("\f");
        ArrayList<String> info = new ArrayList<String>();
        ArrayList<Player> PList = Player.getPList();
        info.add(str1);
        info.add(str2);
        info.add(str3);
 
        for(int row = 0; row < 55; row++)
        {
            if(row != 0)    System.out.println();
            indent();
            if(row == 0 || row == 9 || row == 45 || row == 54)
            {
                System.out.print("---------------------------------------------------------------------------------------------------------------------------------");
            }
            else if(row < 9 || row > 45)
            {
                System.out.print("|");
                  
                for(int i = 0; i < squaresOrder[0].length; i++)
                {
                    if (isMember(squaresOrder[10 * (row / 45)][i],arr)) //I added this if statement
                    {
                        System.out.print(Board.grid[squaresOrder[10 * (row / 45)][i]].getSprite()[row % 45 - 1] + "|");
                    }
                    else
                    {
                        if (i == 0 || i == 10)
                        {
                            System.out.print("                  |");
                        }
                        else
                        {
                            System.out.print("         |");
                        }
                    }
                }
            }
            else
            {
                if(row % 4 == 1)
                {
                    System.out.print("--------------------");
                    System.out.print(Board.centreSprite[row - 10]);
                    System.out.print("--------------------");
                }
                else
                {
                    //System.out.print("|" + Board.grid[squaresOrder[(row - 10) / 4 + 1][0]].getSprite()[(row - 10) % 4] + "|");
                    if (isMember(squaresOrder[(row - 10) / 4 + 1][0],arr))
                    {
                        System.out.print("|"+Board.grid[squaresOrder[(row - 10) / 4 + 1][0]].getSprite()[(row - 10) % 4] + "|");
                    }
                    else
                    {
                        System.out.print("|                  |");
                    }
                    //System.out.print("|                  |"); //test
                     
                    System.out.print(Board.centreSprite[row - 10] + "|");
                     
                    if (isMember(squaresOrder[(row - 10) / 4 + 1][1],arr))
                    {
                        System.out.print(Board.grid[squaresOrder[(row - 10) / 4 + 1][1]].getSprite()[(row - 10) % 4] + "|");
                    }
                    else
                    {
                        System.out.print("                  |");
                    }
                     
                    //System.out.print(Board.grid[squaresOrder[(row - 10) / 4 + 1][1]].getSprite()[(row - 10) % 4] + "|");
                }
                  
                if(row >= 19 && row < 25) //&& row-19 < info.size()?
                {
                    indent();
                    if (row-19 < info.size())
                    {
                        System.out.print(info.get(row-19));
                    }
                }
                else if(row >= 25 && row < 19 + info.size())
                {
                    indent();
                    if(info.get(row-19).length() == 38)     System.out.print(info.get(row-19));
                    else
                    {
                        System.out.print("| " + info.get(row-19));
                        for(int x = 0; x < 35 - info.get(row-19).length(); x++)     System.out.print(" ");
                        System.out.print("|");
                    }
                }
            }
        }
    }
     
    /**
     * Returns true if the given number is a member of the given ArrayList.
     * @param num the number being searched for
     * @param arr the ArrayList being searched
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
     * Prints the intro border to the screen.
     */
    public static void intro()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("\f");
        System.out.println("|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");     
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                        Resize your terminal window to these dimensions.                                                                             |");
        System.out.println("|                                                            You may need to change your font size in BlueJ's Options->Preferences.                                                                   |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                   Press enter to continue...                                                                                        |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                   Northernopoly created by:                                                                                         |");
        System.out.println("|                                                                                       Henry Curtis-Dyck                                                                                             |");
        System.out.println("|                                                                                          Will Gotlib                                                                                                |");
        System.out.println("|                                                                                          Erik Kreem                                                                                                 |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");  
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|                                                                                                                                                                                                     |");
        System.out.println("|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        sc.nextLine();
    }
      
    /**
     * Takes a String and, if it is multiple words, separates it into two Strings of the most equal length possible, returning a String array of the two halves
     * @param str the string that is to be cut in half
     */
    public static String[] cutString(String str) // 
    {
        String[] words = str.split(" ");
        int letterCount1 = 0; // the tally of letters of words up to or just exceeding half of the original string's length, beginning at the start of the string
        int letterCount2 = 0; // the tally of letters of words up to or just exceeding half of the original string's length, beginning at the end of the string
        int index1 = 0; // the index of the word that pushed the letter tally up to or past half of the string's length, beginning at the start of the string
        int index2 = 0; // the index of the word that pushed the letter tally up to or past half of the string's length, beginning at the end of the string
 
        if(words.length < 2) // the original string is either one word long or empty
        {
            return words;
        }
 
        if(words.length == 2) // the original string is two words long
        {
            if(words[0].length() == words[1].length())
            {
                return words;
            }
            String newStr1 = words[0];
            String newStr2 = words[1];
            if(newStr1.length() > newStr2.length())
            {
                while(newStr1.length() > newStr2.length())
                {
                    newStr2 += " ";
                }
            }
            else
            {
                while(newStr1.length() < newStr2.length())
                {
                    newStr1 += " ";
                }
            }
        }
 
        String newStr1 = words[0]; // prevents a fencepost error
        String newStr2;
 
        for(int i = 0; letterCount1 < str.length() / 2; i++) // counts the letters in the words until letterCount reaches half length of the original string
        {
            letterCount1 += words[i].length() + 1; // plus 1 accounts for the space between the words 
            index1 = i;
        }
        for(int i = words.length - 1; letterCount2 < str.length() / 2; i--) // same thing but starting from the end of the array
        {
            letterCount2 += words[i].length() + 1; // plus 1 accounts for the space between the words 
            index2 = i;
        }
 
        if(letterCount1 - str.length() / 2 <= letterCount2 - str.length() / 2) // if the letter tally starting from the first word is closer to the midpoint than the letter tally starting
        // from the last word, or if the tallies are the same
        {
            for(int i = 1; i <= index1; i++) // adds words to the first string up to and including the index that pushed the letter tally past the midpoint when beginning from the first word
            {
                newStr1 += " " + words[i];
            }
            newStr2 = words[index1 + 1]; // prevents a fencepost error
            for(int i = index1 + 2; i < words.length; i++) // adds the rest of the words to the second string
            {
                newStr2 += " " + words[i];
            }
        }
        else // if the letter tally starting from the last word is closer to the midpoint than the letter tally starting from the first word
        {
            for(int i = 1; i < index2; i++) // adds words to the first string up to but not including the index that pushed the letter tally past the midpoint when beginning from the last word
            {
                newStr1 += " " + words[i];
            }
            newStr2 = words[index2]; // prevents a fencepost error
            for(int i = index2 + 1; i < words.length; i++) // adds the rest of the words to the second string
            {
                newStr2 += " " + words[i];
            }
        }
 
        String[] stringCut = new String[] {newStr1, newStr2};
        return stringCut;
    }
      
    /**
     * Prints six spaces to the screen.
     */
    public static void indent()
    {
        System.out.print("      ");
    }
}