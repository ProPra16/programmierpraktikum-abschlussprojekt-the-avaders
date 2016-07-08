package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import java.io.IOException;

/**
 * A LineReader is a source from which Strings are read line by line
 */
public interface LineReader {
	/**
	 * Reads a line and returns it as string
	 * @return A line as String
	 * @throws IOException if an I/O error occurred
     */
	String readLine() throws IOException;
}
