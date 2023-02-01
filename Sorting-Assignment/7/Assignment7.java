/**
 * Program that augments the test code from assignmetn 1 and 2 so that the array is sorted in descending
 * order. To do this, the input from the user is added but negative ( - input ), then when the array is sorted
 * the most negative will be sorted as the smallest. The array is then traversed once after it has been sorted
 * and a[i] becomes -a[i] so that the negative element becomes positive again. This way it is sorted in
 * descending order.
 * 
 * Created: 26th of September 2021
 * @author Nicole Wijkman
 */

import java.util.Scanner;

public class Assignment7 {
    /**
     * Rearranges the array in ascending order.
     * 
     * @param size the size of the array
     * @param a the array to be sorted
     */
    public static void insertionSort(int size, int[] a) {

        for (int i = 1; i < size; i++) {
            int j = i - 1;
            int temp = a[i];

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
    public static void countPrintInversions(int size, int[] input){
        int[] a = input.clone();
        int numberOfInversions = 0;

        System.out.print("Your array: ");

        for(int i = 0; i < a.length; i++){
            System.out.print(a[i] + " ");
        }

        System.out.println("\n");

        for (int i = 1; i < size; i++) {
            int j = i - 1;
            int temp = a[i];

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
     * Main function which lets the user write in numbers that will be sorted in descending order.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Test with the numbers [1, 2, 5, 3, 4, 0]");
        System.out.print("Enter the size of the array: ");
        int size = in.nextInt();
        String temp = in.nextLine(); //catches the \n

        int[] inputArray = new int[size];
        
        System.out.println("Enter your numbers: ");
        
        for(int i = 0; i < size; i++){
            inputArray[i] = -in.nextInt();
        }
        
        insertionSort(inputArray.length, inputArray);

        for(int i = 0; i < size; i++){
            inputArray[i] = -inputArray[i];
        }

        System.out.println("\nThe sorted array: ");

        for(int i = 0; i < size; i++){
            System.out.print(inputArray[i] + " ");
        }

        in.close();
    }
}
