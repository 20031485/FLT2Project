package symbolTable;

import ast.LangType;

public class Attributes {
	LangType type;

	public Attributes(LangType type) {
		this.type = type;
	}
	
	public LangType getType() {
		return type;
	}

	public void setType(LangType type) {
		this.type = type;
	}
	
	public String toString() {
		return type+"";
	}
}
