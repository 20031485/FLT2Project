package ast;

public class NodePrint extends NodeStm {
	private NodeId id;
	
	public NodePrint(NodeId id) {
		this.id = id;
	}
	
	public String toString() {
		return id.toString(); //sistemare
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
}
