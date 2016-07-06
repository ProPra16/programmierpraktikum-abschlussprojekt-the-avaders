package de.hhu.propra16.avaders.konfig;

/**
 * Verwaltet Konfig-Einstellungen als Name-Wert-Paare.
 * 
 * @author Florian-Dt
 */
public interface IKonfigWerte {
	/**
	 * Setzt den Wert für eine Einstellung. Falls sie noch nicht existiert, wird sie erstellt.
	 * 
	 * @param einstellung der Name der Einstellung
	 * @param wert der Wert der Einstellung
	 */
	public void einstellungEintragen(String einstellung, IKonfigEinstellung wert);

	/**
	 * Gibt den Wert der gegebenen Einstellung zurück.
	 * 
	 * @param einstellung der Name der gesuchten Einstellung
	 * @return der Wert der Einstellung
	 * @throws EinstellungNichtGefundenException wenn die Einstellung nicht existiert
	 */
	public IKonfigEinstellung einstellungAbfragen(String einstellung) throws EinstellungNichtGefundenException;
}
