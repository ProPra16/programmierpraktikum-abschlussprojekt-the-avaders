//Test-Dummy, der das Interface ITester und
//einen Test-Dummy verwaltet, der das Interface
//ITestenRueckgabe implementiert und
//beim Aufruf von testBestanden() immer false zurückgibt

package de.hhu.propra16.avaders.logik;

public class TesterDummyFalse implements ITester {
	public ITestenRueckgabe Testen(ITestenUebergabe quelltext) {
		return new TestenRueckgabeDummyFalse();
	}
}
