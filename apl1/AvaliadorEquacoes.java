package apl1;

// Será feito as validações, conversões, avaliações e lógica do calculo
public class AvaliadorEquacoes {
    GerenciadorVariaveis ger; 

    public AvaliadorEquacoes {
        ger = new GerenciadorVariaveis();
    }

	public static void main(String[] args) {
	}
	public void validExpression(String Expression){
		int firstP = 0, lastP = 0;
		boolean lastOperator = false;
		int letterPosition = 0;

		if(Expression.length() == 0 || // Valida se expressao esta vazia
		isOperator(Expression.charAt(0)) || isOperator(Expression.charAt(Expression.length()-1))) { // Verifica se o ultimo e primeiro sao operador
			throw IllegalArgumentException("Invalid Expression.");
		}
		for (char letter : Expression.toCharArray()) { // Faz a leitura da expressao

            if(ger.!isDefinida(letter)){ // Verifica se a variavel esta definida
                throw IllegalArgumentException("Invalid Expression: Indefined variable");
            }
			if(Character.isLetter(letter) && Character.isLetter(Expression.charAt(letterPosition + 1))){ // Verifica se eh String
				throw IllegalArgumentException("Invalid Expression: String detected.");
			}
			if(!Character.isLetterOrDigit(letter) && !isOperator(letter) && letter != '(' && letter != ')' && letter != ' ') { // Verifica se o operador eh valido
				throw IllegalArgumentException("Invalid Expression: Operator not allowed detected.");
			} 			
            if(letter == '('){ // Contagem de parenteses 
                firstP++;
				if(isOperator(Expression.charAt(letterPosition+1))){ // Detecta se possui operador pós parenteses
					throw IllegalArgumentException("Invalid Expression: Has an operator after the parentheses");
				}
			}
			if(letter == ')'){ // Contagem de parenteses
				lastP++;
				lastOperator = false;
			}
			if (isOperator(letter)) { // Detecta se possui operador seguido
				if (lastOperator) {
					throw IllegalArgumentException("Invalid Expression: Consecutive operators.");
				}
				lastOperator = true;
			} else {
				lastOperator = false;
			}
			letterPosition++;
		}
		if(firstP != lastP){ // Detecta quantidade desigual de parenteses
			throw IllegalArgumentException("Invalid Expression: Unequal number of parentheses");
        }
		System.out.println("Valid Expression");
	}
	private boolean isOperator(char c){  // Verifica se eh um operador valido
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
	}

}
