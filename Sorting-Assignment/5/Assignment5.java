/**
 * Program was created to experiment with the cut-off to insertionsort in mergesort. A suitable range for cut-off values to test with could be [0-30].
 * 
 * What I noticed when I did this task was that insertionsort helped make the program fast until the cut off reached about 15, which is when it
 * got a bit slower. On smaller arrays a change could be seen as early as 7 as cut off. This is shown in the attached pdf document with graphs.
 * 
 * Created: 24th of September 2021
 * @author Nicole Wijkman
 */

public class Assignment5 {

    /**
     * Rearranges the array in ascending order and prints it.
     * 
     * @param size the size of the array
     * @param a the array to be sorted
     */
    public static void insertionSort(int[] a, int size) {
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
     * Rearranges the array in ascending order by the use of merge sort. Is a recursive function that
     * splits the array until it consists of single elements, that are then grouped together and sorted.
     * 
     * When the arrays have the length of cut off or smaller, the function goes into insertionsort instead of
     * continuing with merge sort.
     * 
     * @param a the array to be sorted
     * @param size the size of the array
     * @param cutOff the cut off at which the function splits into insertionsort
     * @return the sorted array
     */
    public static int[] mergeSort(int[] a, int size, int cutOff){
        if(size == 1){
            return a;
        }

        int middle = size/2;
        int left = middle;
        int right = size - middle;

        int leftArray[] = new int[middle];
        int rightArray[] = new int[size - middle];

        for(int i = 0; i < middle; i++){
            leftArray[i] = a[i];
        }

        for(int i = middle; i < size; i++){
            rightArray[i - middle] = a[i];
        }

        leftArray = mergeSort(leftArray, left, cutOff);
        rightArray = mergeSort(rightArray, right, cutOff);

        if(left <= cutOff || right <= cutOff){
            return mergeWithInsertion(rightArray, leftArray);
        }

        return merge(rightArray, leftArray);
    }

    /**
     * Sorts the elements and merges the two arrays.
     * 
     * @param rightArray the first array to be merged
     * @param leftArray the second array to be merged
     * @return the merged array
     */
    public static int[] merge(int[] rightArray, int[] leftArray){
        int left = leftArray.length;
        int right = rightArray.length;
        int[] mergedArray = new int[left + right];

        int i = 0;
        int j = 0;
        int k = 0;

        while(i < left && j < right){
            while (j != right && rightArray[j] < leftArray[i]){
                mergedArray[k] = rightArray[j];
                j++;
                k++;
            }

            mergedArray[k] = leftArray[i];
            i++;
            k++;
        }

        while(i < left){
            mergedArray[k] = leftArray[i];
            i++;
            k++;            
        }

        while(j < right){
            mergedArray[k] = rightArray[j];
            j++;
            k++;
        }

        return mergedArray;
    }

    /**
     * Merges the two arrays into a new one, then sorts them with insertion sort.
     * 
     * @param rightArray the first array to be merged
     * @param leftArray the second array to be merged
     * @return the merged array
     */
    public static int[] mergeWithInsertion(int[] rightArray, int[] leftArray){
        int left = leftArray.length;
        int right = rightArray.length;
        int i = 0;
        int j = 0;
        int[] mergedArray = new int[left + right];

        while(i < left){
            mergedArray[i] = leftArray[i];
            i++;
        }

        while(i < left + right){
            mergedArray[i] = rightArray[j];
            i++;
            j++;
        }

        insertionSort(mergedArray, mergedArray.length);

        return mergedArray;
    }

    /**
     * Creates a randomized array with the length 30. Also creates a variable for the cut off, which is sent into
     * the merge sort function. Tests the sorting algorithms with the array and cut off and times it, giving a result
     * in nanoseconds. It also prints out both the unsorted and sorted array.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int testAmount = 10000;
        int[] array = new int[1000];
        int cutOff = 0;

        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * 100);
        }

        System.out.print("\nArray: ");

        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }

        System.out.println("\n");

        for(int i = 0; i <= 30; i++){
            cutOff = i;
            long startTime = System.nanoTime();

            for(int j = 0; j < testAmount; j++){
                mergeSort(array, array.length, cutOff);
            }
    
            long endTime = System.nanoTime();
            long totalTime = (endTime - startTime)/testAmount;
            System.out.println("Time for " + cutOff + " cut offs: " + totalTime);
        }

        System.out.println();
        int[] sorted = mergeSort(array, array.length, cutOff);

        System.out.print("Sorted array: ");

        for(int i = 0; i < array.length; i++){
            System.out.print(sorted[i] + " ");
        }

        System.out.println("\n");
    }
}
