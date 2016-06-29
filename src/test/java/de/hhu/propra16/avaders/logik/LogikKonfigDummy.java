package de.hhu.propra16.avaders.logik;

public class LogikKonfigDummy implements ILogikKonfig {
	private final ILogikKonfigDaten value;
	
	public LogikKonfigDummy(final ILogikKonfigDaten value) {
		this.value = value;
	}
	
	public ILogikKonfigDaten Einstellung(String Eigenschaft) {
		return value;
	}

	public void ErstenTestAuswaehlen() {}

	public boolean naechsterTest() {
		return false;
	}
}
