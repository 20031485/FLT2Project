package parser;

import java.io.FileNotFoundException;
import java.io.IOException;

import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {
	private Scanner scanner;
	
	public Parser(String filename) throws FileNotFoundException {
		this.scanner = new Scanner(filename);
	}
	
	void parseS() {
		Token token = peekToken();
		switch(token.getType()) {
			case TokenType.FUN:
			case TokenType.PARA:
			case TokenType.VAL: //produzione S -> E$
				parseE();
				match(TokenType.EOF);//EOF è dil tipo del token per $
		}
		throw new Exception();//magari fare un'eccezione un po' più precisa...
		//tipo un SyntaxError();
	}
	
	void parseE() throws IOException {
		Token token = scanner.peekToken();
	}
	
	boolean match(TokenType tokenType) {
		if(tokenType == scanner.peekToken().getType())
		return false;
	}
}
