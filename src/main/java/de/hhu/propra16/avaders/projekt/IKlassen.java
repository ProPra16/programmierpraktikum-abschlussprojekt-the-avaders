package de.hhu.propra16.avaders.projekt;

import java.util.List;
import vk.core.api.CompilationUnit;

/**
 * Verwaltet alle Klassen eines Projekts. Vor jedem Projektschritt müssen alle externen Änderungen
 * an einzelnen Klassen Quelltext zurückgespielt werden. Dieses Interface wird von {@link Klassen}
 * implementiert.
 *
 * @author Florian-Dt
 * @see IKlasse
 */

public interface IKlassen {
	/**
	 * Erstellt eine neue Klasse mit dem übergebenen Typ. Falls bereits eine Klasse mit dem
	 * übergebenen Namen existiert, wird die alte Klasse gelöscht und eine neue erstellt.
	 *
	 * @param pKlassenname Der Name der neuen Klasse
	 * @param pTyp Der {@link Klassentyp} der neien Klasse
	 */
	public void neueKlasse(String pKlassenname, Klassentyp pTyp);

	/**
	 * Speichert den Quelltext in einer bestimmten Klasse
	 *
	 * @param pKlassenname Der Name der Klasse, in die gespeichert werden soll
	 * @param pQuelltext Der für die Klasse zu speichernde Quelltext
	 * @throws KlasseNichtGefunden Falls die Klasse mit dem übergebenen Namen nicht gefunden werden konnte
	 */
	public void quelltextSetzen(String pKlassenname, String pQuelltext) throws KlasseNichtGefunden;

	/**
	 * Fragt einen {@link Klassentyp} ab
	 *
	 * @param pKlassenname Der Name der Klasse, deren Typ abgefragt wird
	 * @return Der geforderte Klassentyp
	 * @throws KlasseNichtGefunden Falls die Klasse mit dem übergebenen Namen nicht gefunden werden konnte
	 */
	public Klassentyp typAbfragen(String pKlassenname) throws KlasseNichtGefunden;

	/**
	 * Fragt den Quelltext einer Klasse ab
	 *
	 * @param pKlassenname Der Name der Klasse, deren Quelltext abgefragt wird
	 * @return Der geforderte Quelltext als String
	 * @throws KlasseNichtGefunden Falls die Klasse mit dem übergebenen Namen nicht gefunden werden konnte
	 */
	public String quelltextAbfragen(String pKlassenname) throws KlasseNichtGefunden;

	/**
	 * Gibt ein Feld zurück, das alle Klassennamen enthält. So kann über alle verwalteten Klassen
	 * iteriert werden
	 *
	 * @return Ein Feld aus Strings mit allen Klassennamen
	 */
	public String[] alleKlassennamen();

	/**
	 * Erstellt aus jeder verwalteten Klasse eine {@link CompilationUnit} und gibt diese für alle
	 * Klassen in einem Feld zurück
	 *
	 * @return Eine Feld aus {@link CompilationUnit}s aller verwalteten Klassen
	 */
	public CompilationUnit[] umwandeln();
}
