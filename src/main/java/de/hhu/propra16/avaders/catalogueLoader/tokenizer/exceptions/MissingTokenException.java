package de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;

/**
 * Signals that an expected Token was not found
 */
public class MissingTokenException extends TokenException {
	/**
	 * Produces a new {@link Exception} informing the caller of a missing token
	 * in the specified line
	 * @param token The missing {@link Token}
	 * @param lineNr The line in which the {@link Token} was Expected
     */
	public MissingTokenException(String token, int lineNr) {
		super("Line " + lineNr + " expected " + token + " but did not find it.");
	}
}
