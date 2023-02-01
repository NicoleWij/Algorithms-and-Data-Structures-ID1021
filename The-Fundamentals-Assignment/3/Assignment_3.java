/**
 *  The program was created to implement a generic, iterable FIFO-queue that is based on a double circular list, based on 
 *  an assignment given in the KTH course ID1021.
 * 
 *  A command is entered when the program is started. 1: Enqueue will add an element to the queue. 2: Dequeue removes an element
 *  from the queue. 3: IsEmpty returuns true or false depending on whether the queue is empty or not. 4: Size returns the size of
 *  the queue. 5: Quit will exit the program.
 * 
 *  If only one element is added to the queue it will point to itself with both next and prev. If more than one element are
 *  added, the most recently added one will be added as firsts.prev (the last element), which also has a next and a prev. 
 *  The next points to the first element and the prev to the previous last one.
 * 
 *  The different functions are being called as many times as we add elements, which can be described as (n) times. This means the
 *  time complexity of the program is linear, in other words time complexety is O(n). The memory complexety is determined by the 
 *  number of times the enqueue() function is called, since that one differs from the rest in terms of memory complexity. This is 
 *  because a new node is created each time the function is called, which can be described as (n) times, and gives the program a
 *  memory complexity of O(n). 
 * 
 *  [The dequeue function only changes directions for the nodes, it does not change the memory, which gives it a memory complexity of O(1)]
 * 
 *  Created: 11th of September 2021
 *  @author Nicole Wijkman
 * 
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

 /**
  * Circle class
  */
public class Assignment_3 {
    /**
     * Blueprint for the queue
     * 
     * @param <Item> wrapper type of element
     */
    public static class Queue<Item> implements Iterable<Item>{
        private Node first; //start of the queue
        private int size; //numbers of elements in the queue 

        /**
         * The constructor for the Queue
         */
        public Queue(){
            first = null;
            size = 0;
        }

        /**
         * Blueprint for the Node
         */
        private class Node {
            Item item;
            Node next;
            Node prev;
        }

        /**
         * Function that adds a node (element) to the queue. A new node is created with the item the user
         * sent in to be put in the queue.
         * 
         * If queue is empty: the new nodes next and prev both point to the new node, and then it becomes the first.
         * 
         * Else: the new nodes next points to the first, its prev points to the firsts prev. The previous "last" node
         * is then made to point its next to the newNode, and firsts previous becomes the new node.
         * 
         * @param item the element to be put in the queue
         */
        void enqueue(Item item){
            Node newNode = new Node();
            newNode.item = item;

            if(isEmpty()){
                newNode.next = newNode;
                newNode.prev = newNode;
                first = newNode;
            }else{
                newNode.next = first;
                newNode.prev = first.prev;
                first.prev.next = newNode;
                first.prev = newNode;
            }
            size++;
        }

        /**
         * Function that removes a node (element) from the queue. The first item is saved in a new item, then
         * the first.next.prev becomes first.prev which means the next element in the queue points its prev to the last
         * node in the queue. The same thing happens in the other direction, so that the first is no longer part of the queue.
         * The first then becomes the next element in the queue.
         * 
         * @return The item of the previous first
         */
        Item dequeue(){
            if(isEmpty()){
                throw new NoSuchElementException("The queue is empty.");
            }

            Item item = first.item;

            first.next.prev = first.prev;
            first.prev.next = first.next;
            first = first.next;

            size--;

            return item;
        }

        /**
         * Function that returns true or false depending on if the queue is
         * empty or not
         * 
         * @return true or false
         */
        boolean isEmpty(){
            return first == null;
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

            String string = "[" + first.item + "], ";
            Node temp = first.next;

            while(temp != first){
                string = string + "[" + temp.item + "], ";
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
    
            public boolean hasNext()  { return current != null; }
    
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

        Queue<String> q = new Queue<String>();

        while(input != 5){
            System.out.println();
            System.out.println("1: Enqueue");
            System.out.println("2: Dequeue");
            System.out.println("3: Check if the Queue is empty");
            System.out.println("4: Check the size of the Queue");
            System.out.println("5: Quit");

            Scanner in = new Scanner(System.in);
            System.out.print("\nEnter a case: ");
            input = in.nextInt();
            String temp = in.nextLine();

            switch (input) {
                case 1:
                    System.out.print("\nEnter a string to enqueue: ");
                    String text1 = in.nextLine(); // reads the string that the user writes
    
                    q.enqueue(text1);

                    System.out.println("\nCurrent Queue: " + q.toString());
    
                    break;
                case 2:
                    System.out.print("\nString that was dequeue:d: ");
    
                    System.out.print(q.dequeue() + "\n");

                    System.out.println("\nCurrent Queue: " + q.toString());
    
                    break;
                case 3:
                    System.out.print("\nCheck if Queue is empty: ");
                    System.out.print(q.isEmpty());
    
                    break;
                case 4:
                    System.out.println("\nSize of Queue: " + q.size());
    
                    break;
                case 5:
                    System.out.println("\nThe run of the program has ended.");
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }
}
