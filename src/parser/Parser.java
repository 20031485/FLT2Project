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
					panicMode(token);
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
					panicMode(token);
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
					panicMode(token);
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
				case ID:
					match(TokenType.ID);
					match(TokenType.ASSIGN);
					parseExp();
					return;

				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	//Exp -> Tr ExpP
	private void parseExp() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case INTVAL:
				case FLOATVAL:
				case ID:
					parseTr();
					parseExpP();
					return;
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	//Tr -> Val TrP
	private void parseTr() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case INTVAL:
				case FLOATVAL:
				case ID:
					parseVal();
					parseTrP();
					return;
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	//ExpP -> + Exp | - Exp | eps
	private void parseExpP() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case PLUS:
					match(TokenType.PLUS);
					parseExp();
					return;
				case MINUS:
					match(TokenType.MINUS);
					parseExp();
					return;
				case SEMI:

					//parse eps
					return;
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	//Val -> int | float | id
	private void parseVal() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case INTVAL:
					match(TokenType.INTVAL);
					return;
				case FLOATVAL:
					match(TokenType.FLOATVAL);
					return;
				case ID:
					match(TokenType.ID);
					return;
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	//TrP -> * Tr | / Tr | eps
	private void parseTrP() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case TIMES:
					match(TokenType.TIMES);
					parseTr();
					return;
				case DIV:
					match(TokenType.DIV);
					parseTr();
					return;
				case ID:
					//parse eps
					return;
				default:
					panicMode(token);
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
			panicMode(token);
	}
	
	public void print(String s) {
		System.out.println(s);
	}
	
	private void panicMode(Token token) throws SyntaxException {
		Token wrongToken = token;
		try{
			token = scanner.nextToken();
			print("diomerda");
			throw new SyntaxException("SyntaxException@line:"+wrongToken.getLine()+"\nUnexpected token: "+wrongToken.toString());
		}
		catch(IOException | LexicalException | SyntaxException e) {
			print("diocan");
			parsePrg();
			if(e.getMessage() != null)
			throw new SyntaxException(e.getMessage());
		}
		
	}
}
