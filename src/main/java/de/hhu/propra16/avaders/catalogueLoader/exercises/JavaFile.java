package de.hhu.propra16.avaders.catalogueLoader.exercises;

/**
 * JavaFile holds a template for a Java class
 */
public class JavaFile {
	/**
	 * Represents the name of the java-file as well as of the java class it holds.
	 */
	public final String className;

	/**
	 * Holds the source code of the class
	 */
	public final String sourceCodeTemplate;

	/**
	 * Produces a {@link JavaFile} instance holding a classname
	 * and a sourcecode template
	 * @param className The name the class carries
	 * @param sourceCodeTemplate The template for the source code
     */
	public JavaFile(String className, String sourceCodeTemplate){
		this.className = className;
		this.sourceCodeTemplate = sourceCodeTemplate;
	}
}
