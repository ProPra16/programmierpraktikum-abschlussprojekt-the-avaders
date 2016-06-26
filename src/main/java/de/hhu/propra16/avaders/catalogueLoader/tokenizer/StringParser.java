package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.Token;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;

import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.WhiteSpaceRemover.removeWhiteSpace;

public class StringParser {
	public static Token parseToken(String readLine, int lineNr) throws SamePropertyTwiceException, TokenException {
		switch (readLine){
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
				return new Token(readLine);
			default:{
				if(readLine.startsWith("exercise")){
					return parseExerciseName(readLine, lineNr);
				}
				else if(readLine.startsWith("babysteps")){
					return parseBabySteps(readLine, lineNr);
				}
			}
		}
		throw new UnexpectedTokenException("<exercises>, </exercises>, <description>, </description> \n" +
				"<classes>, </classes>, <tests>, </tests>, </test>, <config> or </config>", readLine, lineNr);
	}

	public static Token parseBabySteps(String readLine, int lineNr) throws SamePropertyTwiceException, TokenException {
		String value = null;
		String time = null;

		readLine = readLine.replaceFirst("babysteps", "");
		readLine = removeWhiteSpace(readLine);

		while(readLine.startsWith("value") || readLine.startsWith("time")) {
			//System.out.println(readLine);
			if (readLine.startsWith("value")) {
				if(value == null) {
					value = parseProperty(readLine, "value", lineNr);
				}
				else throw new SamePropertyTwiceException("value", lineNr);
 			}
			else if (readLine.startsWith("time")) {
				if (time == null) {
					time = parseProperty(readLine, "time", lineNr);
				}
				else throw new SamePropertyTwiceException("time", lineNr);
			}

			// remove read time/value
			readLine = readLine.substring(readLine.indexOf("\"")+1);
			readLine = readLine.substring(readLine.indexOf("\"")+1);
			readLine = removeWhiteSpace(readLine);
		}

		if(!readLine.equals("")){
			throw new UnexpectedTokenException("property: time or value", readLine, lineNr);
		}

		return new BabyStepsToken(value, time);
	}

	private static String parseProperty(String readLine, String property, int lineNr) throws MissingTokenException {
		if(!readLine.contains("=")) throw  new MissingTokenException("=", lineNr);

		readLine = removeWhiteSpace(readLine.replaceFirst(property, ""));
		readLine = removeWhiteSpace(readLine.replaceFirst("=", ""));
		readLine = removeWhiteSpace(readLine.replaceFirst("\"", ""));

		int indexOfQuote = readLine.indexOf("\"");
		if(indexOfQuote == -1) throw new MissingTokenException("\"", lineNr);

		return removeWhiteSpace(readLine.substring(0, indexOfQuote));
	}

	public static Token parseExerciseName(String readLine, int lineNr) throws MissingTokenException {
		if(!readLine.contains("exercise") || !readLine.contains("name") || !readLine.contains("=")){
			throw new MissingTokenException("exercise name or =", lineNr);
		}

		readLine = removeWhiteSpace(readLine.replaceFirst("exercise",""));
		readLine = removeWhiteSpace(readLine.replaceFirst("name", ""));
		readLine = removeWhiteSpace(readLine.replaceFirst("=", ""));

		if(readLine.startsWith("\"") && readLine.endsWith("\"")){
			readLine = readLine.substring(1,readLine.length()-1);
			return new Token("exercise name", readLine);
		}
		else{
			throw new MissingTokenException("\"", lineNr);
		}
	}
}
