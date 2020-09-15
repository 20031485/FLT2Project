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
	
	
	
	public void parse() throws SyntaxException {
		try{
			parsePrg();
		}
		catch(SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	//Prg -> DSs $
	void parsePrg() throws SyntaxException{
		try{
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
					throw new SyntaxException("SyntaxException@line:"+token.getLine()+"\nUnexpected token: "+token.toString());
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	//DSs -> Dcl DSs | Stm DSs | eps
	private void parseDSs() throws SyntaxException {
		try {
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
					//match(TokenType.EOF);//--> ritorno soltanto, il match di EOF è in parsePrg!
					return;
				default:
					throw new SyntaxException("SyntaxException@line:"+token.getLine()+"\nUnexpected token: "+token.toString());
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	//Dcl -> tyFloat id; | tyInt id;
	private void parseDcl() throws SyntaxException {
		try{
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
					throw new SyntaxException("SyntaxException@line:"+token.getLine()+"\nUnexpected token: "+token.toString());
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	//Stm -> print id; | id = Exp;
	private void parseStm() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			//print("parseStm");
			switch(token.getType()) {
				case PRINT:
					match(TokenType.PRINT);
					match(TokenType.ID);
					match(TokenType.SEMI);
					return;
				default:
					throw new SyntaxException("SyntaxException@line:"+token.getLine()+"\nUnexpected token: "+token.toString());
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	void match(TokenType tokenType) throws SyntaxException, IOException, LexicalException {
		Token token = scanner.peekToken();
		if(tokenType == token.getType()) {
			Token nextToken = scanner.nextToken();
			print(nextToken.toString());
		}
		else
			throw new SyntaxException("SyntaxException@line:"+token.getLine()+"\nUnexpected token: "+token.toString());
	}
	
	public void print(String s) {
		System.out.println(s);
	}
}
