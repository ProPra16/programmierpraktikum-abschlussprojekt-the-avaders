package de.hhu.propra16.avaders.catalogueLoader.tokenizer.token;

/**
 * This Token holds information taken from the
 * read file whether or not babysteps is enabled
 * and the time it was set to
 */

//TODO: set default time if no time specified
public class BabyStepsToken extends Token {
	public final String time;

	public BabyStepsToken(String value, String time){
		super("babysteps", value);
		this.time = time;
	}
}
