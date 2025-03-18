package apl1;

public class Fila {

    private String[] fila;
    
    // Indice do primeiro elemento da fila
    private int front = 0;
    
    // Indice do proximo local disponivel para inserção
    private int rear = 0;

    // Cria uma fila com 10 elementos 
    public Fila() {
        this(10);
    }

    public Fila(int tamanho) {
        if (tamanho <= 0) {
            throw new IllegalArgumentException("Tamanho deve ser maior que 0.");
        }
        fila = new String[tamanho];
    }

    // Método que verifica se a fila está vazia (front == rear indica que a fila está vazia).
    public boolean isEmpty() {
        return front == rear;
    }

    /*
     * Método para adicionar um elemento a fila
     * Lança exceção se a fila estiver cheia
     */
    public void enqueue(String e) {
        if (this.isFull()) {
            throw new IllegalStateException("Fila cheia.");
        }
        fila[rear] = e; // Adiciona o elemento na posição de rear
        rear = (rear + 1) % fila.length; // Atualiza o indice rear, realizando um "loop" circular
    }

    /*
     * Método para remover e retornar o primeiro elemento da fila
     * Lança exceção se a fila estiver vazia
     */
    public String dequeue() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Fila vazia.");
        }
        String valor = fila[front]; // Obtem o valor do primeiro elemento da fila
        fila[front] = null; // Remove o elemento
        front = (front + 1) % fila.length; // Atualiza o indice front, realizando um "loop" circular
        return valor; // Retorna o elemento removido
    }

    /*
     * Método que retorna o primeiro elemento da fila sem remove-lo
     * Lança exceção se a fila estiver vazia
     */
    public String front() {
        if (this.isEmpty()) {
        	throw new IllegalStateException("Fila vazia.");
        }
        return fila[front];
    }

    /*
     * Método que retorna o último elemento da fila sem remove-lo
     * Lança exceção se a fila estiver vazia
     */
    public String rear() {
        if (rear == 0) {
        	throw new IllegalStateException("Fila vazia.");
        }
        return fila[(rear - 1 + fila.length) % fila.length]; //// Retorna o último elemento da fila.
    }

    // Método que verifica se a fila está cheia (front e rear estão próximos, indicando que não há espaço)
    public boolean isFull() {
        return (rear + 1) % fila.length == front; //// Verifica se o próximo índice de rear é igual a front
    }

    // Método que retorna o tamanho total da fila
    public int size() {
        return fila.length;
    }
}
