package de.hhu.propra16.avaders.gui;


import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

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

	public Path getPath(TreeItem<String> leaf){
		if(leaf.isLeaf() == false){
			System.out.println(leaf.getValue() + " is no leaf");
			return null;
		}
		String path = leaf.getValue() + ".txt";
		TreeItem<String> parent = leaf.getParent();
		while(parent != null){
			path = parent.getValue() + File.separator + path;
			parent = parent.getParent();
		}
		return Paths.get(path);
	}

	public static void createFile(Path path, String output){
		if(path == null){
			System.err.println("Path is null");
			return;
		}
		List<String> outputList = new ArrayList<>();
		outputList.add(output);
		deleteFile(path);
		try {
			Files.createDirectories(path.subpath(0, path.getNameCount()-1));
			Files.write(path, outputList, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.err.println("Unable to write to File " + path);
		}
	}
	private static void deleteFile(Path path){
		if(!Files.exists(path))
			return;
		try {
			Files.delete(path);
		} catch (IOException e) {
			System.err.println("Unable to delete File " + path);
		}
	}


	public void fill(String rootName){
		TreeItem<String> exercises = new TreeItem<>(rootName);
		for(int i = 0; i < exerciseCatalogue.size(); i++) {
			TreeItem<String> exercise = new TreeItem<>(exerciseCatalogue.getExercise(i).getExerciseName());
			TreeItem<String> classes  = new TreeItem<>("Class");
			createFile(Paths.get("" + rootName + File.separator + exercise.getValue() + File.separator + "description.txt"), exerciseCatalogue.getExercise(i).getDescription());

			for(int j = 0; j < exerciseCatalogue.getExercise(i).getNumberOfClasses(); j++){
				TreeItem<String> currentClass = new TreeItem<>(exerciseCatalogue.getExercise(i).getClassName(j));
				classes.getChildren().add(currentClass);
				createFile(Paths.get("" + rootName + File.separator + exercise.getValue() + File.separator  + getPath(currentClass)), exerciseCatalogue.getExercise(i).getClassTemplate(j));
			}

			TreeItem<String> tests    = new TreeItem<>("Test");
			for(int k = 0; k < exerciseCatalogue.getExercise(i).getNumberOfTests(); k++){
				TreeItem<String> currentTest = new TreeItem<>(exerciseCatalogue.getExercise(i).getTestName(k));
				tests.getChildren().add(currentTest);
				createFile(Paths.get("" + rootName + File.separator + exercise.getValue() + File.separator  + getPath(currentTest)), exerciseCatalogue.getExercise(i).getTestTemplates(k));
			}
			exercise.getChildren().addAll(classes,tests);
			exercises.getChildren().add(exercise);
		}
		treeView.setRoot(exercises);
		treeView.setShowRoot(false);
		treeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.PRIMARY){
					TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
					System.out.println("Selected item: " + item.getValue());
				}
			}
		});

	}


}
