/**
 *  
 *  The program was created to reverse a String input by the user recursively, based on an assignment given in the KTH course
 *  ID1021.
 * 
 *  The user uses the program by entering a string, which will be sent into a function that creates a substring from the 1:st
 *  index in the string and then calls itself. When the string is empty, it stops to return to the previous reursive call and
 *  prints the character, which will then be the last character. This will print the entire string in reverse.
 * 
 *  The use of substring is a good way to handle the recursive solution, since the string keeps getting shorter with each
 *  recursive call. CharAt can then be used to print every character in reverse in an easy way.
 * 
 *  This reverse function is being called recursively n times before reaching the base case so its time complexity is linear. 
 *  This means the time complexety is O(n). The memory complexety is determined by the number of return statements, since each
 *  function call will be stored on the program stack. Since there will be n return statements, the memory complexety will be O(n).
 * 
 *  Created: 11th of September 2021
 *  @author Nicole Wijkman
 * 
 */

import java.util.Scanner;

public class Assignment_2_Recursive{
    
    /** 
     * Function that will run the program, handle the input and call the recursive function.
     * 
     * @param args the input of the program
     */
    public static void main(String[] args) {
        System.out.print("Enter a word: ");
        Scanner in = new Scanner(System.in); //System.in is a standard input stream
        String text = in.nextLine(); //reads the string that the user writes
        in.close(); //closes the scanner

        System.out.print("Reversed word: ");
        reverse(text); //enters the function reverse

        System.out.println(); //prints an empty line at the end
    }

    
    /** 
     * Recursive function that takes the input string and prints it in reverse
     * 
     * @param tex the string input by the user
     */
    public static void reverse(String tex){
        //if the string is empty, which means it's at its end
        //it returns back to the last character to print it out
        if(tex.length() <= 0){
            return;
        }

        //extracts a substring from the given string at index 1, that is sent into the reverse function again
        //index one is chosen because the second letter in the word is to be sent into reverse
        reverse(tex.substring(1));
        System.out.print(tex.charAt(0)); //prints the character at index 0 in the String
    }
}