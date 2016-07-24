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
import static de.hhu.propra16.avaders.catalogueLoader.tokenizer.StringToToken.convertDescription;

/**
 * Reads lexemes from a .xml file until the end of file is reached and returns single
 * {@link Token} instances for parsing
 */
@SuppressWarnings("HardcodedFileSeparator")
public class XMLExerciseTokenizer implements ExerciseTokenizer {
	public static final String XML = ".xml";
	public static final String DESCRIPTION = "description";
	/**
	 * The {@link LineReader the xml-file will be read from}
     */
	private LineReader fileReader;
	/**
	 * The {@link StringToToken} instance for converting strings into tokens
	 */
	private StringToToken stringToToken = StringToToken.getInstance();

	/**
	 * The currently read {@link Token}
     */
	private Token currentToken;

	/**
	 * The {@link Token} that will be read
     */
	private Token nextToken;

	/**
	 * The currently read line
     */
	private String readLine;

	/**
	 * The line the {@link XMLExerciseTokenizer#fileReader} is currently in
     */
	private int lineNumber = 0;

	private static final int INCLUDE = 1;

	/**
	 * Produces a {@link XMLExerciseTokenizer} instance that reads lexemes from
	 * the given {@link LineReader}
	 * @param fileReader The {@link LineReader} instance from which the
	 *                   lexemes are fetched
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
	 * @see ExerciseTokenizer
     */
	public XMLExerciseTokenizer(LineReader fileReader) throws SamePropertyTwiceException, IOException, TokenException {
		if(fileReader instanceof FileReader && !((FileReader)fileReader).getPath().endsWith(XML))
			throw new IOException("Please choose a file with the \".xml\" extension.");

		this.fileReader = fileReader;
		readLine = "";
		currentToken = new Token("DUMMY");
		advance();	advance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void advance() throws SamePropertyTwiceException, IOException, TokenException {
		if (readLine != null && readLine.length() > 0) {
			parseLine();
			return;
		}

		while(readLine != null && readLine.isEmpty()){
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

		if(fullToken != null && fullToken.contains("<!--"))
			fullToken = fullToken.substring(0, fullToken.indexOf("<!--")) +
						fullToken.substring(fullToken.indexOf("-->") + 3);

		readLine = fullToken;
	}

	/**
	 * Parses the current line and sets the current {@link Token} to the one formed from
	 * the information of the read lexeme
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
	 * @throws IOException If an I/O error occurs with the BufferedReader instance
     */
	private void parseLine() throws SamePropertyTwiceException, TokenException, IOException {
		String readToken = "";
		if(readLine != null && readLine.contains("<") && readLine.contains(">")) readToken = tokenize();
		else if(readLine != null)
			throw new UnexpectedTokenException("<exercises>, </exercises>, <description>, </description> \n" +
					"<classes>, </classes>, <tests>, </tests>, </test>, <config> or </config>", readToken, lineNumber);

		currentToken = nextToken;

		if(readToken.startsWith(DESCRIPTION)) {
			nextToken = readContent(DESCRIPTION);
			return;
		}

		if( !readToken.contains("/") || (readToken.indexOf('/') == (readToken.length()-1)) )
			readToken = readToken.trim();

		// end of stream reached
		if(readLine == null) nextToken = null;
		else nextToken = stringToToken.convert(readToken, lineNumber);
	}

	/**
	 * Removes unnecessary symbols from the lexeme, only leaving the information needed.
	 * @return A string only holding the tokens necessary information
	 * @throws IOException If an I/O error occurs with the BufferedReader instance
     */
	private String tokenize() throws IOException {
		String readToken;
		readToken = readLine.substring(readLine.indexOf('<'), readLine.indexOf('>')+INCLUDE);
		readLine = readLine.replaceFirst(readToken, "").trim();
		readToken = readToken.replaceFirst("<","").replaceFirst(">","");

		return readToken;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Token readContent(String tokenWhichShouldBeRead) throws IOException, SamePropertyTwiceException, TokenException {
		String content = readLinesUntil(tokenWhichShouldBeRead);

		return convertDescription(content);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
	 * Reads lines until the given string is encountered and returns them as single string
	 * @param until The string until which the string is to be build
	 * @return The lines that were read up until the given string
	 * @throws IOException If an I/O error occurs with the BufferedReader instance
     */
	private String readLinesUntil(String until) throws IOException, MissingTokenException {
		StringBuilder contentBuilder = new StringBuilder();
		String indent = "";

		while(readLine != null && !readLine.replace(" ","").contains("</" + until + '>')){
			indent = removeIndent(indent);
			contentBuilder.append(indent).append(readLine.trim()).append('\n');
			indent = addIndent(indent);
			readNextLine();
		}

		if (readLine != null) contentBuilder.append(readLine.substring(0, readLine.indexOf("</" + until + ">")));
		else throw new MissingTokenException("</" + until + ">", lineNumber);

		String content = contentBuilder.toString();
		content = remove(content, '<' + until + '>');
		readLine = remove(readLine, insertEscapedCurlyBracket(content));
		readLine = remove(readLine, "</" + until + '>');

		return content.trim();
	}

	/**
	 * Removes indentation "\t" from the given indent string if
	 * the currently read line includes a "}"
	 * @param indent The string holding the current indentation
	 * @return The new indent string
	 */
	private String removeIndent(String indent) {
		if(!indent.equals("") && readLine.contains("}")) return indent.replaceFirst("\t", "");
		return indent;
	}

	/**
	 * Adds indentation "\t" to the given indent string if
	 * the currently read line includes a "{"
	 * @param indent The string holding the current indentation
	 * @return The new indent string
	 */
	private String addIndent(String indent) {
		if(readLine.contains("{")) return indent + '\t';
		return indent;
	}

	/**
	 * Reads the next line and increases the line counter
	 * @throws IOException If an IO error occurs with the BufferedReader instance
     */
	private void readNextLine() throws IOException {
		readLine = fileReader.readLine();
		lineNumber++;
	}

	/**
	 * @return True if there is a next tokens, otherwise false
     */
	@Override
	public boolean hasNextToken(){	return (nextToken != null);	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Token currentToken(){	return currentToken;	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLineNumber(){	return lineNumber;	}
}
