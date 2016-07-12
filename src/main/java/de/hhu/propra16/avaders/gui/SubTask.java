package de.hhu.propra16.avaders.gui;

import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;
import de.hhu.propra16.avaders.gui.tools.ExerciseTools;
import de.hhu.propra16.avaders.gui.tools.FileTools;
import de.hhu.propra16.avaders.gui.tools.PathTools;
import javafx.scene.control.TreeItem;

public class SubTask {
	private ExerciseConfig exerciseConfig;
	private String  testName;
	private String  className;
	private String  testTemplate;
	private String  classTemplate;

	public void load(TreeItem<String> testItem, ExerciseCatalogue exerciseCatalogue ){
		String exerciseName           = PathTools.getNameOutOfPath(testItem,1);
		Exercise currentExercise      = ExerciseTools.getExercise(exerciseName, exerciseCatalogue);
		this.exerciseConfig = currentExercise.getExerciseConfig();
		this.testName      = PathTools.getFileNamePrefix(testItem,".java");
		this.className     = PathTools.getFileNamePrefix(testItem,"Test.java");
		this.testTemplate  = getTestTemplate(testItem);
		this.classTemplate = getClassTemplate(testItem,currentExercise);
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
					return exercise.getClassTemplate(name);
			}
		}
		System.out.println("To " + testItem.getValue() + " corresponding class not found");
		return null;
	}

	@Override
	public String toString(){
		return "TestName: " + testName + "\n" +
				"ClassName: " + className  + "\n" +
				"testTemplate: " + testTemplate  + "\n" +
				"classTemplate:" +classTemplate  + "\n";
	}
}
