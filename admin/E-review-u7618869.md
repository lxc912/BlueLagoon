## Code Review

Reviewed by: Yufei Huang, u7618869

Reviewing code written by: Zeqi Gao, u7591163

Component: Task 3: `isStateWellFormed` & Task 4: `isMoveStringWellFormed` <https://gitlab.cecs.anu.edu.au/u7618869/comp1110-ass2/-/blob/master/src/comp1110/ass2/BlueLagoon.java#L33-L83>

### Comments 

1. The usage of regular expressions to match the strings with given format is clever. Reduced the work of writing several if statement to judge.
2. The comments are adequate, explaining every expression of what it stands for, and features of if statements.
3. The logical structures are appropriated, easy to understand.
4. String name can be modified. E.g. String name could have the "s" at the end removed since it stand for one statement not multiple.
5. Suggestion: Adding space after comma that separates params of method is recommended, which matches format of other codes.
6Suggestion: Simplify if statement at the end? Since return value is same as boolean result of if expression.

