//Test-Dummy, der das Interface ITester und
//einen Test-Dummy verwaltet, der das Interface
//ITestenRueckgabe implementiert und
//beim Aufruf von testBestanden() immer true zur�ckgibt

package de.hhu.propra16.avaders.logik;

public class TesterDummyTrue implements ITester {
	public ITestenRueckgabe Testen(ITestenUebergabe quelltext) {
		return new TestenRueckgabeDummyTrue();
	}
}