package apl1;

public class Main {

	public static void main(String[] args) {
		expressaoValida("A)B+A)");
	}
	public static void expressaoValida(String a){
		int firstP = 0, lastP = 0;
		if(a.charAt(0))
		for (char letra : a.toCharArray()) {
			// Ignorar as letras
			if(Character.isAlphabetic(letra)){
				continue;
			} 			
            if(letra == '('){ // Contar o parenteses para verificar
                firstP++;
            }
			if(letra == ')'){ // Contar o parenteses para verificar 
				lastP++;
			}
			// verifica os operadores
			if(!isOperator(letra)){
				System.out.println("nao e um operador: " + letra);
			}
		}
		if(firstP != lastP){
			// A expressão tem um valor de parenteses desigual
			System.out.println("tem muito parenteses");
		}
	}
	private static boolean isOperator(char c){
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '(' || c == ')';
	}
}
