package de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions;

public class UnknownTokenException extends TokenException {
	public UnknownTokenException(String expectedToken, int lineNr) {
		super("Line " + lineNr + " expected " + expectedToken + ", but did not find it.");
	}
}
