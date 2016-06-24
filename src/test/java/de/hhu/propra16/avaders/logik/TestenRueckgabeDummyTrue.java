//Test-Dummy, der das Interface ITestenRueckgabe und
//beim Aufruf von testBestanden() immer true zur�ckgibt

package de.hhu.propra16.avaders.logik;

import de.hhu.propra16.avaders.logik.ITestenRueckgabe;

public class TestenRueckgabeDummyTrue implements ITestenRueckgabe {
	public boolean testBestanden() {
		return true;
	}
}