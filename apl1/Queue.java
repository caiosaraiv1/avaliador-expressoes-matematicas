package apl1;

public class Queue<T> {

    private T[] queue;
    
    // Índice do primeiro elemento da fila
    private int frontIndex = 0;
    
    // Índice do próximo local disponível para inserção
    private int rearIndex = 0;

    // Cria uma fila com 10 elementos
    public Queue() {
        this(10);
    }

    @SuppressWarnings("unchecked")
	public Queue(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0.");
        }
        queue = (T[]) new Object[size];
    }

    // Método que verifica se a fila está vazia (frontIndex == rearIndex indica que a fila está vazia)
    public boolean isEmpty() {
        return frontIndex == rearIndex;
    }

    /*
     * Método para adicionar um elemento à fila
     * Lança exceção se a fila estiver cheia
     */
    public void enqueue(T element) {
        if (this.isFull()) {
            throw new IllegalStateException("Queue full.");
        }
        queue[rearIndex] = element; // Adiciona o elemento na posição de rearIndex
        rearIndex = (rearIndex + 1) % queue.length; // Atualiza o índice rearIndex, realizando um "loop" circular
    }

    /*
     * Método para remover e retornar o primeiro elemento da fila
     * Lança exceção se a fila estiver vazia
     */
    public T dequeue() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Queue empty.");
        }
        T value = queue[frontIndex]; // Obtém o valor do primeiro elemento da fila
        queue[frontIndex] = null; // Remove o elemento
        frontIndex = (frontIndex + 1) % queue.length; // Atualiza o índice frontIndex, realizando um "loop" circular
        return value; // Retorna o elemento removido
    }

    /*
     * Método que retorna o primeiro elemento da fila sem removê-lo
     * Lança exceção se a fila estiver vazia
     */
    public T peekFront() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Queue empty.");
        }
        return queue[frontIndex];
    }

    /*
     * Método que retorna o último elemento da fila sem removê-lo
     * Lança exceção se a fila estiver vazia
     */
    public T peekRear() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Queue empty.");
        }
        return queue[(rearIndex - 1 + queue.length) % queue.length]; // Retorna o último elemento da fila
    }

    // Método que verifica se a fila está cheia (frontIndex e rearIndex estão próximos, indicando que não há espaço)
    public boolean isFull() {
        return (rearIndex + 1) % queue.length == frontIndex; // Verifica se o próximo índice de rearIndex é igual a frontIndex
    }

    // Método que retorna o tamanho total da fila
    public int getSize() {
        return queue.length;
    }
    
    public int getCurrentSize() {
    	if(rearIndex >= frontIndex) {
    		return rearIndex - frontIndex;
    	}
    	
    	return queue.length - (rearIndex + frontIndex);
    }
    
    // Método para esvaziar a fila
    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }
}
