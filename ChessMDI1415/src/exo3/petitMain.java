package exo3;

class petitMain {
	public static void main(String[] args) {
		String expression = "f9";
		Parser p = new Parser(new StringBuilder(expression));
		System.out.println("'" + expression + "' equals " + p.evaluatex() + " "
				+ p.evaluatey() + " en int ");
	}
}
