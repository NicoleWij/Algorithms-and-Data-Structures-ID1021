/**
 * Program was created to compare the execution times of quicksort where the first element in each sub-array is selected as partitioning
 * element to that of quicksort with median of three partitioning.
 * 
 * What I found was that the execution time with median of three partitioning was faster. This is shown in the attached pdf document with graphs.
 * 
 * Created: 26th of September 2021
 * @author Nicole Wijkman
 */

public class Assignment6 {
    /**
     * Function that takes in an array, sorts the elements around its pivot point and then returns the pivot point.
     * 
     * @param a the array to be sorted
     * @param right the right index of the array
     * @param left the left index of the array
     * @return the pivot position, which is going to be the element most to the right
     */
    public static int quickSortInner(int[] a, int right, int left){
        int pivot = a[left];
        int j = left + 1;

        for(int i = left + 1; i <= right; i++){
            if(a[i] <= pivot){
                int temp = a[j];
                a[j] = a[i];
                a[i] = temp;
                j++;
            }
        }

        int temp = a[j - 1];
        a[j - 1] = a[left];
        a[left] = temp;

        return j - 1;
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
     * Function that looks at the first, middle and last elements of the array, then choses the median
     * of those elements. The median is appointed pivot index and moved to the middle of the array.
     * 
     * The function then sorts the elements around the array, putting those that are larger on the right
     * side of it and those that are smaller on the left side of it.
     * 
     * @param a the array to sort
     * @param right the highest index of the array
     * @param left the lowest index of the array
     * @return the index of the pivot point
     */
    public static int medianOfThreeInner(int[] a, int right, int left){
        int mid = (right + left)/2;
        int pivotIndex = median3(a, right, left, mid);
        int pivot = a[pivotIndex];
        int j = left;

        swap(a, mid, pivotIndex);

        if(a[left] > a[right]){
            swap(a, left, right);
        }

        swap(a, mid, right - 1);
        pivotIndex = right - 1;

        for(int i = left; i < right - 1; i++){
            if(a[i] < pivot){
                swap(a, i, j);
                j++;
            }
        }

        swap(a, j, right - 1);

        return j;
    }    

    /**
     * Function that swaps two elements in an array.
     * 
     * @param array the array with elements to be swapped
     * @param in1 the index of element 1
     * @param in2 the index of element 2
     */
    public static void swap(int[] array, int in1, int in2) {
        int temp = array[in1];
        array[in1] = array[in2];
        array[in2] = temp;
    }

    /**
     * Function that recursively calls back to itself to split the array into two arrays on both sides of the pivot.
     * Keeps doing this until everything is sorted.
     * 
     * @param a the array to be sorted
     * @param right the right index of the array
     * @param left the left index of the array
     */
    public static void medianOfThreeCalled(int[] a, int right, int left){
        if(left < right){
            int pivot = medianOfThreeInner(a, right, left);
            medianOfThreeCalled(a, pivot - 1, left);
            medianOfThreeCalled(a, right, pivot + 1);
        }
    }

    /**
     * Function that checks the median between three values in an array
     * 
     * @param a the array with the values
     * @param i the index of value 1
     * @param j the index of value 2
     * @param k the index of value 3
     * @return the index at which the median is
     */
    private static int median3(int[] a, int i, int j, int k) {
        return (less(a[i], a[j]) ?
               (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
               (less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
    }

    /**
     * Function that checks if v is less than w
     * 
     * @param v the value that's supposed to be smaller
     * @param w the value that's supposed to be bigger
     * @return returns true if v is smaller than w
     */
    private static boolean less(double v, double w) {
        return v < w;
    }

    /**
     * Prepares the array, splits it up in right and left so that doesn't have to be done in main.
     * 
     * @param a the array to be sorted
     */
    public static void medianOfThree(int[] a){
        medianOfThreeCalled(a, a.length - 1, 0);
    }

    /**
     * Function that checks if an array is sorted.
     * 
     * @param array the array to check
     * @return true or false depending on if the array is sorted or not
     */
    public static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) if (array[i] > array[i + 1]) return false;
        return true;
    }

    /**
     * Creates a randomized array in varying sizes that you can change by changing the testAmount, potence
     * and offset. Tests the sorting algorithms with this array and times it, giving a result in nanoseconds.
     * Also prints out the sorted array.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int testAmount = 10000;
        int potence = 1; // 10 ^ potence
        int offset = 3; // if you want 50, 500, 700 etc.

        int[] array = new int[(int)Math.pow(10, potence) * offset];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * 100);
        }
        int[] arrayCopy = array.clone();

        System.out.print("\nArray: ");

        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }

        System.out.println("\n");

        // quick sort with left element as pivot
        long startTime = System.nanoTime();

        for(int i = 0; i < testAmount; i++){
            quickSort(array.clone());
        }

        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime)/testAmount;
        System.out.println("\nQuick sort with first element as pivot point: " + totalTime + " nano seconds");

        System.out.print("Sorted array: ");

        quickSort(array);

        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }

        System.out.println("\nIs sorted: " + isSorted(array));

        // quick sort with middle element as pivot
        startTime = System.nanoTime();

        for(int i = 0; i < testAmount; i++){
            medianOfThree(arrayCopy.clone());
        }

        endTime = System.nanoTime();
        totalTime = (endTime - startTime)/testAmount;
        System.out.println("\n\nQuick sort with median of three: " + totalTime + " nano seconds");

        System.out.print("Sorted array: ");

        medianOfThree(arrayCopy);

        for(int i = 0; i < array.length; i++){
            System.out.print(arrayCopy[i] + " ");
        }

        System.out.println("\nIs sorted: " + isSorted(arrayCopy));

        System.out.println("\n");
    }
}
