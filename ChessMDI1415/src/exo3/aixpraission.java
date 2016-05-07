package exo3;

import java.util.*;

interface Expression {

	public void interpret(StringBuilder context);
}

class TerminalExpressionX implements Expression {
	private String conv;

	public TerminalExpressionX(StringBuilder s) {
		conv = "" + (Character.getNumericValue(s.charAt(0)) - 10);
	}

	@Override
	public void interpret(StringBuilder s) {
		s = s.append(new StringBuilder(conv));
	}
}

class TerminalExpressionY implements Expression {
	private String conv;

	public TerminalExpressionY(StringBuilder s) {
		conv = "" + (Character.getNumericValue(s.charAt(1)) - 1);
	}

	@Override
	public void interpret(StringBuilder s) {
		s = s.append(new StringBuilder(conv));
	}
}

class Parser {
	private LinkedList<Expression> exp = new LinkedList<Expression>();

	public Parser(StringBuilder s) {
		exp.add(new TerminalExpressionX(s));
		exp.add(new TerminalExpressionY(s));
	}

	public int evaluatex() {
		// on créé un context qui va contenir la chaine convertie
		StringBuilder context = new StringBuilder();
		// exp contient deux TerminalExpression (X et Y), qui contiennent la
		// conversion du caractere
		exp.get(0).interpret(context);
		// exp.get(1).interpret(context);
		return Character.getNumericValue(context.toString().charAt(0));
	}

	public int evaluatey() {
		// on créé un context qui va contenir la chaine convertie
		StringBuilder context = new StringBuilder();
		// exp contient deux TerminalExpression (X et Y), qui contiennent la
		// conversion du caractere
		// exp.get(0).interpret(context);
		exp.get(1).interpret(context);
		return Character.getNumericValue(context.toString().charAt(0));
	}

}