package de.hhu.propra16.avaders.catalogueLoader;

/**
 * Signals that an exception has occurred, while parsing the file
 */
public class ParserException extends Exception {
	/**
	 * Produces a {@link ParserException} holding a message
	 * @param msg The message detailing the exception
     */
	public ParserException(String msg){ super(msg); }
}
