package de.hhu.propra16.avaders.gui.tools;

import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;

public class ExerciseTools {
	/**
	 * Gets the exerciseCatalogue according to a given Name of an exercise
	 * @param exerciseName       The name of the exercise
	 * @param exerciseCatalogue  The corresponding catalogue
     * @return The catalogue corresponding to the name of the exercise
     */
	public static ExerciseConfig getConfig(String exerciseName, ExerciseCatalogue exerciseCatalogue){
		for(int exercise = 0; exercise < exerciseCatalogue.size(); exercise++){
			if(exerciseName.contentEquals(exerciseCatalogue.getExercise(exercise).getExerciseName()))
				return exerciseCatalogue.getExercise(exercise).getExerciseConfig();
		}
		System.err.println("Invalid name of exercise: " + exerciseName);
		throw new RuntimeException();
	}

	/**
	 * Gets the exercise according to a given Name of an exercise
	 * @param exerciseName       The name of the exercise
	 * @param exerciseCatalogue  The corresponding catalogue
	 * @return The exercise corresponding to the name of the exercise
	 */
	public static Exercise getExercise(String exerciseName, ExerciseCatalogue exerciseCatalogue){
		for(int exercise = 0; exercise < exerciseCatalogue.size(); exercise++){
			if(exerciseName.contentEquals(exerciseCatalogue.getExercise(exercise).getExerciseName()))
				return exerciseCatalogue.getExercise(exercise);
		}
		System.err.println("Invalid name of exercise: " + exerciseName);
		throw new RuntimeException();
	}
}
