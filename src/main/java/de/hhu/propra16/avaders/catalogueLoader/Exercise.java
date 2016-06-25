package de.hhu.propra16.avaders.catalogueLoader;

public class Exercise {
	private String exerciseName;
	private String description;
	private JavaFiles classTemplates;
	private JavaFiles testTemplates;
	private ExerciseConfig exerciseConfig;

	public Exercise(String exerciseName, String description, JavaFiles classTemplates, JavaFiles testTemplates, ExerciseConfig exerciseConfig){
		this.exerciseName = exerciseName;
		this.description = description;
		this.classTemplates = classTemplates;
		this.testTemplates = testTemplates;
		this.exerciseConfig = exerciseConfig;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public String getDescription() {
		return description;
	}

	public JavaFiles getClassTemplates() {
		return classTemplates;
	}

	public JavaFiles getTestTemplates() {
		return testTemplates;
	}

	public ExerciseConfig getExerciseConfig() {
		return exerciseConfig;
	}
}
