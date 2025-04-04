package apl1;

import java.util.Locale;

/*
 * Classe responsável por gerenciar comandos recebidos.
 */
public class CommandManager {

    private VariableManager variableManager; // Gerenciador de variáveis
    private Queue<String> queue; // Fila de comandos gravados
    private boolean recording = false; // Flag para indicar se está gravando comandos
    private EquationEvaluator equationEvaluator; // Avaliador de expressões matemáticas

    // Construtor - inicializa os gerenciadores e a fila
    public CommandManager() {
        variableManager = new VariableManager();
        queue = new Queue<>();
        equationEvaluator = new EquationEvaluator(variableManager);
    }
    
    // Retorna se o programa está em modo de gravação
    public boolean isRecording() {
        return recording;
    }
    
    // Verifica se o comando informado é válido
    public boolean isValidCommand(String command) {
        if (command == null) throw new NullPointerException("Command cannot be null.");

        String[] validCommands = {"VARS", "RESET", "REC", "STOP", "PLAY", "ERASE", "EXIT"};

        for (String validCommand : validCommands) {
            if (command.equalsIgnoreCase(validCommand)) return true;
        }
        return false;
    }

    // Executa o comando fornecido pelo usuário
    public void executeCommand(String command) throws Exception {
        try {
            if (isValidCommand(command)) {
                switch (command.toUpperCase()) {
                    case "VARS": // Lista as variáveis armazenadas
                        variableManager.listVariables();
                        return;
                    case "RESET": // Reinicia todas as variáveis
                        System.out.println("Variables have been reset.");
                        variableManager.resetVariables();
                        return;
                    case "REC": // Inicia a gravação de comandos
                        startRecording();
                        return;
                    case "STOP": // Para a gravação de comandos
                        stopRecording();
                        return;
                    case "PLAY": // Executa os comandos gravados
                        playCommands();
                        return;
                    case "ERASE": // Apaga todos os comandos gravados
                        clearCommands();
                        return;
                    case "EXIT": // Encerra o programa
                        System.out.println("Exiting the program...");
                        System.exit(0);
                        return;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error -> " + e.getMessage());
            return;
        }
        
        // Verifica se o comando passado é uma atribuição de variável (ex: X = 5)
        if (command.contains("=")) {
            variableManager.processAssignment(command);
            char variable = Character.toUpperCase(command.charAt(0));
            System.out.println(variable + " = " + format(variableManager.getValue(variable)));
            return;
        }
        
        // Verifica se o comando é apenas uma variável (ex: X)
        if (command.length() == 1 && Character.isLetter(command.charAt(0))) {
            char variable = Character.toUpperCase(command.charAt(0));
            if (variableManager.isDefined(variable)) {
                System.out.println(variable + " = " + format(variableManager.getValue(variable)));
                return;
            }
            throw new IllegalArgumentException("Variable " + variable + " is not defined.");
        }
        
        // Se o comando for uma expressão matemática, tenta avaliá-la
        try {
            equationEvaluator.setReceivedEquation(command);
            equationEvaluator.convertEquation();
            double result = equationEvaluator.expressionCalculator();
            System.out.println(format(result));
        } catch (Exception e) {
            System.out.println("Error calculating expression -> " + e.getMessage());
        }
    }
    
    // Formata um número para exibição com duas casas decimais
    private String format(double value) {
        return String.format(Locale.US, "%.2f", value); 
    }

    // Inicia a gravação de comandos
    public void startRecording() {
        if (recording) {
            throw new IllegalArgumentException("Already recording.");
        }
        recording = true;
        System.out.println("Starting recording... (REC: 0/10)");
    }

    // Grava um comando fornecido pelo usuário
    public void recordCommand(String command) throws Exception {
        if (!recording) {
            throw new IllegalStateException("Recording has not been started.");
        }

        // Comandos inválidos para gravação
        if (command.equalsIgnoreCase("REC") ||  
            command.equalsIgnoreCase("PLAY") || 
            command.equalsIgnoreCase("ERASE")) {
            System.out.println("Error -> Invalid command for recording.");
            return;
        }

        // Se o usuário digitar STOP, finaliza a gravação
        if(command.equalsIgnoreCase("STOP")) {
            stopRecording();
            return;
        }

        // Verifica se a fila já atingiu o limite máximo de comandos
        if (queue.isFull()) {
            System.out.println("Command limit reached. Stopping recording...");
            stopRecording();
            return;
        }

        // Se o comando for uma atribuição de variável (ex: X = 10), processa e grava
        if (command.contains("=")) {
            try {
                variableManager.processAssignment(command);
                queue.enqueue(command);
                System.out.println("(REC: " + queue.getCurrentSize() + "/10) " + command);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Error -> " + e.getMessage());
                return;
            }
        }
        
        // Se o comando for uma expressão matemática, valida e grava
        try {
            equationEvaluator.validateExpression(command);
            queue.enqueue(command);
            System.out.println("(REC: " + queue.getCurrentSize() + "/10) " + command);
            return;
        } catch (IllegalArgumentException e) {
            queue.enqueue(command);
            System.out.println("(REC: " + queue.getCurrentSize() + "/10) " + command);
            return;
        }
    }

    // Para a gravação de comandos
    public void stopRecording() {
        if (!recording) {
            throw new IllegalStateException("Recording not started.");
        }
        recording = false;
        System.out.println("Ending recording... (" + queue.getCurrentSize() + "/10)");
    }
    
    // Executa os comandos gravados
    public void playCommands() throws Exception {
        if (queue.isEmpty()) {
            System.out.println("There is no recording to play.");
            return;
        }

        System.out.println("Playing recording...");
        Queue<String> tempQueue = new Queue<>(10); 

        while (!queue.isEmpty()) {
            String command = queue.dequeue();
            System.out.println(command); 

            try {
                if (command.contains("=")) { // Se for uma atribuição de variável
                    variableManager.processAssignment(command);
                } else if (command.length() == 1 && Character.isLetter(command.charAt(0))) { // Se for uma variável
                    char var = Character.toUpperCase(command.charAt(0));
                    if (variableManager.isDefined(var)) {
                        System.out.println(var + " = " + variableManager.getValue(var));
                    } else {
                        System.out.println("Error -> Variable " + var + " is not defined.");
                    }
                } else if (command.equalsIgnoreCase("VARS")) {
                    variableManager.listVariables();
                } else if (command.equalsIgnoreCase("RESET")) {
                    variableManager.resetVariables();
                    System.out.println("Variables have been reset.");
                } else { // Se for uma expressão matemática
                    try {
                        equationEvaluator.setReceivedEquation(command);
                        equationEvaluator.convertEquation();
                        double result = equationEvaluator.expressionCalculator();
                        System.out.printf(Locale.US, "%.0f\n", result); 
                    } catch (Exception e) {
                        System.out.println("Error -> " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            tempQueue.enqueue(command); 
        }

        queue = tempQueue;
    }

    // Limpa a fila de comandos gravados
    public void clearCommands() {
        if (queue.isEmpty()) {
            System.out.println("The queue is already empty.");
            return;
        }

        queue.clear();
        System.out.println("Recording deleted.");
    }
}
