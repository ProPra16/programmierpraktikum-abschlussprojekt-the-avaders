package de.hhu.propra16.avaders.projekt;

import java.io.IOException;
import java.text.ParseException;
import de.hhu.propra16.avaders.logik.ILogik;
import de.hhu.propra16.avaders.konfig.IWerteTabelle;
import de.hhu.propra16.avaders.konfig.EintragNichtGefunden;

/**
 * Diese Klasse soll ein vollständiges Projekt mit Quelltexten und Statuswerten verwalten. Ein
 * solches Projekt kann in eine {@Datei} geschrieben oder aus ihr gelesen werden. Außerdem kann
 * so die grafische Oberfläche besser getrennt werden. Diese verwaltet nur noch ein Projekt, keine
 * einzelnen Klassen mehr.
 */

public interface IProjekt {
	/**
	 * Setzt den Zyklus auf den Schritt RED zurück
	 */
	public void zuruecksetzen();

	/**
	 * Führt den nächsten Zyklusschritt aus
	 */
	public void weiter();

	/**
	 * Sucht in der untergeordneten Wertetabelle nach einem Eintrag mit dem übergebenen Namen
	 * und gibt seinen Wert als Boolean zurück.
	 *
	 * @param pName Der Name des gesuchten Eintrags
	 * @return Der Wert des Eintrags als Boolean
	 * @throws EintragNichtGefunden Falls kein Eintrag mit dem übergebenen Namen in der
	 *                              Wertetabelle gefunden werden konnte
	 * @throws PharserException Falls der Wert nicht in einen Integer umgewandelt werden
	 *                          konnte
	 */
	public int boolAbfragen(String pName) throws EintragNichtGefunden;

	/**
	 * Sucht in der untergeordneten Wertetabelle nach einem Eintrag mit dem übergebenen Namen 
	 * und gibt seinen Wert als Integer zurück.
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
	 * Sucht in der untergeordneten Wertetabelle nach einem Eintrag mit dem übergebenen Namen
	 * und gibt seinen Wert als String zurück.
	 *
	 * @param pName Der Name des gesuchten Eintrags
	 * @return Der Wert des Eintrags als String
	 * @throws EintragNichtGefunden Falls kein Eintrag mit dem übergebenen Namen in der
	 *                              Wertetabelle gefunden werden konnte
	 */
	public String stringAbfragen(String pName) throws EintragNichtGefunden;

	/**
	 * Speichert das gesamte Projekt in der übergebenen Datei
	 *
	 * @param pDatei Die Datei, in der das gesamte Projekt gespeichert werden soll
	 */
	public void speichern(IDatei pDatei) throws IOException, KlasseNichtGefunden;

	/**
	 * Lädt das gesamte Projekt aus der übergebenen Datei. Dies sollte nur erfolgen, wenn
	 * das Projekt noch leer ist
	 *
	 * @param pDatei Die Datei, aus der das gesamte Projekt geladen werden soll
	 */
	public void laden(IDatei pDatei) throws IOException, EOFaufgetreten, KlasseNichtGefunden;
}
