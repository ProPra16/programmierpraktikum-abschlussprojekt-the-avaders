package de.hhu.propra16.avaders.catalogueLoader.exercises;

/**
 * This class holds the necessary information of an exercise, such as it's name
 * description, class- and test-templates as well as its configuration
 * @see JavaFile
 * @see JavaFiles
 * @see ExerciseConfig
 */
public class Exercise {
	/**
	 * The name of the exercise
     */
	private String exerciseName;

	/**
	 * The description of the exercise
     */

	private String description;

	/**
	 * The class templates in this exercise
	 * @see JavaFiles
     */
	private JavaFiles classTemplates;

	/**
	 * The test templates in this exercise
	 * @see JavaFiles
	 */
	private JavaFiles testTemplates;

	/**
	 * The configuration of this exercise
	 * @see ExerciseConfig
     */
	private ExerciseConfig exerciseConfig;

	/**
	 * Creates an {@link Exercise} with a name, description, templates {@link JavaFiles} and an {@link  ExerciseConfig}
	 * @param exerciseName The name of the exercise
	 * @param description The description of the exercise
	 * @param classTemplates The templates for classes the exercise consists of
	 * @param testTemplates The templates for classes the exercise consists of
	 * @param exerciseConfig The configuration for this class
     */
	public Exercise(String exerciseName, String description, JavaFiles classTemplates, JavaFiles testTemplates, ExerciseConfig exerciseConfig){
		this.exerciseName = exerciseName;
		this.description = description;
		this.classTemplates = classTemplates;
		this.testTemplates = testTemplates;
		this.exerciseConfig = exerciseConfig;
	}

	/**
	 * Returns the name of the exercise
	 * @return The name of the exercise
     */
	public String getExerciseName(){	return exerciseName;	}

	/**
	 * Returns the description of the exercise
	 * @return The description of the exercise
     */
	public String getDescription(){	return description;	}

	/**
	 * Produces the indexed template for a class
	 * @param templateNr the index of the class of which the template
	 *                   should be returned
	 * @return The template of the class specified by templateNr
     */
	public String getClassTemplate(int templateNr){	return classTemplates.getJavaFile(templateNr).sourceCodeTemplate;	}

	/**
	 * Produces the indexed template for a test
	 * @param classNr the index of the test of which the template
	 *                   should be returned
	 * @return The template of the class specified by templateNr
     */
	public String getTestTemplates(int classNr){	return testTemplates.getJavaFile(classNr).sourceCodeTemplate;	}

	/**
	 * Returns the name of an indexed class
	 * @param classNumber The index of the class of which the name should be returned
	 * @return The name of the class
     */
	public String getClassName(int classNumber){	return classTemplates.getJavaFile(classNumber).className;	}

	/**
	 * Produces the name of an indexed test
	 * @param testNumber The index of the test of which the name should be returned
	 * @return The name of the test
     */
	public String getTestName(int testNumber){	return testTemplates.getJavaFile(testNumber).className;	}

	/**
	 * Returns the amount of classes in this {@link Exercise}
	 * @return The number of classes in this {@link Exercise}
     */
	public int getNumberOfClasses(){	return classTemplates.size();	}

	/**
	 * Returns the amount of tests in this {@link Exercise}
	 * @return The number of tests in this {@link Exercise}
     */
	public int getNumberOfTests(){	return testTemplates.size(); }

	/**
	 * Returns the configuration in the form of a {@link ExerciseConfig} for
	 * this class
	 * @return The {@link ExerciseConfig} of this exercise
     */
	public ExerciseConfig getExerciseConfig(){	return exerciseConfig; }
}
