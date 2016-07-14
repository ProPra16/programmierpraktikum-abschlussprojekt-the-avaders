package de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;

/**
 * Signals that an Unknown or unexpected Token appeared while parsing the file
 */
public class UnexpectedTokenException extends TokenException {
	/**
	 * Produces an {@link Exception} informing the caller that an unexpected
	 * {@link Token} was read while parsing
	 * @param expectedToken The expected token
	 * @param foundToken The token that instead appeared
	 * @param lineNr The line in which the expected token did not appear in
     */
	public UnexpectedTokenException(String expectedToken, String foundToken, int lineNr) {
		super("Line " + lineNr + " expected " + expectedToken +
				", but found the Token --" + foundToken + "-- instead.");
	}
}
