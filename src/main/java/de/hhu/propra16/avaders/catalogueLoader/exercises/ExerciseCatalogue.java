package de.hhu.propra16.avaders.catalogueLoader.exercises;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds {@link Exercise} instances in a list
 */
public class ExerciseCatalogue {
	private final List<Exercise> catalogue;

	/**
	 * Produces a new empty {@link ExerciseCatalogue}
	 */
	public ExerciseCatalogue(){ catalogue = new ArrayList<>(); }

	/**
	 * @param exercise The {@link Exercise} to be added to the list of exercises
     */
	public void addExercise(Exercise exercise){
		catalogue.add(exercise);
	}

	/**
	 * @param exerciseNumber The index of the {@link Exercise} to return
	 * @return The {@link Exercise} at the specified position in the list
	 * @throws IndexOutOfBoundsException - if the index is out of range
     */
	public Exercise getExercise(int exerciseNumber){
		return catalogue.get(exerciseNumber);
	}

	/**
	 * @return The number of {@link Exercise} instances in the catalogue
     */
	public int size() { return catalogue.size(); }
}
