package de.hhu.propra16.avaders.gui;


import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Created by Batman140 on 09.07.2016.
 */
public class ExercisesTree {
	private ExerciseCatalogue exerciseCatalogue;
	private TreeView<String>  treeView;

	public ExercisesTree(ExerciseCatalogue exerciseCatalogue, TreeView<String> treeView){
		this.exerciseCatalogue = exerciseCatalogue;
		this.treeView = treeView;
	}

	public void fill(){
		TreeItem<String> exercises = new TreeItem<>("Exercise");
		for(int i = 0; i < exerciseCatalogue.size(); i++) {
			TreeItem<String> exercise = new TreeItem<>(exerciseCatalogue.getExercise(i).getExerciseName());
			TreeItem<String> classes  = new TreeItem<>("Class");
			TreeItem<String> tests    = new TreeItem<>("Test");

			for(int j = 0; j < exerciseCatalogue.getExercise(i).getNumberOfClasses(); j++){
				classes.getChildren().add(new TreeItem<>(exerciseCatalogue.getExercise(i).getClassName(j)));
			}

			for(int k = 0; k < exerciseCatalogue.getExercise(i).getNumberOfTests(); k++){
				tests.getChildren().add(new TreeItem<>(exerciseCatalogue.getExercise(i).getTestName(k)));
			}
			exercise.getChildren().addAll(classes,tests);
			exercises.getChildren().add(exercise);
		}
		treeView.setRoot(exercises);
		treeView.setShowRoot(false);
	}
}
