package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.ClassToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;

import java.io.IOException;

import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.insertEscapedCurlyBracket;
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringOperations.remove;
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringToToken.convert;
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringToToken.convertDescription;

/**
 * Reads lexemes from a .xml file until the end of file is reached
 */
public class XMLExerciseTokenizer {
	private LineReader fileReader;
	private Token currentToken;
	private Token nextToken;
	private String readLine;
	private int lineNumber = 0;

	private static final int INCLUDE = 1;

	public XMLExerciseTokenizer(LineReader fileReader) throws SamePropertyTwiceException, IOException, TokenException {
		if(fileReader instanceof FileReader && !((FileReader)fileReader).getPath().endsWith(".xml"))
			throw new IOException("Please choose a file with the \".xml\" extension.");

		this.fileReader = fileReader;
		readLine = "";
		currentToken = new Token("DUMMY");
		advance();	advance();
	}

	/**
	 * Advances the fileReader unless the end of file is reached and parses the line
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
     */
	public void advance() throws SamePropertyTwiceException, IOException, TokenException {
		if (readLine != null && readLine.length() > 0) {
			parseLine();
			return;
		}

		while(readLine != null && readLine.equals("")){
			readStructure();
			if(readLine != null) readLine = readLine.trim();
		}

		parseLine();
	}

	/**
	 * Reads lines until an entire structure in the form <...> is read.
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
     */
	private void readStructure() throws SamePropertyTwiceException, IOException, TokenException {
		readNextLine();
		String fullToken = readLine;

		while(readLine != null && !fullToken.contains(">")){
			readNextLine();
			fullToken = fullToken + readLine;
		}

		readLine = fullToken;
	}

	/**
	 * Parses the current line and sets the current tokens to the one read in the line
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
	 * @throws IOException If an IO error occurs with the BufferedReader instance
     */
	private void parseLine() throws SamePropertyTwiceException, TokenException, IOException {
		String readToken = "";
		if(readLine != null && readLine.contains("<") && readLine.contains(">")) readToken = tokenize();
		else if(readLine != null)
			throw new UnexpectedTokenException("<exercises>, </exercises>, <description>, </description> \n" +
					"<classes>, </classes>, <tests>, </tests>, </test>, <config> or </config>", readToken, lineNumber);

		currentToken = nextToken;

		if(readToken.startsWith("description")) {
			nextToken = readContent("description");
			return;
		}

		if( !readToken.contains("/") || (readToken.indexOf("/") == (readToken.length()-1)) )
			readToken = readToken.trim();

		// end of stream reached
		if(readLine == null) nextToken = null;
		else nextToken = convert(readToken, lineNumber);
	}

	/**
	 * Removes unnecessary symbols from the tokens, only leaving the information needed.
	 * @return A string only holding the tokens necessary information
	 * @throws IOException If an IO error occurs with the BufferedReader instance
     */
	private String tokenize() throws IOException {
		String readToken = "";
		readToken = readLine.substring(readLine.indexOf('<'), readLine.indexOf('>')+INCLUDE);
		readLine = readLine.replaceFirst(readToken, "").trim();
		readToken = readToken.replaceFirst("<","").replaceFirst(">","");

		return readToken;
	}

	/**
	 * Reads the content of a description and returns a tokens with the information stored
	 * @param tokenWhichShouldBeRead the tokens which the content belongs to
	 * @return A Token with the content stored
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
     */
	public Token readContent(String tokenWhichShouldBeRead) throws IOException, SamePropertyTwiceException, TokenException {
		String content = readLinesUntil(tokenWhichShouldBeRead);

		return convertDescription(content);
	}

	/**
	 * Reads the content of a class or test tokens and returns a class tokens holding that information
	 * @param stringToEndOn The String on which to end the reading on
	 * @param classType the type of java file read test or class
	 * @return A ClassToken instance or null if the stringToEndOn was reached
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
     * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
     */
	public ClassToken readJavaFile(String stringToEndOn, String classType) throws IOException, SamePropertyTwiceException, TokenException {
		if(!nextToken.name.equals(stringToEndOn)) {
			String classTemplate = readLinesUntil(classType);
			advance();
			return new ClassToken(classType, currentToken.value, classTemplate);
		}
		else {
			// only skip the /classType tokens
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
	private String readLinesUntil(String until) throws IOException, MissingTokenException {
		StringBuilder contentBuilder = new StringBuilder();
		while(readLine != null && !readLine.replace(" ","").contains("</" + until + ">")){
			contentBuilder.append(readLine.trim()).append("\n");
			readNextLine();
		}

		if (readLine != null) contentBuilder.append(readLine.substring(0, readLine.indexOf("</" + until + ">")));
		else throw new MissingTokenException("</" + until + ">", lineNumber);

		String content = contentBuilder.toString();
		content = remove(content, "<" + until + ">");
		readLine = remove(readLine, insertEscapedCurlyBracket(content));
		readLine = remove(readLine, "</" + until + ">");

		return content.trim();
	}

	/**
	 * Reads the next line and increases the linenr counter
	 * @throws IOException If an IO error occurs with the BufferedReader instance
     */
	private void readNextLine() throws IOException {
		readLine = fileReader.readLine();
		lineNumber++;
	}

	/**
	 * @return True if there is a next tokens, otherwise false
     */
	public boolean hasNextToken(){
		return (nextToken != null);
	}

	/**
	 * @return The currently parsed tokens
     */
	public Token currentToken(){
		return currentToken;
	}

	/**
	 * @return the current line number
     */
	public int getLineNumber() {
		return lineNumber;
	}
}
