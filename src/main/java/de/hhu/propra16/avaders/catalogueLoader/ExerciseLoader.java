package de.hhu.propra16.avaders.catalogueLoader;

import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;

import java.io.IOException;
import java.nio.file.Path;

public interface ExerciseLoader {
	ExerciseCatalogue loadExercise(Path path) throws SamePropertyTwiceException, IOException;
}
