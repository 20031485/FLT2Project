package scanner;

import java.io.CharArrayReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import token.*;

public class Scanner {
	private String fileName;
	final char EOF = (char) -1; // int 65535
	private int line;
	//da utilizzare localmente alla peekToken
	private int currentLine;
	private PushbackReader buffer;
	private String log;
	private Token token;
	private static final int MIN_DIGITS = 1;
	private static final int MAX_DIGITS = 5;

	private List<Character> skipChars = Arrays.asList(' ', '\n', '\t', '\r', EOF);
	private List<Character> letters = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
	private List<Character> numbers = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
	private List<Character> operators = Arrays.asList('+', '-', '*', '/', '=', ';');
	//TODO aggiungere alle hashmaps
	// HashMap caratteri TokenType per associazione fra '+', '-', '*', '/', '=', ';'
	// e TokenType
	private HashMap<Character, TokenType> hashChar;
	
	// HashMap stringhe TokenType per associazione fra parole chiave "print",
	// "float", "int" e TokenType
	private HashMap<String, TokenType> hashTokens;

	public Scanner(String fileName) throws FileNotFoundException {
		this.fileName = fileName;
		this.buffer = new PushbackReader(new FileReader(fileName));
		line = 1;
	}

	public Token peekToken() throws IOException, LexicalException{
		//copio il buffer, così posso trusciarci dentro
		PushbackReader bufferClone = new PushbackReader(new FileReader(fileName));
		Character c = null;
		String tokenString = "";
		//riga di partenza, cioè l'ultima riga a cui ero rimasto nello scan
		currentLine = line;
		
		//se il carattere è legale
		if(numbers.contains(peekChar()) || letters.contains(peekChar()) || operators.contains(peekChar()) || skipChars.contains(peekChar())) {
			
			while(skipChars.contains(peekChar())) {
				c = readChar();
				if(c == '\n') currentLine++;
			}
			c = readChar();
			tokenString += c;
			
			//se c è un operatore
			if(operators.contains(c)) {
				//rimetto sul buffer il carattere che ho letto
				//buffer.unread(c);
				//a seconda dell'operatore letto, restituisco il token appropriato
				switch(c) {
					case '+':
						buffer = bufferClone;
						return new Token(TokenType.PLUS, currentLine, tokenString);
					case '-':
						buffer = bufferClone;
						return new Token(TokenType.MINUS, currentLine, tokenString);
					case '*':
						buffer = bufferClone;
						return new Token(TokenType.TIMES, currentLine, tokenString);
					case '/':
						buffer = bufferClone;
						return new Token(TokenType.DIV, currentLine, tokenString);
					case '=':
						buffer = bufferClone;
						return new Token(TokenType.ASSIGN, currentLine, tokenString);
					case ';':
						buffer = bufferClone;
						return new Token(TokenType.SEMI, currentLine, tokenString);
				}
			}
			
			//se c è una lettera mi aspetto altre lettere
			else if(letters.contains(c)) {
				while(letters.contains(peekChar())) {
					c = readChar();
					tokenString += c;
				}
				//ho letto tutte le lettere finchè non è capitato un altro simbolo
				//restituisco un token a seconda di cosa ho letto
				//if(operators.contains(peekChar()) || skipChars.contains(peekChar())) {
					//sleggo
					//buffer.unread(toCharArray(tokenString));
					//printBuffer();
					print("TokenString: "+tokenString);
					printChArr(tokenString);
					//unreadString(tokenString);
					switch(tokenString) {
						case "int":	
							buffer = bufferClone;
							return new Token(TokenType.INTDEC, currentLine, tokenString);
						case "float":
							buffer = bufferClone;
							return new Token(TokenType.FLOATDEC, currentLine, tokenString);
						case "print":	
							buffer = bufferClone;
							return new Token(TokenType.PRINT, currentLine, tokenString);
						default:
							buffer = bufferClone;
							return new Token(TokenType.ID, currentLine, tokenString);
					}
				/*}
				//altrimenti LexicalException()
				else {
					buffer = bufferClone;
					throw new LexicalException("LexicalException@line:"+currentLine);
				}*/
			}
			
			//se ho letto un numero voglio leggere un int o un float
			else if(numbers.contains(c)) {
				//flag per segnalare una lettura di '.' già avvenuta
				boolean dotSeen = false;
				
				//finchè leggo numeri o '.'
				while(numbers.contains(peekChar()) || peekChar() == '.') {
					//se ho già incontrato un '.'
					if(peekChar() == '.' && dotSeen)
						throw new LexicalException("LexicalException@line:"+currentLine);
					
					//se non ho ancora incontrato '.' e lo incontro ora
					else if(!dotSeen && peekChar() == '.') {
						c = readChar();
						tokenString += c;
					}
					else {
						c = readChar();
						tokenString += c;
					}
					if(operators.contains(peekChar()) || skipChars.contains(peekChar())) {
						if(dotSeen) {
							buffer = bufferClone;
							return new Token(TokenType.FLOATVAL, currentLine, tokenString);
						}
						else {
							buffer = bufferClone;
							return new Token(TokenType.INTVAL, currentLine, tokenString);
						}
					}
				}
			}
		}	
		else
			throw new LexicalException("LexicalException@line:"+currentLine);
		return null;
	}
	
