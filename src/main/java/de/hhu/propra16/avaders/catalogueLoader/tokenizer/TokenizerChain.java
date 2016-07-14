package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;

import java.util.function.Function;

/**
 * Is an Element in a chain that computes a lexeme and returns a token
 * or passes the string along to the next element of the chain
 */
class TokenizerChain {
	private TokenizerChain nextChain;
	private Function<String, Boolean> booleanFunction;
	private TokenizerFunction tokenizerFunction;

	/**
	 * Creates an instance of {@link TokenizerChain} with the given {@link Function}
	 * and {@link TokenizerFunction}
	 * @param booleanFunction The function which determines if {@link TokenizerChain#tokenizerFunction} is
	 *                        applied
	 * @param tokenizerFunction The function that returns a {@link Token} from a string
     */
	TokenizerChain(Function<String, Boolean> booleanFunction, TokenizerFunction tokenizerFunction){
		this.booleanFunction = booleanFunction;
		this.tokenizerFunction = tokenizerFunction;
	}

	/**
	 * Applies the {@link TokenizerChain#tokenizerFunction} if {@link TokenizerChain#booleanFunction}
	 * returns true, otherwise hands the string of to {@link TokenizerChain#nextChain} if it exists,
	 * else throws an {@link Exception}
	 * @param readString The string to be converted
	 * @return The converted {@link Token}
	 * @throws SamePropertyTwiceException If value appears twice in the {@link Token}s
	 * @throws TokenException If an unexpected {@link Token} was read or a {@link Token} is missing
     */
	Token compute(String readString) throws SamePropertyTwiceException, TokenException {
		if(booleanFunction.apply(readString)) return tokenizerFunction.apply(readString);
		else if(nextChain != null) return nextChain.compute(readString);
		return null;
	}

	/**
	 * Sets {@link TokenizerChain#nextChain} to the given {@link TokenizerChain}
	 * @param tokenizerChain The {@link TokenizerChain} that is attached as
	 *                       {@link TokenizerChain#nextChain}
     */
	void setNextChain(TokenizerChain tokenizerChain){
		this.nextChain = tokenizerChain;
	}
}
