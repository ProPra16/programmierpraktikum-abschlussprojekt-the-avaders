package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.exercises.*;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.ExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.MissingTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.UnexpectedTokenException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.BabyStepsToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.ClassToken;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.tokens.Token;

import java.io.IOException;

/**
 * Loads exercise catalogues into a dedicated {@link ExerciseCatalogue} instance provided
 * by the caller with the help of a {@link ExerciseTokenizer}
 * @see CatalogueLoader
 */
public class ExerciseCatalogueLoader implements CatalogueLoader {
	private final ExerciseTokenizer exerciseTokenizer;
	private final ExerciseCatalogue loadedExerciseCatalogue;

	private String exerciseName = null;
	private String description = null;
	private ExerciseConfig exerciseConfig = null;
	private JavaFiles classes = null;
	private JavaFiles tests = null;

	/**
	 * Creates a {@link ExerciseCatalogueLoader} instance that is able to load
	 * an {@link ExerciseCatalogue} from a {@link ExerciseTokenizer}
	 * @see CatalogueLoader
	 * @param exerciseTokenizer The {@link XMLExerciseTokenizer} from which
	 *                             the tokens are read
	 * @param exerciseCatalogue The {@link ExerciseCatalogue} to be filled with the
	 *                          parsed exercises
     */
	public ExerciseCatalogueLoader(ExerciseTokenizer exerciseTokenizer, ExerciseCatalogue exerciseCatalogue){
		this.exerciseTokenizer = exerciseTokenizer;
		this.loadedExerciseCatalogue = exerciseCatalogue;
	}

	/**
	 * Parses the exercise catalogue and returns a {@link ExerciseCatalogue} instance with
	 * the parsed information
	 * @return An {@link ExerciseCatalogue} with the read exercises
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws IOException If an I/O error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
	 * @throws ParserException If an unexpected circumstance is occurs during parsing
     */
	@Override
	public ExerciseCatalogue loadCatalogue()
			throws SamePropertyTwiceException, IOException, TokenException, ParserException {
		return parseExercises();
	}

	/**
	 * Parses all exercises and returns the exerciseCatalogue holding those exercises
	 * @return The ExerciseCatalogue holding the parsed exercises
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
     */
	private ExerciseCatalogue parseExercises()
			throws SamePropertyTwiceException, IOException, TokenException, ParserException {

		if(exerciseTokenizer.currentToken() == null) throw new ParserException("The specified file is empty.");

		if((exerciseTokenizer.currentToken()).name.startsWith("exercises")
				&& exerciseTokenizer.hasNextToken()) exerciseTokenizer.advance();
		else throw new UnexpectedTokenException("exercises", exerciseTokenizer.currentToken().name,
				exerciseTokenizer.getLineNumber());

		while(exerciseTokenizer.hasNextToken() && !exerciseTokenizer.currentToken().name.equals("/exercises")) {
			parseExercise();
			exerciseTokenizer.advance();
		}

		if(loadedExerciseCatalogue.size() == 0)
			throw new ParserException("The specified catalogue does not contain an exercise.");

		if(exerciseTokenizer.currentToken() == null)
			throw new MissingTokenException("exercises", exerciseTokenizer.getLineNumber());

		if(exerciseTokenizer.currentToken().name.equals("/exercises") && !exerciseTokenizer.hasNextToken())
			return loadedExerciseCatalogue;

		return null;
	}

	/**
	 * Parses a single exercise and adds it to the exerciseCatalogue
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
     */
	private void parseExercise() throws SamePropertyTwiceException, IOException, TokenException, ParserException {
		tests = new JavaFiles();
		exerciseConfig = new ExerciseConfig();
		classes = new JavaFiles();

		while(exerciseTokenizer.hasNextToken() && !exerciseTokenizer.currentToken().name.equals("/exercise"))
			parseToken();

		if(exerciseName != null && description != null && classes.size() > 0 && tests.size() > 0 && exerciseConfig != null) {
			loadedExerciseCatalogue.addExercise(
					new Exercise(exerciseName, description, classes, tests, exerciseConfig)
			);
		}
		else throw new ParserException("Exercise name, classes, tests or config missing in file");
	}

	/**
	 * Parses a tokens and adds it's information to the corresponding field
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
     */
	private void parseToken() throws SamePropertyTwiceException, IOException, TokenException, ParserException {
		Token token = exerciseTokenizer.currentToken();
		switch(token.name){
			case "exercise": exerciseName = token.value; break;
			case "description": {
				if(token.value != null) description = token.value;
				else description = "";
				break;
			}
			case "classes":	parseJavaFiles(classes, "class", "/classes"); break;
			case "tests": parseJavaFiles(tests, "test", "/tests"); break;
			case "config": parseConfig(); break;
			default:
				throw new UnexpectedTokenException("<exercise>, <description>, <classes>, <tests> or <config>",
						token.name, exerciseTokenizer.getLineNumber());
		}
		exerciseTokenizer.advance();
	}

	/**
	 * parses all java files (test or class) and adds them to either tests or classes
	 * @param javaFiles The Object in which the java files will be collected
	 * @param classType The type of java file: test or class
	 * @param stringToEndOn The string which marks the end of the java files
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws IOException If an IO error occurs with the BufferedReader instance
     * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
     */
	private void parseJavaFiles(JavaFiles javaFiles, String classType, String stringToEndOn)
			throws SamePropertyTwiceException, IOException, TokenException, ParserException {
		ClassToken classToken;

		do {
			classToken = exerciseTokenizer.readJavaFile(stringToEndOn, classType);
			if(classToken != null) {
				JavaFile javaFile = new JavaFile(classToken.value, classToken.classTemplate);
				javaFiles.addJavaFile(javaFile);
			}
		}while(!exerciseTokenizer.currentToken().name.equals(stringToEndOn) && classToken != null);
	}

	/**
	 * Parses the Config structure in the xml file
	 * @throws SamePropertyTwiceException If the same property was read twice in a tokens
	 * @throws IOException If an IO error occurs with the BufferedReader instance
	 * @throws TokenException If an unexpected tokens was read or a tokens was expected,
	 * but not found
     */
	private void parseConfig() throws SamePropertyTwiceException, IOException, TokenException {
		Token token = exerciseTokenizer.currentToken();

		while(!token.name.equals("/config")) {
			exerciseTokenizer.advance();
			token = exerciseTokenizer.currentToken();

			if(token == null) throw new MissingTokenException("</config>", exerciseTokenizer.getLineNumber());

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
