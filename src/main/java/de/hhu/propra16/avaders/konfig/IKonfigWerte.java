package de.hhu.propra16.avaders.konfig;

/*
 * Interface einer Klasse, die Name-Wert-Paare mit den Einstellungen verwaltet
 * @author Florian-Dt
 */
public interface IKonfigWerte {
	/* Erstellt einen neuen Eintrag in der Werteliste mit dem übergebenen Namen und Wert
	 * oder setzt den Wert des Eintrags mit diesem Namen, falls er schon existiert
	 * @param eigenschaft Name des Eintrags
	 *        wert        Neuer wert des Eintrags
	 * @return <i>Nichts</i>
	 */
	public void einstellungEintragen(String eigenschaft, IKonfigEintrag wert);

	/* Sucht nach dem Eintrag und gibt falls möglich seinen Wert zurück, sonst wird eine Exception geworfen
	 * @param eigenschaft Name des gesuchten Eintrags
	 * @return {@link IKonfigEintrag} Der Wert des Eintrags
	 */
	public IKonfigEintrag einstellungAbfragen(String Eigenschaft) throws Exception;
}