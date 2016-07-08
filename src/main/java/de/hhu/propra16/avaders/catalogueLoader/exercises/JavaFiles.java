package de.hhu.propra16.avaders.catalogueLoader.exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds {@link JavaFile} instances in a list
 */
public class JavaFiles {
	private List<JavaFile>	javaFiles;

	/**
	 * Creates a new empty {@link JavaFiles} instance
	 */
	public JavaFiles(){ javaFiles = new ArrayList<>(); }

	/**
	 * @param javaFile The {@link JavaFile} to be added to the list
     */
	public void addJavaFile(JavaFile javaFile){
		javaFiles.add(javaFile);
	}

	/**
	 * @param javaFileNumber The index of the {@link JavaFile} to be returned
	 * @return The file with the corresponding index
	 * @throws IndexOutOfBoundsException - if the index is out of range
     */
	JavaFile getJavaFile(int javaFileNumber){
		return javaFiles.get(javaFileNumber);
	}

	/**
	 * @return The Amount of {@link JavaFile} instances in the list
     */
	public int size() { return javaFiles.size(); }
}
