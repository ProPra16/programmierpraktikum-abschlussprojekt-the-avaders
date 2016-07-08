package de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions;

/**
 * Signals that a configuration was found to have the same property assigned twice
 */
public class SamePropertyTwiceException extends Exception {
	/**
	 * Creates a new {@link Exception} informing the caller that a property
	 * was assigned twice in the Parsing process
	 * @param property The property which was assigned twice
	 * @param lineNr The line in which the second assignment happened
     */
	public SamePropertyTwiceException(String property, int lineNr){
		super("Line " + lineNr + " holds the property " + property + " twice, every property should only appear once.");
	}
}
