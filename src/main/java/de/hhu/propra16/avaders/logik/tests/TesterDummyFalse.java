//Test-Dummy, der das Interface ITester und
//einen Test-Dummy verwaltet, der das Interface
//ITestenRueckgabe implementiert und
//beim Aufruf von testBestanden() immer false zurückgibt

package de.hhu.propra16.avaders.logik.tests;

import de.hhu.propra16.avaders.logik.ITester;
import de.hhu.propra16.avaders.logik.ITestenRueckgabe;
import de.hhu.propra16.avaders.logik.ITestenUebergabe;

public class TesterDummyFalse implements ITester {
	public ITestenRueckgabe Testen(ITestenUebergabe quelltext) {
		return new TestenRueckgabeDummyFalse();
	}
}