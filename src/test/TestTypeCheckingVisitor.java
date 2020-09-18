package test;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import ast.NodeAST;
import parser.Parser;
import parser.SyntaxException;
import symbolTable.SymbolTable;
import visitor.TypeCheckingVisitor;



public class TestTypeCheckingVisitor {
	@Test
	public void testTypeCheckingVisitor(){
		String path ="src/test/data/testParser.txt";
		try {
			Parser parser = new Parser(path);
			try {
				NodeAST nodePrg = parser.parse();
				System.out.println(nodePrg.toString());
				TypeCheckingVisitor tc = new TypeCheckingVisitor();
				nodePrg.accept(tc);
				System.out.println(SymbolTable.toStr());
				System.out.println(tc.log());
			} catch (SyntaxException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}
