package de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions;

/**
 * Signals that an expected Token was not found
 */
public class MissingTokenException extends TokenException {
	public MissingTokenException(String token, int lineNr) {
		super("Line " + lineNr + " expected " + token + " but did not find it.");
	}
}
