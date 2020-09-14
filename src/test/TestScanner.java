package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class TestScanner {
	
	@Test
	public void testScanId(){
		String path ="src/test/data/testId.txt";
		try {
			Scanner scanner = new Scanner(path);
			try {
				//Token token = scanner.scanId();
				Token token = scanner.peekToken();
				System.out.println(token.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LexicalException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}