package de.hhu.propra16.avaders.catalogueLoader.exercises;

/**
 * JavaFile holds the initial state of the java files
 * in the exercise
 */
public class JavaFile {
	public final String className;
	public final String sourceCodeTemplate;

	public JavaFile(String className, String sourceCodeTemplate){
		this.className = className;
		this.sourceCodeTemplate = sourceCodeTemplate;
	}
}
