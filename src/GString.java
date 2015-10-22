
public class GString implements Data {
	private String data;
	
	public GString(String data) {
		this.data = data;
	}
	
	public String type() {
		return "string";
	}
	
	public String toString() {
		return this.data;
	}

	public String value() {
		return this.data;
	}
}