package de.hhu.propra16.avaders.konfig;

/**
 * Interface eine Klasse, die einen Eintrag in KonfigWerte verwaltet
 * Der Wert wird zuerst gesetzt und kann dann abgefragt werden
 * @author Florian-Dt
 */
public interface IKonfigEintrag {
	/*
	 * Setzt den Wert des Eintrags. Kann nicht mit dem Konstruktor zusammengelegt werden,
       * da dieser nicht im Interface gefordert werden kann.
	 * @param wert Der Wert, auf den der Wert des Eintrags gesetzt werden soll
	 * @return <i>Nichts</i>
	 */
	public void wertSetzen(String wert);

	/*
	 * Gibt falls möglich den Wert als Boolean zurück, wirft sonst eine Exception
	 * @param <i>Keine</i>
	 * @return boolean Der Wert des Eintrags als Wahrheitswert
	 * @exception Exception Falls der Wert nicht gesetzt ist oder nicht konvertiert werden konnte
	 */
	public boolean BooleanAbfragen() throws EintragKonvertierenFehler;

	/*
	 * Gibt falls möglich den Wert als Integer zurück, wirft sonst eine Exception
	 * @param <i>Keine</i>
	 * @return int Der Wert des Eintrags als Integer
	 * @exception Exception Falls der Wert nicht gesetzt ist oder nicht konvertiert werden konnte
	 */
	public int IntegerAbfragen() throws EintragKonvertierenFehler;

	/*
	 * Gibt falls möglich den Wert als String zurück, wirft sonst eine Exception
	 * @param <i>Keine</i>
	 * @return String Der Wert des Eintrags als Zeichenkette
	 * @exception Exception Falls der Wert nicht gesetzt ist oder nicht konvertiert werden konnte
	 */
	public String StringAbfragen() throws EintragKonvertierenFehler;
}
