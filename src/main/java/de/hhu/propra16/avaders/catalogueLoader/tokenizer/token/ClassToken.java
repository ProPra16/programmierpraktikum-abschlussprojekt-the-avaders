package de.hhu.propra16.avaders.catalogueLoader.tokenizer.token;

public class ClassToken extends Token{
	public final String classTemplate;

	public ClassToken(String name, String value, String classTemplate) {
		super(name, value);
		this.classTemplate = classTemplate;
	}
}
