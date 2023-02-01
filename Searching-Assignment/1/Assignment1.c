/**
 * The program was created to clean a text from all characters that are not alphabetic, blank or newline. It replaces all such
 * characters with ' '.
 * 
 * To take in the input the program creates a pointer that is going to point to a char. The program then allocates memory the size
 * of a char. The user can then enter a text, which the program gets through a loop which stops once the user enters the command
 * for EOF (End Of File). If the size is no longer enough, the program allocates new memory space that is double the size of the 
 * previous space.
 * 
 * The program then checks if the character is ' ', '\n' or alphabetic and replaces it with ' ' if it isn't. Otherwise, it just
 * "replaces" it with itself.
 * 
 * Created: 4th of October 2021
 * @author Nicole Wijkman
 */

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

/**
 * Function which takes in an input and filters away the characters which are not alphabetical, ' ' or '\n'.
 */
void filterInput() {
    char *string;
    int size = 1;
    int c;
    string = malloc(1 * sizeof(char));

    printf("\nEnter your text: ");

    for(int i = 0; (c = getchar()) && c != EOF; ++i){
        if(i == size){
            size = 2*size;
            string = realloc(string, size*sizeof(char));
        }

        if(!isalpha(c) && c != ' ' && c != '\n'){
            string[i] = ' ';
        }else{
            string[i] = c;
        }
    }

    printf("\n\n%s", string);
    printf("\n\n");
}

/**
 * Main function which calls the filterInput function
 */
int main()
{
    filterInput();

    return 0;
}