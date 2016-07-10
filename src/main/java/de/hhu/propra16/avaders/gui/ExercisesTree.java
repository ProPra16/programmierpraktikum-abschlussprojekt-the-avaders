package de.hhu.propra16.avaders.gui;


import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


import java.io.File;
import java.nio.file.*;


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

	public void fill(String rootName){
		TreeItem<String> exercises = new TreeItem<>(rootName);
		for(int i = 0; i < exerciseCatalogue.size(); i++) {
			TreeItem<String> exercise = new TreeItem<>(exerciseCatalogue.getExercise(i).getExerciseName());
			TreeItem<String> classes  = new TreeItem<>("Class");
			FileTools.createFile(Paths.get("" + rootName + File.separator + exercise.getValue() + File.separator + "description.txt"), exerciseCatalogue.getExercise(i).getDescription());

			for(int j = 0; j < exerciseCatalogue.getExercise(i).getNumberOfClasses(); j++){
				TreeItem<String> currentClass = new TreeItem<>(exerciseCatalogue.getExercise(i).getClassName(j));
				classes.getChildren().add(currentClass);
				FileTools.createFile(Paths.get("" + rootName + File.separator + exercise.getValue() + File.separator  + PathTools.getLeafPath(currentClass)), exerciseCatalogue.getExercise(i).getClassTemplate(j));
			}

			TreeItem<String> tests    = new TreeItem<>("Test");
			for(int k = 0; k < exerciseCatalogue.getExercise(i).getNumberOfTests(); k++){
				TreeItem<String> currentTest = new TreeItem<>(exerciseCatalogue.getExercise(i).getTestName(k));
				tests.getChildren().add(currentTest);
				FileTools.createFile(Paths.get("" + rootName + File.separator + exercise.getValue() + File.separator  + PathTools.getLeafPath(currentTest)), exerciseCatalogue.getExercise(i).getTestTemplates(k));
			}
			exercise.getChildren().addAll(classes,tests);
			exercises.getChildren().add(exercise);
		}
		setTreeViewStates(exercises);
	}

	public void setTreeViewStates(TreeItem<String> root){
		treeView.setRoot(root);
		treeView.setShowRoot(false);
		treeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.PRIMARY){
					TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
					System.out.println("Selected item: " + item.getValue());
					System.out.println(PathTools.getPath(item));
				}
			}
		});
	}


}
