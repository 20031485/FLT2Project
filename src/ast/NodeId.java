package ast;

public class NodeId extends NodeAST {
	private final String id;
	//private String lexval;
	
	public NodeId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return this.getId();
		//return "<"+this.getId()+","+this.getLexval()+">";
	}

	@Override
	public boolean equals(Object o) {
		if(((o instanceof NodeId) && this.getId().equals(((NodeId)o).getId())) || this == o)
			return true;
		return false;
	}

	public String getId() {
		return this.id;
	}
}
