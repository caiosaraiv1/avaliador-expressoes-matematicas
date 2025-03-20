package apl1;

public class Main {
 // PRECISA AINDA FAZER UMA VERIFICACAO DE SE A VARIAVEL JA ESTA SETADA RSRS XESQUE DELE 
	public static void main(String[] args) {
		expressaoValida("(#+A+B)");
	}
	public static void expressaoValida(String a){
		int firstP = 0, lastP = 0;
		boolean ultimoOperador = false;
		int i = 0;

		if(a.length() == 0 || // Validacao para ver se a expressao estavazia
		isOperator(a.charAt(0)) || isOperator(a.charAt(a.length()-1))) { // verifica se o primeiro e ultimo eh operador
			System.out.println("ih deu ruim");
			return;
		}
		for (char letra : a.toCharArray()) {

			if(Character.isLetter(letra) && Character.isLetter(a.charAt(i + 1))){
				System.out.println("string nao pode mermao");
				return;
			}
			// Validacao se tiver caracteres diferentes doq for permitido ex: #,$,%.
			if(!Character.isLetterOrDigit(letra) && !isOperator(letra) && letra != '(' && letra != ')' && letra != ' ') {
				System.out.println("Expressão inválida: caractere inválido detectado.");
				return;
			} 			
            if(letra == '('){ // Contar o parenteses para verificar
                firstP++;
				if(isOperator(a.charAt(i+1))){
					System.out.println("tem operador depois do (");
					return;
				}
				ultimoOperador = true; //teste doido pra evitar uns negocio como (+A+B)
            }
			if(letra == ')'){ // Contar o parenteses para verificar 
				lastP++;
				ultimoOperador = false;
			}
			// verifica os operadores
			/* if(!isOperator(letra)){
				System.out.println("nao e um operador: " + letra);
			} */
			if (isOperator(letra)) {
				if (ultimoOperador) {
					System.out.println("Expressão inválida: operadores seguidos.");
					return;
				}
				ultimoOperador = true;
			} else {
				ultimoOperador = false;
			}
			i++;
		}
		if(firstP != lastP){
			// A expressão tem um valor de parenteses desigual
			System.out.println("tem muito parenteses");
		}
		System.out.println("deu certo eba");
	}
	private static boolean isOperator(char c){
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
	}
}
