package de.hhu.propra16.avaders.gui.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * A tool-class for loading creating and writing a file when given a path
 */
public class FileTools {
	/**
	 * Creates a file and writes lines to it but only if file does not exist otherwise it does nothing
	 * @param path    Where the file should be saved to
	 * @param output  Content to be saved
     */
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
		} catch (IOException e) {
			System.err.println("Unable to write to File " + path);
		}
	}

	/**
	 * Delete a file if it exists. Otherwise it does nothing.
	 * @param path Path of the file which should be deleted
     */
	private static void deleteFile(Path path){
		if(!Files.exists(path))
			return;
		try {
			Files.delete(path);
		} catch (IOException e) {
			System.err.println("Unable to delete File " + path);
		}
	}

	/**
	 * Reads content from a file
	 * @param path Path to the file
	 * @return     Content of the file as String
     */
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

	/**
	 * Updates a file. If the File exists it will be deleted and then created with the specified content.
	 * Otherwise a new file containing the given content will be created.
	 * @param path   Path to the file
	 * @param output Content to be saved
     */
	public static void updateFile(Path path, String output){
		if(Files.exists(path))
			deleteFile(path);
		List<String> outputList = Arrays.asList(output.split("\\n"));
		try {
			Files.write(path, outputList, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.err.println("Unable to update to File " + path);
		}
	}

}
