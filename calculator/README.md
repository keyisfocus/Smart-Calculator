# Smart Calculator https://hyperskill.org/projects/42

## Stage 1
Write a program that reads two integer numbers from the same line and prints their sum in the standard output. Numbers can be positive, negative, or zero.

## Stage 2
Write a program that reads two numbers in a loop and prints the sum in the standard output. If a user enters only a single number, the program should print the same number. If a user enters an empty line, the program should ignore it.

When the command `/exit` is entered, the program must print `Bye!`, and then stop.

## Stage 3
At this stage, the program should read an unlimited sequence of numbers from the standard input and calculate their sum. Also, add a `/help` command to print some information about the program. If you encounter an empty line, do not output anything.

## Stage 4
At this stage, your calculator should support the addition + and subtraction - operators.

The program must calculate expressions like these: `4 + 6 - 8`, `2 - 3 - 4`, and so on. It must support both unary and binary minus operators. If the user has entered several same operators following each other, the program still should work (like Java or Python REPL).

Consider that the even number of minuses gives a plus, and the odd number of minuses gives a minus! Look at it this way: `2 -- 2` equals `2 - (-2)` equals `2 + 2`.

Modify the result of the `/help` command to explain these operations.

## Stage 5
Modify your program to handle different cases when the given expression has an invalid format. The program should print `Invalid expression` in such cases. The program must never throw the `NumberFormatException` or any other exception.

If a user enters an invalid command, the program must print `Unknown command`.

## Stage 6
At this stage, your program should support variables. We suppose that the name of a variable (identifier) can contain only Latin letters. The case is also important; for example, n is not the same as N. The value can be an integer number or a value of another variable.

## 