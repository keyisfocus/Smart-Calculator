package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (var scan = new Scanner(System.in)) {
            while (true) {
                var line = scan.nextLine().strip();

                if (line.startsWith("/")) {
                    executeCommand(line);
                }

                if (line.isEmpty()) {
                    continue;
                }

                if (!line.matches("[-+]?[0-9]+(\\s+[-+]+\\s+[-+]?[0-9]+)*")) {
                    System.out.println("Invalid expression");
                    continue;
                }

                var nums = new ArrayList<Integer>();
                var signs = new ArrayList<Character>();
                parse(line, nums, signs);

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

    private static void parse(String expression, List<Integer> nums, List<Character> signs) {
        var parts = expression.split("\\s+");

        for (String part : parts) {
            try {
                nums.add(Integer.parseInt(part));
            } catch (NumberFormatException e) {
                signs.add(parseSign(part));
            }
        }
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
}
