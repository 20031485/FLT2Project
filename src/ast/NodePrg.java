package ast;

import java.util.ArrayList;

public class NodePrg extends NodeAST {
	private ArrayList<NodeDclStm> dclStm;

	public NodePrg() {
		this.dclStm = null;
	}
	
	public NodePrg(ArrayList<NodeDclStm> dclStm) {
		this.dclStm = new ArrayList<NodeDclStm>();
		this.dclStm.addAll(dclStm);
	}
	
	//methods
	public ArrayList<NodeDclStm> getDclStm(){
		return this.dclStm;
	}
	
	@Override
	public String toString() {
		return dclStm.toString();
	}

	@Override
	public boolean equals(Object o) {
		if(this == o ||
				this.dclStm.equals(((NodePrg)o).getDclStm()))
			return true;
		return false;
	}
}
