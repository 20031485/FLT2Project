package parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import ast.NodeAST;
import ast.NodeDcl;
import ast.NodeDclStm;
import ast.NodePrg;
import ast.NodePrint;
import ast.NodeStm;
import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;
//TODO classe statica Attributes per mantenere gli attributi durante il parsing
public class Parser {
	private Scanner scanner;
	
	public Parser(String filename) throws FileNotFoundException {
		this.scanner = new Scanner(filename);
	}
	
	public NodeAST parse() throws SyntaxException {
		try{
			return parsePrg();
		}
		catch(SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
	}
	
	//Prg -> DSs $
	private NodePrg parsePrg() throws SyntaxException{
		try{
			Token token = scanner.peekToken();
			//print("parsePrg");
			switch(token.getType()) {
				case FLOATDEC:
				case INTDEC:
				case ID:
				case PRINT:
					NodePrg nodePrg1 = new NodePrg(parseDSs());
					match(TokenType.EOF);
					return nodePrg1;
				case EOF:
					NodePrg nodePrg2 = new NodePrg();
					match(TokenType.EOF);
					return nodePrg2;
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;//da togliere, messo solo per non avere errore
	}
	
	//DSs -> Dcl DSs | Stm DSs | eps
	private ArrayList<NodeDclStm> parseDSs() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			//print("parseDSs");
			switch(token.getType()) {
				case FLOATDEC:
				case INTDEC:
					NodeDcl nodeDcl = parseDcl();
					ArrayList<NodeDclStm> nodeDS1 = parseDSs();
					nodeDS1.add(nodeDcl);
					return nodeDS1;
				case ID:
				case PRINT:
					NodeStm nodeStm = parseStm();
					ArrayList<NodeDclStm> nodeDS2 = parseDSs();
					//if(nodeStm != null)
						//nodeDS.add(nodeStm);
					//nodeDS.add(parseStm());//boh non so se NodeDclStm è giusto LOL
					//nodeDS = parseDSs();
					//nodeDS.add(nodeStm);
					return nodeDS2;
				case EOF:
					return new ArrayList<NodeDclStm>();
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//Dcl -> tyFloat id; | tyInt id;
	private NodeDcl parseDcl() throws SyntaxException {
		try{
			Token token = scanner.peekToken();
			//print("parseDcl");	
			switch(token.getType()) {
				case FLOATDEC:
					NodeDcl nodeDcl1 = new NodeDcl();
					//modifica a nodeDcl da ritornare
					match(TokenType.FLOATDEC);
					match(TokenType.ID);
					match(TokenType.SEMI);
					return nodeDcl1;
				case INTDEC:
					NodeDcl nodeDcl2 = new NodeDcl();
					//modifica a nodeDcl da ritornare
					match(TokenType.INTDEC);
					match(TokenType.ID);
					match(TokenType.SEMI);
					return nodeDcl2;
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//Stm -> print id; | id = Exp;
	private NodeStm parseStm() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			NodeStm nodeStm;
			//print("parseStm");
			switch(token.getType()) {
				case PRINT:
					NodeStm nodePrint = new NodePrint(null);
					match(TokenType.PRINT);
					match(TokenType.ID);
					match(TokenType.SEMI);
					return nodePrint;
				case ID:
					match(TokenType.ID);
					match(TokenType.ASSIGN);
					parseExp();
					match(TokenType.SEMI);
					return null; //modificare in futuro con un return nodeExp credo LOL
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
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
				case PLUS:
				case MINUS:
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
	
	void match(TokenType tokenType) throws SyntaxException, IOException, LexicalException {
		Token token = scanner.peekToken();
		if(tokenType == token.getType()) {
			Token nextToken = scanner.nextToken();
			print(nextToken.toString());
		}
		//else
			//panicMode(token);
	}
	
	public void print(String s) {
		System.out.println(s);
	}
	
	private void panicMode(Token token) throws SyntaxException {
		Token wrongToken = token;
		try{
			token = scanner.nextToken();
			print("panicMode:TRY\n\tSyntaxException@line:"+wrongToken.getLine()+"\nUnexpected token: "+wrongToken.toString());
			parsePrg();
		}
		catch(IOException | LexicalException | SyntaxException e) {
			print("panicMode:CATCH");
			throw new SyntaxException(e.getMessage());
		}
		
	}
}