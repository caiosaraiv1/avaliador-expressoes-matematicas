package apl1;

// Implementação da pilha
public class PilhaString {
	private static int MAX_SIZE = 100;
	private int topoPilha;
	private String vetor[];
	
	//Contrutor vazio
	public PilhaString(){
		this(MAX_SIZE);
	}
	
	//Construtor padrão
	public PilhaString(int tamanho){
	    this.vetor = new String [tamanho];
	    this.topoPilha = -1;
	}
	
	
	//Verificação se pilha vazia
	public boolean isEmpty(){
	    if (topoPilha == -1){
	        return true;
	    }
	    else{
	        return false;
	    }
	}
	
	//Verificação se pilha cheia
	public boolean isFull(){
	    if (topoPilha == tamanho){
	        return true;
	    }
	    else {
	        return false;
	    }
	}
	
	//Adicionar valor na pilha
	public void push(String texto){
	    if (!isFull()){
			this.topoPilha ++;
			this.vetor[topoPilha] = texto; 
		}
		else{
			throw new Exception("Pilha cheia!");
		}
	}

	//Printar valor topo pilha
	public String topo() throws Exception{
		if(!isEmpty()){
			return this.vetor[topoPilha];
		}
		else{
			throw new Exception("Pilha vazia!");
		}
	}

	//Inserir novo valor
	public String pop() throws Exception{
		if(!isEmpty()){
			String charRemovido = this.vetor[topoPilha];
			this.vetor[topoPilha] = 0.0;
			this.topoPilha--;
			return charRemovido;
		}
		else{
			throw new Exception("Pilha vazia!");
		}
	}

	//Qtd de elementos na pilha
	public int size(){
		return (topoPilha + 1);
	}

	//Limpar pilha
	public void clear(){
		while (topoPilha >= 0){
			this.vetor[topoPilha] = 0.0;
			this.topoPilha--;
		}
	}
}