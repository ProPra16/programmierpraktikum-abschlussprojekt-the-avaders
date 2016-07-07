package de.hhu.propra16.avaders.catalogueLoader.exercises;

/**
 * This class holds the necessary information of an exercise
 */
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

	/**
	 * @return The name of the exercise
     */
	public String getExerciseName() {
		return exerciseName;
	}

	/**
	 * @return The description of the exercise
     */
	public String getDescription() {
		return description;
	}

	/**
	 * @param templateNr the index of the class of which the template
	 *                   should be returned
	 * @return The template of the class specified by templateNr
     */
	public String getClassTemplate(int templateNr){
		return classTemplates.getJavaFile(templateNr).sourceCodeTemplate;
	}

	/**
	 *
	 * @param classNr the index of the test of which the template
	 *                   should be returned
	 * @return The template of the class specified by templateNr
     */
	public String getTestTemplates(int classNr){
		return testTemplates.getJavaFile(classNr).sourceCodeTemplate;
	}

	/**
	 * @return True if babysteps is enabled in this exercise
     */
	public boolean babyStepsIsEnabled(){
		return exerciseConfig.isBabySteps();
	}

	/**
	 * @return True if time tracking is enabled in this exercise
     */
	public boolean timeTrackingIsEnabled(){
		return exerciseConfig.isTimeTracking();
	}

	/**
	 * @return The time (as string), specified by babysteps, until all changes are reverted
	 */
	public int babyStepsTime(){
		return exerciseConfig.getBabyStepsTime();
	}

	/**
	 * @param classNumber The index of the class of which the name should be returned
	 * @return The name of the class
     */
	public String getClassName(int classNumber){
		return classTemplates.getJavaFile(classNumber).className;
	}

	/**
	 * @param testNumber The index of the test of which the name should be returned
	 * @return The name of the test
     */
	public String getTestName(int testNumber) {
		return testTemplates.getJavaFile(testNumber).className;
	}

	/**
	 * @return the number of classes in this exercise
     */
	public int getNumberOfClasses(){
		return classTemplates.size();
	}

	/**
	 * @return the number of tests in this exercise
     */
	public int getNumberOfTests(){
		return testTemplates.size();
	}

	/**
	 * @return True if atdd is enabled
     */
	public boolean atdd() {
		return exerciseConfig.isAtdd();
	}
}
