package de.hhu.propra16.avaders.projekt;

import vk.core.api.CompilationUnit;

/**
 * Verwaltet eine Klasse mit ihrem Namen, Klassentyp und Quelltext. Vor jedem Schritt müssen alle
 * externen Änderungen am Quelltext zurückgespielt werden.  Dieses Interface wird von {@link Klasse}
 * implementiert.
 *
 * @author Florian-Dt
 * @see Klassentyp
 */

public interface IKlasse {
	/**
	 * Gibt den Klassennamen zurück
	 *
	 * @return Der Name der Klasse als String
	 */
	public String namenAbfragen();

	/**
	 * Gibt den Klassentyp zurück
	 *
	 * @return Der Klassentyp der Klasse
	 */
	public Klassentyp typAbfragen();

	/**
	 * Speichert den übergebenenen Quelltext in der Klasse. Der alte Quelltext wird ersetzt.
	 *
	 * @param pQuelltext Der zu speichernde Quelltext als String
	 */
	public void quelltextSetzen(String pQuelltext);

	/**
	 * Gibt den Quelltext der Klasse zurück
	 *
	 * @return Der Quelltext der Klasse
	 */
	public String quelltextAbfragen();

	/**
	 * Erstellt eine CompilationUnit aus der Klasse
	 *
	 * @return Eine CompilationUnit mit der Klassennamen, dem Quelltext und einem Wahrheitswert,
	 * der aussagt, ob es sich um einen Test handelt
	 */
	public CompilationUnit umwandeln();
}
