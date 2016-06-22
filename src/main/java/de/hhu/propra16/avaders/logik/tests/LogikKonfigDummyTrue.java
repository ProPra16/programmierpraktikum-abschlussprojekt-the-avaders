//Test-Dummy, der das Interface ILogikKonfig implementiert,
//beim Aufruf von naechsterTest() immer true zurückgibt
//einen Test-Dummy verwaltet, der das Interface
//ILogikKonfigDaten implementiert und
//beim Aufruf von toBoolean() immer true zurückgibt

package de.hhu.propra16.avaders.logik.tests;

import de.hhu.propra16.avaders.logik.ILogikKonfig;
import de.hhu.propra16.avaders.logik.ILogikKonfigDaten;

public class LogikKonfigDummyTrue implements ILogikKonfig {
	public ILogikKonfigDaten Einstellung(String Eigenschaft) {
		return new LogikKonfigDatenDummyTrue();
	}

	public void ErstenTestAuswaehlen() { }

	public boolean naechsterTest() {
		return true;
	}
}