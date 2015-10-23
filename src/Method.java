
public class Method {
	String name;
	String[] formalParams;
	int start;
	int end;
	
	/* make method from declaration line, parse name and params */
	public Method(String line, int lineNum) {
		this.start = lineNum + 1; 
		this.end = GUIDuckMain.advanceToEnd(lineNum);
		this.name = GUIDuckMain.code.line(lineNum)[1];
		//PARSE PARAMS
	}
	
	public void runMethod() {//should take String[] of params
		//parse params, add them to context w/ formal param names
		GUIDuckMain.context.push(new Context());
		for(int line = this.start; line < this.end; line++) {
			line = GUIDuckMain.run(line);
		}
		GUIDuckMain.context.pop();
	}
	
	public String toString() {
		return this.name + "->" + this.start;
	}
}
