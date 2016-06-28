package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader implements LineReader {
    private BufferedReader bufferedReader;

	public FileReader(Path filePath) {
        try {
            bufferedReader = Files.newBufferedReader(filePath);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String readLine() throws  IOException{
		String readLine = bufferedReader.readLine();
		if(readLine == null){
			bufferedReader.close();
		}
		return readLine;
    }
}
