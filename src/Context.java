import java.util.Map;
import java.util.TreeMap;

import org.guiduck.data.Data;

public class Context {
	//Map of variables -> data
	private Map<String, Data> variables;
	
	public Context() {
		this.variables = new TreeMap<String, Data>();
	}
	
	//Add variable->data mapping
	public void addVariable(String var, Data data) {
		this.variables.put(var, data);
	}
	
	public boolean contains(String var) {
		return this.variables.containsKey(var);
	}
	
	public Data get(String var) {
		return this.variables.get(var);
	}
}
