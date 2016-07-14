package de.hhu.propra16.avaders.projekt;

import vk.core.api.CompilationUnit;

/**
 * Verwaltet eine Klasse mit ihrem Namen, Klassentyp und Quelltext. Vor jedem Schritt müssen alle
 * externen Änderungen am Quelltext zurückgespielt werden. Diese Klasse implementiert das
 * Interface {@link IKlasse}, aus dem die meisten Funktionen stammen.
 *
 * @author Florian-Dt
 * @see Klassentyp
 */

public class Klasse implements IKlasse {
	private String fName;
	private String fQuelltext;
	private Klassentyp fTyp;

	/**
	 * Erstellt eine neue Klasse mit dem übergebenen Namen und Klassentyp. Der Quelltext ist zu
	 * Beginn leer
	 *
	 * @param pName Der Klassenname der neuen Klasse
	 * @param pTyp Der Klassentyp der neuen Klasse
	 * @return Eine neue Instanz der Klasse
	 */
	public Klasse(String pName, Klassentyp pTyp) {
		fName = pName;
		fQuelltext = "";
		fTyp = pTyp;
	}

	/**
	 * {@inheritDoc} 
	 */
	public String namenAbfragen() {
		return fName;
	}

	/**
	 * {@inheritDoc} 
	 */
	public Klassentyp typAbfragen() {
		return fTyp;
	}

	/**
	 * {@inheritDoc} 
	 */
	public void quelltextSetzen(String pQuelltext) {
		fQuelltext = pQuelltext;
	}

	/**
	 * {@inheritDoc} 
	 */
	public String quelltextAbfragen() {
		return fQuelltext;
	}

	/**
	 * {@inheritDoc} 
	 */
	public CompilationUnit umwandeln() {
		return new CompilationUnit(fName, fQuelltext, fTyp != Klassentyp.QUELLTEXT);
	}
}
