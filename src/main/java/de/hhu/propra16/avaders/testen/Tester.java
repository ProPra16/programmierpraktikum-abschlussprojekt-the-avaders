package de.hhu.propra16.avaders.testen;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.JavaStringCompiler;

public class Tester implements ITester {
	@Override
	public ITestenRueckgabe testen(CompilationUnit[] sources) {
		JavaStringCompiler compiler = CompilerFactory.getCompiler(sources);
		compiler.compileAndRunTests();
		return new TestenRueckgabe(compiler.getCompilerResult(), compiler.getTestResult());
	}
}
