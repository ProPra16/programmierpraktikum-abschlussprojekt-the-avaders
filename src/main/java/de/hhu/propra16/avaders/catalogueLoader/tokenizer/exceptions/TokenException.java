package de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;

/**
 * Signals that an exception happened, while reading lexemes in the file
 */
public class TokenException extends Exception{
	/**
	 * Priduces an {@link Exception} informin the user
	 * that an error occurred while parsing {@link Token}
	 * @param msg The message of this Exception
     */
	public TokenException(String msg){
		super(msg);
	}
}
