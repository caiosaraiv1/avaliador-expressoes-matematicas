package apl1;

/*
 * Classe responsável por gerenciar os comandos recebidos
 */
public class GerenciadorComandos {
	
	private GerenciadorVaiaveis gerenciador; 
	private Fila fila;
	private boolean gravando = false;
	
	// Construtor que irá inicilizar o GerenciadorVariaveis e a Fila
	public GerenciadorComandos() {
		gerenciador = new GerenciadorVaiaveis();
		fila = new Fila();
	}
	
	/*
	 * Método para verificar se o comando fornecido é valido
	 * Se não for lança uma exceção
	 */
	public boolean verificarComando(String s) {
	    String[] comandosValidos = {"VARS", "RESET", "REC", "STOP", "PLAY", "ERASE", "EXIT"};
		
	    // Verifica se é valido
		for(String comando: comandosValidos) {
			if(s.equalsIgnoreCase(comando))
				return true;
		}
	    
		throw new IllegalArgumentException("Comando inválido: " + s);
	}
	
	/*
	 * Método para executar o comando passado
	 */
	public void executarComando(String s) {
		
		verificarComando(s);
		
		switch (s) {
			case "VARS" :
				gerenciador.listarVars();
				break;
			case "RESET" :
				gerenciador.resetar();
				break;
			case "REC":
                iniciarGravacao();
				break;
			case "STOP":
                pararGravacao();
				break;
			case "PLAY":
				reproduzirComandos();
				break;
			case "ERASE":
				// TODO
				break;
			case "EXIT":
				System.out.println("Saindo do programa...");
				System.exit(0);
				break;
			default:
				throw new IllegalArgumentException("Comando inválido.");
		
		}
	}	
	
	/*
	 * Método para iniciar a gravação de comandos
	 * Se já estiver gravando, lança uma exceção
	 */
	public void iniciarGravacao() {
		// Se já estiver gravando, lança uma exceção
		if(gravando) {
			throw new IllegalArgumentException("Já está gravando.");
			return;
		}
		
		gravando = true;
		System.out.println("Iniciando gravação... (REC: 0/10)");
	}
	
	/*
	 * Método para gravar um comando na fila durante a gravação
	 * Se a gravação não foi iniciada, lança uma exceção
	 * Se o comando for inválido ou a fila estiver cheia, a gravação é interrompida
	 */
	public void gravarComando(String comando) {
	    // Lança exceção se a gravação não foi iniciada
		if(!gravando) {
		    throw new IllegalStateException("Erro: gravação não iniciada.");
		}
		
		// Verifica se o comando é um dos comandos inválidos para gravação
		if(comando.equalsIgnoreCase("REC") || 
			comando.equalsIgnoreCase("STOP") || 
			comando.equalsIgnoreCase("PLAY") || 
			comando.equalsIgnoreCase("ERASE")) {
                System.out.println("Erro: comando inválido para gravação.");
                return;
        }
		// Verifica se a fila está cheia. Se estiver, a gravação é interrompida
		if(fila.isFull()) {
			System.out.println("Limite de comandos atingidos. Parando a gravação...");
			pararGravacao();
			return;
		}
		
		/*
		 * Aqui será verificado se o operador é valido
		 * Essa validação depende da implementação do avaliador de equações
		 * 
		 * EXEMPLO: A % B -> Erro: operador inválido.
		 * 
		 * TODO: Implementar a validação do operador 
		 *
		if(TODO) {
			TODO
			System.out.println("Erro: operador inválido.");
		}
		*/
		
		// Se o comando for válido, adiciona a fila de comandos
		fila.enqueue(comando);
		System.out.println("(REC: " + filaComandos.size() + "/10) " + comando)
	}
	
	/*
	 * Método para parar a gravação de comandos.
	 * Se a gravação não foi iniciada, lança uma exceção.
	 */
	public void pararGravacao() {
		if(!gravando) {
		    throw new IllegalStateException("Erro: gravação não iniciada.");
		}
		gravando = false;
		System.out.println("Encerrando gravação... (" + filaComandos.size() + "/10)");
	}
	
	/*
	 * Método para reproduzir os comandos gravados
	 * Exibe os comandos na ordem em que foram gravados
	 */
	public void reproduzirComandos() {
        System.out.println("Reproduzindo gravação...");
        while(!fila.isEmpty()) {
        	String comando = fila.dequeue();
        	System.out.println(comando);
        }
        /* TODO: Implementar a reinicialização das variáveis após a reprodução.
        System.out.println("Variáveis reiniciadas."); */
	}
}


