package de.hhu.propra16.avaders.catalogueLoader;

import java.nio.file.Path;

public interface ExerciseLoader {
	ExerciseCatalogue loadExercise(Path path);
}
