
public class ExpressionEvaluator {
	
	//ONLY FOR TESTING DO NOT CONTINUE
	public Data evaluate(String[] expression) {
		if(isVar(expression[0])) {
			return getData(expression[0]);
		}
		return new GInt(Integer.parseInt(expression[0]));
	}
	
	//Add to global Tool class?
	private Boolean isVar(String item) {
		return GUIDuckMain.context.peek().contains(item);
	}
	
	private Data getData(String var) {
		return GUIDuckMain.context.peek().get(var);
	}
}
