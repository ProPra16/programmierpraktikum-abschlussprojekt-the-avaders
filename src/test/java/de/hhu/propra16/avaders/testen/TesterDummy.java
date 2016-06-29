package de.hhu.propra16.avaders.testen;

public class TesterDummy implements ITester {
	private final ITestenRueckgabe rueckgabe;
	
	public TesterDummy(final ITestenRueckgabe rueckgabe) {
		this.rueckgabe = rueckgabe;
	}
	
	public ITestenRueckgabe Testen(ITestenUebergabe quelltext) {
		return rueckgabe;
	}
}
