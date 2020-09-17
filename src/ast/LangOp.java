package ast;
//enum per il tipo di operatore
public enum LangOp {
	PLUS("+"),
	MINUS("-"),
	TIMES("*"),
	DIVIDE("/");
	public final String label;
	private LangOp(String label) {
		this.label = label;
	}
}
