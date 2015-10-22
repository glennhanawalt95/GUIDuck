import java.util.Arrays;

import org.guiduck.data.Data;


public class KeywordHandler {
	
	//perform appropriate actions for GUIDuck's keywords
	public void handle(String[] args, int line) {
		switch(args[0]) {
		//set <var> to <exp>: adds var -> value of <exp> in the current context
		case "set":
			//isValidSet?
			String variable = args[1];
			Data result = evaluateExp(truncate(args, "set"));
			addVariable(variable, result);
			break;
		
		//print <exp>: prints value of <exp>
		case "print":
			Data print = evaluateExp(truncate(args, "print"));
			System.out.println(print);
			break;
		
		//Adds name -> line # mapping for defined method, skips execution
		case "task":
			addMethod(args[1], GUIDuckMain.line);
			GUIDuckMain.advanceToEnd();
			break;
		//Needs to throw error if it can't parse an int
		case "do":
			Integer iter = (Integer) evaluateExp(truncate(args, "do")).value();
			LoopInfo newLoop = new LoopInfo(line, iter);
			GUIDuckMain.loopInfo.push(newLoop);
			GUIDuckMain.runLoop();
			break;
		}
	}

	//Get subarray from start->array.length, used to isolate expressions	
	private String[] truncate(String[] args, String type) {
		int start = 0;
		int end = args.length;
		switch(type) {
		case "set":
			start = 3;
			break;
		case "print":
			start = 1;
			break;
		case "do":
			start = 1;
			end -= 1;
			break;
		}
		return Arrays.copyOfRange(args, start, end);
	}
	
	private void addVariable(String var, Data data) {
		GUIDuckMain.context.peek().addVariable(var, data);
	}
	
	private void addMethod(String name, int line) {
		GUIDuckMain.methods.put(name, line);
	}
	
	//ONLY INTEGERS NOW
	private Data evaluateExp(String[] exp) {
		return GUIDuckMain.expressions.evaluate(exp);
	}
}
