package de.hhu.propra16.avaders.logik;

public interface ILogikKonfig {
	public ILogikKonfigDaten Einstellung(String Eigenschaft);
	public void ErstenTestAuswaehlen();
	public boolean naechsterTest();
}