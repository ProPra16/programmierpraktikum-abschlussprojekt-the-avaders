package de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens;

/**
 * This Token holds information taken from the
 * read file whether or not babysteps is enabled
 * and the time it was set to
 */
public class BabyStepsToken extends Token {
	public final String time; // standard time

	public BabyStepsToken(String value, String time){
		super("babysteps", value);

		if(time != null) this.time = time;
		else this.time = "2:00"; // standard time
	}
}
