package ast;

public class NodeBinOp extends NodeExpr {
	private final LangOp op;//operatore
	private final NodeExpr left;//ast a sx dell'operatore
	private final NodeExpr right;//ast a dx dell'operatore
	
	//constructor
	public NodeBinOp(LangOp op, NodeExpr left, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}
	
	//methods
	public LangOp getOp() {
		return op;
	}

	public NodeExpr getLeft() {
		return left;
	}

	public NodeExpr getRight() {
		return right;
	}
	
	@Override
	public String toString() {
		return this.left.toString()+" "+ op + " "+this.right.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o || 
				((this.left.equals(((NodeBinOp) o).getLeft()))
						&& (this.left.equals(((NodeBinOp) o).getLeft())) 
						&& (this.op == ((NodeBinOp) o).getOp())))
					return true;
		return false;
	}
}
