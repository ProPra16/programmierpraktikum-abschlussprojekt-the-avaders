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

	public XMLExerciseTokenizer(LineReader fileReader) throws SamePropertyTwiceException, IOException, TokenException {
		this.fileReader = fileReader;
		readLine = "";
		currentToken = new Token("DUMMY");
		advance();
	}

	public void advance() throws SamePropertyTwiceException, IOException, TokenException {
		while(readLine != null && readLine.equals("")){
			readLine = fileReader.readLine();
			if(readLine != null) {
				readLine = readLine.replaceFirst("<", "").replaceFirst(">", "").toLowerCase();
				readLine = removeWhiteSpace(readLine);
				lineNr++;
			}
		}
		parseLine(readLine);
	}

	private void parseLine(String readLine) throws SamePropertyTwiceException, TokenException {
		currentToken = nextToken;

		// end of stream reached
		if(readLine == null) {
			nextToken = null;
		}
		else {
			nextToken = parseToken(readLine, lineNr);
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
