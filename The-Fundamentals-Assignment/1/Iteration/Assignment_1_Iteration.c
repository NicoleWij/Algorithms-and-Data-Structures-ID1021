/**
 *  The program was created to reverse a char input by the user iteratively in C, based on an assignment given in the KTH course
 *  ID1021.
 * 
 *  The user uses the program by entering characters into a char array that can hold up to 10 chars. When the user is done and
 *  presses enter, the loop breaks. Then, another loop goes through the array from the back and prints every character.
 * 
 *  The iterative version was easier to create and follow, as everything follows the "normal" order of the code, but it required
 *  slightly more code than the recursive version. The iterative method also uses less memory than the recursive and is also faster,
 *  since it does not use the stack. However, the iterative version uses a fixed length array which the recursive version does not.
 *  There are ways to fix this issue (doubling the array size when its limit is reached etc), but then the code will be more complicated.
 * 
 *  Created: 11th of September 2021
 *  @author Nicole Wijkman
 */

#include <stdio.h>

/**
 * Main function that creates a char-array with a fixed number of 10 spaces and reverses an input of chars by the user
*/
int main(){
    char text[10]; //creates an array with 10 spaces
    int i; //initiates int i

    for(i = 0; i < 10; i++){
        int get = getchar();

        //if a newline character is read the loop breaks
        if(get == '\n'){
            break;
        }

        //the text that is put in with getchar() is saved in the text array
        text[i] = get;
    }

    
    for(i = i - 1; i >= 0; i--){
        printf("%c", text[i]);
    }

    printf("\n");

    return 0;
}