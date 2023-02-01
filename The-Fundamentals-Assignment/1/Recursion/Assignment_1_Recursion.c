/**
 *  The program was created to reverse a char input by the user recursively in C, based on an assignment given in the KTH course
 *  ID1021.
 * 
 *  The user uses the program by entering characters that are saved on the stack via getchar(). This is why when the program recurses
 *  and prints all of the characters, it prints the characters in reverse. The last character put on the stack is the first one taken
 *  to be printed.
 * 
 *  The recursive version was slightly harder to create and follow as opposed to the iterative version, but it also required slightly 
 *  less code than the iterative version. However, the recursive method uses more memory and is slower than the iterative, since it uses
 *  the stack to save the characters.
 * 
 *  Created: 11th of September 2021
 *  @author Nicole Wijkman
 */

#include <stdio.h>

/**
 * The function reverse gets a character through getchar, which returns read characters as an unsigned char in an int. If a newline
 * character is read, the recusion stops and the function returns to the previous recursion. Otherwise the program recurses again.
 * 
 * When a newline character is read and the program returns to its previous recursion, it prints the character and then returns again.
 */
void reverse(void){
    int a = getchar();

    if(a == '\n'){
        return;
    }
    else{
        reverse(); 
        putchar(a); 
        return;
    }
}

/**
 * The main function calls the reverse function, then prints a newline to make the printed text look better
 */
int main() {
    reverse(); // calls the reverse function
    printf("\n");

    return 0;
}