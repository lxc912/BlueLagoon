## Code Review

Reviewed by: <Zeqi Gao>, <u7591163>

Reviewing code written by: <Yufei Huang>, <u7618869>

Component: <Task 7> : https://gitlab.cecs.anu.edu.au/u7618869/comp1110-ass2/-/blob/master/src/comp1110/ass2/gui/Viewer.java#L148-262

### Comments 

<write your comments here>

Upon reviewing the code, I can confirm that the purpose of the isMoveValid is to determine if a given move is valid for 
the current player based on the state of the game. Also, successfully passed the test of Valid Move.

1. One of the best features of this code is its use of the code is structured in a modular way, with different parts of 
the code handling different aspects of the game logic, such as checking if a move is valid, finding all land spaces, 
getting placed and occupied settlers, and checking if a place is sea or land. Also, the use of regular expressions to 
extract information about the game state and player moves, this is efficient at parsing and handling state and 
moving strings.

2. The code documentation is relatively complete, but there are few comments, I think can explain more. 
But the logic is clear and various scenarios are considered comprehensively.

3. The program decomposition is appropriate, as the code is divided into logical components that handle different tasks.
And, utilized data structures such as arrays to store strings.

4. In terms of Java code conventions, the naming of the code is confusing and difficult to understand. 
I think the naming of some variables can be improved and become more relevant names.

5. I did not see any errors in the code.

Suggestion:
I think it will be better if there are more comments. For example, each large section of code basically only explains 
the comments at the beginning.
And it would be better to be more concise, now there are some duplicate content. For example, wrote a lot of similar ArrayList.

Overall, I think the code is correct implementation, making it easy to post-modify. 



