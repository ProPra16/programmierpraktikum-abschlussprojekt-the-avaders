package de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions;

public class MissingTokenException extends Exception {
	public MissingTokenException(String token, int lineNr) {
		super("Line " + lineNr + " expected " + token + " but did not find it.");
	}
}
