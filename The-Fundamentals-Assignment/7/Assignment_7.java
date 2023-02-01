/**
 *  
 *  The program was created to take an input of a series of parantheses and check if the parantheses are balanced or not. It shall also
 *  show the time and memory complexity of the algorithm. The program is based on an assignment given in the KTH course ID1021.
 * 
 *  A command is entered when the program is started. 1: Push will add an element to the test Stack. 2: Pop will pop an element from the
 *  test stack. 3: IsEmpty returuns true or false depending on whether the test stack is empty or not. 4: Size returns the size of the queue.
 *  5: Let's the user enter a String of parantheses to check their balance 6: Quit will exit the program.
 * 
 *  Time and memory complexity: Since we only have one loop, the time complexity can be described as O(n). The constant in the memory
 *  complexity will wary depending on if the String sent in is balanced or not, but since the constant is to be ignored the memory complexity
 *  can also be described as O(n).
 * 
 *  Created: 11th of September 2021
 *  @author Nicole Wijkman
 * 
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Balanced class
 */
public class Assignment_7 {

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
         * Function that returns the first.item as a character
         * 
         * @return first.item as a character
         */
        public char peek(){
            return (char)first.item;
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
     * Function that checks if the parantheses sent in by the user are valid and balanced. It creates a new stack, and then
     * for every character in the string it either pops them out of the stack if they are balanced or pushes them into the
     * stack if they are not. Once it has checked everything, it returns true or false depending on if the stack is empty.
     * 
     * @param string the String of parantheses sent in by the user
     * @return true or false depending on if the stack is empty; true if empty, false if not empty.
     */
    public static boolean isValid(String string) {
        Stack<Character> stack = new Stack<>();
        for (char character : string.toCharArray())
            if (stack.size() > 0 && balanced(stack.peek(), character))
                stack.pop();
            else
                stack.push(character);

        return stack.size() == 0;
    }

    /**
     * Function that compares the characters sent in with parantheses to see if they are balanced
     * 
     * @param s stack.peek(), which shows the first element in the stack
     * @param c current character that the loop is checking
     * @return true or false whether the characters match or not
     */
    public static boolean balanced(char s, char c) {
        return (s == '[' && c == ']') || (s == '{' && c == '}') || (s == '(' && c == ')');
    }

    /** 
     * Function that will run the program, handle the input and create the stack
     * 
     * @param args input for program
     */
    public static void main(String[] args) {
        int input = 0;

        Stack<Character> test = new Stack<Character>();

        while(input != 6){
            System.out.println();
            System.out.println("1: Push string onto the test stack");
            System.out.println("2: Pop from the test stack");
            System.out.println("3: Check if the test stack is empty");
            System.out.println("4: Check the size of the test stack");
            System.out.println("5: Check the balance of parantheses");
            System.out.println("6: Quit");

            Scanner in = new Scanner(System.in); // System.in is a standard input stream
            System.out.print("\nEnter a case: ");
            input = in.nextInt();
            String temp = in.nextLine();

            switch (input) {
                case 1:
                    System.out.print("Enter a string to push: ");
                    String text1 = in.nextLine(); // reads the string that the user writes

                    int j;

                    System.out.print("\nPushed: ");
                    
                    for (j = 0; j < text1.length(); j++) {
                        test.push(text1.charAt(j));
                        System.out.print("[" + text1.charAt(j) + "] ");
                    }

                    System.out.println();

                    break;
                case 2:
                    System.out.print("\nPopped: " + "[" + test.pop() + "]");

                    System.out.print("\nRemaining: ");

                    for (Character s : test) {
                        System.out.print("[" + s + "], ");
                    }

                    System.out.println();

                    break;
                case 3:
                    System.out.print("\nCheck if the test stack is empty: ");
                    System.out.print(test.isEmpty() + "\n");
    
                    break;
                case 4:
                    System.out.println("\nSize of the test stack: " + test.size());
    
                    break;
                case 5:
                    System.out.print("Enter a string with parantheses: ");
                    String text2 = in.nextLine(); // reads the string that the user writes

                    System.out.println("Balanced: " + isValid(text2));
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
