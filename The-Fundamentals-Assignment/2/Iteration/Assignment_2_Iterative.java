 /**
 *  
 *  The program was created to reverse a String input by the user iteratively, based on an assignment given in the KTH course
 *  ID1021.
 * 
 *  The user uses the program by entering a string, that will be run through character by character. These characters will
 *  be pushed onto the stack, and then the program iterates through the stack and prints every character that has been popped.
 *  This will print the characters in reverse. Using a stack to save the String and then reverse it is a good way to solve the
 *  problem because a stack is LIFO, which is good for printing in reverse.
 * 
 *  The user gets to enter a command that will either let them enter a string to be reversed or run different tests. 1: Iterative version
 *  lets the user run the program as normal, without testing. 2: Push String onto stack lets the user push a string onto the stack and the
 *  program also prints this string out for them. 3: Pop from empty stack shows the exception shown if the stack is empty when you pop.
 *  4: Push and the Pop from the stack lets the user first enter a string, then pops an element from it. 5: Check if the function is empty
 *  checks whether the stack is empty or not. 6: Check the size of the String lets the user enter a string and then check the size of it.
 * 
 *  Time complexety is O(n) because there are loops but no nested loops. The memory complexety is also O(n) because every char is saved
 *  on the stack.
 * 
 *  Created: 11th of September 2021
 *  @author Nicole Wijkman
 * 
 */

import java.util.Iterator;
import java.util.Scanner;
import java.util.NoSuchElementException;

 /**
 * Iterative class
 */
public class Assignment_2_Iterative {

    /**
     * Blueprint for the Stack
     * 
     * @param <Item> wrapper type of element
     */
    public static class Stack<Item> implements Iterable<Item> {
        private Node first;
        private int stackSize;

        /**
         * The constructor for the Stack
         */
        public Stack() {
            first = null;
            stackSize = 0;
        }

        /**
         * Blueprint for the Node
         */
        private class Node {
            Item item;
            Node next;
        }

        /**
         * Function that pushes an item onto the stack
         * 
         * @param item the item which to push
         */
        void push(Item item) {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            stackSize++;
        }

        /**
         * Function that pops an item from the stack and returns it
         * 
         * @return returns the popped item
         */
        Item pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("The stack is empty.");
            }

            Item item = first.item;
            first = first.next;
            stackSize--;
            return item;
        }

        /**
         * Function that checks if the stack is empty by checking if the first element is null
         * 
         * @return true or false
         */
        boolean isEmpty() {
            return first == null;
        }

        /**
         * Function that checks the size of the stack
         * 
         * @return the size of the stack
         */
        int size() {
            return stackSize;
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
            private Node current;

            public LinkedIterator(Node first) {
                current = first;
            }

            public boolean hasNext() {
                return current != null;
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

    
    /** 
     * Function that will run the program, handle the input and create the stack
     * 
     * @param args input for program
     */
    public static void main(String[] args) {
        System.out.println("1: Iterative version");
        System.out.println("2: Push string onto the stack");
        System.out.println("3: Pop from empty stack");
        System.out.println("4: Push and then Pop from the stack");
        System.out.println("5: Check if the stack is empty");
        System.out.println("6: Check the size of the String");

        Scanner in = new Scanner(System.in);
        System.out.print("\nEnter a case: ");
        int input = in.nextInt();
        String temp = in.nextLine();

        Stack<Character> collection = new Stack<Character>();

        switch (input) {
            case 1:
                System.out.print("Enter a string to reverse: ");
                String text1 = in.nextLine();

                int i;

                for (i = 0; i < text1.length(); i++) {
                    collection.push(text1.charAt(i));
                }

                for (Character s : collection) {
                    System.out.print(s);
                }

                break;
            case 2:
                System.out.print("Enter a string to push: ");
                String text2 = in.nextLine();

                int j;

                System.out.println("Pushed: ");
                
                for (j = 0; j < text2.length(); j++) {
                    collection.push(text2.charAt(j));
                    System.out.print("[" + text2.charAt(j) + "] ");
                }

                System.out.println();

                break;
            case 3:
                collection.pop();

                break;
            case 4:
                System.out.print("\nEnter a string to push and pop: ");
                String text3 = in.nextLine();

                int a;

                System.out.print("\nPushed: ");
                
                for (a = 0; a < text3.length(); a++) {
                    collection.push(text3.charAt(a));
                    System.out.print("[" + text3.charAt(a) + "] ");
                }

                System.out.print("\n\nPopped: " + "[" + collection.pop() + "]\n");

                System.out.print("\nRemaining: ");

                for (Character s : collection) {
                    System.out.print("[" + s + "]");
                }

                System.out.println("\n");

                break;
            case 5:
                System.out.println("Check if stack is empty: ");
                System.out.println(collection.isEmpty());

                break;
            
            case 6:
                System.out.print("Enter a String to check: ");
                String text4 = in.nextLine();

                for (a = 0; a < text4.length(); a++) {
                    collection.push(text4.charAt(a));
                }

                System.out.println("\nSize of String: " + collection.size());

                break;
            default:
                System.out.println("Unknown command");
                break;
        
    
        }

        in.close(); // closes the scanner
    }
}