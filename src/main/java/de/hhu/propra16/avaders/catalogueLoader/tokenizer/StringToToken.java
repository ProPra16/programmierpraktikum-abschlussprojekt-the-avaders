package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;

import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.*;
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.remove;

/**
 * Converts strings into tokens instances and returns them
 */
@SuppressWarnings("HardcodedFileSeparator")
public class StringToToken {

	/**
	 * The singleton INSTANCE of this class
     */
	private static final StringToToken INSTANCE = initializeStringToTokenAndTokenizerChain();
	public static final String EXERCISES = "exercises";
	public static final String EXERCISE = "/exercise";
	public static final String CLASSES = "classes";
	public static final String CLASSES1 = "/classes";
	public static final String TESTS = "tests";
	public static final String TESTS1 = "/tests";
	public static final String CONFIG = "config";
	public static final String CONFIG1 = "/config";
	public static final String EXERCISE1 = "exercise";
	public static final String CLASS = "class";
	public static final String TEST = "test";
	public static final String BABYSTEPS = "babysteps";
	public static final String TIMETRACKING = "timetracking";
	public static final String ATDD = "atdd";
	public static final String VALUE = "value";
	public static final String TIME = "time";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";

	/**
	 * The head of the {@link TokenizerChain} for converting the string into a token
     */
	private TokenizerChain tokenizerChainHead;

	/**
	 * The line the {@link ExerciseTokenizer} read
     */
	private static int currentLineNumber;

	/**
	 * Returns the singleton INSTANCE of this class
	 * @return The singleton INSTANCE of this class
     */
	public static StringToToken getInstance(){
		return INSTANCE;
	}

	/**
	 * Initializes the singleton INSTANCE of this class by
	 * creating the {@link TokenizerChain} for converting
	 * a string into a {@link Token}
	 * @return The singleton INSTANCE of this class
     */
	private static StringToToken initializeStringToTokenAndTokenizerChain() {
		// INSTANCE chain
		TokenizerChain emptyTagsChain = new TokenizerChain(
				(readString) ->
				(readString.startsWith(EXERCISES)	|| readString.startsWith(EXERCISE)	||
				readString.startsWith(CLASSES)	|| readString.startsWith(CLASSES1)	||
				readString.startsWith(TESTS)		|| readString.startsWith(TESTS1)		||
				readString.startsWith(CONFIG)		|| readString.startsWith(CONFIG1)),
				Token::new
		);

		TokenizerChain exerciseTagChain = new TokenizerChain(
				(readString) -> readString.startsWith(EXERCISE1),
				(readString) -> parseTokenName(EXERCISE1, readString)
		);

		TokenizerChain classTagChain = new TokenizerChain(
				(readString) -> readString.startsWith(CLASS),
				(readString) -> parseTokenName(CLASS, readString)
		);

		TokenizerChain testTagChain = new TokenizerChain(
				(readString) -> readString.startsWith(TEST),
				(readString) -> parseTokenName(TEST, readString)
		);

		TokenizerChain babystepsChain = new TokenizerChain(
				(readString) -> readString.startsWith(BABYSTEPS),
				(readString) -> {
					if (readString.contains("/")) readString = readString.replaceFirst("/", "").trim();
					return parseBabySteps(readString);
				}
		);

		TokenizerChain timetrackingChain = new TokenizerChain(
				(readString) -> readString.startsWith(TIMETRACKING),
				(readString) -> {
					if(readString.contains("/")) readString = readString.replaceFirst("/", "").trim();
					return parseSingleValueToken(TIMETRACKING, readString);
				}
		);

		TokenizerChain atddChain = new TokenizerChain(
				(readString) -> readString.startsWith(ATDD),
				(readString) -> {
					if (readString.contains("/")) readString = readString.replaceFirst("/", "").trim();
					return parseSingleValueToken(ATDD, readString);
				}
		);

		TokenizerChain errorChain = new TokenizerChain(
				(readString) -> true,
				(readString) -> {
					throw new UnexpectedTokenException("<exercises>, </exercises>, <description>, </description> \n" +
							"<classes>, </classes>, <tests>, </tests>, </test>, <config> or </config>", readString, currentLineNumber);
				}
		);

		// setup chain order
		emptyTagsChain.setNextChain(exerciseTagChain);
		exerciseTagChain.setNextChain(classTagChain);
		classTagChain.setNextChain(testTagChain);
		testTagChain.setNextChain(babystepsChain);
		babystepsChain.setNextChain(timetrackingChain);
		timetrackingChain.setNextChain(atddChain);
		atddChain.setNextChain(errorChain);

		return new StringToToken(emptyTagsChain);
	}

	/**
	 * Creates an INSTANCE of StringToToken with the tokenizerChainHead
	 * attached
	 * @param tokenizerChainHead The Head of the chain that is used for
	 *                           converting the string to a {@link Token}
	 */
	private StringToToken(TokenizerChain tokenizerChainHead){	this.tokenizerChainHead = tokenizerChainHead;	}

