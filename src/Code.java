import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Code {
	public List<String[]> code;
	public int size;

	public Code(String fileName) {
		this.code = new ArrayList<String[]>();
		//Attempt to build source code from passed file
		try {
			Scanner reader = new Scanner(new File(fileName));
			while(reader.hasNextLine()) {
				this.code.add(parseLine(reader.nextLine()));
			}
			reader.close();
			this.size = this.code.size();
		} catch (FileNotFoundException e) {
			System.out.println("We're sorry, we couldn't find that file!");
			e.printStackTrace();
		}
	}
	
	public String[] line(int lineNum) {
		return this.code.get(lineNum);
	}
	
	//Break line into tokens--update to match complexity while iteratively enhancing
	//Currently splits by whitespace
	private String[] parseLine(String nextLine) {
		return nextLine.trim().split("\\s+");
	}

}
