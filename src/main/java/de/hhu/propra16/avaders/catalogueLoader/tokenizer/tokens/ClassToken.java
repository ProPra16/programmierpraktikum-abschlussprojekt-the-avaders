package de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens;

/**
 * This Token holds the name and template of
 * class files determined by the file the exercise
 * catalogue was read from
 */
public class ClassToken extends Token{
	/**
	 * Holds the template of a java class as it was read with a ExerciseTokenizer
	 */
	public final String classTemplate;

	/**
	 * Produces a token holding information from a class lexeme
	 * @param name The name of the token
	 * @param fileName The name of the class or test
	 * @param classTemplate The template of this class
     */
	public ClassToken(String name, String fileName, String classTemplate) {
		super(name, fileName);
		this.classTemplate = classTemplate;
	}
}
