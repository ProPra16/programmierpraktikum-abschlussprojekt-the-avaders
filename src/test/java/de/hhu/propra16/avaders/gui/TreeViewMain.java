package de.hhu.propra16.avaders.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TreeViewMain extends Application {
	private Stage          primaryStage;


	@Override
	public void start(Stage primaryStage) throws Exception{
		initStage(primaryStage);

	}


	//Initializes Stage, load and store MainController
	private void initStage(Stage primaryStage){
		this.primaryStage = primaryStage;



		TreeView<String>  treeView = new TreeView();
		TreeItem<String>  exercises = new TreeItem<>("Exercises");
		TreeItem<String>  excerciseRoot2 = new TreeItem<>("Exercise2");
		TreeItem<String>  exercise1 = new TreeItem<>("Exercise1");
		TreeItem<String>  exercise2 = new TreeItem<>("Exercise2");

		TreeItem<String>  test1  = new TreeItem<>("test1");
		TreeItem<String>  class1 = new TreeItem<>("class1");
		TreeItem<String>  test2  = new TreeItem<>("test2");
		TreeItem<String>  class2 = new TreeItem<>("class2");

		exercise1.getChildren().addAll(test1,class1);
		exercise2.getChildren().addAll(test2,class2);
		exercises.getChildren().addAll(exercise1,exercise2);
		treeView.setShowRoot(false);
		treeView.setRoot(exercises);

		primaryStage.setTitle("TreeViewTester");
		Scene scene = new Scene(treeView, 300, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
