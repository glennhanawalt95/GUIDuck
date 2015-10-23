
public class Loop {
	public int start;
	public int end;
	public int iter;
	
	Loop(int start, int iter) {
		this.start = start + 1;
		this.iter = iter;
		this.end = findEnd(this.start);
	}

	private int findEnd(int line) {
		int loopCount = 0;
		while(!GUIDuckMain.startsWith(line, "done") || loopCount != 0) {
			if (GUIDuckMain.startsWith(line, "done")) {
				loopCount--;
			}
			else if (GUIDuckMain.startsWith(line, "do")) {
				loopCount++;
			}
			line++;
		}
		return line;
	}
	
	public String toString() {
		return this.start + "->" + this.end;
	}

	/* Runs a loop; may recursively run nested loops. Returns control to main
	 * at where loop ends */
	public int runLoop() {
		GUIDuckMain.context.push(new Context(GUIDuckMain.context.peek()));
		for(int i = 0; i < this.iter; i++) {
			for(int j = this.start; j < this.end; j++) {
				j = GUIDuckMain.run(j);
			}
		}
		return this.end;
	}
}