	/**
	 * Extract information from the given string and collects them
	 * in a {@link Token} that is then returned
	 * @param readString The string which will be analyzed
	 * @param lineNumber The current line number
	 * @return A {@link Token} INSTANCE with the information from the given string
	 * @throws SamePropertyTwiceException if a property appears twice within the tokens
	 * @throws TokenException If an unexpected {@link Token} was read or a Token is missing
     */
	public Token convert(String readString, int lineNumber) throws SamePropertyTwiceException, TokenException {
		currentLineNumber = lineNumber;
		if (readString.startsWith("/")) readString = '/' + remove(readString, "/");

		return tokenizerChainHead.compute(readString);
	}

	/**
	 * Parses a {@link Token} which only holds a single property: a value
	 * @param token The name of the token which this value belongs to
	 * @param readString The string that holds the rest of the information
	 * @return The tokens with a value attached
	 * @throws UnexpectedTokenException If there was a {@link Token} that does not belong there
	 * @throws SamePropertyTwiceException If value appears twice in the {@link Token}s
	 * @throws MissingTokenException If a {@link Token} like = is missing
     */
	private static Token parseSingleValueToken(String token, String readString) throws UnexpectedTokenException, SamePropertyTwiceException, MissingTokenException {
		String value = null;

		readString = readString.replaceFirst(token, "");
		readString = readString.trim();

		while(readString.startsWith(VALUE)) {
			if (readString.startsWith(VALUE)) {
				if (value == null) value = parseProperty(readString, VALUE);
				else throw new SamePropertyTwiceException(VALUE, currentLineNumber);
			}
			readString = removeProperty(readString);
		}

		if(!readString.isEmpty())
			throw new UnexpectedTokenException("property: value", readString, currentLineNumber);

		return new Token(token, value);
	}

	/**
	 * Removes a property such as "true" or "2:00" from the string and
	 * the whitespaces from beginning and end of the string
	 * @param readString The string from which the property is to be removed
	 * @return The string with the property removed
     */
	private static String removeProperty(String readString) {
		// remove read time/value
		readString = readString.substring(readString.indexOf('\"')+1);
		readString = readString.substring(readString.indexOf('\"')+1);
		return readString.trim();
	}

	/**
	 * Parses the babySteps configuration
	 * @param readString The string which the configuration is to be read from
	 * @return A {@link BabyStepsToken} INSTANCE with the read information
	 * @throws SamePropertyTwiceException If a property was found twice in the string
	 * @throws TokenException If an unexpected {@link Token} appeared
     */
	private static Token parseBabySteps(String readString) throws SamePropertyTwiceException, TokenException {
		String value = null;
		String time = null;

		readString = readString.replaceFirst(BABYSTEPS, "");
		readString = readString.trim();

		while(readString.startsWith(VALUE) || readString.startsWith(TIME)) {
			if (readString.startsWith(VALUE)) {
				if(value == null) value = parseProperty(readString, VALUE);
				else throw new SamePropertyTwiceException(VALUE, currentLineNumber);
 			}
			else if (readString.startsWith(TIME)) {
				if (time == null) time = parseProperty(readString, TIME);
				else throw new SamePropertyTwiceException(TIME, currentLineNumber);
			}

			readString = removeProperty(readString);
		}

		if(!readString.isEmpty())
			throw new UnexpectedTokenException("property: time or value", readString, currentLineNumber);

		return new BabyStepsToken(value, StringTimeToSeconds(time));
	}

	/**
	 * Parses a property inside a lexeme like atdd, timeTracking or babysteps
	 * @param readString The string which hold the property information
	 * @param property The property the configuration belongs to
	 * @return the configuration of the property like "true", "2:00"
	 * @throws MissingTokenException If a {@link Token} was found missing
     */
	private static String parseProperty(String readString, String property) throws MissingTokenException {
		if(!readString.contains("=")) throw  new MissingTokenException("=", currentLineNumber);

		readString = remove(readString, property);
		readString = remove(readString, "=");
		readString = remove(readString, "\"");

		int indexOfQuote = readString.indexOf('\"');
		if(indexOfQuote == -1) throw new MissingTokenException("\" around " + readString, currentLineNumber);

		return readString.substring(0, indexOfQuote).trim();
	}

	/**
	 * Parses the name property of a {@link Token}
	 * @param token The name of the token which this name belongs to
	 * @param readString The string from which the name will be read
	 * @return A {@link Token} with a name as value
	 * @throws MissingTokenException If a " is found to be missing
     */
	private static Token parseTokenName(String token, String readString) throws MissingTokenException {
		if(!readString.contains(token) || !readString.contains(NAME) || !readString.contains("="))
			throw new MissingTokenException(token + ", name or =", currentLineNumber);

		readString = remove(readString, token);
		readString = parseProperty(readString, NAME);

		return new Token(token, readString);
	}

	/**
	 * Creates a {@link Token} with a description as its value
	 * @param descriptionContent The content of the description
	 * @return The {@link Token} that holds the description's content as its value
     */
	static Token convertDescription(String descriptionContent){	return new Token(DESCRIPTION, descriptionContent);	}
}
