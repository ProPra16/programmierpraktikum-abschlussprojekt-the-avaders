package de.hhu.propra16.avaders.konfig;

/**
 * Exception, die geworfen wird, wenn eine Einstellung nicht konvertiert werden konnte.
 * 
 * @author Florian-Dt
 */
public class EinstellungKonvertierenException extends IllegalArgumentException {
	public EinstellungKonvertierenException() {
		super();
	}
	
	public EinstellungKonvertierenException(String message) {
		super(message);
	}
	
	public EinstellungKonvertierenException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public EinstellungKonvertierenException(Throwable cause) {
		super(cause);
	}
}
