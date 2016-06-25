package de.hhu.propra16.avaders.catalogueLoader.tokenizer;


import java.io.IOException;

public interface LineReader {
	String readLine() throws IOException;
}
