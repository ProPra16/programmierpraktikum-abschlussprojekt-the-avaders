package de.hhu.propra16.avaders.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Batman140 on 10.07.2016.
 */
public class FileTools {

	public static void createFile(Path path, String output){
		if(path == null){
			System.err.println("Path is null");
			return;
		}
		List<String> outputList = new ArrayList<>();
		outputList = Arrays.asList(output.split("\\n"));
		deleteFile(path);
		try {
			Files.createDirectories(path.subpath(0, path.getNameCount()-1));
			Files.write(path, outputList, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.err.println("Unable to write to File " + path);
		}
	}
	private static void deleteFile(Path path){
		if(!Files.exists(path))
			return;
		try {
			Files.delete(path);
		} catch (IOException e) {
			System.err.println("Unable to delete File " + path);
		}
	}

}
