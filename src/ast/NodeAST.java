package ast;

public abstract class NodeAST {
	private TypeDescriptor resType;
	public abstract String toString();
	public abstract boolean equals(Object o);
	public abstract void accept();
}
