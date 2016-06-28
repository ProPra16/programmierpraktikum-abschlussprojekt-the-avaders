package de.hhu.propra16.avaders.catalogueLoader.exercises;

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

	public String getClassTemplate(int templateNr){
		return classTemplates.getJavaFile(templateNr).sourceCodeTemplate;
	}

	public String getTestTemplates(int classNr){
		return testTemplates.getJavaFile(classNr).sourceCodeTemplate;
	}

	public boolean babyStepsIsEnabled(){
		return exerciseConfig.isBabySteps();
	}

	public boolean timeTrackingIsEnabled(){
		return exerciseConfig.isTimeTracking();
	}

	public String babyStepsTime(){
		return exerciseConfig.getBabyStepsTime();
	}

	public String getClassName(int classNr){
		return classTemplates.getJavaFile(classNr).className;
	}

	public String getTestName(int testNr) {
		return testTemplates.getJavaFile(testNr).className;
	}

	public int getNrOfClasses(){
		return classTemplates.size();
	}

	public int getNrOfTests(){
		return testTemplates.size();
	}
}
