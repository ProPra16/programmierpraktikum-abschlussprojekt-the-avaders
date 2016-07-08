package de.hhu.propra16.avaders.catalogueLoader.exercises;

/**
 * JavaFile holds a template for a Java class
 */
public class JavaFile {
	public final String className;
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
