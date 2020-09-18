package test;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import parser.Parser;
import parser.SyntaxException;

public class TestParser {
	@Test
	public void testParser(){
		String path ="src/test/data/testParser.txt";
		try {
			Parser parser = new Parser(path);
			try {
				System.out.println(parser.parse().toString());
			} catch (SyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
