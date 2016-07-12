package de.hhu.propra16.avaders.konfig;

import java.util.HashMap;
import java.text.ParseException;

/**
 * Ein Klasse, die WerteEintrag implementiert, speichert einen einzelnen Wert mit einem bestimmten
 * {@Datentyp} und gibt ihn wieder zurück. Dabei wird er falls nötig konvertiert. Instanzen
 * solcher Klassen werden in einer {@link WerteTabelle} mit einem Namen versehen gespeichert und*
 * verwalten dort einzelne Einstellungen oder Statuswerte des Programms
 *
 * @author Florian-Dt
 * @see IWerteTabelle
 */

public interface IWerteEintrag {

	/**
	 * Speichert den übergebenen Wahrheitswert als Wert dieser Instanz und ersetzt dabei falls
	 * vorhanden den alten Wert
	 *
	 * @param pWert Ein Wahrheitswert, der als Wert gespeichert werden soll
	 */
	public void boolSetzen(boolean pWert);

	/**
	 * Speichert den übergebenen Integer als Wert dieser Instanz und ersetzt dabei falls vorhanden
	 * den alten Wert
	 *
	 * @param pWert Ein Integer-Wert, der als Wert gespeichert werden soll
	 */
	public void intSetzen(int pWert);

	/**
	 * Speichert den übergebenen String als Wert dieser Instanz und ersetzt dabei falls vorhanden
	 * den alten Wert
	 *
	 * @param pWert Ein String, der als Wert gespeichert werden soll
	 */
	public void stringSetzen(String pWert);

	/**
	 * Gibt den gespeicherten Wert als Boolean zurück. Falls der Wert im Original nicht als
	 * Boolean vorliegt, wird er entsprechend konvertiert
	 *
	 * @return Der gespeichert Wert als String
	 */
	public boolean boolAbfragen();

	/**
	 * Gibt den gespeicherten Wert als Integer zurück. Falls der Wert im Original nicht als
	 * Integer vorliegt, wird er entsprechend konvertiert
	 *
	 * @return Der gespeichert Wert als Integer
	 * @throws PharserException Falls der Wert nicht in einen Integer umgewandelt werden
	 *                          konnte
	 */
	public int intAbfragen() throws ParseException;

	/**
	 * Gibt den gespeicherten Wert als String zurück. Falls der Wert im Original nicht als
	 * String vorliegt, wird er entsprechend konvertiert
	 *
	 * @return Der gespeichert Wert als String
	 */
	public String stringAbfragen();
}
