
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
	public static Map<String, Integer> methods;
	//start/stop address of current loops
	public static Stack<LoopInfo> loopInfo;
	
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
	private static int run(int i) {
		String[] codeLine = code.line(i);
		return keywords.handle(codeLine, i);
	}

	/* Constructs all fields */
	private static void initializeFields() {
		keywords = new KeywordHandler();
		expressions = new ExpressionEvaluator();
		methods = new TreeMap<String, Integer>();
		context = new Stack<Context>();
		loopInfo = new Stack<LoopInfo>();
		context.push(new Context());
		code = new Code("test");
		line = 0;
	}
	
	/* Runs a loop; may recursively run nested loops. Returns control to main
	 * at where loop ends */
	public static int runLoop() {
		LoopInfo loop = loopInfo.peek();
		for(int i = 0; i < loop.iter; i++) {
			for(int j = loop.start; j < loop.end; j++) {
				j = run(j);
				//don't read the lines in the loop twice
				if (!loop.equals(loopInfo.peek())) {
					j = loopInfo.peek().end;
					loopInfo.pop();
				}
			}
		}
		//don't return to main flow w/loop on stack;
		if (loopInfo.size() == 1) {
			loopInfo.pop();
		}
		return loop.end;
	}
	
	/* Skips the execution of a method at declaration time */
	public static void advanceToEnd() {
		while(!code.line(line)[0].equals("end")) {
			line++;
		}
	}
	
	public static boolean startsWith(int index, String term) {
		return code.line(index)[0].equals(term);
	}
	
	/* Runs a method */
	public static void runMethod(String name) {
		// InitParms method needed: break up parameters, evaluate, match to formal params
		context.push(new Context()); 
		int methodLine = methods.get(name) + 1;
		while(!code.line(methodLine)[0].equals("end")) {
			methodLine = run(methodLine);
			methodLine++;
		}	
		context.pop();
	}
}
