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
			assertTrue(scanner.nextToken().equals(token));
			
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
		

		@Test
		public void testCorrectFloatdec(){
			String path ="src/test/data/floatdec_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.FLOATDEC, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongFloatdec(){
			String path ="src/test/data/floatdec_wrong.txt";
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
			
		
		
		
		@Test
		public void testCorrectIntdec(){
			String path ="src/test/data/intdec_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.INTDEC, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongIntdec(){
			String path ="src/test/data/intdec_wrong.txt";
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
			
															
			
		
		@Test
		public void testCorrectPrint(){
			String path ="src/test/data/print_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.PRINT, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongPrint(){
			String path ="src/test/data/print_wrong.txt";
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
		
		
		
		@Test
		public void testCorrectId(){
			String path ="src/test/data/Id_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.ID, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongId(){
			String path ="src/test/data/id_wrong.txt";
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
		
		
		
		
		@Test
		public void testCorrectFloatval(){
			String path ="src/test/data/floatval_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.FLOATVAL, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongFloatval(){
			String path ="src/test/data/floatval_wrong.txt";
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
		
		
		
		
		@Test
		public void testCorrectAssign(){
			String path ="src/test/data/assign_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.ASSIGN, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongAssign(){
			String path ="src/test/data/assign_wrong.txt";
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
		
		
		
		
		
		@Test
		public void testCorrectPlus(){
			String path ="src/test/data/plus_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.PLUS, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongPlus(){
			String path ="src/test/data/plus_wrong.txt";
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
	
		
		
		
		@Test
		public void testCorrectMinus(){
			String path ="src/test/data/minus_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.PLUS, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongMinus(){
			String path ="src/test/data/minus_wrong.txt";
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
		
		
		
		
		@Test
		public void testCorrectTimes(){
			String path ="src/test/data/times_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.TIMES, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongTimes(){
			String path ="src/test/data/times_wrong.txt";
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
		
		
		
		
		@Test
		public void testCorrectDiv(){
			String path ="src/test/data/div_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.DIV, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongDiv(){
			String path ="src/test/data/dov_wrong.txt";
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
		
		
		
		
		@Test
		public void testCorrectSemi(){
			String path ="src/test/data/semi_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.SEMI, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongSemi(){
			String path ="src/test/data/semi_wrong.txt";
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
		
		
		
		
		@Test
		public void testCorrectEof(){
			String path ="src/test/data/eof_correct.txt";
			try {
				Scanner scanner = new Scanner(path);
				Token token = new Token(TokenType.EOF, 1, "");
				assertTrue(scanner.peekToken().equals(token));
				assertTrue(scanner.nextToken().equals(token));
				
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
		public void testWrongEof(){
			String path ="src/test/data/eof_wrong.txt";
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