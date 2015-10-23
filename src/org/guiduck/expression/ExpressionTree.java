package org.guiduck.expression;

import java.util.Arrays;

import org.guiduck.data.Data;
import org.guiduck.data.GInt;
import org.guiduck.expression.Operations.Add;
import org.guiduck.expression.Operations.Divide;
import org.guiduck.expression.Operations.Mod;
import org.guiduck.expression.Operations.Multiply;
import org.guiduck.expression.Operations.Operation;
import org.guiduck.expression.Operations.Subtract;

public class ExpressionTree {
	private ExpressionNode root;
	
	public ExpressionTree(String exp) {
		this.buildTree(format(exp));
	}
	
	private void buildTree(String[] args) {
		this.root = createNodes(args, 0, args.length-1);
	}
	
	private ExpressionNode createNodes(String[] args, int start, int end) {
		System.out.println(Arrays.toString(args) + ", " + start + ", " + end);
		if(end - start == 1) {
			return new TermNode(new GInt(Integer.parseInt(args[0])));
		}/* else if(args.length == 3) {
			if(!OperationType.isOp(args[1])) {
				throw new ParseException("Invalid Equation: " + Arrays.toString(Arrays.copyOfRange(args, start, end)));
			}
			OperationNode head = new OperationNode(OperationType.getByString(args[1]));
			/*
			 * Abstract this to use things other than ints.
			 *
			head.setLeft(new TermNode(new GInt(Integer.parseInt(args[0]))));
			head.setRight(new TermNode(new GInt(Integer.parseInt(args[2]))));
			return head;
		}*/ else {
			//We are going to split the tree based on the lowest OperationType.priority
			int splitIndex = 0;
			//#basically inifinity
			int lowestPriority = Integer.MAX_VALUE;
			//parenthesis level
			int parenLevel = 0;
			for(int i = start; i < end; i++) {
				if(OperationType.isOp(args[i])) {
					int priority = OperationType.getByString(args[i]).priority;
					if(priority < lowestPriority) {
						//We've found the lowest, and we want to respect the left to right
						//Order of operations.
						if(priority == 1) {
							splitIndex = i;
							break;
						}
						splitIndex = i;
						lowestPriority = priority;
					}
				}
			}
			OperationNode head = new OperationNode(OperationType.getByString(args[splitIndex]));
			head.setLeft(createNodes(args, start, splitIndex));
			head.setRight(createNodes(args, splitIndex+1, end));
		}
		return null;
	}
	
	/* Returns String[] processed into tokens */
	private static String[] format(String string) {
		String[] specialChars = {"(", ")", "+", "-", "*", "/", "%", "^"};
		for(String item: specialChars) {
			string = string.replace(item, " " + item + " ");
		}
		return string.trim().split("\\s+");
	}

	public Data evaluate() {
		return evaluate(root);
	}
	
	private Data evaluate(ExpressionNode node) {
		if(node instanceof TermNode) {
			return ((TermNode) node).value;
		} else {
			OperationNode opNode = (OperationNode) node;
			OperationType opType = opNode.type;
			return opType.apply(evaluate(opNode.left()), evaluate(opNode.right()));
		}
	}

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
		
		public ExpressionNode left() {
			return children[0];
		}
		
		public ExpressionNode right() {
			return children[1];
		}
		
		public void setLeft(ExpressionNode node) {
			children[0] = node;
		}
		
		public void setRight(ExpressionNode node) {
			children[1] = node;
		}
	}
	
	enum OperationType {
		MULTIPLICATION("*", new Multiply(), 2),
		MOD("%", new Mod(), 2),
		ADDITION("+", new Add(), 1),
		SUBTRACTION("-", new Subtract(), 1),
		DIVISION("/", new Divide(), 2),
		NOT_SUPPORTED("NaN", null, 10);
		
		 
		private final String id;
		private final Operation op;
		private final int priority;
		
		OperationType(String id, Operation op, int priority) {
			this.op = op;
			this.id = id;
			this.priority = priority;
		}
		
		String getID() {
			return this.id;
		}
		
		int getPriority() {
			return priority;
		}
		
		Data apply(Data d1, Data d2) {
			return this.op.apply(d1, d2);
		}
		
		static OperationType getByString(String s) {
			for(OperationType op : values()) {
				if(op.id.equalsIgnoreCase(s)) {
					return op;
				}
			}
			return OperationType.NOT_SUPPORTED;
		}
		
		static boolean isOp(String s) {
			return OperationType.getByString(s) != OperationType.NOT_SUPPORTED;
		}
	}
}
