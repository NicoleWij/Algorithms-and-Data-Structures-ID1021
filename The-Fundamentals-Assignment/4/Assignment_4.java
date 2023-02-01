/**
 *  The program was created to implement a generic, iterable circular linked list which will allow the user to insert and remove
 *  elements to/from the front and back end of the queue, based on an assignment given in the KTH course ID1021.
 * 
 *  A command is entered when the program is started. 1: Enqueue will add an element to the queue normally, from the back. 2: EnqueueFirst
 *  will add an element to the queue from the front. 3: Dequeue removes an element from the queue. 4: DequeueLast removes an element from
 *  the back of the queue. 5: IsEmpty returuns true or false depending on whether the queue is empty or not. 6: Size returns the size of
 *  the queue. 7: Writes the whole queue as a String. 8: Quit will exit the program.
 * 
 *  If only one element is added to the queue, the first.next will be null.
 * 
 *  The enqueue() functions are being called as many times as we add elements, which can be described as (n) times. However, as there are
 *  loops in the enqueue() function (which is also called from enqueueLast()), which will be looped through (n) times at the worst case
 *  scenario, the complexity of the program is quadratic, which can also be described as O(n^2). The memory complexety is determined by 
 *  the number of times the enqueue() or enqueueFirst() function is called, since that one differs from the rest in terms of memory complexity.
 *  This is because a new node is created each time the functions are called, which can be described as (n) times, and gives the program a
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
 * Singular class
 */
public class Assignment_4 {
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
        }

        /**
         * Function that adds a node (element) to the queue from the front or back depending on enqueueFirst.
         * If enqueueFirst is false, a new node is created and its item becomes the item sent into the function. If the 
         * queue is already empty, then newNode.next points at itself and first becomes the newNode. Otherwise, a temporary 
         * node is created to go through a while loop all the way to the end of the queue, where the temporary node will 
         * point its next to the newNode, and then the newNode will point its next to first.
         * 
         * If enqueueFirst is false, the item will be added from the back. If it is true, then the newNode will become the first
         * and the item will be added from the front.
         * 
         * @param item the element to be put in the queue
         */
        void enqueue(Item item, Boolean enqueueFirst){
            Node newNode = new Node();
            newNode.item = item;

            if(isEmpty()){
                newNode.next = newNode;
                first = newNode;
            }else{
                Node temp = first;

                while(temp.next != first){
                    temp = temp.next;
                }

                temp.next = newNode;
                newNode.next = first;
            }

            if(enqueueFirst){
                first = newNode;
            }

            size++;
        }

        /**
         * Function that adds a node (element) to the queue from the front. Sends the item and true
         * to the function enqueue, which will then add the element to the front.
         * 
         * @param item the element to be put in the queue
         */
        public void enqueueFirst(Item item){
            enqueue(item, true);
        }

        /**
         * Function that removes a node (element) from the queue from the front or back depending on 
         * dequeueLast. The first.item is saved in a new item. If first points to itself, which means
         * there's only one element in the queue, then first is set to null and the item is returned.
         * 
         * If dequeueLast is true, then two temporary nodes are created that goes through the queue.
         * When temp2.next equals the first, which means the last item in the queue has been reached,
         * the temp2.item is saved as the item and temp1.next becomes first, which removes the last item.
         * 
         * If dequeueLast is false, only one temporary node is created to iterate through the list to make
         * the last item point at first.next. First then becomes first.next, and the first item is returned.
         * 
         * @return The item of the previous first or last depending on dequeueLast
         */
        Item dequeue(Boolean dequeueLast){
            if(isEmpty()){
                throw new NoSuchElementException("The queue is empty.");
            }

            Item item = first.item;

            if(first == first.next){
                first = null;
            }else if(dequeueLast){
                Node temp1 = first;
                Node temp2 = first.next;

                while(temp2.next != first){
                    temp1 = temp2;
                    temp2 = temp2.next;
                }

                temp1.next = first;
                item = temp2.item;
            }else{
                Node temp = first.next;

                while(temp.next != first){
                    temp = temp.next;
                }

                temp.next = first.next;
                first = first.next;
            }

            size--;

            return item;
        }

        /**
         * Function that removes a node (element) from the queue from the back. The function calls dequeue
         * with dequeueLast set to true, which will dequeue the last item insteadd of the first
         * 
         * @return The item of the previous last
         */
        public Item dequeueLast(){
            return dequeue(true);
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
    
            public boolean hasNext()  { return current != null;                     }
    
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

        while(input != 8){
            System.out.println();
            System.out.println("1: Enqueue an element last");
            System.out.println("2: Enqueue an element first");
            System.out.println("3: Dequeue the element that is first");
            System.out.println("4: Dequeue the element that is last");
            System.out.println("5: Check if the Queue is empty");
            System.out.println("6: Check the size of the Queue");
            System.out.println("7: Write the whole Queue as a String");
            System.out.println("8: Quit");

            Scanner in = new Scanner(System.in); // System.in is a standard input stream
            System.out.print("\nEnter a case: ");
            input = in.nextInt();
            String temp = in.nextLine();

            switch (input) {
                case 1:
                    System.out.print("\nEnter a string to enqueue from the back: ");
                    String text1 = in.nextLine(); // reads the string that the user writes
    
                    q.enqueue(text1, false);

                    System.out.println("\nCurrent Queue: " + q.toString());
    
                    break;
                case 2:
                    System.out.print("\nEnter a string to enqueue from the front: ");
                    String text2 = in.nextLine(); // reads the string that the user writes
    
                    q.enqueueFirst(text2);

                    System.out.println("\nCurrent Queue: " + q.toString());
    
                    break;
                case 3:
                    System.out.print("\nString that was dequeue:d from the front of the Queue: ");
    
                    System.out.print(q.dequeue(false) + "\n");

                    System.out.println("\nCurrent Queue: " + q.toString());
    
                    break;
                case 4:
                    System.out.print("\nString that was dequeue:d from the back of the Queue: ");
    
                    System.out.print(q.dequeueLast() + "\n");

                    System.out.println("\nCurrent Queue: " + q.toString());
    
                    break;
                case 5:
                    System.out.print("\nCheck if Queue is empty: ");
                    System.out.println(q.isEmpty());
    
                    break;
                case 6:
                    System.out.println("\nSize of the Queue: " + q.size());
    
                    break;
                case 7:
                    System.out.println("\nThe queue as a String: " + q.toString());
    
                    break;
                case 8:
                    System.out.println("\nThe run of the program has ended.");
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }
}
