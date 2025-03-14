package apl1;
/*
 * Classe que irá gerencias as variáveis utilizadas.
 * Armazena as variáveis (A-Z) e seus respectivos valores.
 */
public class GerenciadorVariaveis {

	private final int MAX_SIZE = 26;
	private char[] vars;
	private double[] nums;
	private int index = 0;
	
	// Construtor padrão que inicializa os arrays de variaveis e valores
	public GerenciadorVariaveis() {
		this.vars = new char[MAX_SIZE];
		this.nums = new double[MAX_SIZE];
	}
	
	/*
	 * Irá processar uma string de formato 'X = 5'
	 * Valida o formato e chama o metodo addVarNum para armazenar a variavel
	 * Jogará uma exceção se o formato for inválido ou o valor não for numerico
	 */
	public void processarAtribuicao(String s) {
		// Remove os espaços e divide a string pelo sinal de igual
		String[] parts = s.replaceAll(" ", "").split("=");
		
		// Valida se tem o formato correto 'X = 5' e se a variavel é apenas um unico caracter
		if(parts.length != 2 || parts[0].length() != 1) {
			throw new IllegalArgumentException("Formato inválido. Use: 'variavel = numero'");
		}
		
		// Converte para maiusculo e obtem o caracter da variavel
		char var = Character.toUpperCase(parts[0].charAt(0));
		// Valida se está no intervalo de A-Z
		if (var < 'A' || var > 'Z') {
			throw new IllegalArgumentException("Vaviável inválida. Use: letras (A-Z)");
		}
		
		try {
			// Converte de string para double
			double num = Double.parseDouble(parts[1]);
			// Adiciona ou atualiza a variavel e seu valor
			addVarNum(var, num);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Valor inválido.");
		}
	}
	
	/*
	 * Adiciona uma nova variavel ou atualiza uma existente
	 * Lança exceção se o limite tiver sido excedido
	 */
	private void addVarNum(char var, double num) {
		// Verifica se a variavel ja existe e apenas atualiza seu valor caso sim
		for(int i = 0; i < index; i++) {
			if(vars[i] == var) {
				nums[i] = num;
				return;
			}
		}
		
		// Se a variavel não existir, adiciona em uma nova posição
		if(index < MAX_SIZE) {
			vars[index] = var;
			nums[index] = num;
			index++;
		} else {
			throw new IllegalArgumentException("Número máximo de variáveis atingido.");
		}
	}
	
	// Verifica se a variavel já existe
	public boolean isDefinida(char var) {
		for(int i = 0; i < index; i++) {
			if(vars[i] == var) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Obtem o valor associado a uma variavel
	 * Joga uma exceção se a variavel não estiver definida
	 */
	public double getValor(char var) {
		// Percorre o array de variaveis para encontrar o valor
		for(int i = 0; i < index; i++) {
			if(vars[i] == var) {
				return nums[i];
			}
		}
		
		throw new IllegalArgumentException("Variável " + var + " não definida.");
	}
	
	// Lista todas as variaveis definidas e seus valores
	public void listarVars() {
		// Verifica se tem variaveis definidas
		if(index == 0) {
			System.out.println("Nenhum variável definida.");
			return;
		}
		
		// Printa todas as variaveis e seus valores
		for(int i = 0; i < index; i++) {
			System.out.println(vars[i] + " = " + nums[i]);
		}
	}
	
	// Reseta todas as variaveis criando arrays novos e zerando o index;
	public void resetar() {
		this.vars = new char[MAX_SIZE];
		this.nums = new double[MAX_SIZE];
		this.index = 0;
	}
	
	// Retorna o numero atual de variaveis definidas
	public int getIndex() {
		return index;
	}
	
}
