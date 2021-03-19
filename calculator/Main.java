package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (var scan = new Scanner(System.in)) {
            while (true) {
                var line = scan.nextLine().strip();
                if (line.equalsIgnoreCase("/exit")) {
                    System.out.println("Bye!");
                    break;
                }

                if (line.equalsIgnoreCase("/help")) {
                    System.out.println("The program calculates given expression");
                    continue;
                }

                if (line.isEmpty()) {
                    continue;
                }

                var parts = line.split("\\s+");
                var current = 0;
                char op = 0;
                for (String part : parts) {
                    if (part.matches("-?\\+?[0-9]+")) {
                        var i = Integer.parseInt(part);
                        if (op == '-') {
                            current -= i;
                        } else {
                            current += i;
                        }
                    } else {
                        op = parseOp(part);
                    }
                }
                System.out.println(current);
            }
        }
    }

    private static char parseOp(String s) {
        boolean plus = true;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '-') {
                plus = !plus;
            }
        }
        return plus ? '+' : '-';
    }
}
