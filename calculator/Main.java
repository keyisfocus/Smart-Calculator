package calculator;

import java.util.Arrays;
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
                    System.out.println("The program calculates the sum of numbers");
                    continue;
                }

                if (line.isEmpty()) {
                    continue;
                }

                var nums = line.split("\\s+");
                System.out.println(Arrays.stream(nums)
                        .mapToInt(Integer::parseInt)
                        .sum());
            }
        }
    }
}
