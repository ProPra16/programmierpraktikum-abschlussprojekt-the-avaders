package de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens;

/**
 * This Token holds the name and template of
 * class files determined by the file the exercise
 * catalogue was read from
 */
public class ClassToken extends Token{
	public final String classTemplate;

	public ClassToken(String name, String value, String classTemplate) {
		super(name, value);
		this.classTemplate = classTemplate;
	}
}
