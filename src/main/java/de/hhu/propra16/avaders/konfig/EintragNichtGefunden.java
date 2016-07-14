package de.hhu.propra16.avaders.konfig;

/*
 * Exception, die geworfen wird, wenn in einer {@link WerteTabelle} kein Eintrag mit dem
 * geforderten Namen gefunden werden konnte
 * <p>
 * Ersetzt die Klasse {@link EinstellungNichtGefunden}
 *
 * @author Florian-Dt
 */

public class EintragNichtGefunden extends Exception {
	/*
	 * Wirft eine EintragNichtGefunden-Exception
	 *
	 * @param pName Name des Eintrags, der nicht gefunden werden konnte
	 */
	public EintragNichtGefunden(String pName) {
		super(pName);
	}
}
