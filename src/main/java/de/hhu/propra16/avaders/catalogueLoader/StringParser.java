package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.Token;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;

import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.WhiteSpaceRemover.removeWhiteSpace;

public class StringParser {
	public static Token parseToken(String readLine, int lineNr) throws SamePropertyTwiceException {
		switch (readLine){
			case "exercises":
			case "/exercises":
			case "description":
			case "/description":
			case "classes":
			case "/classes":
			case "class":
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
		//TODO: throw exception unknown token
		return null;
	}

	public static Token parseBabySteps(String readLine, int lineNr) throws SamePropertyTwiceException {
		String value = null;
		String time = null;

		readLine = readLine.replaceFirst("babysteps", "");
		readLine = removeWhiteSpace(readLine);

		while(readLine.startsWith("value") || readLine.startsWith("time")) {
			//System.out.println(readLine);
			if (readLine.startsWith("value")) {
				if(value == null) {
					value = parseProperty(readLine, "value");
				}
				else throw new SamePropertyTwiceException("value", lineNr);
 			}
			else if (readLine.startsWith("time")) {
				if (time == null) {
					time = parseProperty(readLine, "time");
				}
				else throw new SamePropertyTwiceException("time", lineNr);
			}
			// remove read time/value
			readLine = readLine.substring(readLine.indexOf("\"")+1);
			readLine = readLine.substring(readLine.indexOf("\"")+1);
			readLine = removeWhiteSpace(readLine);
		}

		return new BabyStepsToken(value, time);
	}

	private static String parseProperty(String readLine, String property) {
		readLine = removeWhiteSpace(readLine.replaceFirst(property, ""));
		readLine = removeWhiteSpace(readLine.replaceFirst("=", ""));
		readLine = removeWhiteSpace(readLine.replaceFirst("\"", ""));
		return removeWhiteSpace(readLine.substring(0,readLine.indexOf("\"")));
	}

	public static Token parseExerciseName(String readLine, int lineNr) {
		readLine = removeWhiteSpace(readLine.replaceFirst("exercise",""));
		readLine = removeWhiteSpace(readLine.replaceFirst("name", ""));
		readLine = removeWhiteSpace(readLine.replaceFirst("=", ""));
		if(readLine.startsWith("\"") && readLine.endsWith("\"")){
			readLine = readLine.substring(1,readLine.length()-1);
			return new Token("exercise name", readLine);
		}
		else{
			//TODO: Throw Exception expected exercisename, found something else
			return null;
		}
	}
}
