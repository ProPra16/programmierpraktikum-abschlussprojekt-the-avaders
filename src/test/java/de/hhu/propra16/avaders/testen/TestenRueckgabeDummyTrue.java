//Test-Dummy, der das Interface ITestenRueckgabe und
//beim Aufruf von testBestanden() immer true zurückgibt

package de.hhu.propra16.avaders.logik;

public class TestenRueckgabeDummyTrue implements ITestenRueckgabe {
	public boolean testBestanden() {
		return true;
	}
}
