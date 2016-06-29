package de.hhu.propra16.avaders.logik;

public interface ILogikKonfig {
	/**
	 * Gibt die Einstellung mit dem gegebenen Namen zurück.
	 * 
	 * @param name der Name der Einstellung
	 * @return der Wert der Einstellung
	 */
	public ILogikKonfigDaten einstellung(String name);
}
