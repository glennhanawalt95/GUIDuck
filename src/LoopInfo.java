
public class LoopInfo {
	public int start;
	public int end;
	public int iter;
	
	LoopInfo(int start, int iter) {
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
}
