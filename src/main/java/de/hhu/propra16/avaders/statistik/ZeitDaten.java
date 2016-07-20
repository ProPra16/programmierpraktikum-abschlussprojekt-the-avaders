package de.hhu.propra16.avaders.statistik;

import de.hhu.propra16.avaders.logik.LogikRueckgabe;

/**
 * Eine Instanz der Klasse ZeitDaten speichert Daten mit einem Zeitstempel, die Testergebnisse
 * und Statusinformationen beinhalten. Diese Daten sind nach ERstellung nicht mehr änderbar,
 * können aber ausgelesen werden. Zusätzlich wird das Interface Comperable implementiert, dass
 * es ermöglicht, zwei Zeitstempel miteinander zu vergleichen.
 */

public class ZeitDaten implements IZeitDaten {
	private LogikRueckgabe fDaten;
	protected IZeitWert fZeitWert;

	/**
	 * Erstellt eine neue Instanz von ZeitDaten mit dem übergebenen Zeitstempel und den
	 * übergebenen Daten
	 *
	 * @param zeitWert Ein aktueller Zeitstempel, der dauerhaft gespeichert wird
	 * @param daten Die Programmdaten, die dauerhaft gespeichert werden sollen
	 */
	public ZeitDaten(IZeitWert zeitWert, LogikRueckgabe daten) {
		fDaten = daten;
		fZeitWert = zeitWert;
	}

	/**
	 * {@InheritedDoc}
	 */
	public IZeitWert zeitWertAbfragen() {
		return fZeitWert;
	}

	/**
	 * {@InheritedDoc}
	 */
	public LogikRueckgabe datenAbfragen() {
		return fDaten;
	}

	/**
	 * {@InheritedDoc}
	 */
	public int compareTo(IZeitDaten anderer) {
		return fZeitWert.compareTo(anderer.zeitWertAbfragen());
	}

	//Ergänzung für dummes Java
	public int compareTo(ZeitDaten anderer) {
		return compareTo(anderer);
	}
}
