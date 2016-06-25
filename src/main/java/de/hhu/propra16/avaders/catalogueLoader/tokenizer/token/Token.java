package de.hhu.propra16.avaders.catalogueLoader.tokenizer.token;

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
