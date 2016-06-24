//Test-Dummy, der das Interface ILogikKonfigDaten und
//beim Aufruf von toBoolean() immer true zur�ckgibt

package de.hhu.propra16.avaders.logik;

import de.hhu.propra16.avaders.logik.ILogikKonfigDaten;

public class LogikKonfigDatenDummyTrue implements ILogikKonfigDaten {
	public boolean toBoolean() {
		return true;
	}
}