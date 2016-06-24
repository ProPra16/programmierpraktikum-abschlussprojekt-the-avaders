//Test-Dummy, der das Interface ILogikKonfig implementiert,
//beim Aufruf von naechsterTest() immer false zur�ckgibt
//einen Test-Dummy verwaltet, der das Interface
//ILogikKonfigDaten implementiert und
//beim Aufruf von toBoolean() immer false zur�ckgibt

package de.hhu.propra16.avaders.logik;

public class LogikKonfigDummyFalse implements ILogikKonfig {
	public ILogikKonfigDaten Einstellung(String Eigenschaft) {
		return new LogikKonfigDatenDummyFalse();
	}

	public void ErstenTestAuswaehlen() { }

	public boolean naechsterTest() {
		return false;
	}
}