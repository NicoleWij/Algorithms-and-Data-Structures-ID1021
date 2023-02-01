
/**
 *  In this program it is shown how the built-in hashcode() function for Strings in Java distributes the hashcodes for
 *  the words the text.
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

public class Assignment5 {
    public static class BST<Key extends Comparable<Key>, Value> {
        private Node root; // root of BST

        /**
         * Constructor for the Nodes
         */
        private class Node {
            private Key key; // key
            private Value val; // associated value
            private Node left, right; // links to subtrees
            private int N; // # nodes in subtree rooted here

            public Node(Key key, Value val, int N) {
                this.key = key;
                this.val = val;
                this.N = N;
            }
        }

        /**
         * Returns the current size of the tree by returning the amount of nodes
         * 
         * @return the size
         */
        public int size() {
            return size(root);
        }

        /**
         * If the tree is empty, return 0, otherwise return the size of the tree.
         * 
         * @param x The root
         * @return Size of the tree
         */
        private int size(Node x) {
            if (x == null)
                return 0;
            else
                return x.N;
        }

        /**
         * Function which returns the the value associated with the key sent in
         * 
         * @param key the key associated with the value we want
         * @return the value associated with the key sent in
         */
        public Value get(Key key) {
            return get(root, key);
        }

        /**
         * Function which returns value associated with key in the subtree rooted at x.
         * Returns null if key not present in subtree rooted at x.
         * 
         * @param x   The current node
         * @param key the relevant key
         * @return the value associated with key in the subtree rooted at x
         */
        private Value get(Node x, Key key) {
            if (x == null)
                return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                return get(x.left, key);
            else if (cmp > 0)
                return get(x.right, key);
            else
                return x.val;
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
         * Function that searches for key. Updates value if found, grow table if new.
         * 
         * @param key The relevant key
         * @param val The relevant value
         */
        public void put(Key key, Value val) {
            root = put(root, key, val);
        }

        /**
         * Function which change key's value to val if key in subtree rooted at x,
         * otherwise it adds a new node to subtree associating key with val.
         * 
         * @param x   The current node
         * @param key The relevant key
         * @param val The relevant value
         * @return The node at which
         */
        private Node put(Node x, Key key, Value val) {
            if (x == null)
                return new Node(key, val, 1);
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x.left = put(x.left, key, val);
            else if (cmp > 0)
                x.right = put(x.right, key, val);
            else
                x.val = val;
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }

        /**
         * Through the other select funtion, this function finds the node containing key
         * of rank k and returns it.
         * 
         * @param k the rank of the key
         * @return the key of the node containing key of rank k
         */
        public Key select(int k) {
            return select(root, k).key;
        }

        /**
         * This function returns a node containing key of rank k. If the node is null,
         * it returns null, otherwise it checks the size of x.left. It recursively goes
         * down the tree to find the correct node and return it.
         * 
         * @param x The node that's going to be selected
         * @param k The rank of the key
         * @return a node containing key of rank k
         */
        private Node select(Node x, int k) {
            if (x == null)
                return null;
            int t = size(x.left);
            if (t > k)
                return select(x.left, k);
            else if (t < k)
                return select(x.right, k - t - 1);
            else
                return x;
        }
    }

    /**
     * Function that runs the program and handles the input
     * 
     * @param args the input to the program
     */
    public static void main(String[] args) {
        int wordCount = 0;
        BST<String, Integer> table = new BST<String, Integer>();
        Scanner in = null;

        try {
            in = new Scanner(new File(
                    "C:\\Users\\Nicole\\Desktop\\Skola\\Program\\Java\\OOD\\Searching-Assignment-ID1021\\theText.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        outer: while (in.hasNextLine()) {
            Scanner scanned = new Scanner(in.nextLine());
            while (scanned.hasNext()) {
                String str = scanned.next().toLowerCase();

                for (int i = 0; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && c != '\n') {
                        str = str.replace(c, ' ');
                    }
                }

                str = str.trim();
                String[] stringArray = str.split(" ");

                for (String word : stringArray) {
                    if (word.length() > 0) {
                        if (!table.contains(word)){
                            table.put(word, 1);
                            wordCount++; //counts the amount of different words
                        }else
                            table.put(word, table.get(word) + 1);
                    }
                }
            }
        }

        int[] hashArray = new int[wordCount];

        int max = 0;

        for (int i = 1; i < table.size(); i++) {
            int hash = (table.select(i).hashCode() & 0x7fffffff) % wordCount;
            hashArray[hash]++;

            if (hashArray[hash] > max) {
                max = hashArray[hash];
            }
        }

        for (int i = 0; i < table.size(); i++) {
            int hash = (table.select(i).hashCode() & 0x7fffffff) % wordCount;
            System.out.print(table.select(i) + ": ");
            System.out.println(table.get(table.select(i++).toLowerCase()));
            System.out.println("With the hashcode: " + hash + " that is used " + hashArray[hash] + " times total.\n");
        }

        int countMoreThanOnce = 0;
        int totalHashcodesUsed = 0;

        for (int i : hashArray) {
            if (i > 1) {
                countMoreThanOnce++;
            }
            if (i > 0) {
                totalHashcodesUsed++;
            }
        }

        System.out.println("\nThe amount of hashcodes that were used more than once is " + countMoreThanOnce
                + " out of a total of " + totalHashcodesUsed + " hashcodes.");
        System.out.println("The most repeated hashcode was used " + max + " times.\n");
    }
}
