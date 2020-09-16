package ast;

import java.util.ArrayList;

public class NodePrg extends NodeAST {
	private ArrayList<NodeDclStm> dclStm;

	public NodePrg() {
	}
	
	public NodePrg(ArrayList<NodeDclStm> dclStm) {
		this.dclStm.addAll(dclStm);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
}
