package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import token.*;

public class Scanner {
	final char EOF = (char) -1; // int 65535
	private int line;
	private PushbackReader buffer;
	private String log;
	private Token token;
	private static final int MIN_DIGITS = 1;
	private static final int MAX_DIGITS = 5;

	private List<Character> skipChars = Arrays.asList(' ', '\n', '\t', '\r', EOF);
	private List<Character> letters = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
	private List<Character> numbers = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
	private List<Character> operators = Arrays.asList('+', '-', '*', '/', '=', ';');
	// HashMap caratteri TokenType per associazione fra '+', '-', '*', '/', '=', ';'
	// e TokenType
	private HashMap<Character, TokenType> hashChar;
	
	// HashMap stringhe TokenType per associazione fra parole chiave "print",
	// "float", "int" e TokenType
	private HashMap<String, TokenType> hashTokens;

	public Scanner(String fileName) throws FileNotFoundException {
		this.buffer = new PushbackReader(new FileReader(fileName));
		line = 1;

	}

	//legge caratteri dallo stream, restituisce un token ma non consuma caratteri dell'input
	//chiamando due volte peekToken ottengo lo stesso token
	public Token peekToken() throws IOException {
		Token token = null;
		char c = 'a';
		char[] pushback = null;
		String tokenString = "";
		
		//finchè non becco un carattere illegale o finchè non finisco il token
		while(true) {
			c = peekChar();
			System.out.println("Read char \""+c+"\"");
			
			//se il carattere è una lettera, allora voglio leggere un id
			//oppure un INTDEC o FLOATDEC, quindi controllo che la stringa sia
			//int o float o altro
			if(letters.contains(c)) {
				while(!skipChars.contains(c) && (letters.contains(c) || numbers.contains(c))) {
					c = peekChar();
					System.out.println("Read char \""+c+"\"");
					
					//se ho trovato un carattere tra (' ', '\n', '\t', '\r', EOF), ritorno quello 
					//che ho calcolato finora
					if(skipChars.contains(c)) {
						switch(tokenString) {
							case "int":
								return new Token(TokenType.INTDEC, line, null);
							case "float":
								return new Token(TokenType.FLOATDEC, line, null);
							default:
								return new Token(TokenType.ID, line, null);
						}
					}
					//altrimenti lo metto in coda alla stringa che sto scandendo
					tokenString += c;
				}
				System.out.println("String scanned: "+tokenString);
			}
			//se invece ho letto un numero, mi aspetto una cifra
			else if(numbers.contains(c)) {
				while(!skipChars.contains(c) && numbers.contains(c)) {
					c = peekChar();
					System.out.println("Read char \""+c+"\"");
					tokenString += c;
				}
				System.out.println("String scanned: "+tokenString);
			}
			else {
				System.out.println("===Illicit char===");
				break;
			}
		}
		return token;
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
		if(c == '\n') 
			line++;
		return c;
	}

	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
}
