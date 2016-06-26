package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.Token;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;

import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.WhiteSpaceRemover.removeWhiteSpace;

public class StringParser {
	public static Token parseToken(String readToken, int lineNr) throws SamePropertyTwiceException, TokenException {
		switch (readToken){
			case "exercises":
			case "/exercises":
			case "description":
			case "/description":
			case "classes":
			case "/classes":
			case "class":
			case "/class":
			case "tests":
			case "/tests":
			case "test":
			case "/test":
			case "config":
			case "/config":
				return new Token(readToken);
			default:{
				if(readToken.startsWith("exercise")){
					return parseExerciseName(readToken, lineNr);
				}
				else if(readToken.startsWith("babysteps")){
					return parseBabySteps(readToken, lineNr);
				}
			}
		}
		throw new UnexpectedTokenException("<exercises>, </exercises>, <description>, </description> \n" +
				"<classes>, </classes>, <tests>, </tests>, </test>, <config> or </config>", readToken, lineNr);
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

			// remove read time/value
			readToken = readToken.substring(readToken.indexOf("\"")+1);
			readToken = readToken.substring(readToken.indexOf("\"")+1);
			readToken = removeWhiteSpace(readToken);
		}

		if(!readToken.equals("")){
			throw new UnexpectedTokenException("property: time or value", readToken, lineNr);
		}

		return new BabyStepsToken(value, time);
	}

	private static String parseProperty(String readToken, String property, int lineNr) throws MissingTokenException {
		if(!readToken.contains("=")) throw  new MissingTokenException("=", lineNr);

		readToken = removeWhiteSpace(readToken.replaceFirst(property, ""));
		readToken = removeWhiteSpace(readToken.replaceFirst("=", ""));
		readToken = removeWhiteSpace(readToken.replaceFirst("\"", ""));

		int indexOfQuote = readToken.indexOf("\"");
		if(indexOfQuote == -1) throw new MissingTokenException("\" in " + readToken, lineNr);

		return removeWhiteSpace(readToken.substring(0, indexOfQuote));
	}

	public static Token parseExerciseName(String readToken, int lineNr) throws MissingTokenException {
		if(!readToken.contains("exercise") || !readToken.contains("name") || !readToken.contains("=")){
			throw new MissingTokenException("exercise name or =", lineNr);
		}

		readToken = removeWhiteSpace(readToken.replaceFirst("exercise",""));
		readToken = removeWhiteSpace(readToken.replaceFirst("name", ""));
		readToken = removeWhiteSpace(readToken.replaceFirst("=", ""));

		if(readToken.startsWith("\"") && readToken.endsWith("\"")){
			readToken = readToken.substring(1,readToken.length()-1);
			return new Token("exercise name", readToken);
		}
		else{
			throw new MissingTokenException("\"", lineNr);
		}
	}
}
