package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;

import java.io.IOException;
import java.nio.file.Path;

public interface ExerciseLoader {
	ExerciseCatalogue loadExercise() throws SamePropertyTwiceException, IOException, TokenException;
}
