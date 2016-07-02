package de.hhu.propra16.avaders.testen;

import vk.core.api.CompilationUnit;

public interface ITester {
	/**
	 * Kompiliert die gegebenen {@link CompilationUnit}s und führt alle Tests aus.
	 * 
	 * @param sources die zu testenden {@link CompilationUnit}s
	 * @return die Compiler- und Testergebnisse
	 */
	public ITestenRueckgabe testen(CompilationUnit[] sources);
}
