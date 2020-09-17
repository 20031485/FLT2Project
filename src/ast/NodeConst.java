package ast;

//node representing constants in the AST
public class NodeConst extends NodeExpr {
	private String value;
	private LangType type;

	public NodeConst(String value, LangType type) {
		this.value = value;
		this.type = type;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public LangType getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return this.getValue();
	}

	@Override
	public boolean equals(Object o) {
		if(this == o || 
			((this.value.equals(((NodeConst) o).getValue())) && this.type == ((NodeConst) o).getType()))
				return true;
		return false;
	}
}
