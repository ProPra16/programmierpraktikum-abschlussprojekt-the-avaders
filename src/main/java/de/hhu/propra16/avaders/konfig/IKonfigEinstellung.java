package de.hhu.propra16.avaders.konfig;

/**
 * Interface für eine Einstellung in {@link KonfigWerte}.
 * 
 * @author Florian-Dt
 */
public interface IKonfigEinstellung {
	/**
	 * Setzt den Wert der Einstellung.
	 * 
	 * @param wert der Wert, auf den der Eintrag gesetzt werden soll
	 */
	public void wertSetzen(String wert);

	/**
	 * Gibt falls möglich den Wert als {@link boolean} zurück.
	 * 
	 * @return der Wert der Einstellung als {@link boolean}
	 * @throws EinstellungKonvertierenException falls der Wert nicht konvertiert werden konnte
	 */
	public boolean booleanAbfragen() throws EinstellungKonvertierenException;
	
	/**
	 * Gibt falls möglich den Wert als {@link int} zurück.
	 *
	 * @return der Wert der Einstellung als {@link int}
	 * @throws EinstellungKonvertierenException falls der Wert nicht konvertiert werden konnte
	 */
	public int integerAbfragen() throws EinstellungKonvertierenException;
	
	/**
	 * Gibt falls möglich den Wert als {@link String} zurück.
	 *
	 * @return der Wert der Einstellung als {@link String}
	 * @throws EinstellungKonvertierenException falls der Wert nicht konvertiert werden konnte
	 */
	public String stringAbfragen() throws EinstellungKonvertierenException;
}
