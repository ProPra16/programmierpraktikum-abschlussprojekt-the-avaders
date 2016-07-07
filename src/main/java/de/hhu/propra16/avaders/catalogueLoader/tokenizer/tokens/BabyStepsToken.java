package de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens;

/**
 * This Token holds information taken from the
 * read file whether or not babysteps is enabled
 * and the time it was set to
 */
public class BabyStepsToken extends Token {
	public final int time; // standard time

	public BabyStepsToken(String value, int time){
		super("babysteps", value);

		//TODO: make time integer and change everything accordingly
		if(time != 0) this.time = time;
		else this.time = 120; // standard time
	}
}
