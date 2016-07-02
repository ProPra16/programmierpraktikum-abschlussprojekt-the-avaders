package de.hhu.propra16.avaders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage      primaryStage;
	private static BorderPane mainBase;
	
	public static BorderPane getMainBase() { return mainBase;}

	@Override
	public void start(Stage stage) throws Exception{
		primaryStage = stage;
		mainBase = FXMLLoader.load(getClass().getResource("applicationView.fxml"));
		primaryStage.setTitle("Test Driven Developement Trainer");
		mainBase.getCenter().setVisible(false);
		mainBase.getBottom().setVisible(false);
		
		
		Scene scene = new Scene(mainBase, 600, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getPrimaryStage(){
		return primaryStage;
	}
}
