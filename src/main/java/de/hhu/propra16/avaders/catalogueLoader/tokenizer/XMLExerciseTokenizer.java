package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.ClassToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.Token;

import java.io.IOException;

import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.insertEscapedCurlyBracket;
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.remove;
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.removeWhiteSpace;
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringToToken.convert;
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringToToken.convertDescription;

public class XMLExerciseTokenizer {
	private LineReader fileReader;
	private Token currentToken;
	private Token nextToken;
	private String readLine;
	private int lineNr = 0;

	private static final int INCLUDE = 1;

	public XMLExerciseTokenizer(LineReader fileReader) throws SamePropertyTwiceException, IOException, TokenException {
		this.fileReader = fileReader;
		readLine = "";
		currentToken = new Token("DUMMY");
		advance();	advance();
	}

	public void advance() throws SamePropertyTwiceException, IOException, TokenException {
		if (readLine != null && readLine.length() > 0) {
			parseLine();
			return;
		}

		while(readLine != null && readLine.equals("")){
			readNextLine();
			if(readLine != null) {
				readLine = removeWhiteSpace(readLine);
			}
		}

		parseLine();
	}

	private void parseLine() throws SamePropertyTwiceException, TokenException, IOException {
		String readToken = "";
		if(readLine != null && readLine.contains("<") && readLine.contains(">")) {
			readToken = tokenize();
		}
		else if(readLine != null){
			//System.out.println(readLine);
			throw new UnexpectedTokenException("<exercises>, </exercises>, <description>, </description> \n" +
					"<classes>, </classes>, <tests>, </tests>, </test>, <config> or </config>", readToken, lineNr);
		}

		currentToken = nextToken;

		if(readToken.equals("description")) {
			nextToken = readContent("description");
			return;
		}

		// end of stream reached
		if(readLine == null) nextToken = null;
		else nextToken = convert(readToken, lineNr);
	}

	private String tokenize() throws IOException {
		String readToken = "";
		readToken = readLine.substring(readLine.indexOf('<'), readLine.indexOf('>')+INCLUDE);
		readLine = removeWhiteSpace(readLine.replaceFirst(readToken, ""));
		readToken = removeWhiteSpace(readToken.replaceFirst("<","").replaceFirst(">",""));

		return readToken;
	}

	public Token readContent(String tokenWhichShouldBeRead) throws IOException, SamePropertyTwiceException, TokenException {
		String content = readLinesUntil(tokenWhichShouldBeRead);

		return convertDescription(content);
	}

	public ClassToken readJavaFile(String stringToEndOn, String classType) throws IOException, SamePropertyTwiceException, TokenException {
		if(!nextToken.name.equals(stringToEndOn)) {
			String classTemplate = readLinesUntil(classType);
			advance();
			return new ClassToken(classType, currentToken.value, classTemplate);
		}
		// only skip the /classType token
		else {
			advance();
			return null;
		}
	}

	private String readLinesUntil(String until) throws IOException {
		String content = "";
		while(readLine != null && !readLine.replace(" ","").contains("</" + until + ">")){
			content = content + removeWhiteSpace(readLine) + "\n";
			readNextLine();
		}

		if (readLine != null) {
			content = content + readLine.substring(0, readLine.indexOf("</" + until + ">"));
		}
		content = remove(content, "<" + until + ">");
		//System.out.println("content: " + content);
		readLine = remove(readLine, insertEscapedCurlyBracket(content));
		readLine = remove(readLine, "</" + until + ">");

		return removeWhiteSpace(content);
	}

	private void readNextLine() throws IOException {
		readLine = fileReader.readLine();
		lineNr++;
	}

	public boolean hasNextToken(){
		return (nextToken != null);
	}

	public Token currentToken(){
		return currentToken;
	}

	public int getLineNr() {
		return lineNr;
	}
}
