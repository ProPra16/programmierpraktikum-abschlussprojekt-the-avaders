package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;

import java.io.IOException;

/**
 * An ExerciseLoader returns an ExerciseCatalogue
 */
public interface ExerciseLoader {
	ExerciseCatalogue loadExercise() throws SamePropertyTwiceException, IOException, TokenException, ParserException;
}
