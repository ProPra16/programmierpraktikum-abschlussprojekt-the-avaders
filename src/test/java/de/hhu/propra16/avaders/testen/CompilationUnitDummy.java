package de.hhu.propra16.avaders.testen;

import vk.core.api.CompilationUnit;

public class CompilationUnitDummy extends CompilationUnit {
	final ITestenRueckgabe rueckgabe;
	
	public CompilationUnitDummy(final ITestenRueckgabe rueckgabe) {
		super(null, null, false);
		this.rueckgabe = rueckgabe;
	}
}
