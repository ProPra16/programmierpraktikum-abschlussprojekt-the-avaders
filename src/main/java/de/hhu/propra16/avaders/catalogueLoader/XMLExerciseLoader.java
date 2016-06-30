package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.exercises.*;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.ClassToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.token.Token;

import java.io.IOException;

/**
 * Loads Exercises from a .xml file
 */
public class XMLExerciseLoader implements ExerciseLoader {
	private final XMLExerciseTokenizer xmlExerciseTokenizer;
	private final ExerciseCatalogue loadedExerciseCatalogue;

	private String exerciseName = null;
	private String description = null;
	private ExerciseConfig exerciseConfig = null;
	private JavaFiles classes = null;
	private JavaFiles tests = null;

	public XMLExerciseLoader(XMLExerciseTokenizer xmlExerciseTokenizer, ExerciseCatalogue exerciseCatalogue){
		this.xmlExerciseTokenizer = xmlExerciseTokenizer;
		this.loadedExerciseCatalogue = exerciseCatalogue;
	}

	/**
	 * Parses the exercise catalogue and returns a ExerciseCatalogue instance with
	 * the parsed information
	 * @return An ExerciseCatalogue with the read exercises
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
     */
	@Override
	public ExerciseCatalogue loadExercise() throws SamePropertyTwiceException, IOException, TokenException {
		return parseExercises();
	}

	/**
	 * Parses all exercises and returns the exerciseCatalogue holding those exercises
	 * @return The ExerciseCatalogue holding the parsed exercises
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
     */
	private ExerciseCatalogue parseExercises() throws SamePropertyTwiceException, IOException, TokenException {
		if((xmlExerciseTokenizer.currentToken()).name.equals("exercises")
				&& xmlExerciseTokenizer.hasNextToken()){
				xmlExerciseTokenizer.advance();
		}
		else{
			throw new UnexpectedTokenException("<exercises>", xmlExerciseTokenizer.currentToken().name,
					xmlExerciseTokenizer.getLineNr());
		}

		while(xmlExerciseTokenizer.hasNextToken() && !xmlExerciseTokenizer.currentToken().name.equals("/exercises")) {
			parseExercise();
			xmlExerciseTokenizer.advance();
		}

		if(xmlExerciseTokenizer.currentToken().name.equals("/exercises") && !xmlExerciseTokenizer.hasNextToken()){
			return  loadedExerciseCatalogue;
		}
		else{
			//TODO: THROW ERROR End of classes reached, but more to read
			//System.out.println("not returning Exercises");
			return null;
		}
	}

	/**
	 * Parses a single exercise and adds it to the exerciseCatalogue
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
     */
	private void parseExercise() throws SamePropertyTwiceException, IOException, TokenException {
		while(xmlExerciseTokenizer.hasNextToken() && !xmlExerciseTokenizer.currentToken().name.equals("/exercise")){
			parseToken();
		}
		loadedExerciseCatalogue.addExercise(new Exercise(exerciseName, description, classes, tests, exerciseConfig));
	}

	/**
	 * Parses a token and adds it's information to the corresponding field
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
     */
	private void parseToken() throws SamePropertyTwiceException, IOException, TokenException {
		Token token = xmlExerciseTokenizer.currentToken();
		switch(token.name){
			case "exercise": exerciseName = token.value; break;
			case "description": description = token.value; break;
			case "classes":	parseClasses(); break;
			case "tests": parseTests(); break;
			case "config": parseConfig(); break;
			default:
				//TODO: throw error
		}
		xmlExerciseTokenizer.advance();
	}

	/**
	 * parses all tests inside <tests> ... </tests> and adds them to tests
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
     */
	private void parseTests() throws SamePropertyTwiceException, IOException, TokenException {
		tests = new JavaFiles();
		parseJavaFiles(tests, "test", "/tests");
	}

	/**
	 * parses all classes inside <classes> ... </classes> and adds them to tests
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
	 * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
     */
	private void parseClasses() throws IOException, SamePropertyTwiceException, TokenException {
		classes = new JavaFiles();
		parseJavaFiles(classes, "class", "/classes");
	}

	/**
	 * parses all java files (test or class) and adds them to either tests or classes
	 * @param javaFiles The Object in which the java files will be collected
	 * @param classType The type of java file: test or class
	 * @param stringToEndOn The string which marks the end of the java files
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
	 * @throws IOException If an IO error occurs with the BufferedReader instance
     * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
     */
	private void parseJavaFiles(JavaFiles javaFiles, String classType, String stringToEndOn) throws SamePropertyTwiceException, IOException, TokenException {
		ClassToken classToken;

		do {
			classToken = xmlExerciseTokenizer.readJavaFile(stringToEndOn, classType);
			if(classToken != null) {
				JavaFile javaFile = new JavaFile(classToken.value, classToken.classTemplate);
				javaFiles.addJavaFile(javaFile);
			}
		}while(!xmlExerciseTokenizer.currentToken().name.equals(stringToEndOn) && classToken != null);
	}

	/**
	 * Parses the Config structure in the xml file
	 * @throws SamePropertyTwiceException If the same property was read twice in a token
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected token was read or a token was expected,
	 * but not found
     */
	private void parseConfig() throws SamePropertyTwiceException, IOException, TokenException {
		exerciseConfig = new ExerciseConfig();
		Token token = xmlExerciseTokenizer.currentToken();

		while(!token.name.equals("/config")) {
			xmlExerciseTokenizer.advance();
			token = xmlExerciseTokenizer.currentToken();

			switch (token.name) {
				case "babysteps":
					exerciseConfig.setBabySteps(Boolean.valueOf(token.value));
					exerciseConfig.setBabyStepsTime(((BabyStepsToken) token).time);
					break;
				case "timetracking":
					exerciseConfig.setTimeTracking(Boolean.valueOf(token.value));
					break;
				case "atdd":
					exerciseConfig.setAtdd(Boolean.valueOf(token.value));
					break;
			}
		}
	}
}
