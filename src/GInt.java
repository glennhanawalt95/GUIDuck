
public class GInt implements Data {
	private Integer data;
	
	public GInt(Integer data) {
		this.data = data;
	}
	
	public String type() {
		return "integer";
	}
	
	public String toString() {
		return "" + this.data;
	}

	public Integer value() {
		return this.data;
	}
}
