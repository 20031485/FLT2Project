package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import parser.Parser;
import parser.SyntaxException;
import scanner.LexicalException;

public class TestParser {
	@Test
	public void testParser() {
		String path ="src/test/data/testParser.txt";
		try {
			Parser parser = new Parser(path);
			try {
				parser.parse();
			} catch (SyntaxException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}
