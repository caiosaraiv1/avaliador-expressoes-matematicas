package apl1;

import java.util.Locale;

/*
 * Classe responsável por gerenciar os comandos recebidos
 */
public class CommandManager {

    private VariableManager variableManager;
    private Queue<String> queue;
    private boolean recording = false;
    private EquationEvaluator equationEvaluator;

    public CommandManager() {
        variableManager = new VariableManager();
        queue = new Queue<>();
        equationEvaluator = new EquationEvaluator(variableManager);
    }

    public boolean isRecording() {
        return recording;
    }

    public boolean isValidCommand(String command) {
        if (command == null) throw new NullPointerException("Command cannot be null.");

        String[] validCommands = {"VARS", "RESET", "REC", "STOP", "PLAY", "ERASE", "EXIT"};

        for (String validCommand : validCommands) {
            if (command.equalsIgnoreCase(validCommand)) return true;
        }
        return false;
    }

    public void executeCommand(String command) throws Exception {
        try {
            if (isValidCommand(command)) {
                switch (command.toUpperCase()) {
                    case "VARS":
                        variableManager.listVariables();
                        return;
                    case "RESET":
                        variableManager.resetVariables();
                        return;
                    case "REC":
                        startRecording();
                        return;
                    case "STOP":
                        stopRecording();
                        return;
                    case "PLAY":
                        playCommands();
                        return;
                    case "ERASE":
                        clearCommands();
                        return;
                    case "EXIT":
                        System.out.println("Exiting the program...");
                        System.exit(0);
                        return;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if (command.contains("=")) {
            variableManager.processAssignment(command);
            char variable = Character.toUpperCase(command.charAt(0));
            System.out.println(variable + " = " + format(variableManager.getValue(variable)));
            return;
        }

        if (command.length() == 1 && Character.isLetter(command.charAt(0))) {
            char variable = Character.toUpperCase(command.charAt(0));
            if (variableManager.isDefined(variable)) {
                System.out.println(variable + " = " + format(variableManager.getValue(variable)));
                return;
            }
            throw new IllegalArgumentException("Error: variable " + variable + " is not defined.");
        }

        try {
            equationEvaluator.setReceivedEquation(command);
            equationEvaluator.convertEquation();
            double result = equationEvaluator.expressionCalculator();
            System.out.println(format(result));
        } catch (Exception e) {
            System.out.println("Error calculating expression: " + e.getMessage());
        }
    }

    private String format(double value) {
        return String.format(Locale.US, "%.2f", value); // Usa ponto como separador decimal
    }

    public void startRecording() {
        if (recording) {
            throw new IllegalArgumentException("Already recording.");
        }
        recording = true;
        System.out.println("Starting recording... (REC: 0/10)");
    }

 // Dentro de recordCommand()
    public void recordCommand(String command) throws Exception {
        if (!recording) {
            throw new IllegalStateException("Recording not started.");
        }

        // Verifica comandos inválidos
        if (command.equalsIgnoreCase("REC") ||  
            command.equalsIgnoreCase("PLAY") || 
            command.equalsIgnoreCase("ERASE")) {
            System.out.println("Erro: comando inválido para gravação."); // ✅ mensagem corrigida
            return;
        }

        if (command.equalsIgnoreCase("STOP")) {
            stopRecording();
            return;
        }

        if (queue.isFull()) {
            System.out.println("Command limit reached. Stopping recording...");
            stopRecording();
            return;
        }

        // Atribuições
        if (command.contains("=")) {
            try {
                variableManager.processAssignment(command);
                queue.enqueue(command);
                System.out.println("(REC: " + queue.getCurrentSize() + "/10) " + command);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                return;
            }
        }

        // Expressões válidas
        try {
            equationEvaluator.validateExpression(command);
            queue.enqueue(command);
            System.out.println("(REC: " + queue.getCurrentSize() + "/10) " + command);
            return;
        } catch (IllegalArgumentException e) {
            // Mesmo que inválida, ainda grava o comando como foi digitado
            queue.enqueue(command);
            System.out.println("(REC: " + queue.getCurrentSize() + "/10) " + command);
            return;
        }
    }

    public void stopRecording() {
        if (!recording) {
            throw new IllegalStateException("Recording not started.");
        }
        recording = false;
        System.out.println("Ending recording... (" + queue.getCurrentSize() + "/10)");
    }

 // Dentro de playCommands()
    public void playCommands() throws Exception {
        if (queue.isEmpty()) {
            System.out.println("Não há gravação para ser reproduzida."); // ✅ mensagem corrigida
            return;
        }

        System.out.println("Reproduzindo gravação...");
        Queue<String> tempQueue = new Queue<>(10); // ✅ manter comandos

        while (!queue.isEmpty()) {
            String command = queue.dequeue();
            System.out.println(command); // ✅ sempre imprime o comando

            try {
                if (command.contains("=")) {
                    variableManager.processAssignment(command);
                } else if (command.length() == 1 && Character.isLetter(command.charAt(0))) {
                    char var = Character.toUpperCase(command.charAt(0));
                    if (variableManager.isDefined(var)) {
                        System.out.println(var + " = " + variableManager.getValue(var));
                    } else {
                        System.out.println("Erro: variável " + var + " não definida.");
                    }
                } else if (command.equalsIgnoreCase("VARS")) {
                    variableManager.listVariables();
                } else if (command.equalsIgnoreCase("RESET")) {
                    variableManager.resetVariables();
                    System.out.println("Variáveis reiniciadas.");
                } else {
                    try {
                        equationEvaluator.setReceivedEquation(command);
                        equationEvaluator.convertEquation();
                        double result = equationEvaluator.expressionCalculator();
                        System.out.printf("%.0f\n", result); // ✅ saída sem casas decimais, como no exemplo
                    } catch (Exception e) {
                        System.out.println("Erro: operador inválido."); // ✅ mensagem padrão para falha de expressão
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

            tempQueue.enqueue(command); // ✅ regrava no final para manter a gravação após o play
        }

        queue = tempQueue; // ✅ restaura gravação após execução
    }


    public void clearCommands() {
        if (queue.isEmpty()) {
            System.out.println("The queue is already empty.");
            return;
        }

        queue.clear();
        System.out.println("Recording deleted..");
    }
}
