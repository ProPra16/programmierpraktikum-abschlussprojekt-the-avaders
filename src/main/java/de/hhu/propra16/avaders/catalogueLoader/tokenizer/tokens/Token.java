package de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens;

/**
 * This class represents a tokens in the file the
 * exercise catalogue is read from
 */
public class Token {
	public final String name;
	public final String value;

	public Token(String name, String value){
		this.name = name;
		this.value = value;
	}

	public Token(String name){
		this.name = name;
		this.value = null;
	}
}
