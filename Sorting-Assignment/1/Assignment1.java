/**
 *  The program was created to Augment the sorting process so that all the content of the array that is being sorted is printed
 *  after each inner loop iteration.
 * 
 *  Created: 22nd of September 2021
 *  @author Nicole Wijkman
 */

import java.util.Scanner;

public class Assignment1 {

    /**
     * Rearranges the array in ascending order, prints every change and insertion as well as counting the number of inversions.
     * 
     * @param size the size of the array
     * @param a the array to be sorted
     */
    public static void insertionSort(int size, char[] a) {
        int numberOfSwaps = 0;

        System.out.print("Your array: ");

        for(char c : a){
            System.out.print(" " + Character.toString(c) + " ");
        }

        System.out.println();

        for (int i = 1; i < size; i++) {
            int j = i - 1;
            char temp = a[i];

            System.out.println();

            while(j >= 0 && (temp < a[j])) {
                a[j + 1] = a[j];

                System.out.print("Array after change: ");

                for(char c : a){
                    System.out.print(" " + Character.toString(c) + " ");
                }

                System.out.println();

                j--;
            }

            if(j != i - 1){
                numberOfSwaps++;
            }

            a[j + 1] = temp;

            System.out.print("Array after loop " + i + ": ");

            for(char c : a){
                System.out.print(" " + Character.toString(c) + " ");
            }

            System.out.println();
        }

        System.out.println("\nNumber of swaps: " + numberOfSwaps + "\n");
    }


    /**
     * Reads in a String and an int from standard input; insertionSort sorts the numbers in the string;
     * and prints them to standard output in ascending order.
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
        insertionSort(size, inputArray);

        System.out.print("Sorted array: ");

        for(char c : inputArray){
            System.out.print(" " + Character.toString(c) + " ");
        }

        System.out.println("\n");
    }
}