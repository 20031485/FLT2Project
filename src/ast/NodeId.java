package ast;

public class NodeId extends NodeAST {
	private String id;
	
	public NodeId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return this.getId();
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
