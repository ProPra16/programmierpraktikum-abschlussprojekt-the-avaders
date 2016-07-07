package de.hhu.propra16.avaders.catalogueLoader;

/**
 * Signals that an exception has occurred, while parsing the file
 */
public class ParserException extends Exception {
	public ParserException(String msg){ super(msg); }
}
