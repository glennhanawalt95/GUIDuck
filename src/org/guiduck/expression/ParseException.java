package org.guiduck.expression;

import java.util.Arrays;

import org.guiduck.data.Data;

public class ParseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ParseException() {
		super();
	}
	
	public ParseException(Data... data) {
		super("Parse Exception with data: " + Arrays.toString(data));
	}
	
	public ParseException(String message) {
		super(message);
	}

}
