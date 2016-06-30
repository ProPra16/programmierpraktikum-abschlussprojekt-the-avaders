package de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions;

public class SamePropertyTwiceException extends Exception {
	public SamePropertyTwiceException(String property, int lineNr){
		super("Line " + lineNr + " holds the property " + property + " twice, every property should only appear once.");
	}
}
