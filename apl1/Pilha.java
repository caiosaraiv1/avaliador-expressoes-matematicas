package apl1;

//ISABELA

// Implementação da pilha
public class Pilha {
	private static int MAX_SIZE = 100;
	private int topoPilha;
	private double vetor[];
	
	
	//Construtor padrão
	public Pilha(int tamanho){
	    this.vetor = new double [tamanho];
	    this.topoPilha = -1;
	}
	
	//Contrutor vazio
	public Pilha(){
	    this(MAX_SIZE);
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
	public void push(double num){
	    if (!isFull()){
			this.topoPilha ++;
			this.vetor[topoPilha] = num; 
		}
		else{
			throw new Exception("Pilha cheia!");
		}
	}

	//Printar valor topo pilha
	public double topo() throws Exception{
		if(!isEmpty()){
			return this.vetor[topoPilha];
		}
		else{
			throw new Exception("Pilha vazia!");
		}
	}

	//Inserir novo valor
	public double pop() throws Exception{
		if(!isEmpty()){
			double numRemovido = this.vetor[topoPilha];
			this.vetor[topoPilha] = 0.0;
			this.topoPilha--;
			return numRemovido;
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