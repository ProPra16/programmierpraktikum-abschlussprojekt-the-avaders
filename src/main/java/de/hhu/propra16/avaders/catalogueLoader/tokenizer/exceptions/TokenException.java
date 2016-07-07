package de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions;

/**
 * Signals that an exception happened, while reading lexemes in the file
 */
public class TokenException extends Exception{
	public TokenException(String msg){
		super(msg);
	}
}
