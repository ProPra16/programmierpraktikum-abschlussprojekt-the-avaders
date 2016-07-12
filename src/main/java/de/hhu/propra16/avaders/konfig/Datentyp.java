package de.hhu.propra16.avaders.konfig;

/**
 * Diese Aufzählung beinhaltet alle Datentypen, die in {@link WerteEintrag} gespeichert werden
 * können und sollte die Datentypen beinhalten, die bezüglich den Programmeinstellungen und
 * Statuswerten verwendet werden
 */
public enum Datentyp {
	/**
	 * Ein Wahrheitswert als primitiver Wahrheitswert. In einen Integer-Wert umgewandelt wird der
	 * Zustand "Wahr" in 1 und der Zustand "Falsch" in 0 konvertiert.
	 */
	BOOLEAN,
	
	/**
	 * Ein ganzzahliger Wert als primitiver Datentyp. In einen Boolean-Wert umgewandelt entsprechen
	 * der Wert 0 dem Zustand "Falsch" und alle anderen Werte dem Zustand "Falsch".
	 */
	INTEGER,
	
	/**
	 * Eine Zeichenkette, die ohne Konvertierungsprobleme alle anderen Datentypen speichern kann.
	 */
	STRING
}
