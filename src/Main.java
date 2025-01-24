import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static class SlotMachine {
        private int balance;

        public SlotMachine(int initialBalance) {
            this.balance = initialBalance;
        }

        public int getBalance() {
            return balance;
        }

        public void adjustBalance(int amount) {
            balance += amount;
        }

        public int[] spin() {
            Random random = new Random();
            return new int[]{
                    random.nextInt(9) + 1,
                    random.nextInt(9) + 1,
                    random.nextInt(9) + 1
            };
        }
    }

    public static void main(String[] args) {
        SlotMachine user = new SlotMachine(100);
        SlotMachine machine = new SlotMachine(100000);
        List<String> spinHistory = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("User: $" + user.getBalance());
            System.out.println("Machine: $" + machine.getBalance());
            System.out.print("[play], [history], [quit]> ");
            String option = scanner.nextLine();

            if (option.equalsIgnoreCase("play")) {
                play(user, machine, spinHistory);
            } else if (option.equalsIgnoreCase("history")) {
                showHistory(spinHistory);
            } else if (option.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid input. Please choose [play], [history], or [quit].");
            }
        }
        scanner.close();
    }

    public static void play(SlotMachine user, SlotMachine machine, List<String> spinHistory) {
        user.adjustBalance(-5);
        machine.adjustBalance(5);

        int[] result = machine.spin();
        System.out.printf("| %d | %d | %d |%n", result[0], result[1], result[2]);

        String spinResult = String.format("Spin: | %d | %d | %d | - ", result[0], result[1], result[2]);
        if (result[0] == result[1] && result[1] == result[2]) {
            int payout = (int) Math.pow(result[0] + result[1] + result[2], 3);
            machine.adjustBalance(-payout);
            user.adjustBalance(payout);
            System.out.println("You win $" + payout + "!");
            spinResult += "Win $" + payout;
        } else {
            System.out.println("You didn't win.");
            spinResult += "Loss";
        }

        spinHistory.add(spinResult);

        if (user.getBalance() <= 0) {
            System.out.println("Too Bad");
            System.exit(0);
        } else if (machine.getBalance() <= 0) {
            System.out.println("Out of order");
            System.exit(0);
        }
    }

    public static void showHistory(List<String> spinHistory) {
        if (spinHistory.isEmpty()) {
            System.out.println("No spins recorded yet.");
        } else {
            System.out.println("Spin History:");
            for (String record : spinHistory) {
                System.out.println(record);
            }
        }
    }
}
