package org.guiduck.data;


public class GBool implements Data {
	private Boolean data;
	
	public GBool(Boolean data) {
		this.data = data;
	}
	
	public String type() {
		return "boolean";
	}
	
	public String toString() {
		return this.data.toString();
	}

	public Boolean value() {
		return this.data;
	}
}