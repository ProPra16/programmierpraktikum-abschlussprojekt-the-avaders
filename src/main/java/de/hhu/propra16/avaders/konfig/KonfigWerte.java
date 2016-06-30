package de.hhu.propra16.avaders.konfig;

import java.util.HashMap;

/*
 * Diese Klasse verwaltet Name-Wert-Paare mit den Einstellungen
 * @author Florian-Dt
 */

public class KonfigWerte implements IKonfigWerte {
	private HashMap<String, IKonfigEintrag> eigenschaften;

	/* Erstellt eine neue Instanz von KonfigWerte
	 * @param <i>Keine</i>
	 */
	public KonfigWerte() {
		eigenschaften = new HashMap<>();
	}

	/* Erstellt einen neuen Eintrag in der Werteliste mit dem übergebenen Namen und Wert
	 * oder setzt den Wert des Eintrags mit diesem Namen, falls er schon existiert
	 * @param eigenschaft Name des Eintrags
	 *        wert        Neuer wert des Eintrags
	 * @return <i>Nichts</i>
	 */
	public void einstellungEintragen(String eigenschaft, IKonfigEintrag wert) {
		eigenschaften.put(eigenschaft, wert);
	}

	/* Sucht nach dem Eintrag und gibt falls möglich seinen Wert zurück, sonst wird eine Exception geworfen
	 * @param eigenschaft Name des gesuchten Eintrags
	 * @return {@link IKonfigEintrag} Der Wert des Eintrags
	 */
	public IKonfigEintrag einstellungAbfragen(String eigenschaft) throws Exception {
		if (!eigenschaften.containsKey(eigenschaft)) throw new Exception();
		return eigenschaften.get(eigenschaft);
	}
}