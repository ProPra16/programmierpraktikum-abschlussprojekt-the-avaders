package de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens;

import de.hhu.propra16.avaders.extensions.Babysteps;

/**
 * This Token holds information taken from the
 * read file whether or not {@link Babysteps} is enabled
 * and the time it was set to
 */
public class BabyStepsToken extends Token {
	public static final String BABYSTEPS = "babysteps";
	/**
	 * Stores the time extracted from the babysteps lexeme
	 */
	public final int time; // standard time

	/**
	 * Produces a BabyStepsToken, keeping record of the values
	 * the lexeme carries
	 * @param value The string whether or not babysteps is activated
	 * @param time The time in seconds that were read in the lexeme
     */
	public BabyStepsToken(String value, int time){
		super(BABYSTEPS, value);
		this.time = time;
	}
}
