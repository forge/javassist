package org.jboss.forge.furnace.proxy.javassist.compiler;

import org.jboss.forge.furnace.proxy.javassist.compiler.CompileError;
import org.jboss.forge.furnace.proxy.javassist.compiler.Lex;
import org.jboss.forge.furnace.proxy.javassist.compiler.Parser;
import org.jboss.forge.furnace.proxy.javassist.compiler.SymbolTable;
import org.jboss.forge.furnace.proxy.javassist.compiler.TokenId;
import org.jboss.forge.furnace.proxy.javassist.compiler.ast.*;

public class ParseTest implements TokenId {
    public static void main(String[] args) throws CompileError {
	Parser p = new Parser(new Lex(args[0]));
	SymbolTable stb = new SymbolTable();
	// MethodDecl s = (MethodDecl)p.parseMember(stb);
	Stmnt s = p.parseStatement(stb);
	System.out.println(s == null ? "null" : s.toString());
    }
}
