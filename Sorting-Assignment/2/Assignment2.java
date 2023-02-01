/**
 *  The program was created to count the number of inversions in the input array as well as print a list of all inversions on the format 
 *  [i,a[i]], [j, a[j]] where i and j are indices and a[i], a[j] are the values of the elements. The method is called from main() before
 *  the array is sorted.
 * 
 *  In the worst case scenario, which is when the numbers are in the wrong order, the time complexity would be O(n^2). In the best case
 *  scenario however, the second loop would never have to be entered as all of the numbers are already sorted. The time complexity would
 *  thus be O(n). The average is also O(n^2) because if even one of the elements aren't sorted, the second loop has to be entered.
 * 
 *  (With our loop for printing the time complexity will be O(n^3))
 * 
 *  Created: 22nd of September 2021
 *  @author Nicole Wijkman
 */

import java.util.Scanner;

public class Assignment2 {

    /**
     * Rearranges the array in ascending order and prints it.
     * 
     * @param size the size of the array
     * @param a the array to be sorted
     */
    public static void insertionSort(int size, char[] a) {

        for (int i = 1; i < size; i++) {
            int j = i - 1;
            char temp = a[i];

            while(j >= 0 && (temp < a[j])) {
                a[j + 1] = a[j];
                j--;
            }

            a[j + 1] = temp;
        }
    }

    /**
     * Rearranges the array in ascending order, prints it and counts the number of inversions.
     * 
     * @param size the size of the array
     * @param numbers the array to be sorted
     */
    public static void countPrintInversions(int size, char[] input){
        char[] a = input.clone();
        int numberOfInversions = 0;

        System.out.print("Your array: ");

        for(char c : a){
            System.out.print(" " + Character.toString(c) + " ");
        }

        System.out.println("\n");

        for (int i = 1; i < size; i++) {
            int j = i - 1;
            char temp = a[i];

            while(j >= 0 && (temp < a[j])) {
                System.out.println("[" + i + "," + temp + "], [" + j + "," + a[j] + "]");
                a[j + 1] = a[j];

                numberOfInversions++;
                j--;
            }

            a[j + 1] = temp;
        }

        System.out.println("\nNumber of inversions: " + numberOfInversions + "\n");
    }


    /**
     * Reads in a String and an int from standard input; countPrintInversions counts and prints the
     * number of inversions; insertionSort sorts the array; and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Test with the numbers [1, 2, 5, 3, 4, 0]");
        System.out.print("Enter the size of the array: ");
        int size = in.nextInt();
        String temp = in.nextLine(); //catches the \n

        char[] inputArray = new char[size];
        
        System.out.print("Enter a String of numbers: ");
        String input = in.nextLine();

        for(int i = 0; i < size; i++){
            inputArray[i] = input.charAt(i);
        }
        
        in.close();

        System.out.println();
        countPrintInversions(size, inputArray);
        insertionSort(size, inputArray);

        System.out.print("Sorted array: ");

        for(char c : inputArray){
            System.out.print(" " + Character.toString(c) + " ");
        }

        System.out.println("\n");
    }
}