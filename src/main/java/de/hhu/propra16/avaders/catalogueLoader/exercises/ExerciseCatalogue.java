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

	/**
	 * @param exercise The exercise to be added to the list of exercises
     */
	public void addExercise(Exercise exercise){
		catalogue.add(exercise);
	}

	/**
	 * @param exerciseNumber The index of the exercise to return
	 * @return The exercise at the specified position in the list
	 * @throws IndexOutOfBoundsException - if the index is out of range
     */
	public Exercise getExercise(int exerciseNumber){
		return catalogue.get(exerciseNumber);
	}

	/**
	 * @return The number of exercises in the catalogue
     */
	public int size() { return catalogue.size(); }
}
