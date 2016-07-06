package de.hhu.propra16.avaders.konfig;

/**
 * Exception, die geworfen wird, wenn eine Einstellung nicht gefunden wurde.
 * 
 * @author Florian-Dt
 */
public class EinstellungNichtGefundenException extends RuntimeException {
	public EinstellungNichtGefundenException() {
		super();
	}
	
	public EinstellungNichtGefundenException(String message) {
		super(message);
	}
	
	public EinstellungNichtGefundenException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public EinstellungNichtGefundenException(Throwable cause) {
		super(cause);
	}
}
