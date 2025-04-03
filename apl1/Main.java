package apl1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        CommandManager commandManager = new CommandManager();

        System.out.println("Welcome to the Equation Evaluator. Type your commands below.");

        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine();

            try {
                if (commandManager.isRecording()) {
                    commandManager.recordCommand(userInput);
                } else {
                    commandManager.executeCommand(userInput);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }
}
