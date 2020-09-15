package parser;

import java.io.FileNotFoundException;
import java.io.IOException;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {
	private Scanner scanner;
	
	public Parser(String filename) throws FileNotFoundException {
		this.scanner = new Scanner(filename);
	}
	
	
	
	public void parse() throws SyntaxException, IOException, LexicalException {
		parsePrg();
	}
	
	//Prg -> DSs $
	void parsePrg() throws SyntaxException, IOException, LexicalException {
		Token token = scanner.peekToken();
		//print("parsePrg");
		switch(token.getType()) {
			case FLOATDEC:
			case INTDEC:
			case ID:
			case PRINT:
			case EOF:
				parseDSs();
				match(TokenType.EOF);
				return;
			default:
				throw new SyntaxException("SyntaxException@line:"+token.getLine()+"\nUnexpected token!");
		}
	}
	
	//DSs -> Dcl DSs | Stm DSs | eps
	private void parseDSs() throws IOException, LexicalException, SyntaxException {
		Token token = scanner.peekToken();
		//print("parseDSs");
		switch(token.getType()) {
			case FLOATDEC:
			case INTDEC:
				parseDcl();
				parseDSs();
				return;
			case ID:
			case PRINT:
				parseStm();
				parseDSs();
				return;
			case EOF:
				match(TokenType.EOF);
				return;
			default:
				throw new SyntaxException("SyntaxException@line:"+token.getLine()+"\nUnexpected token!");
		}
	}
	
	//Dcl -> tyFloat id; | tyInt id;
	private void parseDcl() throws IOException, LexicalException, SyntaxException {
		Token token = scanner.peekToken();
		//print("parseDcl");
		switch(token.getType()) {
			case FLOATDEC:
				match(TokenType.FLOATDEC);
				match(TokenType.ID);
				match(TokenType.SEMI);
				return;
			case INTDEC:
				match(TokenType.INTDEC);
				match(TokenType.ID);
				match(TokenType.SEMI);
				return;
			default:
				throw new SyntaxException("SyntaxException@line:"+token.getLine()+"\nUnexpected token!");
		}
	}
	
	//Stm -> print id; | id = Exp;
	private void parseStm() throws IOException, LexicalException, SyntaxException {
		Token token = scanner.peekToken();
		//print("parseStm");
		switch(token.getType()) {
			case PRINT:
				match(TokenType.PRINT);
				match(TokenType.ID);
				match(TokenType.SEMI);
				return;
			default:
				throw new SyntaxException("SyntaxException@line:"+token.getLine()+"\nUnexpected token!");
		}
	}
	
	void match(TokenType tokenType) throws SyntaxException, IOException, LexicalException {
		Token token = scanner.peekToken();
		if(tokenType == token.getType()) {
			if(token.getType() == TokenType.EOF) {
				return;
			}
			print(scanner.nextToken()+"");
		}
		else
			throw new SyntaxException("SyntaxException@line:"+token.getLine()+"\nUnexpected token!");
	}
	
	public void print(String s) {
		System.out.println(s);
	}
}
