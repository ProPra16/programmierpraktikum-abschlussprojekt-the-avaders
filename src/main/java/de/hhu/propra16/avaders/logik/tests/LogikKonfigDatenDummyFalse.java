//Test-Dummy, der das Interface ILogikKonfigDaten und
//beim Aufruf von toBoolean() immer false zur�ckgibt

package de.hhu.propra16.avaders.logik.tests;

import de.hhu.propra16.avaders.logik.ILogikKonfigDaten;

public class LogikKonfigDatenDummyFalse implements ILogikKonfigDaten {
	public boolean toBoolean() {
		return false;
	}
}