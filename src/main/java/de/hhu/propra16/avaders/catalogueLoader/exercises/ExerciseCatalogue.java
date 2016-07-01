package de.hhu.propra16.avaders.catalogueLoader.exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the exercises that were parsed with the ExerciseLoader
 */
public class ExerciseCatalogue {
	private final List<Exercise> catalogue;

	public ExerciseCatalogue(){
		catalogue = new ArrayList<>();
	}

	public void addExercise(Exercise exercise){
		catalogue.add(exercise);
	}

	public Exercise getExercise(int exerciseNumber){
		return catalogue.get(exerciseNumber);
	}

	public int size() {
		return catalogue.size();
	}
}
