package apl1;

// Será feito as validações, conversões, avaliações e lógica do calculo
public class EquationEvaluator {
	
	private VariableManager variableManager = new VariableManager();
	private String receivedEquation;
	@SuppressWarnings("unused")
	private String convertedEquation;
	private int equationLength;
	private Stack<String> stack;

	// Contrutor vazio
	public EquationEvaluator() {
		this(""); // Chama o segundo construtor
	}

	// Contrutor
	public EquationEvaluator(String equacaoRecebida) {
		this.variableManager = new VariableManager();
		this.receivedEquation = removeSpacesAndUppercase(receivedEquation);
		validateExpression(receivedEquation); // Valida a expressão antes de processá-la
        	this.convertedEquation = "";
       		this.equationLength = receivedEquation.length();
        	this.stack = new Stack<>();
	}

	// Define a equação recebida
    public void setReceivedEquation(String receivedEquation) {
	this.receivedEquation = receivedEquation;
        validateExpression(receivedEquation);
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
    public void validateExpression(String expression) {
        if (expression.isEmpty() || isOperator(expression.charAt(0)) || isOperator(expression.charAt(expression.length() - 1))) {
            throw new IllegalArgumentException("Invalid Expression.");
        }

        int openParentheses = 0, closeParentheses = 0;
        boolean lastWasOperator = false;
        char[] chars = expression.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char letter = chars[i];

            if (!variableManager.isDefined(letter)) {
                throw new IllegalArgumentException("Invalid Expression: Undefined variable");
            }

            if (Character.isLetter(letter) && i < chars.length - 1 && Character.isLetter(chars[i + 1])) {
                throw new IllegalArgumentException("Invalid Expression: String detected.");
            }

            if (isInvalidChar(letter)) {
                throw new IllegalArgumentException("Invalid Expression: Operator not allowed detected.");
            }

            if (letter == '(') {
                openParentheses++;
                if (i < chars.length - 1 && isOperator(chars[i + 1])) {
                    throw new IllegalArgumentException("Invalid Expression: Has an operator after the parentheses");
                }
            } else if (letter == ')') {
                closeParentheses++;
                lastWasOperator = false;
            } else if (isOperator(letter)) {
                if (lastWasOperator) {
                    throw new IllegalArgumentException("Invalid Expression: Consecutive operators.");
                }
                lastWasOperator = true;
            } else {
                lastWasOperator = false;
            }
        }

        if (openParentheses != closeParentheses) {
            throw new IllegalArgumentException("Invalid Expression: Unequal number of parentheses");
        }
    }

    // Verifica se um caractere é inválido
    private boolean isInvalidChar(char c) {
        return !Character.isLetterOrDigit(c) && !isOperator(c) && c != '(' && c != ')' && c != ' ';
    }

    // Verifica se um caractere é um operador matemático
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Verifica se o operador no topo da pilha tem maior precedência
    private boolean isTopOperatorHigher(String comparedOperator) throws Exception {
        if (comparedOperator.equals("^")) return true;
        if (comparedOperator.equals("*") || comparedOperator.equals("/")) {
            return stack.peek().equals("^");
        }
        return false;
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
            this.stack.pop();
            continue;
        }

        if (isOperator(c.charAt(0))) {
            while (!this.stack.isEmpty() && isTopOperatorHigher(c)) {
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
    // Calcula a equação em posfixa
    public Double expressionCalculator(){
        for(int i = 0; i < this.equationLength; i++){
            char c = this.convertedEquation.charAt(i);
            if(!isOperator(c)){
                double value = this.VariableManager.getValue(c); //Pega o valor numerico
                this.stack.push(value); //empilha
            }
            else{
                double num2 = this.stack.pop();
                double num1 = this.stack.pop();
                if (c == '/'){
                    double result = num1/num2;
                    this.stack.push(result);
                }
                if (c == '*'){
                    double result = num1*num2;
                    this.stack.push(result);
                }
                if (c == '+'){
                    double result = num1+num2;
                    this.stack.push(result);
                }
                if (c == '-'){
                    double result = num1-num2;
                    this.stack.push(result);
                }
            }
        }
        return this.stack.topo(); //Retorna o resultado
    }
}
