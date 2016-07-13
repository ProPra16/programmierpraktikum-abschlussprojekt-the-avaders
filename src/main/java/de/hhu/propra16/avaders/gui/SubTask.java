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

public class SubTask {
	private ExerciseConfig exerciseConfig;
	private String  testName;
	private String  className;
	private String  testTemplate;

	public String getClassTemplate() {return classTemplate;}
	public String getTestTemplate() {return testTemplate;}

	private String  classTemplate;
	private Path    testPath;
	private Path    classPath;



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

	private void setTestTemplate(String testOutputArea){
		this.testTemplate = testOutputArea;
	}

	private void setClassTemplate(String codeOutputArea){
		this.classTemplate = codeOutputArea;
	}

	public void updateForNextCycle(TextArea codeRefactorOutputArea, TextArea testRefactorOutputArea){
		this.classTemplate = codeRefactorOutputArea.getText();
		this.testTemplate  = testRefactorOutputArea.getText();
		System.out.println("Updated classTemplate:\n" + classTemplate+ "\nUpdated testTemplate:\n" + testTemplate + "\n");
	}

	public String getTemplate(Step mode){
		switch (mode){
			case CODE_REFACTOR:
			case RED:               return testTemplate;
			case TEST_REFACTOR:
			case GREEN:             return classTemplate;
			default:
				System.err.println("SubTask.getTemplate(...) called out of Red and Green-phase!");
				throw new RuntimeException();
		}
	}

	public void load(TreeItem<String> testItem, ExerciseCatalogue exerciseCatalogue ){
		String exerciseName           = PathTools.getNameOutOfPath(testItem,1);
		Exercise currentExercise      = ExerciseTools.getExercise(exerciseName, exerciseCatalogue);
		this.exerciseConfig = currentExercise.getExerciseConfig();
		this.testName      = PathTools.getFileNamePrefix(testItem,".java");
		this.className     = PathTools.getFileNamePrefix(testItem,"Test.java");
		this.testTemplate  = getTestTemplate(testItem);
		this.classTemplate = getClassTemplate(testItem,currentExercise);
		System.out.println("");
	}

	private static String getTestTemplate(TreeItem<String> testItem){
		return FileTools.readFile(PathTools.getPath(testItem));
	}

	//class xxx.java; test xxxTest.java
	private static String getClassTemplate(TreeItem<String> testItem, Exercise exercise){
		if(testItem.isLeaf() & testItem.getParent().getValue().contentEquals("Test")){
			String testItemReduced = testItem.getValue().replace("Test", "");
			for(int name = 0; name < exercise.getNumberOfClasses(); name++){
				if(exercise.getClassName(name).contentEquals(testItemReduced))
					return FileTools.readFile(Paths.get(PathTools.getPath(testItem).subpath(0,2).toString() + File.separator + "Class" + File.separator +
							exercise.getClassName(name) + ".java"));
			}
		}
		System.out.println("To " + testItem.getValue() + " corresponding class not found");
		return null;
	}

	@Override
	public String toString(){
		return "TestName: " + testName + "\n" +
				"ClassName: " + className  + "\n" +
				"testTemplate:\n" + testTemplate  + "\n" +
				"classTemplate:\n" +classTemplate  + "\n";
	}

	public void createPaths(TreeItem<String> selection) {
		this.testPath  = PathTools.getPath(selection);
		this.classPath = Paths.get(testPath.subpath(0,2) + File.separator + "Class" + File.separator + className + ".java");
	}

	public void saveToFiles() {
		FileTools.updateFile(testPath, testTemplate);
		FileTools.updateFile(classPath, classTemplate);
	}

	public ExerciseConfig getExerciseConfig() {
		return exerciseConfig;
	}
}
