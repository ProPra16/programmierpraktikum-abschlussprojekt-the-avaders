package de.hhu.propra16.avaders.catalogueLoader.exercises;

import de.hhu.propra16.avaders.catalogueLoader.ParserException;

/**
 * JavaFile holds a template for a Java class
 */
public class JavaFile {
	public static final String WHITE_SPACE = "\\s";
	public static final String CLASS = "class";
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
	 * @throws ParserException If the name of the class does not equal the
	 * 							class name given in the source code
     */
	public JavaFile(String className, String sourceCodeTemplate) throws ParserException {
		this.className = className;
		this.sourceCodeTemplate = sourceCodeTemplate;

		if(!isValidClassName())
			throw new ParserException("Class: " + className + " name in attribute and source code do not match");
	}

	/**
	 * Checks if the name of the javaFile is valid, by comparing it to the
	 * class name in the source code
	 * @return True if the class name is valid, false otherwise
     */
	private boolean isValidClassName() {
		String classHeader = sourceCodeTemplate.substring(0, sourceCodeTemplate.indexOf('{')+1);
		return classHeader.replaceAll(WHITE_SPACE, "").contains(CLASS + className + '{');
	}
}
