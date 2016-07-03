package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;

import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.remove;

/**
 * Converts strings into tokens instances and returns them
 */
public class StringToToken {
	/**
	 * Extract information from the given string and collects them
	 * in a tokens that is then returned
	 * @param readString The string which will be analyzed
	 * @param lineNr The current line number
	 * @return A tokens instance with the information from the given string
	 * @throws SamePropertyTwiceException if a property appears twice within the tokens
	 * @throws TokenException if an unexpected Token was read or a Token is missing
     */
	public static Token convert(String readString, int lineNr) throws SamePropertyTwiceException, TokenException {
		if (readString.startsWith("/")) readString = readString.replaceAll("\\s","");

		if(readString.startsWith("exercises") 		|| readString.startsWith("/exercises")	||
				readString.startsWith("classes")  	|| readString.startsWith("/classes")	||
				readString.startsWith("tests") 		|| readString.startsWith("/tests")		||
				readString.startsWith("/test")		||
				readString.startsWith("config")		|| readString.startsWith("/config")		||
				readString.startsWith("/exercise")){
					return new Token(readString);
		}
		else if(readString.startsWith("exercise")) return parseTokenName("exercise", readString, lineNr);
		else if(readString.startsWith("class")) return parseTokenName("class", readString, lineNr);
		else if(readString.startsWith("test")) return parseTokenName("test", readString, lineNr);
		else if(readString.startsWith("babysteps")){
			if(readString.contains("/")) readString = readString.replaceFirst("/", "").trim();
				return parseBabySteps(readString, lineNr);
			}
		else if(readString.startsWith("timetracking")){
			if(readString.contains("/")) readString = readString.replaceFirst("/", "").trim();
			return parseSingleValueToken("timetracking", readString, lineNr);
		}
		else if(readString.startsWith("atdd")){
			if(readString.contains("/")) readString = readString.replaceFirst("/", "").trim();
				return parseSingleValueToken("atdd", readString, lineNr);
			}
		else{
			throw new UnexpectedTokenException("<exercises>, </exercises>, <description>, </description> \n" +
				"<classes>, </classes>, <tests>, </tests>, </test>, <config> or </config>", readString, lineNr);
		}
	}

	/**
	 * Parses a tokens which only holds a single property: a value
	 * @param token The tokens which this value belongs to
	 * @param readString The string that holds the rest of the information
	 * @param lineNr The current line number
	 * @return The tokens with a value attached
	 * @throws UnexpectedTokenException If there was a tokens that does not belong there
	 * @throws SamePropertyTwiceException If value appears twice in the tokens
	 * @throws MissingTokenException If a tokens like = is missing
     */
	private static Token parseSingleValueToken(String token, String readString, int lineNr) throws UnexpectedTokenException, SamePropertyTwiceException, MissingTokenException {
		String value = null;

		readString = readString.replaceFirst(token, "");
		readString = readString.trim();

		while(readString.startsWith("value")) {
			if (readString.startsWith("value")) {
				if (value == null) {
					value = parseProperty(readString, "value", lineNr);
				} else throw new SamePropertyTwiceException("value", lineNr);
			}
			readString = removeProperty(readString);
		}

		if(!readString.equals("")){
			throw new UnexpectedTokenException("property: value", readString, lineNr);
		}

		return new Token(token, value);
	}

	/**
	 * Removes a property such as "true" or "2:00" from the string and
	 * the whitespaces from beginning and end of the string
	 * @param readString The string from which the property is to be removed
	 * @return The string with the property removeed
     */
	private static String removeProperty(String readString) {
		// remove read time/value
		readString = readString.substring(readString.indexOf("\"")+1);
		readString = readString.substring(readString.indexOf("\"")+1);
		return readString.trim();
	}

	/**
	 * Parses the babysteps configuration
	 * @param readString The string which the configuration is to be read from
	 * @param lineNr The line in which the fileReader is currently
	 * @return A BabyStepsToken instance with the read information
	 * @throws SamePropertyTwiceException If a property was found twice in the string
	 * @throws TokenException If an unexpected tokens appeared
     */
	private static Token parseBabySteps(String readString, int lineNr) throws SamePropertyTwiceException, TokenException {
		String value = null;
		String time = null;

		readString = readString.replaceFirst("babysteps", "");
		readString = readString.trim();

		while(readString.startsWith("value") || readString.startsWith("time")) {
			if (readString.startsWith("value")) {
				if(value == null) {
					value = parseProperty(readString, "value", lineNr);
				}
				else throw new SamePropertyTwiceException("value", lineNr);
 			}
			else if (readString.startsWith("time")) {
				if (time == null) {
					time = parseProperty(readString, "time", lineNr);
				}
				else throw new SamePropertyTwiceException("time", lineNr);
			}

			readString = removeProperty(readString);
		}

		if(!readString.equals("")){
			throw new UnexpectedTokenException("property: time or value", readString, lineNr);
		}

		return new BabyStepsToken(value, time);
	}

	/**
	 * Parses a property inside a tokens like atdd, timeTracking or babysteps
	 * @param readString The string which hold the property information
	 * @param property The property the configuration belongs to
	 * @param lineNr the current line number
	 * @return the configuration of the property like "true", "2:00"
	 * @throws MissingTokenException
     */
	private static String parseProperty(String readString, String property, int lineNr) throws MissingTokenException {
		if(!readString.contains("=")) throw  new MissingTokenException("=", lineNr);

		readString = remove(readString, property);
		readString = remove(readString, "=");
		readString = remove(readString, "\"");

		int indexOfQuote = readString.indexOf("\"");
		if(indexOfQuote == -1) throw new MissingTokenException("\" around " + readString, lineNr);

		return readString.substring(0, indexOfQuote).trim();
	}

	/**
	 * Parses the name property of a tokens
	 * @param token The tokens which this name belongs to
	 * @param readString The String from which the name will be read
	 * @param lineNr the current line number
	 * @return A Token with a name as value
	 * @throws MissingTokenException If a " is found to be missing
     */
	private static Token parseTokenName(String token, String readString, int lineNr) throws MissingTokenException {
		if(!readString.contains(token) || !readString.contains("name") || !readString.contains("=")){
			throw new MissingTokenException(token + ", name or =", lineNr);
		}

		readString = remove(readString, token);
		readString = remove(readString, "name");
		readString = remove(readString, "=");

		if(readString.startsWith("\"") && readString.endsWith("\"")){
			readString = readString.substring(1,readString.length()-1);
			return new Token(token, readString);
		}
		else{
			throw new MissingTokenException("\"", lineNr);
		}
	}

	/**
	 * Creates a tokens with a description as its value
	 * @param descriptionContent The content of the description
	 * @return The tokens that holds the descriptionContent as its value
     */
	static Token convertDescription(String descriptionContent){
		return new Token("description", descriptionContent);
	}
}
