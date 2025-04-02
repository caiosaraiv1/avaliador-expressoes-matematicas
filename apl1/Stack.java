package apl1;

// Implementação da pilha
public class Stack<T> {
    private static final int MAX_SIZE = 100; // Tamanho máximo da pilha
    private int top;
    private T array[];
    
    // Construtor que recebe o tamanho da pilha
    @SuppressWarnings("unchecked")
    public Stack(int size) {
        this.array = (T[]) new Object[size];
        this.top = -1;
    }
    
    // Construtor padrão que usa o tamanho máximo definido
    public Stack() {
        this(MAX_SIZE);
    }
    
    // Verifica se a pilha está vazia
    public boolean isEmpty() {
        return top == -1;
    }
    
    // Verifica se a pilha está cheia
    public boolean isFull() {
        return top == array.length - 1;
    }
    
    // Adiciona um elemento na pilha
    public void push(T element) {
        if (isFull()) {
            throw new IllegalArgumentException("Stack full.");
        }
        array[++top] = element;
    }

    // Retorna o elemento no topo da pilha sem removê-lo
    public T peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Stack empty.");
        }
        return array[top];
    }

    // Remove e retorna o elemento do topo da pilha
    public T pop() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Stack empty.");
        }
        return array[top--];
    }

    // Retorna a quantidade de elementos na pilha
    public int size() {
        return top + 1;
    }

    // Limpa a pilha, removendo todos os elementos
    public void clear() {
        top = -1;
    }
}
