package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import java.io.IOException;

/**
 * A LineReader is a source from which Strings are read line by line
 */
public interface LineReader {
	String readLine() throws IOException;
}
