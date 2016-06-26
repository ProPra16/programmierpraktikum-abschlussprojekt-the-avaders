package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.Token;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;

import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.remove;
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.removeWhiteSpace;

public class StringToToken {
	public static Token convert(String readToken, int lineNr) throws SamePropertyTwiceException, TokenException {
		//System.out.println(readToken);
		switch (readToken){
			case "exercises":
			case "/exercises":
			case "classes":
			case "/classes":
			case "tests":
			case "/tests":
			case "test":
			case "/test":
			case "config":
			case "/config":
				return new Token(readToken);
			default:{
				if(readToken.startsWith("exercise")){
					return parseTokenName("exercise", readToken, lineNr);
				}
				else if(readToken.startsWith("class")){
					return parseTokenName("class", readToken, lineNr);
				}
				else if(readToken.startsWith("babysteps")){
					return parseBabySteps(readToken, lineNr);
				}
				else if(readToken.startsWith("timetracking")){
					return parseTimeTracking(readToken, lineNr);
				}
			}
		}
		throw new UnexpectedTokenException("<exercises>, </exercises>, <description>, </description> \n" +
				"<classes>, </classes>, <tests>, </tests>, </test>, <config> or </config>", readToken, lineNr);
	}

	private static Token parseTimeTracking(String readToken, int lineNr) throws UnexpectedTokenException, SamePropertyTwiceException, MissingTokenException {
		String value = null;

		readToken = readToken.replaceFirst("timetracking", "");
		readToken = removeWhiteSpace(readToken);

		while(readToken.startsWith("value")) {
			if (readToken.startsWith("value")) {
				if (value == null) {
					value = parseProperty(readToken, "value", lineNr);
				} else throw new SamePropertyTwiceException("value", lineNr);
			}
			readToken = removeProperty(readToken);
		}

		if(!readToken.equals("")){
			throw new UnexpectedTokenException("property: value", readToken, lineNr);
		}

		return new Token("timetracking", value);
	}

	private static String removeProperty(String readToken) {
		// remove read time/value
		readToken = readToken.substring(readToken.indexOf("\"")+1);
		readToken = readToken.substring(readToken.indexOf("\"")+1);
		return removeWhiteSpace(readToken);
	}

	public static Token parseBabySteps(String readToken, int lineNr) throws SamePropertyTwiceException, TokenException {
		String value = null;
		String time = null;

		readToken = readToken.replaceFirst("babysteps", "");
		readToken = removeWhiteSpace(readToken);

		while(readToken.startsWith("value") || readToken.startsWith("time")) {
			if (readToken.startsWith("value")) {
				if(value == null) {
					value = parseProperty(readToken, "value", lineNr);
				}
				else throw new SamePropertyTwiceException("value", lineNr);
 			}
			else if (readToken.startsWith("time")) {
				if (time == null) {
					time = parseProperty(readToken, "time", lineNr);
				}
				else throw new SamePropertyTwiceException("time", lineNr);
			}

			readToken = removeProperty(readToken);
		}

		if(!readToken.equals("")){
			throw new UnexpectedTokenException("property: time or value", readToken, lineNr);
		}

		return new BabyStepsToken(value, time);
	}

	private static String parseProperty(String readToken, String property, int lineNr) throws MissingTokenException {
		if(!readToken.contains("=")) throw  new MissingTokenException("=", lineNr);

		readToken = remove(readToken, property);
		readToken = remove(readToken, "=");
		readToken = remove(readToken, "\"");

		int indexOfQuote = readToken.indexOf("\"");
		if(indexOfQuote == -1) throw new MissingTokenException("\" around " + readToken, lineNr);

		return removeWhiteSpace(readToken.substring(0, indexOfQuote));
	}

	public static Token parseTokenName(String token, String readToken, int lineNr) throws MissingTokenException {
		if(!readToken.contains(token) || !readToken.contains("name") || !readToken.contains("=")){
			throw new MissingTokenException(token + ", name or =", lineNr);
		}

		readToken = remove(readToken, token);
		readToken = remove(readToken, "name");
		readToken = remove(readToken, "=");

		if(readToken.startsWith("\"") && readToken.endsWith("\"")){
			readToken = readToken.substring(1,readToken.length()-1);
			//System.out.println(readToken);
			return new Token(token, readToken);
		}
		else{
			throw new MissingTokenException("\"", lineNr);
		}
	}

	public static Token convertDescription(String descriptionContent){
		return new Token("description", descriptionContent);
	}
}
