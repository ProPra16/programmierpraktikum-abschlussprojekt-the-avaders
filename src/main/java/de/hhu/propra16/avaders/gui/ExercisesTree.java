package de.hhu.propra16.avaders.gui;

import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.gui.tools.FileTools;
import de.hhu.propra16.avaders.gui.tools.PathTools;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.nio.file.*;

public class ExercisesTree {
	private ExerciseCatalogue exerciseCatalogue;
	private TreeView<String>  treeView;

	public ExercisesTree(ExerciseCatalogue exerciseCatalogue, TreeView<String> treeView){
		this.exerciseCatalogue = exerciseCatalogue;
		this.treeView = treeView;
	}

	//TODO understand warning
	public void fill(String rootName){
		TreeItem<String> exercises = new TreeItem<>(rootName);
		for(int i = 0; i < exerciseCatalogue.size(); i++) {
			TreeItem<String> exercise = new TreeItem<>(exerciseCatalogue.getExercise(i).getExerciseName());
			FileTools.createFile(Paths.get("" + rootName + File.separator + exercise.getValue() + File.separator + "description.txt"), exerciseCatalogue.getExercise(i).getDescription());

			TreeItem<String> classes  = new TreeItem<>("Class");
			for(int j = 0; j < exerciseCatalogue.getExercise(i).getNumberOfClasses(); j++){
				TreeItem<String> currentClass = new TreeItem<>(exerciseCatalogue.getExercise(i).getClassName(j));
				classes.getChildren().add(currentClass);
				FileTools.createFile(Paths.get("" + rootName + File.separator + exercise.getValue() + File.separator  + PathTools.getPath(currentClass)), exerciseCatalogue.getExercise(i).getClassTemplate(j));
			}

			TreeItem<String> tests    = new TreeItem<>("Test");
			for(int k = 0; k < exerciseCatalogue.getExercise(i).getNumberOfTests(); k++){
				TreeItem<String> currentTest = new TreeItem<>(exerciseCatalogue.getExercise(i).getTestName(k));
				tests.getChildren().add(currentTest);
				FileTools.createFile(Paths.get("" + rootName + File.separator + exercise.getValue() + File.separator  + PathTools.getPath(currentTest)), exerciseCatalogue.getExercise(i).getTestTemplates(k));
			}
			exercise.getChildren().addAll(classes,tests);
			exercises.getChildren().add(exercise);
		}
		treeView.setRoot(exercises);
		treeView.setShowRoot(false);
	}

}
