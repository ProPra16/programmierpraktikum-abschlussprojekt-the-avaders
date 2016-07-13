package de.hhu.propra16.avaders.gui.tools;

import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;

public class ExerciseTools {
	//TODO handle RuntimeException
	public static ExerciseConfig getConfig(String exerciseName, ExerciseCatalogue exerciseCatalogue){
		for(int exercise = 0; exercise < exerciseCatalogue.size(); exercise++){
			if(exerciseName.contentEquals(exerciseCatalogue.getExercise(exercise).getExerciseName()))
				return exerciseCatalogue.getExercise(exercise).getExerciseConfig();
		}
		System.err.println("Invalid name of exercise: " + exerciseName);
		throw new RuntimeException();
	}

	//TODO handle RuntimeException
	public static Exercise getExercise(String exerciseName, ExerciseCatalogue exerciseCatalogue){
		for(int exercise = 0; exercise < exerciseCatalogue.size(); exercise++){
			if(exerciseName.contentEquals(exerciseCatalogue.getExercise(exercise).getExerciseName()))
				return exerciseCatalogue.getExercise(exercise);
		}
		System.err.println("Invalid name of exercise: " + exerciseName);
		throw new RuntimeException();
	}
}
