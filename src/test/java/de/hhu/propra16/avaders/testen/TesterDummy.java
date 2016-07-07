package de.hhu.propra16.avaders.testen;

import vk.core.api.CompilationUnit;

public class TesterDummy implements ITester {
	public TesterDummy() {}
	
	@Override
	public ITestenRueckgabe testen(final CompilationUnit... sources) {
		return ((CompilationUnitDummy)sources[sources.length-1]).rueckgabe;
	}
}
