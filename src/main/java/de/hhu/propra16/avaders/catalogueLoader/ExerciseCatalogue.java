package de.hhu.propra16.avaders.catalogueLoader;

import java.util.ArrayList;
import java.util.List;

public class ExerciseCatalogue {
	private List<Exercise> catalogue;

	public ExerciseCatalogue(){
		catalogue = new ArrayList<>();
	}

	void addExercise(Exercise exercise){
		catalogue.add(exercise);
	}

	public Exercise getExercise(int exerciseNumber){
		return catalogue.get(exerciseNumber);
	}
}