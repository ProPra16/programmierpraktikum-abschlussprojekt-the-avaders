package de.hhu.propra16.avaders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
		GridPane root = FXMLLoader.load(getClass().getResource("applicationView.fxml"));
		primaryStage.setTitle("Test Driven Developement Trainer");
		
		
		Scene scene = new Scene(root, 300, 275);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
