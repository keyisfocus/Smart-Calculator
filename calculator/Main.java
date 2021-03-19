package calculator;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static final Map<String, BigInteger> vars = new HashMap<>();

    public static void main(String... args) {

        try (var scan = new Scanner(System.in)) {
            while (true) {
                try {
                    var line = scan.nextLine().strip();

                    if (line.startsWith("/")) {
                        executeCommand(line);
                        continue;
                    }

                    if (line.isEmpty()) {
                        continue;
                    }

                    if (line.contains("=")) {
                        assignment(line);
                        continue;
                    }

                    if (!line.matches("[0-9a-zA-Z-+/*\\s=()]+")) {
                        throw new IllegalArgumentException("Invalid expression");
                    }

                    var operand = new StringBuilder();
                    var operator = new StringBuilder();

                    var stack = new ArrayDeque<Character>();
                    var postfix = new StringBuilder();

                    for (int i = 0; i < line.length(); i++) {
                        var c = line.charAt(i);
                        if (Character.isWhitespace(c)) {
                            continue;
                        }

                        if (Character.isLetterOrDigit(c)) {
                            if (operator.length() != 0) {
                                if (operator.toString().equals("-") && line.charAt(i - 1) != ' ') {
                                    operand.append('-');
                                } else {
                                    parseOperator(operator, stack, postfix);
                                }
                                operator.setLength(0);
                            }
                            operand.append(c);
                        } else {
                            if (operand.length() != 0) {
                                postfix.append(operand).append(" ");
                                operand.setLength(0);
                            }
                            if (c == '-' || c == '+') {
                                if (!operator.toString().matches("[-+]*")) {
                                    parseOperator(operator, stack, postfix);
                                    operator.setLength(0);
                                }
                            } else if (operator.length() != 0) {
                                parseOperator(operator, stack, postfix);
                                operator.setLength(0);
                            }
                            operator.append(c);
                        }
                    }
                    if (operand.length() != 0) {
                        postfix.append(operand).append(" ");
                    }
                    if (operator.length() != 0) {
                        parseOperator(operator, stack, postfix);
                    }

                    while (!stack.isEmpty()) {
                        var o = stack.pop();
                        if (o == '(' || o == ')') {
                            throw new IllegalArgumentException("Invalid expression");
                        }
                        postfix.append(o).append(" ");
                    }
                    postfix.deleteCharAt(postfix.length() - 1);

                    var res = calculate(postfix.toString().strip());
                    System.out.println(res);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (NoSuchElementException e) {
                    System.out.println("Invalid expression");
                }
            }
        }
    }

    private static void executeCommand(String command) {
        switch (command.toLowerCase(Locale.ROOT)) {
            case "/exit":
                System.out.println("Bye!");
                System.exit(0);
            case "/help":
                System.out.println("The program calculates given expression");
                break;
            default:
                throw new IllegalArgumentException("Unknown command");
        }
    }

    private static void assignment(String line) {
        var parts = line.split("=");
        parts[0] = parts[0].strip();
        parts[1] = parts[1].strip();

        if (line.indexOf('=') != line.lastIndexOf('=') ||
                !parts[0].matches("[a-zA-Z]+") ||
                !parts[1].matches("-?([0-9]+|[a-zA-Z]+)")) {
            throw new IllegalArgumentException("Invalid expression");
        }
        if (parts[1].matches("-?[0-9]+")) {
            vars.put(parts[0], new BigInteger(parts[1]));
        } else {
            if (vars.containsKey(parts[1])) {
                var v = vars.get(parts[1]);
                if (parts[1].charAt(0) == '-') {
                    v = v.negate();
                }
                vars.put(parts[0], v);
            } else {
                throw new IllegalArgumentException("Unknown variable");
            }
        }
    }


    private static void parseOperator(CharSequence s, Deque<Character> stack, StringBuilder sb) {
        char c;
        if (s.length() != 1) {
            c = parseSign(s);
        } else {
            c = s.charAt(0);
        }

        if (c == ')') {
            char i;
            while ((i = stack.pop()) != '(') {
                sb.append(i).append(" ");
            }
        } else if (stack.isEmpty() ||
                stack.peek() == '(' ||
                getPrecedence(c) > getPrecedence(stack.peek()) ||
                c == '(') {
            stack.push(c);
        } else if (getPrecedence(c) <= getPrecedence(stack.peek())) {
            while (!stack.isEmpty() &&
                    stack.peek() != '(' &&
                    getPrecedence(stack.peek()) >= getPrecedence(c)) {
                sb.append(stack.pop()).append(" ");
            }
            stack.push(c);
        }
    }

    private static char parseSign(CharSequence s) {
        boolean plus = true;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '-') {
                plus = !plus;
            }
        }
        return plus ? '+' : '-';
    }

    private static int getPrecedence(char c) {
        switch (c) {
            case '-':
            case '+':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private static BigInteger calculate(String postfix) {
        var stack = new ArrayDeque<BigInteger>();
        for (String s : postfix.split("\\s+")) {
            if (s.matches("-?[0-9]+")) {
                stack.push(new BigInteger(s));
            } else if (s.matches("-?[a-zA-Z]+")) {
                if (vars.containsKey(s)) {
                    var v = vars.get(s);
                    if (s.charAt(0) == '-') {
                        v = v.negate();
                    }
                    stack.push(v);
                } else {
                    throw new IllegalArgumentException("Unknown variable");
                }
            } else {
                var y = stack.pop();
                var x = stack.pop();
                var r = performOperation(s, x, y);
                stack.push(r);
            }
        }
        var res = stack.pop();
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression");
        } else {
            return res;
        }
    }

    private static BigInteger performOperation(String operator, BigInteger x, BigInteger y) {
        switch (operator) {
            case "+":
                return x.add(y);
            case "-":
                return x.subtract(y);
            case "*":
                return x.multiply(y);
            case "/":
                return x.divide(y);
            default:
                throw new IllegalArgumentException("Invalid expression");
        }
    }
}
