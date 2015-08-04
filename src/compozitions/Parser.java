package compozitions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Parser {
	private Scanner scanner;
	private TypeItem type;
	private String[] data;
	private int curData;
	private int curLine;
	Parser(String fileName) throws FileNotFoundException{
			scanner = new Scanner(new File(fileName), "cp1251");
	}
	
	boolean next() {
		curData = 0;
		++curLine;
		if(scanner.hasNextLine()){
			String nextLine = scanner.nextLine();
			if(nextLine.trim().equals(""))
				return next();
			String[] str = nextLine.split(":");
			type = TypeItem.valueOf(str[0].trim());
			if(str.length>=2)
				data = str[1].split(",");
			else
				data = new String[0];
			return true;
		}
		scanner.close();
		type = TypeItem.Îøèáêà;
		data = null;
		return false;
	}
	
	TypeItem getType() {
		return type;
	}
	
	int getCurLine(){
		return curLine;
	}
	
	String getNextData() {
		return (data.length>curData) ? data[curData++].trim() : "";
	}
}
