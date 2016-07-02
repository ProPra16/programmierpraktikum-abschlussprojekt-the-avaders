package de.hhu.propra16.avaders.konfig;

/*
 * Exception, die geworfen wird, wenn die abgefragte Einstellung nicht gefunden wurde
 * @author Florian-Dt
 */

public class EintragKonvertierenFehler extends Exception {
	/*
	 * Wirft eine EintragKonvertierenFehler-Exception
	 * @param wert Einstellungswert, der nicht konvertiert werden konnte
	 */
	public EintragKonvertierenFehler(String wert) {
		super(wert);
	}
}
