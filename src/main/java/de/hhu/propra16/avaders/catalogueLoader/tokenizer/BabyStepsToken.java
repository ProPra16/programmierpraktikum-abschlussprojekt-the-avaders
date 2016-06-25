package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

public class BabyStepsToken extends Token {
	String time;

	public BabyStepsToken(String value, String time){
		super("babysteps", value);
		this.time = time;
	}
}
