package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

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

	public FileReader(Path filePath) {
		this.path = filePath.toString().toLowerCase();
        try {
            bufferedReader = Files.newBufferedReader(filePath);
        }catch(IOException e){
            e.printStackTrace();
        }
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
	public String getPath(){
		return path;
	}
}
