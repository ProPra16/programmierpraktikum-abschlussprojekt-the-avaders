package de.hhu.propra16.avaders.statistik;

import de.hhu.propra16.avaders.logik.LogikRueckgabe;

/**
 * Die Statistik vermerkt alle relevanten Testergebnisse mit einem Zeitstempel und speichert sie
 * ab. Diese Daten können seriell wieder ausgelesen werden, beispielsweise, um mit der Klasse
 * {@link Zeitstrahl} grafisch ausgegeben zu werden.
 */

public interface IStatistik {
	/**
	 * Trägt die übergegebenenen Änderungen in die Statistik ein. Der Zeiger wird nicht geändert.
	 *
	 * @param daten Daten, die Testergebnisse und den Programmstatus beinhalten
	 */
	public void wechselEintragen(LogikRueckgabe daten);

	/**
	 * Wählt den ersten Eintrag in der Statistik aus. Diese serielle Abarbeitung ist nützlich
	 * zur grafischen Darstellung mit der Klasse {@link Zeitstrahl}
	 */
	public void zumErstenEintrag();

	/**
	 * Wählt den nächsten Eintrag in der Statistik aus. Diese serielle Abarbeitung ist nützlich
	 * zur grafischen Darstellung mit der Klasse {@link Zeitstrahl}
	 */
	public void naechsterEintrag();

	/**
	 * Fragt den momentan ausgewählten Eintrag in der Statistik ab. Falls die Statistik leer ist,
	 * wird nul zurückgegeben
	 *
	 * @return Die momentan ausgewählten ZeitDaten
	 */
	public ZeitDaten eintragAbfragen();
}
