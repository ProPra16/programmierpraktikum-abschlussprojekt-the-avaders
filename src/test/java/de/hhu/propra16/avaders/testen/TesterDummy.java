package de.hhu.propra16.avaders.testen;

import java.util.Arrays;
import java.util.Iterator;

import vk.core.api.CompilationUnit;

public class TesterDummy implements ITester {
	private final Iterator<ITestenRueckgabe> rueckgaben;
	
	public TesterDummy(final Iterator<ITestenRueckgabe> rueckgaben) {
		this.rueckgaben = rueckgaben;
	}
	
	public TesterDummy(final Iterable<ITestenRueckgabe> rueckgaben) {
		this(rueckgaben.iterator());
	}
	
	public TesterDummy(final ITestenRueckgabe[] rueckgaben) {
		this(Arrays.asList(rueckgaben));
	}
	
	@Override
	public ITestenRueckgabe testen(final CompilationUnit[] sources) {
		return this.rueckgaben.next();
	}
}
