package parser;

public class SyntaxException extends Exception{
	public SyntaxException(String msg) {
		System.err.println(msg);
	}
}
