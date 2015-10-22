import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Fully functional* integer expression parser
//
//* not thouroughly tested
//	not set up to process variables
//  not set up to process strings
// 	needs to be able to call methods-->do this first?
//	can we reuse for booleans?


public class ExpEvalTestMain {
	public static void main(String[] args) {
		String[] code = format("1+(((2+5-6)+3)*5)+1");
		System.out.println(finalEvaluate(code));
	}
	
	/* Returns String[] processed into tokens */
	private static String[] format(String string) {
		String[] specialChars = {"(", ")", "+", "-", "*", "/", "%", "^"};
		for(String item: specialChars) {
			string = string.replace(item, " " + item + " ");
		}
		return string.trim().split("\\s+");
	}

	/* Returns the result of evaluating an iteger expression in the form of an array where each
	 * item (numbers, parens, operators) is an array index. Needs support for variables and method calls
	 * (do method calls later!) */
	public static Integer finalEvaluate(String[] expression) {
		List<String[]> subExp = splitSignificant(expression);
		int result = evaluate(subExp.get(0));
		//Because it will always be <exp> <op> <exp>
		for(int i = 2; i < subExp.size(); i += 2) {
			result = operation(result, subExp.get(i - 1), evaluate(subExp.get(i)));
		}
		return result;
	}
	
	/* Returns result of applying the passed operator (should be 1 element array) to result and i */
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


	/* Returns value of passed item represented as array. Returns immediate value if
	 * the item is an atom, otherwise recursively break down to atoms
	 * Currnet implementation only supports ints */
	public static int evaluate(String[] expression) {
		//ONLY WORKS FOR INTEGERS
		if (expression.length == 1) {
			return Integer.parseInt(expression[0]);//DATA
		} else {
			return finalEvaluate(expression);
		}
	}
	
	/* Splits an array into a list of arrays of characters, operators, and un-nested paren statements */
	public static List<String[]> splitSignificant(String[] expression) {
		int index = 0;
		int unAddedEnd = 0;
		int unAddedStart = -1;
		List<String[]> result = new ArrayList<String[]>();
		while(index < expression.length) {
			if(expression[index].equals("(")) {
				addIfValid(result, expression, unAddedStart, unAddedEnd);
				int match = findMatch(index + 1, expression);
				append(result, expression, index + 1, match);
				index = match;
				unAddedStart = match;
				unAddedEnd = unAddedStart;
			}
			if(isOperator(expression[index])) {
				addIfValid(result, expression, unAddedStart, unAddedEnd);
				append(result, expression, index, index + 1);
				unAddedStart = index;
				unAddedEnd = index;
			}
			index++;
			unAddedEnd++;
		}
		addIfValid(result, expression, unAddedStart, expression.length);
		
		//debug print
		for(String[] arr: result) { System.out.println(Arrays.toString(arr)); }
		
		return result;
	}
	
	/* Adds sub-expression array to result list, if array is not empty */
	public static void addIfValid(List<String[]> result, String[] expression, int start, int end) {
		if (end > start + 1) {
			append(result, expression, start + 1, end);
		}
	}
	
	/* Adds sub-expression array to result list */
	private static void append(List<String[]> result, String[] expression,
			int start, int end) {
		result.add(Arrays.copyOfRange(expression, start, end));
	}

	/* Boolean return representing if a String is a supported operator */
	private static boolean isOperator(String string) {
		return string.equals("+") ||
		       string.equals("-") ||
		       string.equals("*") ||
		       string.equals("%") ||
		       string.equals("^") ||
		       string.equals("/");
	}

	/* Iterates through an array of items, to find closing parenthese for passed index */
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
