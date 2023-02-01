/**
 * Program was made to compare the execution times for sorting large arrays of integers with insertionsort, mergesort and quicksort.
 * 
 * The amount of tests used were based on the size of the array. For smaller arrays, I had to test a lot more than for
 * very big arrays. The reason for this is that for small arrays the execution time was so short that the time it took
 * for the program to go into the function which measures time played a significant role in the end result. By using more
 * tests for the small arrays, this problem was combated.
 * 
 * The results from the comparisons show how slow insertion sort becomes when the arrays get big. Based on this, one
 * should avoid to use insertion sort for arrays with a number of elements over 50, which is where it really took of in
 * my testing.
 * 
 * Quick sort stayed continously fast up until I reached arrays with around 50 000 elements. At that point merge sort
 * took the lead and stayed continously the fastest up to my last test with 1 000 000 elements.
 * 
 * All results are shown in the attached pdf document with graphs.
 * 
 * Created: 24th of September 2021
 * @author Nicole Wijkman
 * 
 */

public class Assignment4 {
    /**
     * Rearranges the array in ascending order by the use of insertion sort.
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
     * Rearranges the array in ascending order by the use of merge sort. Is a recursive function that
     * splits the array until it consists of single elements, that are then grouped together and sorted.
     * 
     * @param a the array to be sorted
     * @param size the size of the array
     * @return the sorted array
     */
    public static int[] mergeSort(int[] a, int size){
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

        leftArray = mergeSort(leftArray, left);
        rightArray = mergeSort(rightArray, right);

        int[] mergedArray = new int[size];

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
     * Function that takes in an array, sorts the elements around its pivot point and then returns the pivot point.
     * 
     * @param a the array to be sorted
     * @param right the right index of the array
     * @param left the left index of the array
     * @return the pivot position, which is going to be the element most to the right
     */
    public static int quickSortInner(int[] a, int right, int left){
        int pivotIndex = a[right];
        int j = left;

        for(int i = left; i < right; i++){
            if(a[i] <= pivotIndex){
                int temp = a[j];
                a[j] = a[i];
                a[i] = temp;
                j++;
            }
        }

        int temp = a[j];
        a[j] = pivotIndex;
        a[right] = temp;

        return j;
    }

    /**
     * Function that recursively calls back to itself to split the array into two arrays on both sides of the pivot.
     * Keeps doing this until everything is sorted.
     * 
     * @param a the array to be sorted
     * @param right the right index of the array
     * @param left the left index of the array
     */
    public static void quickSortCalled(int[] a, int right, int left){
        if(left < right){
            int pivot = quickSortInner(a, right, left);
            quickSortCalled(a, pivot - 1, left);
            quickSortCalled(a, right, pivot + 1);
        }
    }

    /**
     * Prepares the array, splits it up in right and left so that doesn't have to be done in main.
     * 
     * @param a the array to be sorted
     */
    public static void quickSort(int[] a){
        quickSortCalled(a, a.length - 1, 0);
    }

    /**
     * Creates a randomized array in varying sizes that you can change by changing the testAmount, potence
     * and offset. Tests the sorting algorithms with this array and times it, giving a result in nanoseconds.
     * Also prints out the sorted array.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int testAmount = 1000;
        int potence = 1; // 10 ^ potence
        int offset = 1; // if you want 50, 500, 700 etc.

        int[] array = new int[(int)Math.pow(10, potence) * offset];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * 100);
        }

        int size = array.length;

        // insertion sort
        long startTime = System.nanoTime();

        for(int i = 0; i < testAmount; i++){
            insertionSort(size, array.clone());
        }

        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime)/testAmount;
        System.out.println("\nInsertion sort: " + totalTime + " nanoseconds");

        // merge sort
        startTime = System.nanoTime();

        for(int i = 0; i < testAmount; i++){
            mergeSort(array.clone(), size);
        }

        endTime = System.nanoTime();
        totalTime = (endTime - startTime)/testAmount;
        System.out.println("Merge sort: " + totalTime + " nanoseconds");

        // quick sort
        startTime = System.nanoTime();

        for(int i = 0; i < testAmount; i++){
            quickSort(array.clone());
        }

        endTime = System.nanoTime();
        totalTime = (endTime - startTime)/testAmount;
        System.out.println("Quick sort: " + totalTime + " nanoseconds\n");
    }
}
