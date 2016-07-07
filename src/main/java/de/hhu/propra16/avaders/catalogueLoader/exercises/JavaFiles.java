package de.hhu.propra16.avaders.catalogueLoader.exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the java files of the exercise
 */
public class JavaFiles {
	private List<JavaFile>	javaFiles;

	public JavaFiles(){
		javaFiles = new ArrayList<>();
	}

	/**
	 * @param javaFile The File to be added to the list
     */
	public void addJavaFile(JavaFile javaFile){
		javaFiles.add(javaFile);
	}

	/**
	 * @param javaFileNumber The index of the file to be returned
	 * @return The file with the corresponding index
	 * @throws IndexOutOfBoundsException - if the index is out of range
     */
	public JavaFile getJavaFile(int javaFileNumber){
		return javaFiles.get(javaFileNumber);
	}

	/**
	 * @return The Amount of files in the list
     */
	public int size() { return javaFiles.size(); }
}
