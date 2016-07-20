package de.hhu.propra16.avaders.statistik;

/*
 * Die Klasse ZeitWert repräsentiert einen Zeitstempel, der einen entsprehcenden Wert dauerhaft
 * speichert und Funktionen zur Umrechnung bietet. Außerdem implementiert er das Interface
 * Comperable und kann sich so mit anderen Zeitstempeln vergleichen.
 */

public interface IZeitWert extends Comparable<ZeitWert> {
	/**
	 * Fragt den dauerhaft gespeicherten Wert ab
	 *
	 * @return Der gespeicherte Wert als double
	 */
	public double wertAbfragen();


	/**
	 * Vergleicht diesen ZeitStempel mit dem übergebenen Zeitstempel
	 * @param anderer Eine andere Zeitsttempel, mit dem sich verglichen werden soll
	 * @return -1, falls dieser Zeitstempel kleiner ist als der übergebene Zeitstempel<p>
	 *          0, falls beide Zeitstempel gleich sind<p> 
	 *          1, falls dieser Zeitstempel größer ist als der übergebene Zeitstempel
	 */
	public int compareTo(IZeitWert anderer);
}
