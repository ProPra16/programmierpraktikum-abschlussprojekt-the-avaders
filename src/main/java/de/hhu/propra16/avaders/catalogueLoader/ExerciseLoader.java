package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;

import java.io.IOException;

public interface ExerciseLoader {
	ExerciseCatalogue loadExercise() throws SamePropertyTwiceException, IOException, TokenException;
}
