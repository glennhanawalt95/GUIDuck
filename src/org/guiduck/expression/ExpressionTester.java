package org.guiduck.expression;

public class ExpressionTester {
	public static void main(String[] args) {
		String exp = "4 + 3 + 5 * (6 / 7 + (3 + 5))";
		ExpressionTree tree = new ExpressionTree(exp);
		System.out.println(tree.evaluate());
	}
}
