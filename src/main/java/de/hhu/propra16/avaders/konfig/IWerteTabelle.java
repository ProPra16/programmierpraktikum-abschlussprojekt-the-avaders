package de.hhu.propra16.avaders.konfig;

import java.text.ParseException;

/**
 * Eine Klasse, die IWerteTabelle implementiert, soll eine Liste mit {@link WerteEintrag}
 * erwalten. Dort sollen Einstellungen und Statuswerte des Programms in einer Datenstruktur
 * gespeichert werden, unabhängig von ihrem {@link Datentyp}. Als Datentypen sind zur Zeit
 * String und Integer möglich.
 * <p>
 * Dieses Interface ersetzt das Interface der Klasse {@link KonfigWerte}, die den gleichen
 * Zweck hatte, jedoch auf die Speicherung von Einstellungsdaten spezialisiert war.
 *
 * @author Florian-Dt
 */

public interface IWerteTabelle {
	/**
	 * Speichert eine Einrag mit dem übergebenen Namen und Wert in der Wertetabelle.
	 * Falls bereits ein Eintrag mit diesem Namen vorhanden ist, wird ihr Wert ersetzt.
	 *
	 * @param pName Der eindeutige Name des Eintrags, über den er verwaltet wird
	 * @param pWert Ein Integer-Wert, mit dem der neue Eintrag initialisiert wird
	 */
	public void intSetzen(String pName, int pWert);

	/**
	 * Speichert eine Einrag mit dem übergebenen Namen und Wert in der Wertetabelle.
	 * Falls bereits ein Eintrag mit diesem Namen vorhanden ist, wird ihr Wert ersetzt.
	 *
	 * @param pName Der eindeutige Name des Eintrags, über den er verwaltet wird
	 * @param pWert Ein String, mit dem der neue Eintrag initialisiert wird
	 */
	public void stringSetzen(String pName, String pWert);

	/**
	 * Sucht in der Wertetabelle nach einem Eintrag mit dem übergebenen Namen und gibt seinen
	 * Wert als Integer zurück.
	 *
	 * @param pName Der Name des gesuchten Eintrags
	 * @return Der Wert des Eintrags als Integer
	 * @throws EintragNichtGefunden Falls kein Eintrag mit dem übergebenen Namen in der
	 *                              Wertetabelle gefunden werden konnte
	 * @throws PharserException Falls der Wert nicht in einen Integer umgewandelt werden
	 *                          konnte
	 */
	public int intAbfragen(String pName) throws EintragNichtGefunden, ParseException;

	/**
	 * Sucht in der Wertetabelle nach einem Eintrag mit dem übergebenen Namen und gibt seinen
	 * Wert als String zurück.
	 *
	 * @param pName Der Name des gesuchten Eintrags
	 * @return Der Wert des Eintrags als String
	 * @throws EintragNichtGefunden Falls kein Eintrag mit dem übergebenen Namen in der
	 *                              Wertetabelle gefunden werden konnte
	 */
	public String stringAbfragen(String pName) throws EintragNichtGefunden;
}
