/**
 *  In this program the user is able to repeatedly ask how many times a word is written in the text. The program is based on
 *  a hash table which uses separate chaining. The program also functions without re-reading the text or re-building the data
 *  structures used. 
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
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Assignment6 {
    public static class Queue<Item> implements Iterable<Item> {
        private Node<Item> first;
        private Node<Item> last;
        private int n;

        /**
         * Constructor for the Node
         */
        private class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Constructor for the queue
         */
        public Queue() {
            first = null;
            last = null;
            n = 0;
        }

        /**
         * Checks if the queue is empty
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Checks the size of the queue
         * 
         * @return the size of the queue
         */
        public int size() {
            return n;
        }

        /**
         * Checks the first item in the queue
         * 
         * @return Returns the first item in the queue
         */
        public Item peek() {
            if (isEmpty())
                throw new NoSuchElementException("Queue underflow");
            return first.item;
        }

        /**
         * Enqueues an item at the end of the queue
         * 
         * @param item the item to enqueue
         */
        public void enqueue(Item item) {
            Node<Item> oldlast = last;
            last = new Node<Item>();
            last.item = item;
            last.next = null;
            if (isEmpty())
                first = last;
            else
                oldlast.next = last;
            n++;
        }

        /**
         * Dequeues the first item in the queue
         * 
         * @return the item that was removed
         */
        public Item dequeue() {
            if (isEmpty())
                throw new NoSuchElementException("Queue underflow");
            Item item = first.item;
            first = first.next;
            n--;
            if (isEmpty())
                last = null;
            return item;
        }

        /**
         * Function that gets and creates an instance of the iterator class
         * 
         * @return the iterator
         */
        public Iterator<Item> iterator() {
            return new LinkedIterator(first);
        }
        /**
         * Blueprint for the iterator
         */
        private class LinkedIterator implements Iterator<Item> {
            private Node<Item> current;

            public LinkedIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext() {
                return current != null;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Item next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }

    public static class SequentialSearchST<Key, Value> {
        private int n; // number of key-value pairs
        private Node first; // the linked list of key-value pairs

        // a helper linked list data type
        private class Node {
            private Key key;
            private Value val;
            private Node next;

            public Node(Key key, Value val, Node next) {
                this.key = key;
                this.val = val;
                this.next = next;
            }
        }

        /**
         * Initializes an empty symbol table.
         */
        public SequentialSearchST() {
        }

        /**
         * Returns the number of key-value pairs in this symbol table.
         *
         * @return the number of key-value pairs in this symbol table
         */
        public int size() {
            return n;
        }

        /**
         * Returns true if this symbol table is empty.
         *
         * @return {@code true} if this symbol table is empty; {@code false} otherwise
         */
        public boolean isEmpty() {
            return size() == 0;
        }

        /**
         * Returns true if this symbol table contains the specified key.
         *
         * @param key the key
         * @return {@code true} if this symbol table contains {@code key}; {@code false}
         *         otherwise
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public boolean contains(Key key) {
            if (key == null)
                throw new IllegalArgumentException("argument to contains() is null");
            return get(key) != null;
        }

        /**
         * Returns the value associated with the given key in this symbol table.
         *
         * @param key the key
         * @return the value associated with the given key if the key is in the symbol
         *         table and {@code null} if the key is not in the symbol table
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public Value get(Key key) {
            if (key == null)
                throw new IllegalArgumentException("argument to get() is null");
            for (Node x = first; x != null; x = x.next) {
                if (key.equals(x.key))
                    return x.val;
            }
            return null;
        }

        /**
         * Inserts the specified key-value pair into the symbol table, overwriting the
         * old value with the new value if the symbol table already contains the
         * specified key. Deletes the specified key (and its associated value) from this
         * symbol table if the specified value is {@code null}.
         *
         * @param key the key
         * @param val the value
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public void put(Key key, Value val) {
            if (key == null)
                throw new IllegalArgumentException("first argument to put() is null");
            if (val == null) {
                delete(key);
                return;
            }

            for (Node x = first; x != null; x = x.next) {
                if (key.equals(x.key)) {
                    x.val = val;
                    return;
                }
            }
            first = new Node(key, val, first);
            n++;
        }

        /**
         * Removes the specified key and its associated value from this symbol table (if
         * the key is in this symbol table).
         *
         * @param key the key
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public void delete(Key key) {
            if (key == null)
                throw new IllegalArgumentException("argument to delete() is null");
            first = delete(first, key);
        }

        // delete key in linked list beginning at Node x
        // warning: function call stack too large if table is large
        private Node delete(Node x, Key key) {
            if (x == null)
                return null;
            if (key.equals(x.key)) {
                n--;
                return x.next;
            }
            x.next = delete(x.next, key);
            return x;
        }

        /**
         * Returns all keys in the symbol table as an {@code Iterable}. To iterate over
         * all of the keys in the symbol table named {@code st}, use the foreach
         * notation: {@code for (Key key : st.keys())}.
         *
         * @return all keys in the symbol table
         */
        public Iterable<Key> keys() {
            Queue<Key> queue = new Queue<Key>();
            for (Node x = first; x != null; x = x.next)
                queue.enqueue(x.key);
            return queue;
        }
    }

    public static class SeparateChainingHashST<Key, Value> {
        private static final int INIT_CAPACITY = 4;

        private int n; // number of key-value pairs
        private int m; // hash table size
        private SequentialSearchST<Key, Value>[] st; // array of linked-list symbol tables

        /**
         * Initializes an empty symbol table.
         */
        public SeparateChainingHashST() {
            this(INIT_CAPACITY);
        }

        /**
         * Initializes an empty symbol table with {@code m} chains.
         * 
         * @param m the initial number of chains
         */
        public SeparateChainingHashST(int m) {
            this.m = m;
            st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
            for (int i = 0; i < m; i++)
                st[i] = new SequentialSearchST<Key, Value>();
        }

        // resize the hash table to have the given number of chains,
        // rehashing all of the keys
        private void resize(int chains) {
            SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
            for (int i = 0; i < m; i++) {
                for (Key key : st[i].keys()) {
                    temp.put(key, st[i].get(key));
                }
            }
            this.m = temp.m;
            this.n = temp.n;
            this.st = temp.st;
        }

        // hash function for keys - returns value between 0 and m-1 (assumes m is a
        // power of 2)
        // (from Java 7 implementation, protects against poor quality hashCode()
        // implementations)
        private int hash(Key key) {
            int h = key.hashCode();
            h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
            return h & (m - 1);
        }

        /**
         * Returns the number of key-value pairs in this symbol table.
         *
         * @return the number of key-value pairs in this symbol table
         */
        public int size() {
            return n;
        }

        /**
         * Returns true if this symbol table is empty.
         *
         * @return {@code true} if this symbol table is empty; {@code false} otherwise
         */
        public boolean isEmpty() {
            return size() == 0;
        }

        /**
         * Returns true if this symbol table contains the specified key.
         *
         * @param key the key
         * @return {@code true} if this symbol table contains {@code key}; {@code false}
         *         otherwise
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public boolean contains(Key key) {
            if (key == null)
                throw new IllegalArgumentException("argument to contains() is null");
            return get(key) != null;
        }

        /**
         * Returns the value associated with the specified key in this symbol table.
         *
         * @param key the key
         * @return the value associated with {@code key} in the symbol table;
         *         {@code null} if no such value
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public Value get(Key key) {
            if (key == null)
                throw new IllegalArgumentException("argument to get() is null");
            int i = hash(key);
            return st[i].get(key);
        }

        /**
         * Inserts the specified key-value pair into the symbol table, overwriting the
         * old value with the new value if the symbol table already contains the
         * specified key. Deletes the specified key (and its associated value) from this
         * symbol table if the specified value is {@code null}.
         *
         * @param key the key
         * @param val the value
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public void put(Key key, Value val) {
            if (key == null)
                throw new IllegalArgumentException("first argument to put() is null");
            if (val == null) {
                delete(key);
                return;
            }

            // double table size if average length of list >= 10
            if (n >= 10 * m)
                resize(2 * m);

            int i = hash(key);
            if (!st[i].contains(key))
                n++;
            st[i].put(key, val);
        }

        /**
         * Removes the specified key and its associated value from this symbol table (if
         * the key is in this symbol table).
         *
         * @param key the key
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public void delete(Key key) {
            if (key == null)
                throw new IllegalArgumentException("argument to delete() is null");

            int i = hash(key);
            if (st[i].contains(key))
                n--;
            st[i].delete(key);

            // halve table size if average length of list <= 2
            if (m > INIT_CAPACITY && n <= 2 * m)
                resize(m / 2);
        }

        // return keys in symbol table as an Iterable
        public Iterable<Key> keys() {
            Queue<Key> queue = new Queue<Key>();
            for (int i = 0; i < m; i++) {
                for (Key key : st[i].keys())
                    queue.enqueue(key);
            }
            return queue;
        }
    }

    /**
     * Unit tests the {@code SeparateChainingHashST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int maxLength = 142453;
        int wordCount = 0;
        int i;
        SeparateChainingHashST<String, Integer> a = new SeparateChainingHashST<String, Integer>();
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

        Scanner scan = new Scanner(System.in);
        String text = "";

        while(true){
            System.out.print("Write 'QUIT' to stop the program.\nEnter a word to check: ");
            text = scan.nextLine();

            if(text.equals("QUIT")){
                break;
            }

            if(a.contains(text)){
                System.out.println("\nThe word '" + text +"' appears in the text '" + a.get(text) + "' times.\n");
            }else{
                System.out.println("\nThe word '" + text + "' appears in the text '0' times.\n");
            }
        }
    }
}
