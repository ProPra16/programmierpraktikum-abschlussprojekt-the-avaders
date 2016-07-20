package de.hhu.propra16.avaders.projekt;

/**
 * Diese Aufzählung umfasst alle Typen, die eine Klasse haben kann. Abgesehen von QUELLTEXT stehen
 * alle Typen bei der Umwandlung zu einer CompilationUnit für einen Test
 */

public enum Klassentyp {
	/**
	 * Quelltexte umfassen das eigentliche Programm, abgesehen von den Tests.
	 */
	QUELLTEXT,

	/**
	 * Pruefungen werden vom Nutzer im Schritt RED geschriben. Sie müssen am Ende dieses
	 * Schrittes fehlschlagen und am Ende des GREEN-Schrittes bestanden werden.
	 */
	PRUEFUNG,

	/**
	 * Akzeptanztests werden von Nutzer geschriben, müssen jedoch nicht schon nach einem
	 * Zyklus erfüllt werden
	 */
	AKZEPTANZTEST,

	/**
	 * Durch die Konfiguration vorgegebener Test. Es ist Aufgabe des Nutzers, ihn zu bestehen
	 * Eine weitere Auflage ist, dass mit jedem Zyklus nur eine Aufgabe zusätzlich bestanden
	 * werden darf
	 */
	AUFGABE;
}
