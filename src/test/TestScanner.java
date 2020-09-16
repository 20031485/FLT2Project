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
	public void testCorrectIntval(){
		String path ="src/test/data/intval_correct.txt";
		try {
			Scanner scanner = new Scanner(path);
			Token token = new Token(TokenType.INTVAL, 1, "1231241139276");
			assertTrue(scanner.peekToken().equals(token));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LexicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWrongIntval(){
		String path ="src/test/data/intval_wrong.txt";
		try {
			Scanner scanner = new Scanner(path);
			LexicalException lexEx = assertThrows(LexicalException.class, () -> scanner.peekToken());
			//assertTrue(lexEx.getMessage().equals("LexicalException@line:1\n" + 
			//		"9 digits after '.' (must be between 1 and 5)!"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}