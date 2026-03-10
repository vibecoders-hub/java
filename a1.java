import java.util.Scanner;

enum Ones { ZERO(0, "Zero"), ONE(1, "One"), TWO(2, "Two"), 
THREE(3, "Three"), FOUR(4, "Four"), FIVE(5, "Five"), SIX(6, "Six"), 
SEVEN(7, "Seven"), EIGHT(8, "Eight"), NINE(9, "Nine"), TEN(10, "Ten"), ELEVEN(11, "Eleven"), TWELVE(12, "Twelve"), THIRTEEN(13, "Thirteen"), FOURTEEN(14, "Fourteen"), 
FIFTEEN(15, "Fifteen"), SIXTEEN(16, "Sixteen"), SEVENTEEN(17, "Seventeen"), 
EIGHTEEN(18, "Eighteen"), NINETEEN(19, "Nineteen");
private int value=0;
private String word="";

Ones(int value, String word) 
{
this.value = value;
this.word = word;
}
public int getValue() 
{
return value;
}
public String getWord()
 {
return word;
}
public static String getWordByValue(int number) 
{
for (Ones o : Ones.values()) 
{
if (o.getValue() == number) 
{
return o.getWord();
}
}
return "";
}
}

enum Tens { TWENTY(20, "Twenty"), THIRTY(30, "Thirty"), FORTY(40, "Forty"),
FIFTY(50, "Fifty"), SIXTY(60, "Sixty"), SEVENTY(70, "Seventy"), 
EIGHTY(80, "Eighty"), NINETY(90, "Ninety");
private int value=0;
private String word="";

Tens(int value, String word) {
this.value = value;
this.word = word;
}
public int getValue() 
{
return value;
}
public String getWord() 
{
return word;
}
public static String getWordByValue(int number) 
{
for (Tens t : Tens.values()) 
{
if (t.getValue() == number) 
{
return t.getWord();
}
}
return "";
}
}
public class PartA1_Enum 
{
public static String convert(int number) {
    if (number == 0)
        return Ones.ZERO.getWord();
    String result = "";
    
    if (number >= 1000) 
    {
        result += convert(number / 1000) + " Thousand ";
        number %= 1000;
    }

    if (number >= 100) 
    {
        result += Ones.getWordByValue(number / 100) + " Hundred ";
        number %= 100;
    }
    
    if (number >= 20) 
    {
        result += Tens.getWordByValue((number / 10) * 10) + " ";
        number %= 10;
    }
    
    if (number > 0) 
    {
        result += Ones.getWordByValue(number);
    }

    return result;
}

public static void main(String[] args) 
{
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter a number (0 - 99999): ");
    
    int num = sc.nextInt();
    if (num < 0 || num > 99999)
        System.out.println("Number out of range!");
        else    
        System.out.println("In words: " + convert(num));
    
    sc.close();
}
}

