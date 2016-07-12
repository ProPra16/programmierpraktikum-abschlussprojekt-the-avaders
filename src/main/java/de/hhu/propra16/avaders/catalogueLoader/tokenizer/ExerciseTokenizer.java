package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.ClassToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;

import java.io.IOException;

/**
 * Breaks up a source from which exercises are read into single tokens
 */
public interface ExerciseTokenizer {
	/**
	 * Advances to the next lexeme (unless the source was fully parsed) and parses the line
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
	 */
	void advance() throws SamePropertyTwiceException, IOException, TokenException;

	/**
	 * Reads the content of a description and returns a {@link Token} with the information stored
	 * @param tokenWhichShouldBeRead the tokens which the content belongs to
	 * @return A {@link Token} with the content stored
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
	 */
	Token readContent(String tokenWhichShouldBeRead) throws IOException, SamePropertyTwiceException, TokenException;

	/**
	 * Reads the content of a class or test lexeme and returns a {@link ClassToken} holding that information
	 * @param stringToEndOn The String on which to end the reading on
	 * @param classType the type of java file read test or class
	 * @return A {@link ClassToken} instance or null if the stringToEndOn was reached
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
	 */
	ClassToken readJavaFile(String stringToEndOn, String classType) throws IOException, SamePropertyTwiceException, TokenException;

	/**
	 * Returns true if the source that is being parsed holds another lexeme
	 * @return True if there is a next tokens, otherwise false
	 */
	boolean hasNextToken();

	/**
	 * Returns the currently parsed Token
	 * @return The currently parsed {@link Token}
	 */
	Token currentToken();

	/**
	 * Returns the currently parsed line in the source
	 * @return the current line number
	 */
	int getLineNumber();
}
