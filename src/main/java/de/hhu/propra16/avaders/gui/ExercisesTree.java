package de.hhu.propra16.avaders.gui;


import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
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
}
