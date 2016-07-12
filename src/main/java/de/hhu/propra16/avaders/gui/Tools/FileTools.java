package de.hhu.propra16.avaders.gui.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileTools {
	//finished
	public static void createFile(Path path, String output){
		if(path == null){
			System.err.println("Path is null");
			return;
		}
		List<String> outputList = Arrays.asList(output.split("\\n"));
		if(Files.exists(path))
			return;
		try {
			Files.createDirectories(path.subpath(0, path.getNameCount()-1));
			Files.write(path, outputList, StandardOpenOption.CREATE);
			System.out.println("" + path.toString() + " created!");
		} catch (IOException e) {
			System.err.println("Unable to write to File " + path);
		}
	}

	//finished
	private static void deleteFile(Path path){
		if(!Files.exists(path))
			return;
		try {
			Files.delete(path);
		} catch (IOException e) {
			System.err.println("Unable to delete File " + path);
		}
	}

	//finished
	public static String readFile(Path path){
		List<String> content;
		try {
			content = Files.readAllLines(path);
		} catch (IOException e) {
			System.err.println("Unable to read file " + path + ", readFile returns null");
			return null;
		}
		if(content.size() <= 0) {
			System.err.println("Empty File " + path);
			return null;
		}
		String output = "";
		for (String line : content){
			output += line + "\n";
		}
		return output;
	}

}
