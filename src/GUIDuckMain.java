
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;


public class GUIDuckMain {
	//Rename ActionHandler or EventHandler?
	public static KeywordHandler keywords;
	//needs to be updated to prototype ExpEvalTestMain
	public static ExpressionEvaluator expressions;
	//variable -> value mapping
	public static Stack<Context> context;
	//source code
	public static Code code;
	//current line to be executed
	public static int line;
	//method name -> addess mapping
	public static Map<String, Method> methods;
	//start/stop address of current loops
	public static Stack<Loop> loops;
	
	public static void main(String[] args) {
		initializeFields();
		while (line < code.size) {
			line = run(line);
			line++;
		}
		//DEBUG PRINT OF METHOD-MAPPINGS
		System.out.println(methods);
	}
	
	/* Executes current line of code, returns int representing where to start further execution
	 * (return val will be different than line param if method definition, loop, or conditional */
	public static int run(int i) {
		String[] codeLine = code.line(i);
		return keywords.handle(codeLine, i);
	}

	/* Constructs all fields */
	private static void initializeFields() {
		keywords = new KeywordHandler();
		expressions = new ExpressionEvaluator();
		methods = new TreeMap<String, Method>();
		context = new Stack<Context>();
		context.push(new Context());
		code = new Code("test");
		line = 0;
	}
		
	/* Skips the execution of a method at declaration time */
	public static int advanceToEnd(int i) {
		while(!code.line(i)[0].equals("end")) {
			i++;
		}
		return i;
	}
	
	public static boolean startsWith(int index, String term) {
		return code.line(index)[0].equals(term);
	}
}
