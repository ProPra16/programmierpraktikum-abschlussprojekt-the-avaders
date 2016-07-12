package de.hhu.propra16.avaders.statistik;

import de.hhu.propra16.avaders.logik.LogikRueckgabe;
import java.util.List;

/**
 * Die Statistik vermerkt alle relevanten Testergebnisse mit einem Zeitstempel und speichert sie
 * ab. Diese Daten können seriell wieder ausgelesen werden, beispielsweise, um mit der Klasse
 * {@link Zeitstrahl} grafisch ausgegeben zu werden.
 */

public class Statistik implements IStatistik {
	private List<ZeitDaten> fDaten;
	private ZeitGeber fZeitGeber;
	private int zeiger;

	/**
	 * Erstellt eine neue Instanz von Statistik mit leerer Liste. Sobald der erste Eintrag
	 * in der Liste erstellt wird, wird dieser ausgewählt.
	 *
	 * @return Eine Neue Instanz von Statstik
	 */
	public Statistik(ZeitGeber zeitGeber) {
		fZeitGeber = zeitGeber;
		zeiger = 0;
	}

	/**
	 * {@InheritedDoc}
	 */
	public void wechselEintragen(LogikRueckgabe daten) {
		fDaten.add(new ZeitDaten(fZeitGeber.abfragen(), daten));
	}

	/**
	 * {@InheritedDoc}
	 */
	public void zumErstenEintrag() {
		if (zeiger>0) zeiger = 0;
	}

	/**
	 * {@InheritedDoc}
	 */
	public void naechsterEintrag() {
		if (zeiger<fDaten.size()-1) zeiger++;
	}

	/**
	 * {@InheritedDoc}
	 */
	public ZeitDaten eintragAbfragen() {
		if (fDaten.size()==0)
			return null;
		else
			return fDaten.get(zeiger);
	}
}
