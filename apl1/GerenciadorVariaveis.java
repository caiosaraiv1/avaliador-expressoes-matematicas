package apl1;

// Gerencia as variaveis definidas pelos usuario
public class GerenciadorVariaveis {

	private String var; 
	private Double num;
	private final int MAX_SIZE = 26;
	private String[] vars;
	private double[] nums;
	private int index = 0;
	
	// construtor padrao
	public GerenciadorVariaveis() {
		this.vars = new String(MAX_SIZE);
		this.nums = new double(MAX_SIZE);
	}
	
	
	public GerenciadorVariaveis(String s) {
		// chama o construto padrao
		this();
		
		/// separa a string a partir do =
		String[] parts = s.replaceAll(" ", "").toLowerCase().split("=");
		if(parts.length == 2) {
			this.var = parts[0];
			this.num = Double.parseDouble(parts[1]);			
			addVarNum();
		} else {
			// joga exceção se estiver no formato invalido
            throw new IllegalArgumentException("Formato inválido. Use: 'variavel = numero'");
		}
	}
	
	// metodo para adicionar as variaveis e numeros no array
	private void addVarNum() {
		if(index < MAX_SIZE) {
			this.vars[index] = this.var;
			this.nums[index] = this.num;
			this.index++;			
		} else {
			// se tiver passado do tamanho max pertido joga exceção
            throw new IllegalStateException("Número máximo de variáveis atingido.");
		}
	}
	
	public int getNum(int i) {
		return nums[i];
	}
	
	public String getVar(int i) {
		return vars[i]; 
	}
	
	
}
