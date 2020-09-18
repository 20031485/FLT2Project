package visitor;

import ast.*;
import symbolTable.Attributes;
import symbolTable.SymbolTable;

public class TypeCheckingVisitor implements IVisitor{
	StringBuilder log = new StringBuilder();
	
	@Override
	public void visit(NodePrg node) {
		SymbolTable.init();
		for(NodeAST ast : node.getDclStm()) {
			ast.accept(this);
		}
	}
	
	/*visita id e expr. 
	 * Poi controlla compatibilità e, se necessario, rimpiazza expr con converti(expr). 
	 * Deve assegnare Error a resType di node se l’espressione aveva resType uguale a Error 
	 * oppure se i tipi di identificatore e espressione non erano compatibili. 
	 * Se tutto va bene assegna VOID a resType.*/
	@Override
	public void visit(NodeAssign node) {
		//visita nodeId
		node.getId().accept(this);
		//visita nodeExpr
		node.getExpr().accept(this);
		//controlla compatibilità e, se necessario, rimpiazza expr con converti(expr)
		if(compatible(node.getId().getTypeDescriptor(), node.getExpr().getTypeDescriptor())) {
			node.setExpr(convert(node.getExpr()));
		}
		//assegna node.typeDesc = ERROR se expr.typeDesc = ERROR o se id ed expr non sono compatibili
		if(node.getExpr().getTypeDescriptor() == TypeDescriptor.ERROR
				|| !compatible(node.getId().getTypeDescriptor(), node.getExpr().getTypeDescriptor())) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
		}
		//Se tutto va bene assegna VOID a resType
		else {
			node.setTypeDescriptor(TypeDescriptor.VOID);
		}
	}

	/*visita exprLeft e exprRight. Poi
	(a) se uno delle sottoespressioni ha resType uguale a Error assegna Error a resType del nodo corrente
	(b) se i tipi delle espressioni sono uguali assegna il loro tipo al nodo corrente, altrimenti
	(c) introduce la conversione di tipo (come per l’assegnamento).*/
	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);
		//se uno delle sottoespressioni ha resType uguale a Error assegna Error a resType del nodo corrente
		if(node.getLeft().getTypeDescriptor() == TypeDescriptor.ERROR
				|| node.getRight().getTypeDescriptor() == TypeDescriptor.ERROR) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			log.append("ERROR: " + node.toString());
		}
		
		//se i tipi delle espressioni sono uguali assegna il loro tipo al nodo corrente, altrimenti
		else if(node.getLeft().getTypeDescriptor() == node.getRight().getTypeDescriptor()) {
			node.setTypeDescriptor(node.getLeft().getTypeDescriptor());
		}
		//introduce la conversione di tipo (come per l’assegnamento)
		else {
			node.setRight(convert(node.getRight()));
			node.setTypeDescriptor(node.getRight().getTypeDescriptor());
		}
	}
	
	@Override
	public void visit(NodeConv node) {
		//estende nodoAST
		//ha un TypeDesc
		//ottengo nodoEspres e faccio accept
		node.getExpr().accept(this);
		//se accept non dà errore
		//se il nodo NodeConv ha tipo == int dobbiamo trasformarlo float
//		se il nodo non è int, la conversione non è possibile, quindi imposto ad errore
	}
	
	@Override
	public void visit(NodeConst node) {
	}

	/*Sia id il campo di tipo NodeId di node: se il nome di
	id è già definito nella Symbol Table assegna Error a resType (typeDescriptor) di node.
	Altrimenti crea un oggetto attr (istanza di Attributes) contenente un campo
	con il suo tipo e inserisci nome associato a attr nella Symbol Table.*/
	@Override
	public void visit(NodeDcl node) {
		//se node è già definito nella ST, imposto il tipo di node a ERROR
		if(SymbolTable.lookup(node.getId().getId()) != null) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			log.append("ERROR: "+node.toString());//variabile già definita
		}
		//altrimenti creo degli attributi
		else {
			Attributes attrs = new Attributes();
			//agli attributi assegno il tipo del nodeId nel nodeDcl
			attrs.setType(node.getId().getTypeDescriptor());
			node.setTypeDescriptor(attrs.getType());
			node.getId().setDefinition(attrs);
			SymbolTable.enter(node.getId().getId(), attrs);
		}
	}

	@Override
	public void visit(NodeDeref node) {
		/*Attributes attrs;
		//se non c'è la entry nella ST
		if((attrs = SymbolTable.lookup(node.getId().getId())) == null) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			log.append("ERROR: "+node.toString());
		}
		//se c'è la entry
		else if(attrs.getType() == TypeDescriptor.INT || attrs.getType() == TypeDescriptor.FLOAT){
			node.setTypeDescriptor(attrs.getType());
		}*/
		node.getId().accept(this);
		//imposta tipo node a tipo id
		//vedi print
		node.setTypeDescriptor(node.getId().getTypeDescriptor());
	}

	/*Sia nome il nome contenuto in node. Se nome non è
	definito nella Symbol Table assegna Error a resType di node. Altrimenti
	assegna attr.tipo a node.resType e attr a node.definition.*/
	@Override
	public void visit(NodeId node) {
		//se il nome non è definito nella ST
		if(SymbolTable.lookup(node.getId()) == null) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			log.append("ERROR: "+node.toString()); //precisare
		}
		//se il nome è definito nella ST ha degli attributi
		else {
			//get attrs dalla ST con l'ID del nodeDeref
			Attributes attrs = SymbolTable.lookup(node.getId());
			//assegna node.resType = attrs.type
			node.setTypeDescriptor(attrs.getType());
			//assegna node.definition = attrs
			node.setDefinition(attrs);
			//inserisce la entry nella ST
			//SymbolTable.enter(node.getId(), attrs);
		}
	}

	/*Visita del campo id il nome contenuto in node. Se
	nome non è definito nella Symbol Table assegna Error a resType di node.
	Altrimenti assegna Void a resType di node.*/
	@Override
	public void visit(NodePrint node) {//fare come per nodeDeref>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		/*if(SymbolTable.lookup(node.getId().getId()) == null) {
			node.setTypeDescriptor(TypeDescriptor.ERROR);
			log.append("ERROR: "+node.toString());
		}
		else {
			node.getId().accept(this);
			node.setTypeDescriptor(TypeDescriptor.VOID);
		}*/
		//accept
		//imposta tipo di print con tipo di id
		node.getId().accept(this);
		node.setTypeDescriptor(node.getId().getTypeDescriptor());
	}
	
	private boolean compatible(TypeDescriptor t1, TypeDescriptor t2) {
		if(((t1 != TypeDescriptor.ERROR && t2 != TypeDescriptor.ERROR) && (t1 == t2))
				||(t1 == TypeDescriptor.FLOAT && t2 == TypeDescriptor.INT))
			return true;
		return false;
	}
	
	/*se resType di node è Float ritorna node altrimenti ritorna un NodeConv 
	 * che ha resType uguale a Float e contiene node.*/
	private NodeExpr convert(NodeExpr node) {
		if(node.getTypeDescriptor() == TypeDescriptor.FLOAT)
			return node;
		else {
			return new NodeConv(node, TypeDescriptor.FLOAT);
		}
	}
	
	public String log() {
		return log.toString();
	}
}