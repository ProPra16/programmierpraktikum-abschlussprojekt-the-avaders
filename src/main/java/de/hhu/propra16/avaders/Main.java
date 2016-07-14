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

/**
 * The Main-class which defines the main-control
 */
public class Main extends Application {
	private Stage          primaryStage;
	private MainController controller;
	private BorderPane     mainBase;

	/**
	 * Getter for the primarystage to set the timeChart on when timeTracking
	 * is enabled
	 * @return The main stage
     */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * The start-method where the main action of the program takes place
	 * @param primaryStage Main stage for gui
	 * @throws Exception   Thrown when something went wrong
     */
	@Override
	public void start(Stage primaryStage) throws Exception{
		initStage(primaryStage);
		makeThisAccessibleFor(controller);
	}

	/**
	 * Give the controller access to main
	 * @param controller The MainController
     */
	private void makeThisAccessibleFor(MainController controller){
		this.controller.setMain(this);
	}

	/**
	 * Initializes the Stage
	 * @param primaryStage The Stage to be initialized
     */
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

	/**
	 * The main catalogue-loader
	 * @return The path to the Catalogue if existing, otherwise null.
     */
	public Path getCatalogue() {
		FileChooser catalogueChoosePopup = new FileChooser();
		catalogueChoosePopup.setTitle("Choose Exercise");
		catalogueChoosePopup.getExtensionFilters().add( new FileChooser.ExtensionFilter("xml files", "*.xml"));
		File catalogueFile = catalogueChoosePopup.showOpenDialog(primaryStage);
		if(catalogueFile != null)
			return Paths.get(catalogueFile.getAbsolutePath());
		return null;
	}

	/**
	 * Main-Method
	 * @param args Commandline-parameter
     */
	public static void main(String[] args) {
		launch(args);
	}
}
