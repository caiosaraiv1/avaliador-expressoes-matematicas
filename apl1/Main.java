package apl1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
    	Scanner scanner = new Scanner(System.in);
        CommandManager commandManager = new CommandManager();
        
        System.out.println("Olá, Joaquim! Iniciando o programa!");
        
        while(true) {
        	String userInput = scanner.nextLine();
        	if(commandManager.isRecording()) {
        		commandManager.recordCommand(userInput); 
        		continue;
        	}
        	commandManager.executeCommand(userInput);
        }
    }
}
