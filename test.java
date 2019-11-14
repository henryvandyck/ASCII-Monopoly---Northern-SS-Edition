
public class test
{
    // instance variables - replace the example below with your own


    public static void test()
    {
        char[] yeet = new char[5];
        yeet[0] = 'a';
        yeet[1] = 'b';
        yeet[2] = 'c';
        yeet[3] = (char)(15);
        yeet[4] = 'd';
        printchArr(yeet);
    }

    public static void printchArr(char[] arr)
    {
        for (int i = 0; i < arr.length; i++)
        {
            System.out.println(arr[i]);
        }
    }
}
