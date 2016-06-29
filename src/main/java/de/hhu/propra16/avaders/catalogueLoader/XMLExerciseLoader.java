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

	@Override
	public ExerciseCatalogue loadExercise() throws SamePropertyTwiceException, IOException, TokenException {
		return parseExercises();
	}

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
			//System.out.println(xmlExerciseTokenizer.currentToken().name);
			parseExercise();
			xmlExerciseTokenizer.advance();
			//System.out.println("\n --- new Exercise --- \n\n");
		}

		//System.out.println("END: " + xmlExerciseTokenizer.currentToken().name + ", " + xmlExerciseTokenizer.hasNextToken());

		if(xmlExerciseTokenizer.currentToken().name.equals("/exercises") && !xmlExerciseTokenizer.hasNextToken()){
			//System.out.println("returning Exercises");
			return  loadedExerciseCatalogue;
		}
		else{
			//TODO: THROW ERROR End of classes reached, but more to read
			//System.out.println("not returning Exercises");
			return null;
		}
	}

	private void parseExercise() throws SamePropertyTwiceException, IOException, TokenException {
		while(xmlExerciseTokenizer.hasNextToken() && !xmlExerciseTokenizer.currentToken().name.equals("/exercise")){
			parseToken();
		}
		//System.out.println("ADDING CLASSES to EXERCISE WITH LENGTH OF: " + classes.size());
		loadedExerciseCatalogue.addExercise(new Exercise(exerciseName, description, classes, tests, exerciseConfig));
	}

	private void parseToken() throws SamePropertyTwiceException, IOException, TokenException {
		Token token = xmlExerciseTokenizer.currentToken();
		//System.out.println("parsing: _" + token.name + "_");
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

	private void parseTests() throws SamePropertyTwiceException, IOException, TokenException {
		tests = new JavaFiles();
		parseJavaFiles(tests, "test", "/tests");
	}

	private void parseClasses() throws IOException, SamePropertyTwiceException, TokenException {
		classes = new JavaFiles();
		parseJavaFiles(classes, "class", "/classes");
	}

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

	private void parseConfig() throws SamePropertyTwiceException, IOException, TokenException {
		exerciseConfig = new ExerciseConfig();
		Token token = xmlExerciseTokenizer.currentToken();

		while(!token.name.equals("/config")) {
			xmlExerciseTokenizer.advance();
			token = xmlExerciseTokenizer.currentToken();

			if (token.name.equals("babysteps")) {
				//System.out.println("BABYSTEPS: " + Boolean.valueOf(token.value));
				exerciseConfig.setBabySteps(Boolean.valueOf(token.value));
				exerciseConfig.setBabyStepsTime(((BabyStepsToken) token).time);
			} else if (token.name.equals("timetracking")) {
				exerciseConfig.setTimeTracking(Boolean.valueOf(token.value));
			}
		}
	}
}
