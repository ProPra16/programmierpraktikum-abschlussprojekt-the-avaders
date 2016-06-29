package de.hhu.propra16.avaders.testen;

public class TestenRueckgabeDummy implements ITestenRueckgabe {
	private final boolean testBestanden;
	
	public TestenRueckgabeDummy(final boolean testBestanden) {
		this.testBestanden = testBestanden;
	}
	
	public boolean testBestanden() {
		return testBestanden;
	}
}
