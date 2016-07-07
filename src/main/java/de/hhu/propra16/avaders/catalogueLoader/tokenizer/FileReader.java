package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import de.hhu.propra16.avaders.catalogueLoader.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Holds a BufferedReader instance that
 * reads single lines from the filePath it receives
 * in its constructor and returns them.
 */
public class FileReader implements LineReader {
    private BufferedReader bufferedReader;
	private String path;

	public FileReader(Path filePath) throws IOException, ParserException {
		if (filePath == null) throw new ParserException("Path cannot be null");
		this.path = filePath.toString().toLowerCase();
		bufferedReader = Files.newBufferedReader(filePath);
    }

	/**
	 * Reads a line inside the given file and returns it.
	 * If the end of the file is reached, the reader is closed
	 * and null is returned.
	 *
	 * @return A String containing the read line
	 * @throws IOException If an I/O error occurs
	 * @see java.io.BufferedReader
	 */
    public String readLine() throws  IOException{
		String readLine = bufferedReader.readLine();
		if(readLine == null){
			bufferedReader.close();
		}
		return readLine;
    }

	/**
	 * @return The path this reader operates on as String
     */
	String getPath(){
		return path;
	}
}
