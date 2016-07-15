package de.hhu.propra16.avaders.gui;

import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;
import de.hhu.propra16.avaders.gui.tools.ExerciseTools;
import de.hhu.propra16.avaders.gui.tools.FileTools;
import de.hhu.propra16.avaders.gui.tools.PathTools;
import de.hhu.propra16.avaders.logik.Step;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Every exercise contains subTasks. SubTask: When the users starts a cycle on a test-Class.
 */
public class SubTask {
	private ExerciseConfig exerciseConfig;
	private String  testName;
	private String  className;
	private String  testTemplate;
	private String  classTemplate;
	private Path    testPath;
	private Path    classPath;

	public Path getClassPath() {
		return classPath;
	}

	/**
	 * Gets the current edition on the classTemplate
	 * @return Edited classTemplate

     */
	public String getClassTemplate() {return classTemplate;}

	/**
	 * Gets the current edition on the testTemplate
	 * @return Edited testTemplate
	 */
	public String getTestTemplate() {return testTemplate;}


	/**
	 * Gives the name of the currently handled class according to phase
	 * @param mode Current phase
	 * @return Name of the currently handled class
     */
	public String getName(Step mode){
		switch (mode){
			case TEST_REFACTOR:
			case RED:           return testName;
			case CODE_REFACTOR:
			case GREEN:         return className;
			default:
				System.err.println("SubTask.getName(...) called out of cycle!");
				throw new RuntimeException();
		}
	}


	/**
	 * Updates the templates to the edited ones for next cycle
	 * @param codeRefactorOutputArea The source for updating classTemplate
	 * @param testRefactorOutputArea The source for updating testTemplate
     */
	public void updateForNextCycle(TextArea codeRefactorOutputArea, TextArea testRefactorOutputArea){
		this.classTemplate = codeRefactorOutputArea.getText().trim();
		this.testTemplate  = testRefactorOutputArea.getText().trim();
		System.out.println("Updated classTemplate:\n" + classTemplate+ "\nUpdated testTemplate:\n" + testTemplate + "\n");
	}


	/**
	 * Gets the template according to current phase
	 * @param mode Current phase
	 * @return The template according to current phase
     */
	public String getTemplate(Step mode){
		switch (mode){
			case TEST_REFACTOR:
			case RED:               return testTemplate;
			case CODE_REFACTOR:
			case GREEN:             return classTemplate;
			default:
				System.err.println("SubTask.getTemplate(...) called out of Red and Green-phase!");
				throw new RuntimeException();
		}
	}


	/**
	 * Loads the templates out of the specified catalogue
	 * @param testItem           The TreeItem to determine the current class
	 * @param exerciseCatalogue  The catalogue where the states will be get off only by knowing the treeItem
     */
	public void load(TreeItem<String> testItem, ExerciseCatalogue exerciseCatalogue ){
		String exerciseName           = PathTools.getNameOutOfPath(testItem,1);
		Exercise currentExercise      = ExerciseTools.getExercise(exerciseName, exerciseCatalogue);
		this.exerciseConfig = currentExercise.getExerciseConfig();
		this.testName      = PathTools.getFileNamePrefix(testItem,".java");
		this.className     = PathTools.getFileNamePrefix(testItem,"Test.java");
		this.testTemplate  = getTestTemplate(testItem).trim();
		this.classTemplate = getClassTemplate(testItem,currentExercise).trim();
	}


	/**
	 * Gives the testTemplate out of a file
	 * @param testItem Selected item
	 * @return The testTemplate read out of a file
     */
	private static String getTestTemplate(TreeItem<String> testItem){
		return FileTools.readFile(PathTools.getPath(testItem));
	}

	/**
	 * Finds the classFile according to selected testname in TreeItem and reads the classTemplate out of it
	 * @param testItem Selected TreeItem
	 * @param exercise Current exercise
     * @return The classTemplate
     */
	private static String getClassTemplate(TreeItem<String> testItem, Exercise exercise){
		if(testItem.isLeaf() & testItem.getParent().getValue().contentEquals("Test")){
			String testItemReduced = testItem.getValue().replace("Test", "");
			for(int name = 0; name < exercise.getNumberOfClasses(); name++){
				if(exercise.getClassName(name).contentEquals(testItemReduced))
					return FileTools.readFile(Paths.get(PathTools.getPath(testItem).subpath(0,2).toString() + File.separator + "Class" + File.separator +
							exercise.getClassName(name) + ".java"));
			}
		}
		return null;
	}

	/**
	 * String-representation of this class
	 * @return String-representation
     */
	@Override
	public String toString(){
		return "TestName: " + testName + "\n" +
				"ClassName: " + className  + "\n" +
				"testTemplate:\n" + testTemplate  + "\n" +
				"classTemplate:\n" +classTemplate  + "\n";
	}

	/**
	 * Creates a path out of a TreeItem
	 * @param selection The treeItem to create a path for
     */
	public void createPaths(TreeItem<String> selection) {
		this.testPath  = PathTools.getPath(selection);
		this.classPath = Paths.get(testPath.subpath(0,2) + File.separator + "Class" + File.separator + className + ".java");
	}

	/**
	 * Updates the files on hard-drive
	 */
	public void saveToFiles() {
		FileTools.updateFile(testPath, testTemplate);
		FileTools.updateFile(classPath, classTemplate);
	}

	/**
	 * Getter for the exercise-config
	 * @return The exercise-config
     */
	public ExerciseConfig getExerciseConfig() {
		return exerciseConfig;
	}
}
