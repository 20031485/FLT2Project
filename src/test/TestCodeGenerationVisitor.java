package test;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;
import ast.NodeAST;
import ast.NodePrg;
import parser.Parser;
import parser.SyntaxException;
import scanner.Scanner;
import symbolTable.SymbolTable;
import visitor.CodeGenerationVisitor;
import visitor.TypeCheckingVisitor;

public class TestCodeGenerationVisitor {
	@Test
	public void testCodeGenerationVisitor(){
		try {
			NodePrg ast = astInit("src/test/data/testParser.txt");
			CodeGenerationVisitor visitor = new CodeGenerationVisitor();
			try {
				visitor.visit(ast);
				System.out.println(visitor.getCode());
			}
			catch(IndexOutOfBoundsException e) {
				fail("IndexOutOfBoundsException\n");
			}
		} catch (FileNotFoundException | SyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private NodePrg astInit(String dataPath) throws FileNotFoundException, SyntaxException {
		String path = dataPath;
		//Scanner scanner = new Scanner(path);
		Parser parser = new Parser(path);
		NodePrg ast = (NodePrg) parser.parse();
		SymbolTable.init();
		TypeCheckingVisitor visitor1 = new TypeCheckingVisitor();
		visitor1.visit(ast);
		return ast;
	}
}