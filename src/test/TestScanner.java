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
	public void testScanner(){
		String path ="src/test/data/testId.txt";
		try {
			Scanner scanner = new Scanner(path);
			try {
				Token token = new Token(null, 0, null);
				while(scanner.peekToken().getType() != TokenType.EOF) {
					System.out.print(scanner.nextToken().toString());
				}
				//il prossimo è EOF
				token = new Token(TokenType.EOF, scanner.getLine(), null);
				System.out.print(token.toString());
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