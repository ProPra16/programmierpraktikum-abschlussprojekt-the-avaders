package de.hhu.propra16.avaders.gui;

import de.hhu.propra16.avaders.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultMainTemplate extends Application {
	private Stage          primaryStage;


	@Override
	public void start(Stage primaryStage) throws Exception{
		initStage(primaryStage);

	}


	//Initializes Stage, load and store MainController
	private void initStage(Stage primaryStage){
		this.primaryStage = primaryStage;
		StackPane root = new StackPane();
		primaryStage.setTitle("Default Main-Template");
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