	/*private char[] toCharArray(String s) {
		CharArrayReader charArr = new CharArrayReader(s.toCharArray());
		return charArr;
	}*/
	
	private void printChArr(String s) {
		for(int i = 0; i < s.length(); i++)
			System.out.print(s.toCharArray()[i]);
		System.out.print("\n");
	}
	
	private void unreadString(String s) throws IOException {
		for(int i = 0; i < s.length(); i++)
			buffer.unread(s.toCharArray()[i]);
	}
	
	//legge caratteri dallo stream, restituisce un token e lo consuma
	//consuma i caratteri dell'input sufficienti ad ottenere il prossimo token
	//chiamando due volte nextToken otterrò due token diversi
/*	public Token nextToken() throws IOException {

		// nextChar contiene il prossimo carattere dell'input.
		char nextChar = peekChar();
		
		// Avanza nel buffer leggendo i carattere in skipChars
		// incrementando line se leggi '\n'.
		// Se raggiungi la fine del file ritorna il Token EOF
		if(nextChar == '\n') {
			line++;
		}
		// Se nextChar e' in numbers
			return scanNumber()
		// che legge sia un intero che un float e ritorna il Token INUM o FNUM
		// i caratteri che leggete devono essere accumulati in una stringa
		// che verra' assegnata al campo valore del Token
		if(numbers.contains(nextChar)) 
			return scanNumber();
		
		// Se nextChar e' in letters
		//                return scanId()
		// che legge tutte le lettere minuscole e ritorna un Token ID o
		// il Token associato Parola Chiave (per generare i Token per le
		// parole chiave usate l'HaskMap di corrispondenza
		if(letters.contains(nextChar)) 
			return scanId();
		
		// Se nextChar e' un operatore o = o ;
		// ritorna il Token associato con l'operatore o il delimitatore
		// Anche qui per generare i Token usate l'HashMap di corrispondenza
		if(nextChar == '=') {
			return ;
		}
		if(nextChar == ';') {
			return;
		}
		// Altrimenti il carattere NON E' UN CARATTERE LEGALE
		// Potete:
		// (1) Cercare di recuperare l'errore scartando il carattere (segnalarlo
		// nella stringa log) e andando avanti
		// (2) Interrompere l'analisi lessicale sollevando una eccezione
		
		return null;

	}*/
	
	private Token scanNumber() throws IOException, LexicalException{
		String string = "";
		while(numbers.contains(peekChar())) {
			string += readChar();
		}
		if(peekChar() != '.')
			return new Token(TokenType.INTVAL, line, string);
		//if a point is scanned, initiate FLOAT scan
		string += readChar();
		int nC = 0;
		while(numbers.contains(peekChar())) {
			nC++;
			string += readChar();
		}
		//if I read too many/few characters after the point, 
		//a lexical exception is thrown
		if((nC <= MAX_DIGITS) && (nC >= MIN_DIGITS))
			return new Token(TokenType.FLOATVAL, line, string);
		else
			throw new LexicalException("LexicalException@line:"+line);
	}
	
	public Token scanId() throws IOException, LexicalException{
		String string = "";
		//read at least one character
		while(letters.contains(peekChar())){
			string += readChar();
		}
		if(string.equals("int")) {
			return new Token(TokenType.INTDEC, line, string);
		}
		else if(string.equals("float")) {
			return new Token(TokenType.FLOATDEC, line, string);
		}
		else {
			return new Token(TokenType.ID, line, string);
		}
	}
	
	private char readChar() throws IOException {
		char c = ((char) this.buffer.read());
		//if(c == '\n') 
		//	line++;
		print("Consumed: "+c);
		return c;
	}

	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
	
	public void printBuffer() {
		System.out.println(buffer);
	}
	
	public void print(String s) {
		System.out.println(s);
	}
}
