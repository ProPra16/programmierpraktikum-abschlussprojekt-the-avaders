package de.hhu.propra16.avaders.logik;

public class LogikKonfigDummy implements ILogikKonfig {
	private final ILogikKonfigDaten value;
	
	public LogikKonfigDummy(final ILogikKonfigDaten value) {
		this.value = value;
	}
	
	@Override
	public ILogikKonfigDaten einstellung(String name) {
		return value;
	}
}
