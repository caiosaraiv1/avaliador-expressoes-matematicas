package apl1;

// Será feito as validações, conversões, avaliações e lógica do calculo
public class EquationEvaluator {
	
	private VariableManager variableManager;
	private String receivedEquation;
	private String convertedEquation;
	private int equationLength;
	private Stack<String> stack;
	private Stack<Double> stackDouble;

	// Construtor vazio
	public EquationEvaluator() {
		this(""); // Chama o segundo construtor
	}
	
	// Construtor com equação
	public EquationEvaluator(String receivedEquation) {
		this.variableManager = new VariableManager();
	    this.receivedEquation = removeSpacesAndUppercase(receivedEquation); // Remove espaços antes
	    validateExpression(this.receivedEquation); // Agora valida a versão correta
	    this.convertedEquation = "";
	    this.equationLength = this.receivedEquation.length();
	    this.stack = new Stack<>();
	    this.stackDouble = new Stack<>();
	}
	
	// Construtor que recebe o gerenciador
	public EquationEvaluator(VariableManager variableManager) {
	    this.variableManager = variableManager; //  usa a mesma instância
	    this.receivedEquation = "";
	    this.convertedEquation = "";
	    this.stack = new Stack<>();
	    this.stackDouble = new Stack<>();
	}

	// Define a equação recebida
    public void setReceivedEquation(String receivedEquation) {
    	this.receivedEquation = removeSpacesAndUppercase(receivedEquation);
        validateExpression(receivedEquation);
        this.equationLength = this.receivedEquation.length();
        this.convertedEquation = "";
    }

    // Remove espaços da equação e a converte para letras maiúsculas
    private String removeSpacesAndUppercase(String equation) {
        String result = "";
        for (int i = 0; i < equation.length(); i++) {
            char c = equation.charAt(i);
            if (c != ' ') {
                result += String.valueOf(c).toUpperCase();
            }
        }
        return result;
    }

    // Valida se a equação está corretamente formatada
    public boolean validateExpression(String expression) {
    	if(expression.isEmpty()) return false;
    	
        if (isOperator(expression.charAt(0)) || isOperator(expression.charAt(expression.length() - 1))) {
            throw new IllegalArgumentException("Invalid Expression.");
        }

        int openParentheses = 0, closeParentheses = 0;
        boolean lastWasOperator = false;
        char[] chars = expression.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char letter = chars[i];

            if (Character.isLetter(letter) && i < chars.length - 1 && Character.isLetter(chars[i + 1])) {
                throw new IllegalArgumentException("String detected.");
            }

            if (isInvalidChar(letter)) {
                throw new IllegalArgumentException("Operator not allowed detected.");
            }

            if (letter == '(') {
                openParentheses++;
                if (i < chars.length - 1 && isOperator(chars[i + 1])) {
                    throw new IllegalArgumentException("Has an operator after the parentheses");
                }
            } else if (letter == ')') {
                closeParentheses++;
                lastWasOperator = false;
            } else if (isOperator(letter)) {
                if (lastWasOperator) {
                    throw new IllegalArgumentException("Consecutive operators.");
                }
                lastWasOperator = true;
            } else {
                lastWasOperator = false;
            }
        }

        if (openParentheses != closeParentheses) {
            throw new IllegalArgumentException("Unequal number of parentheses");
        }
        
        return true;
    }

    // Verifica se um caractere é inválido
    private boolean isInvalidChar(char c) {
        return !Character.isLetterOrDigit(c) && !isOperator(c) && c != '(' && c != ')' && c != ' ';
    }

    // Verifica se um caractere é um operador matemático
    public boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    //método de precedência dos operadores
    private int getPrecedence(String op) {
        switch (op) {
            case "^": return 3;
            case "*": case "/": return 2;
            case "+": case "-": return 1;
            default: return 0;
        }
    }

	// Converte equação de notação infixa para pós-fixa
	public void convertEquation() throws Exception {
	    for (int i = 0; i < this.equationLength; i++) {
	        String c = String.valueOf(this.receivedEquation.charAt(i));
	        
	        if (c.equals("(")) {	
	            this.stack.push(c);
	            continue;
	        }
	
	        if (c.equals(")")) {
	            while (!this.stack.isEmpty() && !this.stack.peek().equals("(")) {
	                this.convertedEquation += this.stack.pop();
	            }
	            this.stack.pop(); // remove o parêntese de abertura
	            continue;
	        }
	
	        if (isOperator(c.charAt(0))) {
	            while (!this.stack.isEmpty() && getPrecedence(this.stack.peek()) >= getPrecedence(c)) {
	                this.convertedEquation += this.stack.pop();
	            }
	            this.stack.push(c);
	            continue;
	        }
	
	        this.convertedEquation += c;
	    }

	    // Esvazia a pilha ao final
	    while (!this.stack.isEmpty()) {
	        String topo = this.stack.pop();
	        if (!topo.equals("(") && !topo.equals(")")) {
	            this.convertedEquation += topo;
	        }
	    }
	}

    // Calcula a equação em pós-fixa
    public Double expressionCalculator() {
        for(int i = 0; i < this.convertedEquation.length(); i++) {
            char c = this.convertedEquation.charAt(i);
            if (!isOperator(c)) {
                double value = this.variableManager.getValue(c); // Pega valor da variável
                this.stackDouble.push(value);
            } else {
                double num2 = this.stackDouble.pop();
                double num1 = this.stackDouble.pop();
                if (c == '/') this.stackDouble.push(num1 / num2);
                if (c == '*') this.stackDouble.push(num1 * num2);
                if (c == '+') this.stackDouble.push(num1 + num2);
                if (c == '-') this.stackDouble.push(num1 - num2);
            }
        }
        return this.stackDouble.peek(); // Retorna o resultado
    }
}
