package de.hhu.propra16.avaders.logik;

public class LogikKonfigDatenDummy implements ILogikKonfigDaten {
	private final boolean value;
	
	public LogikKonfigDatenDummy(final boolean value) {
		this.value = value;
	}
	
	public boolean toBoolean() {
		return value;
	}
}
