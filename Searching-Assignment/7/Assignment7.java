/**
 *  This program, based on a Binary Search tree, reads the first two hundred words of the text and allows the user
 *  to chose whether to print them in alphabetic order or reverse alphabetic order. The printing of the content is
 *  done in a recursive function which ensures the time complexity of the printing to be O(N) (where N is the number
 *  of different words in the text) as well as the memory complexity to be O(log(N)) assuming the tree is balanced.
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

public class Assignment7 {
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
         * @param x The current node
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
         * Function which change key's value to val if key in subtree rooted at x, otherwise it adds a new 
         * node to subtree associating key with val.
         * 
         * @param x The current node
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
         * Through the other select funtion, this function finds the node containing key of rank k and returns it.
         * 
         * @param k the rank of the key
         * @return the key of the node containing key of rank k
         */
        public Key select(int k) {
            return select(root, k).key;
        }

        /**
         * This function returns a node containing key of rank k. If the node is null, it returns null, otherwise it checks
         * the size of x.left. It recursively goes down the tree to find the correct node and return it.
         * 
         * @param x The node that's going to be selected
         * @param k The rank of the key
         * @return a node containing key of rank k
         */
        private Node select(Node x, int k) { // Return Node containing key of rank k.
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

        /**
         * Function which calls printOrdered and sends in the root
         */
        public void printNormal(){
            printOrdered(root);
        }

        /**
         * If the current node is empty, return null. Otherwise, recursively go down through the tree to the left and print
         * the smallest element first.
         * 
         * @param node The current node
         */
        public void printOrdered(Node node){
            if(node == null){
                return;
            }
            printOrdered(node.left);
            System.out.println(node.key);
            printOrdered(node.right);
        }

        /**
         * Function which calls printReverse and sends in the root
         */
        public void printReverse(){
            printReverseOrdered(root);
        }

        /**
         * If the current node is empty, return null. Otherwise, recursively go down through the tree to the right and print
         * the biggest element first.
         * 
         * @param node The current node
         */
        public void printReverseOrdered(Node node){
            if(node == null){
                return;
            }
            printReverseOrdered(node.right);
            System.out.println(node.key);
            printReverseOrdered(node.left);
        }
    }

    /**
     * The frequency counter is used to test the program
     */
    public static class frequencyCounter {
        /**
         * Main function creates a new BST, reads in the text and adds the text to the BST.
         * 
         * @param args the input to the program
         */

        public static void main(String[] args) {
            int maxLength = 200;
            int wordCount = 0;
            int i;
            BST<String, Integer> a = new BST<String, Integer>();
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

            System.out.println("1: Print in alphabetic order");
            System.out.println("2: Print in reverse alphabetic order");
            System.out.print("\nEnter your case: ");

            Scanner scan = new Scanner(System.in);
            int input = scan.nextInt();
            String temp = scan.nextLine();

            switch (input) {
                case 1:
                    a.printNormal();
                    break;
                case 2:
                    a.printReverse();
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }
}