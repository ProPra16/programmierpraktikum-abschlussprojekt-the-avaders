package de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions;

/**
 * Signals that a configuration was found to have the same property assigned twice
 */
public class SamePropertyTwiceException extends Exception {
	public SamePropertyTwiceException(String property, int lineNr){
		super("Line " + lineNr + " holds the property " + property + " twice, every property should only appear once.");
	}
}
