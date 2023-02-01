/*
 *  Program that implements a function in C which takes an array of integers (both positive and negative) and orders the elements 
 *  in the array so that all negative elements come before the positive. This was done without using any of the sorting methods. 
 * 
 *  Because of how no new arrays are created, only some new variables, the algorithm only uses O(1) extra memory.
 * 
 *  Created: 22nd of September 2021
 *  @author Nicole Wijkman
 */

#include <stdio.h>

/*
 *  Function that orders the elements in the array in such a way that all the negative elements come before
 *  the positive elements. It does not sort the elements according to size other than look if they are less than 0.
 * 
 *  @param size the size of the array
 *  @param text[] the array with the integers
 */
void orderElements(int size, int text[]){
    int j;
    int i;
    int temporary;
    
    for (i = 0; i < size; i++) {
        if(text[i] < 0){
            if(i != j){
                temporary = text[i];
                text[i] = text[j];
                text[j] = temporary;
                
                j++;
            }
        }
    }
    
    printf("Ordered with negative to the left: ");
    for(i = 0; i < size; i++){
        printf("[%d] ", text[i]);
    }
}

/**
 * The user enters the size of an input, which becomes the size of the array created. The user then enters an input into every space
 * in the array. The function then prints the input, then calls for orderElements to order them.
 */
int main()
{
    int i;
    int size;
    
    printf("Enter size of input: ");
    scanf("%d",&size);
    
    int text[size];
    
    for(int i = 0; i < size; i++){
        printf("Enter input ");
        printf("%d", (i + 1));
        printf(": ");
        scanf("%d", &text[i]);
    }

    printf("\nInput: ");
    for(i = 0; i < size; i++){
        printf("[%d] ", text[i]);
    }
    
    printf("\n");
    
    orderElements(size, text);
    return 0;
}
