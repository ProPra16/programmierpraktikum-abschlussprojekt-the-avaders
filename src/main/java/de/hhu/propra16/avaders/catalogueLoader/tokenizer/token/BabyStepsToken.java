package de.hhu.propra16.avaders.catalogueLoader.tokenizer.token;

public class BabyStepsToken extends Token {
	public final String time;

	public BabyStepsToken(String value, String time){
		super("babysteps", value);
		this.time = time;
	}
}
