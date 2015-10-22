import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
//Fully functional* integer expression parser
//
//* not thouroughly tested
//	not set up to process variables
//  not set up to process strings
// 	needs to be able to call methods-->do this first?
//	can we reuse for booleans?


public class ExpEvalTestMain {
	public static void main(String[] args) {
		String[] code = "( ( ( 2 + 5 - 6 ) + 3 ) * 5 ) + 1".split("\\s+");
		System.out.println(finalEvaluate(code));
	}

	
	public static Integer finalEvaluate(String[] expression) {
		List<String[]> subExp = splitSignificant(expression);
		int result = evaluate(subExp.get(0));
		//Because it will always be op exp op
		for(int i = 2; i < subExp.size(); i += 2) {
			result = operation(result, subExp.get(i - 1), evaluate(subExp.get(i)));
		}
		return result;
	}
	
	private static int operation(int result, String[] op, int i) {
		String operator = op[0];
		switch(operator) {
		case "+":
			return result + i;
		
		case "-":
			return result - i;
		
		case "*":
			return result * i;
		
		case "/":
			return result / i;
		
		case "%":
			return result % i;
		
		case "^":
			return (int) Math.pow(result, i);
		}
		//throw error
		System.out.println("couldn't find operator: " + operator);
		return -1;
	}


	//MAKE THIS WORK FOR DATA
	public static int evaluate(String[] expression) {
		//System.out.println(Arrays.toString(expression));
		if (expression.length == 1) {
			return Integer.parseInt(expression[0]);//DATA
		} else {
			return finalEvaluate(expression);
		}
	}
	
	//Split an array into a list of arrays, where characters, operators, and parens are separated out
	//ADD METHODS
	public static List<String[]> splitSignificant(String[] expression) {
		int index = 0;
		int unParenEnd = 0;
		int unParenStart = -1;
		List<String[]> result = new ArrayList<String[]>();
		while(index < expression.length) {
			if(expression[index].equals("(")) {
				if (unParenEnd > unParenStart + 1) {
					result.add(Arrays.copyOfRange(expression, unParenStart + 1, unParenEnd));
				}
				int match = findMatch(index + 1, expression);
				result.add(Arrays.copyOfRange(expression, index + 1, match));
				index = match;
				unParenStart = match;
				unParenEnd = unParenStart;
			}
			if(isOperator(expression[index])) {
				if (unParenEnd > unParenStart + 1) {
					result.add(Arrays.copyOfRange(expression, unParenStart + 1, unParenEnd));
				}
				result.add(Arrays.copyOfRange(expression, index, index+1));
				unParenStart = index;
				unParenEnd = index;
			}
			index++;
			unParenEnd++;
		}
		if(unParenStart + 1 < expression.length) 
		result.add(Arrays.copyOfRange(expression, unParenStart +  1, expression.length));
		for(String[] arr: result) {
			System.out.println(Arrays.toString(arr));
		}
		return result;
	}


	private static boolean isOperator(String string) {
		return string.equals("+") ||
		       string.equals("-") ||
		       string.equals("*") ||
		       string.equals("%") ||
		       string.equals("^") ||
		       string.equals("/");
	}


	private static int findMatch(int i, String[] expression) {
		int count = 0;
		while(!(i < expression.length && expression[i].equals(")") && count == 0)) {
			if (expression[i].equals(")")) {
				count--;
			} else if (expression[i].equals("(")) {
				count++;
			}
			i++;
		}
		return i;
	}
}
