package de.hhu.propra16.avaders.exerciseLoader;

public class Exercise {
	final String exerciseName;
	final String description;
	final JavaFiles classTemplates;
	final JavaFiles testTemplates;
	final ExerciseConfig exerciseConfig;

	public Exercise(String exerciseName, String description, JavaFiles classTemplates, JavaFiles testTemplates, ExerciseConfig exerciseConfig){
		this.exerciseName = exerciseName;
		this.description = description;
		this.classTemplates = classTemplates;
		this.testTemplates = testTemplates;
		this.exerciseConfig = exerciseConfig;
	}
}
