package de.hhu.propra16.avaders.konfig;

/*
 * Exception, die geworfen wird, wenn die abgefragte Einstellung nicht gefunden wurde
 * @author Florian-Dt
 */

public class EinstellungNichtGefunden extends Exception {
	/*
	 * Wirft eine EinstellungNichtGefunden-Exception
	 * @param einstellung Einstellung, die nicht gefunden werden konnte
	 */
	public EinstellungNichtGefunden(String einstellung) {
		super(einstellung);
	}
}
