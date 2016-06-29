package de.hhu.propra16.avaders.testen;

import vk.core.api.CompilationUnit;

public class TesterDummy implements ITester {
	private final ITestenRueckgabe rueckgabe;
	
	public TesterDummy(final ITestenRueckgabe rueckgabe) {
		this.rueckgabe = rueckgabe;
	}
	
	@Override
	public ITestenRueckgabe testen(CompilationUnit[] sources) {
		return rueckgabe;
	}
}
