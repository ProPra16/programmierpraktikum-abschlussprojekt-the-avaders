package de.hhu.propra16.avaders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {
	private Stage          primaryStage;
	private MainController controller;
	private BorderPane     mainBase;


	@Override
	public void start(Stage primaryStage) throws Exception{
		initStage(primaryStage);
		makeThisAccessibleFor(controller);

	}

	private void makeThisAccessibleFor(MainController controller){
		this.controller.setMain(this);
	}

	//Initializes Stage, load and store MainController
	private void initStage(Stage primaryStage){
		this.primaryStage = primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("applicationView.fxml"));
			this.mainBase     = loader.load();
			this.controller   = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		primaryStage.setTitle("Test Driven Development Trainer");
		Scene scene = new Scene(mainBase, 800, 600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Path getExercise() {
		FileChooser exerciseChoosePopup = new FileChooser();
		exerciseChoosePopup.setTitle("Choose Exercise");
		exerciseChoosePopup.getExtensionFilters().add( new FileChooser.ExtensionFilter("xml files", "*.xml"));
		File exerciseFile = exerciseChoosePopup.showOpenDialog(primaryStage);
		if(exerciseFile != null)
			return Paths.get(exerciseFile.getAbsolutePath());
		return null;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
