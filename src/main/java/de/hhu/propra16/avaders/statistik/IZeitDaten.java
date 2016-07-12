package de.hhu.propra16.avaders.statistik;

import de.hhu.propra16.avaders.logik.LogikRueckgabe;

/**
 * Eine Instanz der Klasse ZeitDaten speichert Daten mit einem Zeitstempel, die Testergebnisse
 * und Statusinformationen beinhalten. Diese Daten sind nach ERstellung nicht mehr änderbar,
 * können aber ausgelesen werden. Zusätzlich wird das Interface Comperable implementiert, dass
 * es ermöglicht, zwei Zeitstempel miteinander zu vergleichen.
 */

public interface IZeitDaten extends Comparable<ZeitDaten> {
	/**
	 * Fragt den dauerhaft gespeicherten Zeitstempel ab
	 *
	 * @return Der gespeicherte Zeitstempel als ZeitWert
	 */
	public IZeitWert zeitWertAbfragen();

	/**
	 * Fragt die zum Zeitstempel gespeicherten Progammdaten ab
	 *
	 * @return Instanz dder Klasse LogikRueckgabe mit den gespeicherten Daten
	 */
	public LogikRueckgabe datenAbfragen();

	/**
	 * Vergleicht den ZeitStempel dieser Instanz von ZeitDaten mit dem der übergebenen Instanz
	 * @param anderer Eine andere Instanz von ZeitDaten, mit der die Zeitstempel verglichen
	 *                werden sollen
	 * @return -1, falls der Zeitstempel dieser Instanz kleiner ist als der Zeitstempel der
	 *             übergebenen Instanz<p>
	 *          0, falls beide Zeitstempel gleich sind<p> 
	 *          1, falls der Zeitstempel dieser Instanz größer ist als der Zeitstempel der
	 *             übergebenen Instanz
	 */
	public int compareTo(IZeitDaten anderer);
}
