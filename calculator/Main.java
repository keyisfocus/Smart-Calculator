package calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, Integer> vars = new HashMap<>();

    public static void main(String[] args) {

        try (var scan = new Scanner(System.in)) {
            while (true) {
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

                if (!line.matches("[-+]?([0-9]|[a-zA-Z])+(\\s+[-+]+\\s+[-+]?([0-9]|[a-zA-Z])+)*")) {
                    System.out.println("Invalid expression");
                    continue;
                }

                var nums = new ArrayList<Integer>();
                var signs = new ArrayList<Character>();

                if (parse(line, nums, signs)) {

                    var res = nums.get(0);
                    for (int i = 0; i < signs.size(); i++) {
                        var n = nums.get(i + 1);
                        var s = signs.get(i);
                        res = s == '-' ? res - n : res + n;
                    }
                    System.out.println(res);
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
                System.out.println("Unknown command");
        }
    }

    private static void assignment(String line) {
        var parts = line.split("=");
        parts[0] = parts[0].strip();
        parts[1] = parts[1].strip();

        if (line.indexOf('=') != line.lastIndexOf('=') ||
                !parts[0].matches("[a-zA-Z]+") ||
                !parts[1].matches("([0-9]+|[a-zA-Z]+)")) {
            System.out.println("Invalid expression");
            return;
        }
        if (parts[1].matches("[0-9]+")) {
            vars.put(parts[0], Integer.parseInt(parts[1]));
        } else {
            if (vars.containsKey(parts[1])) {
                vars.put(parts[0], vars.get(parts[1]));
            } else {
                System.out.println("Invalid expression");
                return;
            }
        }
    }

    private static boolean parse(String expression, List<Integer> nums, List<Character> signs) {
        var parts = expression.split("\\s+");

        for (String part : parts) {
            if (part.matches("[0-9]+")) {
                nums.add(Integer.parseInt(part));
            } else if (part.matches("[a-zA-Z]+")) {
                if (vars.containsKey(part)) {
                    nums.add(vars.get(part));
                } else {
                    System.out.println("Unknown variable");
                    return false;
                }
            } else {
                signs.add(parseSign(part));
            }
        }
        return true;
    }

    private static char parseSign(String s) {
        boolean plus = true;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '-') {
                plus = !plus;
            }
        }
        return plus ? '+' : '-';
    }
}
