package apl1;

//ISABELA

// Implementação da pilha
public class Pilha {
    private static int MAX_SIZE = 100;
    private int topoPilha;
    private int vetor[];
}

//Construtor padrão
public pilha(int tamanho){
    this.vetor = new int [tamanho];
    this.topoPilha = -1;
}

//Contrutor vazio
public pilha(){
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