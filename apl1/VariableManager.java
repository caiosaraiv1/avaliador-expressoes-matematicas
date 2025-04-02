package apl1;

/*
 * Classe que gerencia as variáveis utilizadas.
 * Armazena as variáveis (A-Z) e seus respectivos valores.
 */
public class VariableManager {

    private final int MAX_SIZE = 26; // Número máximo de variáveis suportadas
    private char[] variables; // Array para armazenar os nomes das variáveis
    private double[] values; // Array para armazenar os valores das variáveis
    private int count = 0; // Contador de variáveis definidas

    // Construtor padrão que inicializa os arrays de variáveis e valores
    public VariableManager() {
        this.variables = new char[MAX_SIZE];
        this.values = new double[MAX_SIZE];
    }

    /*
     * Processa uma string no formato 'X = 5'.
     * Valida o formato e chama o método addVariable para armazenar a variável.
     * Lança uma exceção se o formato for inválido ou o valor não for numérico.
     */
    public void processAssignment(String input) {
        // Remove os espaços e divide a string pelo sinal de igual
        String[] parts = input.replaceAll(" ", "").split("=");

        // Valida se tem o formato correto 'X = 5' e se a variável tem apenas um caractere
        if (parts.length != 2 || parts[0].length() != 1) {
            throw new IllegalArgumentException("Invalid format. Use: 'variable = number'");
        }

        // Converte para maiúsculo e obtém o caractere da variável
        char variable = Character.toUpperCase(parts[0].charAt(0));
        
        // Valida se está no intervalo de A-Z
        if (variable < 'A' || variable > 'Z') {
            throw new IllegalArgumentException("Invalid variable. Use letters (A-Z)");
        }

        try {
            // Converte de string para double
            double value = Double.parseDouble(parts[1]);
            // Adiciona ou atualiza a variável e seu valor
            addVariable(variable, value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid value.");
        }
    }

    /*
     * Adiciona uma nova variável ou atualiza uma existente.
     * Lança exceção se o limite de variáveis for excedido.
     */
    private void addVariable(char variable, double value) {
        // Verifica se a variável já existe e apenas atualiza seu valor
        for (int i = 0; i < count; i++) {
            if (variables[i] == variable) {
                values[i] = value;
                return;
            }
        }

        // Se a variável não existir, adiciona em uma nova posição
        if (count < MAX_SIZE) {
            variables[count] = variable;
            values[count] = value;
            count++;
        } else {
            throw new IllegalArgumentException("Maximum number of variables reached.");
        }
    }

    // Verifica se a variável já foi definida
    public boolean isDefined(char variable) {
        for (int i = 0; i < count; i++) {
            if (variables[i] == variable) {
                return true;
            }
        }
        return false;
    }

    /*
     * Obtém o valor associado a uma variável.
     * Lança uma exceção se a variável não estiver definida.
     */
    public double getValue(char variable) {
        // Percorre o array de variáveis para encontrar o valor
        for (int i = 0; i < count; i++) {
            if (variables[i] == variable) {
                return values[i];
            }
        }

        throw new IllegalArgumentException("Variable " + variable + " not defined.");
    }

    // Lista todas as variáveis definidas e seus valores
    public void listVariables() {
        // Verifica se há variáveis definidas
        if (count == 0) {
            throw new IllegalArgumentException("No variables defined.");
        }

        // Exibe todas as variáveis e seus valores
        for (int i = 0; i < count; i++) {
            System.out.println(variables[i] + " = " + values[i]);
        }
    }

    // Reseta todas as variáveis criando arrays novos e zerando o contador
    public void resetVariables() {
        this.variables = new char[MAX_SIZE];
        this.values = new double[MAX_SIZE];
        this.count = 0;
    }

    // Retorna o número atual de variáveis definidas
    public int getVariableCount() {
        return count;
    }
}