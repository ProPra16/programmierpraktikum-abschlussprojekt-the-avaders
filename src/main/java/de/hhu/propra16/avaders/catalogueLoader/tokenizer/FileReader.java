package de.hhu.propra16.avaders.catalogueLoader.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader implements LineReader {

    private BufferedReader bufferedReader;
	private int lineNr = 1;

    FileReader(Path filePath) {
        try {
            bufferedReader = Files.newBufferedReader(filePath);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String readLine() throws  IOException{
		lineNr++;
		return bufferedReader.readLine();
    }

    void closeReader(){
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public int getLineNr(){
		return lineNr;
	}
}
