package apl1;

/*
 * Classe responsável por gerenciar os comandos recebidos
 */
public class CommandManager {
    
    private VariableManager variableManager; // Gerenciador de variáveis
    private Queue<String> queue; // Fila para armazenar comandos gravados
    private boolean recording = false; // Indica se a gravação está ativa
    private EquationEvaluator equationEvaluator; // Avaliador de expressões matemáticas

    // Construtor que inicializa o Gerenciador de Variáveis, a Fila e o Avaliador
    public CommandManager() {
        variableManager = new VariableManager();
        queue = new Queue<>();
        equationEvaluator = new EquationEvaluator();
    }
    
    /*
     * Método para verificar se o comando fornecido é válido
     * Se não for, lança uma exceção
     */
    public boolean isValidCommand(String command) {
        String[] validCommands = {"VARS", "RESET", "REC", "STOP", "PLAY", "ERASE", "EXIT"};
        
        for (String validCommand : validCommands) {
            if (command.equalsIgnoreCase(validCommand)) {
                return true;
            }
        }
        throw new IllegalArgumentException("Invalid Command: " + command);
    }
    
    /*
     * Método para executar o comando passado
     */
    public void executeCommand(String command) {
        isValidCommand(command);
        
        switch (command) {
            case "VARS":
                variableManager.listVariables();
                break;
            case "RESET":
                variableManager.resetVariables();
                break;
            case "REC":
                startRecording();
                break;
            case "STOP":
                stopRecording();
                break;
            case "PLAY":
                playCommands();
                break;
            case "ERASE":
                clearCommands();
                break;
            case "EXIT":
                System.out.println("Exiting the program...");
                System.exit(0);
                break;
            default:
                throw new IllegalArgumentException("Invalid Command.");
        }
    }
    
    /*
     * Método para iniciar a gravação de comandos
     * Se já estiver gravando, lança uma exceção
     */
    public void startRecording() {
        if (recording) {
            throw new IllegalArgumentException("Already recording.");
        }
        recording = true;
        System.out.println("Starting recording... (REC: 0/10)");
    }
    
    /*
     * Método para gravar um comando na fila durante a gravação
     */
    public void recordCommand(String command) {
        if (!recording) {
            throw new IllegalStateException("Recording not started.");
        }
        
        if (command.equalsIgnoreCase("REC") || 
            command.equalsIgnoreCase("STOP") || 
            command.equalsIgnoreCase("PLAY") || 
            command.equalsIgnoreCase("ERASE")) {
            System.out.println("Invalid command for recording.");
            return;
        }
        
        if (queue.isFull()) {
            System.out.println("Command limit reached. Stopping recording...");
            stopRecording();
            return;
        }
        
        try {
            equationEvaluator.validateExpression(command);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
        
        queue.enqueue(command);
        System.out.println("(REC: " + queue.getSize() + "/10) " + command);
    }
    
    /*
     * Método para parar a gravação de comandos.
     */
    public void stopRecording() {
        if (!recording) {
            throw new IllegalStateException("Recording not started.");
        }
        recording = false;
        System.out.println("Ending recording... (" + queue.getSize() + "/10)");
    }
    
    /*
     * Método para reproduzir os comandos gravados
     */
    public void playCommands() {
        if (queue.isEmpty()) {
            System.out.println("There is no recording to play.");
            return;
        }
        
        System.out.println("Playing recording...");
        while (!queue.isEmpty()) {
            String command = queue.dequeue();
            System.out.println(command);
        }
    }
    
    /*
     * Método para apagar os comandos gravados
     */
    public void clearCommands() {
        if (queue.isEmpty()) {
            System.out.println("The queue is already empty.");
            return;
        }
        
        queue.clear();
        System.out.println("Recording deleted..");
    }
}
