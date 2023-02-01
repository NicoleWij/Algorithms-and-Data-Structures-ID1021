/**
 *  In this program, the first thousand words from the text are used to measure the running time of the ordered array ST. 
 *  The frequency counter from page 372 is used as a test program.
 * 
 *  All imported code taken from:
 *  https://algs4.cs.princeton.edu/home/
 *  Algorithms Fourth Edition, Robert Sedgewick Kevin Wayne
 * 
 *  Created: 4th of October 2021
 *  @author Nicole Wijkman
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Assignment2 {
    public static class BinarySearchST<Key extends Comparable<Key>, Value> {
        private Key[] keys;
        private Value[] vals;
        private int size;

        /**
         * Constructor for the two arrays in BinarySearchST
         * 
         * @param capacity the capacity of the arrays
         */
        public BinarySearchST(int capacity) {
            keys = (Key[]) new Comparable[capacity];
            vals = (Value[]) new Object[capacity];
        }

        /**
         * Returns the current size of the arrays.
         * 
         * @return the size.
         */
        public int size() {
            return size;
        }

        /**
         * Checks through the function get if the key exists in the keys array.
         * 
         * @param hi The key to check for
         * @return true or false depending on if the key exists or not.
         */
        private boolean contains(Key hi) {
            return get(hi) != null;
        }

        /**
         * This function returns the key at the index sent into the function.
         * 
         * @param k The index at which key to get
         * @return The key at the index sent in
         */
        public Key select(int k) {
            return keys[k];
        }

        /**
         * This function uses rank to find the index of the value to get, which is then returned to the function that
         * called get. If the index is empty or for some reason has the wrong key, it returns null.
         * 
         * @param key The key relevant to the value
         * @return The corresponding value to the key
         */
        public Value get(Key key) {
            if (isEmpty())
                return null;
            int i = rank(key);
            if (i < size && keys[i].compareTo(key) == 0)
                return vals[i];
            else
                return null;
        }

        boolean isEmpty() {
            return keys[0] == null;
        }

        /**
         * This function inspects key and as long as lo is smaller than hi, it keeps updating lo, hi and mid.
         * The point of the function is to find the place where key is supposed to be put and return that index
         * to the function that called it.
         * 
         * @param key the key to be put in the array
         * @return the index where key is supposed to be put
         */
        public int rank(Key key) {
            int lo = 0, hi = size - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                int cmp = key.compareTo(keys[mid]);
                if (cmp < 0)
                    hi = mid - 1;
                else if (cmp > 0)
                    lo = mid + 1;
                else
                    return mid;
            }
            return lo;
        }

        /**
         * The function put searches for key and updates value if key is found, but grows the table if key is new.
         * Places the parameters in the keys and vals arrays, as well as increase the size.
         * 
         * @param key The inserted key
         * @param val The inserted value
         */
        public void put(Key key, Value val) {
            int i = rank(key);
            if (i < size && keys[i].compareTo(key) == 0) {
                vals[i] = val;
                return;
            }
            for (int j = size; j > i; j--) {
                keys[j] = keys[j - 1];
                vals[j] = vals[j - 1];
            }
            keys[i] = key;
            vals[i] = val;
            size++;
        }
    }

    /**
     * The frequency counter is used to test the program
     */
    public static class frequencyCounter {
        /**
         * The main function creates a new BinarySearchST, scans and filters through the text and adds the 
         * words to the BinarySearchST.
         * 
         * @param args the input to the program
         */
        public static void main(String[] args) {
            int maxLength = 1000;
            int wordCount = 0;
            int i = 0;
            BinarySearchST<String, Integer> a = new BinarySearchST<String, Integer>(17000);
            Scanner in = null;

            try {
                in = new Scanner(new File(
                        "C:\\Users\\Nicole\\Desktop\\Skola\\Program\\Java\\OOD\\Searching-Assignment-ID1021\\theText.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            long startTime = System.nanoTime();

            outer: while (in.hasNextLine()) {
                Scanner scanned = new Scanner(in.nextLine());
                while (scanned.hasNext()) {
                    String str = scanned.next().toLowerCase();

                    for(i = 0; i < str.length(); i++) {
                        char c = str.charAt(i);
                        if(!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && c != '\n') {
                            str = str.replace(c, ' ');
                        }
                    }

                    str = str.trim();
                    String[] stringArray = str.split(" ");

                    for (String word : stringArray) {
                        if (word.length() > 0) {
                            if (!a.contains(word))
                                a.put(word, 1);
                            else
                                a.put(word, a.get(word) + 1);
                        }

                        wordCount++;
                        if (wordCount >= maxLength)
                            break outer;
                    }
                }
            }

            long endTime = System.nanoTime();
            long totalTime = (endTime - startTime);


            for (i = 0; i < a.size(); i++) {
                System.out.print(a.select(i) + ": ");
                System.out.println(a.get(a.select(i++).toLowerCase()));
            }

            String max = a.select(0);
            for (i = 0; i < a.size(); i++) {
                if (a.get(a.select(i)) > a.get(max)) {
                    max = a.select(i);
                }
            }

            System.out.println("\nTotal execution time: " + totalTime + " nanoseconds");
            System.out.println("The most frequently used word is: '" + max + "', which was used " + a.get(max) + " times.\n");
        }
    }
}