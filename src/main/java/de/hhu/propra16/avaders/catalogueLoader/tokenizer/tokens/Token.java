package de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens;

/**
 * This class represents a tokens in the file the
 * exercise catalogue is read from
 */
public class Token {
	public final String name;
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
