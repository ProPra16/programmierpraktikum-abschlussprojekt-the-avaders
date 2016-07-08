package de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens;

/**
 * This Token holds information taken from the
 * read file whether or not babysteps is enabled
 * and the time it was set to
 */
public class BabyStepsToken extends Token {
	public final int time; // standard time

	/**
	 * Produces a BabyStepsToken, keeping record of the values
	 * the lexeme carries
	 * @param value The string whether or not babysteps is activated
	 * @param time The time in seconds that were read in the lexeme
     */
	public BabyStepsToken(String value, int time){
		super("babysteps", value);
		this.time = time;
	}
}
