package de.hhu.propra16.avaders.statistik;

/*
 * Die Klasse ZeitWert repräsentiert einen Zeitstempel, der einen entsprehcenden Wert dauerhaft
 * speichert und Funktionen zur Umrechnung bietet. Außerdem implementiert er das Interface
 * Comperable und kann sich so mit anderen Zeitstempeln vergleichen.
 */

public class ZeitWert implements IZeitWert {
	protected double wert;

	/**
	 * Erstellt einen neuen Zeitstempel mit dem übergebenen Wert vom Zeitgeber
	 *
	 * @param daten Ein für diesen Programmgesamtzyklus eindeutiger Zeitwert, beispielsweise die
	 *              bisherige Laufzeit des Projekts in Millisekunden
	 * @return Der erzeugte Zeitstempel
	 */
	public ZeitWert(double daten) {
		wert = daten;
	}

	/**
	 * {@InheritedDoc}
	 */
	public double wertAbfragen() {
		return wert;
	}

	/**
	 * {@InheritedDoc}
	 */
	public int compareTo(IZeitWert anderer) {
		if (wert<anderer.wertAbfragen()) return -1;
		if (wert>anderer.wertAbfragen()) return 1;
		return 0;
	}

	//Ergänzung für dummes Java
	public int compareTo(ZeitWert anderer) {
		return compareTo(anderer);
	}
}
