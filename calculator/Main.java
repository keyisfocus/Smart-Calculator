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

                if (line.isEmpty()) {
                    continue;
                }

                var nums = line.split("\\s+");
                if (nums.length == 1) {
                    System.out.println(nums[0]);
                } else {
                    System.out.println(Integer.parseInt(nums[0]) +
                            Integer.parseInt(nums[1]));
                }
            }
        }
    }
}
