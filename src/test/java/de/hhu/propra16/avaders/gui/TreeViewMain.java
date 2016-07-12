package de.hhu.propra16.avaders.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
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



		TreeView<de.hhu.propra16.avaders.gui.phases.Test>  treeView = new TreeView();


		primaryStage.setTitle("TreeViewTester");
		Scene scene = new Scene(treeView, 300, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
