/**
 *  The program was created to implement a generalized queue which allows the user to remove the k:th element from the queue, in
 *  which the most recently added element has index 1. Program was based on an assignment given in the KTH course ID1021.
 * 
 *  A command is entered when the program is started. 1: Insert will add an element to the queue normally, from the back. 2: Delete 
 *  removes an element from the queue at a certain index. 3: IsEmpty returuns true or false depending on whether the queue is empty or not.
 *  4: Size returns the size of the queue. 5: Writes the whole queue as a String. 6: Quit will exit the program.
 * 
 *  The insert() function is being called as many times as we add elements, which can be described as (n) times. This means the
 *  time complexity of the program is linear, in other words time complexety is O(n). In the worst case scenario the dequeueLast function
 *  has to go through a while loop (n) amount of times, which gives it the time complexity of O(n).
 * 
 *  The memory complexety is determined by the number of times the insert() function is called, since that one differs from the rest in 
 *  terms of memory complexity. This is because a new node is created each time the function is called, which can be described as (n) times, 
 *  and gives the program a memory complexity of O(n). 
 * 
 *  Created: 11th of September 2021
 *  @author Nicole Wijkman
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

 /**
 * Queue class
 */
public class Assignment_5 {

    /**
     * Blueprint for the queue
     * 
     * @param <Item> wrapper type of element
     */
    public static class GeneralizedQueue<Item> implements Iterable<Item>{
        private Node first; //start of the queue
        private Node last; //end of the queue
        private int size; //numbers of elements in the queue

        /**
         * The constructor for the Queue
         */
        public GeneralizedQueue(){
            first = null;
            last = null;
            size = 0;
        }

        /**
         * Blueprint for the Node
         */
        private class Node {
            Item item;
            Node next;
        }

        /**
         * Function that adds a node (element) to the queue from the back. The previous last node
         * becomes the oldLast, which then is put as the prev to the last. The next for the
         * last is the first node in the queue. 
         * 
         * @param item the element to be put in the queue
         */
        void insert(Item item){
            Node oldLast = last;
            last = new Node();
            last.next = null;
            last.item = item;

            if(isEmpty()){
                first = last;
            }else{
                oldLast.next = last;
            }
            size++;
        }

        /**
         * Function that runs through the queue to delete the element at the k:th index. The first index is
         * at the back of the queue, hence why pos = size - k.
         * 
         * @param k the index at which the element shall be deleted
         * @return the deleted item
         */
        Item delete(int k){
            if(isEmpty() || k > size || k < 1){
                throw new NoSuchElementException("Index is out of range.");
            }

            int pos = size - k;
            Node temp1 = first;
            Node temp2 = temp1.next;

            while(pos > 1){
                temp1 = temp2;
                temp2 = temp2.next;
                pos--;
            }

            if(temp1.next == last){
                last = temp1;
            }
            
            temp1.next = temp2.next;

            size--;

            return temp2.item;
        }

        /**
         * Function that returns true or false depending on if the queue is
         * empty or not
         * 
         * @return true or false
         */
        boolean isEmpty(){
            return size() == 0;
        }

        /**
         * Function that returns the size of the queue
         * 
         * @return The size of the queue
         */
        int size(){
            return size;
        }

        /**
         * Function that takes the items in the queue and creates a String with them.
         * 
         * @return the items in the queue as a String
         */
        public String toString(){
            if(isEmpty()){
                return "";
            }

            String string = "[" + first.item + "] ";
            Node temp = first.next;

            while(temp != null){
                string = string + "[" + temp.item + "] ";
                temp = temp.next;
            }

            return string;
        }

        /**
         * Function that gets and creates an instance of the iterator class
         * 
         * @return the iterator
         */
        public Iterator<Item> iterator(){
            return new LinkedIterator(first);  
        }
    
        /**
         * Blueprint for the iterator
         */
        private class LinkedIterator implements Iterator<Item> {
            private Node current;
    
            public LinkedIterator(Node first) {
                current = first;
            }
    
            public boolean hasNext()  { return current != null;}
    
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next; 
                return item;
            }
        }
    }

    /** 
     * Function that runs the program and handles the input
     * 
     * @param args the input to the program
     */
    public static void main(String[] args) {
        int input = 0;

        GeneralizedQueue<String> q = new GeneralizedQueue<String>();

        while(input != 6){
            System.out.println();
            System.out.println("1: Insert");
            System.out.println("2: Delete");
            System.out.println("3: Check if the Queue is empty");
            System.out.println("4: Check the size of the Queue");
            System.out.println("5: Write the whole Queue as a String");
            System.out.println("6: Quit");

            Scanner in = new Scanner(System.in); // System.in is a standard input stream
            System.out.print("\nEnter a case: ");
            input = in.nextInt();
            String temp = in.nextLine();

            switch (input) {
                case 1:
                    System.out.print("\nEnter a string to insert: ");
                    String text1 = in.nextLine();
    
                    q.insert(text1);

                    System.out.println("\nCurrent Queue: " + q.toString() + "\n");
    
                    break;
                case 2:
                    System.out.print("\nEnter what number of element to delete: ");
                    int text2 = in.nextInt();

                    System.out.print("\nString that was deleted: ");
    
                    System.out.print(q.delete(text2) + "\n");

                    System.out.println("\nCurrent Queue: " + q.toString());
    
                    break;
                case 3:
                    System.out.print("\nCheck if Queue is empty: ");
                    System.out.println(q.isEmpty());
    
                    break;
                case 4:
                    System.out.println("\nSize of Queue: " + q.size());
    
                    break;
                case 5:
                    System.out.println("\nThe queue as a String: " + q.toString());
    
                    break;
                case 6:
                    System.out.println("\nThe run of the program has ended.");
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }
}
