package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;

import java.io.IOException;
import java.nio.file.Path;

public class XMLExerciseLoader implements ExerciseLoader {
	XMLExerciseTokenizer xmlExerciseTokenizer;
	ExerciseCatalogue loadedExerciseCatalogue;
	private Exercise currentlyParsingExercise;

	public XMLExerciseLoader(XMLExerciseTokenizer xmlExerciseTokenizer){
		this.xmlExerciseTokenizer = xmlExerciseTokenizer;
	}

	@Override
	public ExerciseCatalogue loadExercise() throws SamePropertyTwiceException, IOException, TokenException {
		return parseExercises();
	}

	ExerciseCatalogue parseExercises() throws SamePropertyTwiceException, IOException, TokenException {
		loadedExerciseCatalogue = new ExerciseCatalogue();

		if((xmlExerciseTokenizer.currentToken()).name == "exercises"
				&& xmlExerciseTokenizer.hasNextToken()){
				xmlExerciseTokenizer.advance();
		}
		else{
			//TODO: THROW ERROR unknown token
		}

		while(xmlExerciseTokenizer.hasNextToken()){
			parseToken();
		}

		if(xmlExerciseTokenizer.currentToken().name == "/exercise"
				&& !xmlExerciseTokenizer.hasNextToken()){
			return  loadedExerciseCatalogue;
		}
		else{
			//TODO: THROW ERROR End of classes reached, but more to read
			return null;
		}
	}

	private void parseToken() throws SamePropertyTwiceException, IOException, TokenException {
		xmlExerciseTokenizer.advance();
		switch(xmlExerciseTokenizer.currentToken().name){
			case "exercisename":	//TODO: add exercise name to Exercise
			case "description": // TODO: add description to Exercise
			case "classes":	//TODO: parse classes
			case "tests": //TODO: parse tests
			case "config": //TODO: parse config
		}
	}
}
