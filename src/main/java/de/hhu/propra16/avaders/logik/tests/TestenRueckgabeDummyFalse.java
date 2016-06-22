//Test-Dummy, der das Interface ITestenRueckgabe und
//beim Aufruf von testBestanden() immer false zurückgibt

package de.hhu.propra16.avaders.logik.tests;

import de.hhu.propra16.avaders.logik.ITestenRueckgabe;

public class TestenRueckgabeDummyFalse implements ITestenRueckgabe {
	public boolean testBestanden() {
		return false;
	}
}