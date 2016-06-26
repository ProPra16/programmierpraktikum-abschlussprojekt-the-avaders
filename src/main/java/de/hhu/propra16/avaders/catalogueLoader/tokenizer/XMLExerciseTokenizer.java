package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.Token;

import java.io.IOException;

import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringParser.parseToken;
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.WhiteSpaceRemover.*;

public class XMLExerciseTokenizer {
	private LineReader fileReader;
	private Token currentToken;
	private Token nextToken;
	private int lineNr;
	private String readLine;

	private static final int INCLUDE = 1;

	public XMLExerciseTokenizer(LineReader fileReader) throws SamePropertyTwiceException, IOException, TokenException {
		this.fileReader = fileReader;
		readLine = "";
		currentToken = new Token("DUMMY");
		advance();
	}

	public void advance() throws SamePropertyTwiceException, IOException, TokenException {
		if (readLine != null && readLine.length() > 0) {
			parseLine();
			return;
		}

		while(readLine != null && readLine.equals("")){
			readLine = fileReader.readLine();
			if(readLine != null) {
				readLine = removeWhiteSpace(readLine.toLowerCase());
				lineNr++;
			}
		}
		parseLine();
	}

	private void parseLine() throws SamePropertyTwiceException, TokenException {
		String readToken = "";
		if(readLine != null && readLine.contains("<") && readLine.contains(">")) {
			readToken = readLine.substring(readLine.indexOf('<'), readLine.indexOf('>')+INCLUDE);
			readLine = removeWhiteSpace(readLine.replaceFirst(readToken, ""));
			readToken = removeWhiteSpace(readToken.replaceFirst("<","").replaceFirst(">",""));
		}
		else if(readLine != null){
			throw new UnexpectedTokenException("<exercises>, </exercises>, <description>, </description> \n" +
					"<classes>, </classes>, <tests>, </tests>, </test>, <config> or </config>", readToken, lineNr);
		}

		currentToken = nextToken;

		// end of stream reached
		if(readLine == null) {
			nextToken = null;
		}
		else {
			nextToken = parseToken(readToken, lineNr);
		}
	}

	public boolean hasNextToken(){
		return (nextToken != null);
	}

	public Token currentToken(){
		return currentToken;
	}

	public int getLineNr(){
		return lineNr;
	}
}
