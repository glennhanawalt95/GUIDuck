
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;


public class GUIDuckMain {
	public static KeywordHandler keywords;
	public static ExpressionEvaluator expressions;
	public static Stack<Context> context;
	public static Code code;
	public static int line;
	public static Map<String, Integer> methods;
	public static Stack<LoopInfo> loopInfo;
	
	public static void main(String[] args) {
		initializeFields();
		while (line < code.size) {
			run(line);
			line++;
		}
		//DEBUG PRINT OF METHOD-MAPPINGS
		System.out.println(loopInfo);
		System.out.println(methods);
	}
	
	//Executes current line of code.
	private static void run(int i) {
		String[] codeLine = code.line(i);
		if(isKeyWord(codeLine[0])) {
			keywords.handle(codeLine, i);
		}
	}
	
	//Tests if string is keyword, add more tests as you add keywords
	private static boolean isKeyWord(String string) {
		return (string.equals("print") || string.equals("set") 
				|| string.equals("task")  || string.equals("do"));
	}

	//Constructs all fields
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
	
	public static void runLoop() {
		LoopInfo loop = loopInfo.peek();
		for(int i = 0; i < loop.iter; i++) {
			for(int j = loop.start; j < loop.end; j++) {
				run(j);
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
		line = loop.end;
	}
	
	//Skips the execution of a method; used for avoid executing method declarations
	public static void advanceToEnd() {
		while(!code.line(line)[0].equals("end")) {
			line++;
		}
	}
	
	public static boolean startsWith(int index, String term) {
		return code.line(index)[0].equals(term);
	}
}
