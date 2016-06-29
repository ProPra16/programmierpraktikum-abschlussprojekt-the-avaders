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

/**
 * Reads Tokens from a .xml file until the end of file is reached
 */
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

	/**
	 * Advances the fileReader unless the end of file is reached and parses the line
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
     */
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

	/**
	 * Parses the current line and sets the current token to the one read in the line
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
	 * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
	 * @throws IOException If an IO error occurs with the BufferedReader instance
     */
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

	/**
	 * Removes unnecessary symbols from the token, only leaving the information needed.
	 * @return A string only holding the tokens necessary information
	 * @throws IOException If an IO error occurs with the BufferedReader instance
     */
	private String tokenize() throws IOException {
		String readToken = "";
		readToken = readLine.substring(readLine.indexOf('<'), readLine.indexOf('>')+INCLUDE);
		readLine = removeWhiteSpace(readLine.replaceFirst(readToken, ""));
		readToken = removeWhiteSpace(readToken.replaceFirst("<","").replaceFirst(">",""));

		return readToken;
	}

	/**
	 * Reads the content of a description and returns a token with the information stored
	 * @param tokenWhichShouldBeRead the token which the content belongs to
	 * @return A Token with the content stored
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
	 * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
     */
	public Token readContent(String tokenWhichShouldBeRead) throws IOException, SamePropertyTwiceException, TokenException {
		String content = readLinesUntil(tokenWhichShouldBeRead);

		return convertDescription(content);
	}

	/**
	 * Reads the content of a class or test token and a class token holdingthat information
	 * @param stringToEndOn The String on which to end the reading on
	 * @param classType the type of java file read test or class
	 * @return A ClassToken instance or null if the stringToEndOn was reached
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
     * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
     */
	//TODO: stringToEndOn can be inferred from classType: unnecessary
	public ClassToken readJavaFile(String stringToEndOn, String classType) throws IOException, SamePropertyTwiceException, TokenException {
		if(!nextToken.name.equals(stringToEndOn)) {
			String classTemplate = readLinesUntil(classType);
			advance();
			return new ClassToken(classType, currentToken.value, classTemplate);
		}
		else {
			// only skip the /classType token
			advance();
			return null;
		}
	}

	/**
	 * Reads lines until the given string and returns them as single string
	 * @param until The string until which the string is to be build
	 * @return the lines that were read up until the given string
	 * @throws IOException If an IO error occurs with the BufferedReader instance
     */
	//TODO: Use StringBuilder for better performance
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
		readLine = remove(readLine, insertEscapedCurlyBracket(content));
		readLine = remove(readLine, "</" + until + ">");

		return removeWhiteSpace(content);
	}

	/**
	 * Reads the next line and increases the linenr counter
	 * @throws IOException If an IO error occurs with the BufferedReader instance
     */
	private void readNextLine() throws IOException {
		readLine = fileReader.readLine();
		lineNr++;
	}

	/**
	 * @return True if there is a next token, otherwise false
     */
	public boolean hasNextToken(){
		return (nextToken != null);
	}

	/**
	 * @return The currently parsed token
     */
	public Token currentToken(){
		return currentToken;
	}

	/**
	 * @return the current line number
     */
	public int getLineNr() {
		return lineNr;
	}
}
