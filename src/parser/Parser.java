package parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import ast.LangOp;
import ast.LangType;
import ast.NodeAST;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConst;
import ast.NodeDcl;
import ast.NodeDclStm;
import ast.NodeDeref;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrg;
import ast.NodePrint;
import ast.NodeStm;
import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

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
					if(nodeStm != null)
						nodeDS2.add(nodeStm);
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
					//NodeDcl nodeDcl1 = new NodeDcl(LangType.FLOAT, new NodeId(token.getValue()));
					//modifica a nodeDcl da ritornare
					match(TokenType.FLOATDEC);
					//match(TokenType.ID);
					NodeId nodeId1 = new NodeId(match(TokenType.ID));
					match(TokenType.SEMI);
					return new NodeDcl(LangType.FLOAT, nodeId1);//nodeDcl1;
				case INTDEC:
					//NodeDcl nodeDcl2 = new NodeDcl(LangType.INT, new NodeId(token.getValue()));
					//modifica a nodeDcl da ritornare
					match(TokenType.INTDEC);
					//match(TokenType.ID);
					NodeId nodeId2 = new NodeId(match(TokenType.ID));
					match(TokenType.SEMI);
					return new NodeDcl(LangType.INT, nodeId2);//nodeDcl2;
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
			//print("parseStm");
			switch(token.getType()) {
				case PRINT:
					match(TokenType.PRINT);
					//match(TokenType.ID);
					NodeStm nodePrint = new NodePrint(new NodeId(match(TokenType.ID)));
					match(TokenType.SEMI);
					return nodePrint;
				case ID:
					NodeId nodeId = new NodeId(match(TokenType.ID));
					match(TokenType.ASSIGN);
					//parseExp();
					NodeAssign nodeAssign = new NodeAssign(nodeId, parseExp());
					match(TokenType.SEMI);
					return nodeAssign; //modificare in futuro con un return nodeExp credo LOL
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
	private NodeExpr parseExp() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case INTVAL:
				case FLOATVAL:
				case ID:
					NodeExpr nodeTr = parseTr();
					NodeExpr nodeExpP = parseExpP(nodeTr);
					return nodeExpP;
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//Tr -> Val TrP
	private NodeExpr parseTr() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case INTVAL:
				case FLOATVAL:
				case ID:
					NodeExpr nodeVal = parseVal();
					return parseTrP(nodeVal);
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//ExpP -> + Exp | - Exp | eps
	private NodeExpr parseExpP(NodeExpr leftBranch) throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case PLUS:
					match(TokenType.PLUS);
					//NodeExpr rightBranch1 = parseExp();
					return new NodeBinOp(LangOp.PLUS, leftBranch, parseExp());//rightBranch1);
				case MINUS:
					match(TokenType.MINUS);
					//NodeExpr rightBranch2 = parseExp();
					return new NodeBinOp(LangOp.MINUS, leftBranch, parseExp());//rightBranch2);
				case SEMI:
					//parse eps
					return leftBranch;
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//Val -> int | float | id
	private NodeExpr parseVal() throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case INTVAL:
					return new NodeConst(LangType.INT, match(TokenType.INTVAL));
				case FLOATVAL:
					return new NodeConst(LangType.FLOAT, match(TokenType.FLOATVAL));
				case ID:
					return new NodeDeref(new NodeId(match(TokenType.ID)));
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	//TrP -> * Tr | / Tr | eps
	private NodeExpr parseTrP(NodeExpr leftBranch) throws SyntaxException {
		try {
			Token token = scanner.peekToken();
			switch(token.getType()) {
				case TIMES:
					match(TokenType.TIMES);
					//parseTr();
					return new NodeBinOp(LangOp.TIMES, leftBranch, parseTr());
				case DIV:
					match(TokenType.DIV);
					//parseTr();
					return new NodeBinOp(LangOp.DIVIDE, leftBranch, parseTr());
				case PLUS:
				case MINUS:
				case SEMI:
					//parse eps
					return leftBranch;
				default:
					panicMode(token);
			}
		}
		catch(IOException | LexicalException | SyntaxException e) {
			throw new SyntaxException(e.getMessage());
		}
		return null;
	}
	
	private String match(TokenType tokenType) throws SyntaxException, IOException, LexicalException {
		Token token = scanner.peekToken();
		if(tokenType == token.getType()) {
			Token nextToken = scanner.nextToken();
			//print(nextToken.toString());
			return nextToken.getValue();//ritorna una stringa solo se il token è un INTVAL o FLOATVAL
		}
		//else
			//panicMode(token);
		return null;
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