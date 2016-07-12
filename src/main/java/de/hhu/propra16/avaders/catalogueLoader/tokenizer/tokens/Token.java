package de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens;

/**
 * This class represents a token in the file the
 * exercise catalogue is read from
 */
public class Token {
	/**
	 * Distinguishes this {@link Token} from others
	 */
	public final String name;

	/**
	 * Holds the value extracted from the lexeme corresponding to this {@link Token}
	 */
	public final String value;

	/**
	 * Produces a token giving context to the information of a lexeme
	 * @param name The name of the token
	 * @param value The value attached to the lexeme
     */
	public Token(String name, String value){
		this.name = name;
		this.value = value;
	}

	/**
	 * Produces a token, that only consists of a single name
	 * @param name The name of the token
     */
	public Token(String name){
		this.name = name;
		this.value = null;
	}
}
