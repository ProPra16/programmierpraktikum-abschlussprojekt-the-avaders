package de.hhu.propra16.avaders.catalogueLoader.tokenizer;


import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;

/**
 * A {@link TokenizerFunction} is a Function for
 * converting a string into a {@link Token}
 */
@FunctionalInterface
interface TokenizerFunction {
	/**
	 * Applies the function to the string and returns the produced Token
	 * @param String The string to be converted
	 * @return The {@link Token} that was converted from the string
	 * @throws SamePropertyTwiceException If value appears twice in the {@link Token}s
	 * @throws TokenException If an unexpected {@link Token} was read or a {@link Token} is missing
     */
	Token apply(String String) throws SamePropertyTwiceException, TokenException;
}
