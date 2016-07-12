package de.hhu.propra16.avaders.projekt;

/**
 * Exception, die geworfen wird, wenn die abgefragte Klasse nicht gefunden wurde
 * @author Florian-Dt
 */

public class KlasseNichtGefunden extends Exception {
	/**
	 * Wirft eine KlsddrNichtGefunden-Exception
	 * @param klasse Klasse, die nicht gefunden werden konnte
	 */
	public KlasseNichtGefunden(String pKlasse) {
		super(pKlasse);
	}
}
