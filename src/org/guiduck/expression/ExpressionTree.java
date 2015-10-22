package org.guiduck.expression;

import org.guiduck.data.Data;

public class ExpressionTree {

	private interface ExpressionNode { }
	
	/*
	 * Terminating node, doesn't have children.
	 */
	private class TermNode implements ExpressionNode {
		private final Data value;
		
		public TermNode(Data value) {
			this.value = value;
		}
	}
	
	/*
	 * Holds and expression type and it's children to apply it to.
	 */
	private class OperationNode implements ExpressionNode {
		private final ExpressionNode[] children;
		private final OperationType type;
		
		public OperationNode(OperationType type) {
			this.children = new ExpressionNode[2];
			this.type = type;
		}
	}
	
	enum OperationType {
		MULTIPLICATION("*"),
		MOD("%"),
		ADDITION("+"),
		SUBTRACTION("-"),
		DIVISION("/"),
		NOT_SUPPORTED("NaN");
		
		 
		private final String id;
		
		OperationType(String id) {
			this.id = id;
		}
		
		String getID() {
			return this.id;
		}
		
		static OperationType getByString(String s) {
			for(OperationType op : values()) {
				if(op.id.equalsIgnoreCase(s)) {
					return op;
				}
			}
			return OperationType.NOT_SUPPORTED;
		}
	}
}
