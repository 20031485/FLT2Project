package token;

public class Token {

	private int line;
	private TokenType type;
	private String value;
	
	public Token(TokenType type, int line, String value) {
		this.type = type;
		this.line = line;
		this.value = value;
	}
	
	public Token(TokenType type, int line) {
	}

    // Getters per i campi
	public int getLine() {
		return this.line;
	}
	
	public TokenType getType() {
		return this.type;
	}
	
	public String getvalue() {
		//significativo solo per i token che hanno un valore
		if(this.value != null) 
			return this.value;
		return "none";
	}

	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof Token))
			return false;
		
		Token that = (Token) o;
		return (this.line == that.line 
				&& this.value.equals(that.value) 
				&& this.type.equals(that.type));
	}

	public String toString() {
		String string = "<" + type + ", ";
		if(type.equals(TokenType.INTVAL) || type.equals(TokenType.FLOATVAL))
			string += "val: "+value+", ";
		string += "line: " + line + ">";
		return string;
	}
}
