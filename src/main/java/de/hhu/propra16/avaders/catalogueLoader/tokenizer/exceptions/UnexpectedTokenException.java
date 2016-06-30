package de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions;

public class UnexpectedTokenException extends TokenException {
	public UnexpectedTokenException(String expectedToken, String foundToken, int lineNr) {
		super("Line " + lineNr + " expected " + expectedToken +
				", but found the Token --" + foundToken + "-- instead.");
	}
}
