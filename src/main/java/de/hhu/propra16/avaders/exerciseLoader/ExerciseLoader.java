package de.hhu.propra16.avaders.exerciseLoader;

import java.nio.file.Path;

public interface ExerciseLoader {
	ExerciseCatalogue loadExercise(Path path);
}
