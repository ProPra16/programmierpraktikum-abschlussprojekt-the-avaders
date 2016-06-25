package de.hhu.propra16.avaders.extensions;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
//import org.junit.Test;

//import static org.junit.Assert.*;

public class BabystepsTest extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane gridPane = new GridPane();
		primaryStage.setScene(new Scene(gridPane,10,10));
		primaryStage.show();
		startTimer(gridPane);
	}

	//@Test
	public void startTimer(GridPane gp) throws Exception {
		TextArea textArea = new TextArea("test");
		Button button = new Button("start");
		Babysteps babysteps = new Babysteps(textArea);
		button.setOnAction(evt -> babysteps.startTimer(10));
		gp.add(textArea,0,0);
		gp.add(button,0,1);
		textArea.setText("ich bin anders");
		//assertEquals("test",textArea.getText());
		//assertEquals(false, textArea.isEditable());
		System.out.println(textArea.getText()+" re: "+babysteps.getRemainingSeconds());
		if(!textArea.getText().equals("test")) throw new Exception("TextArea wurde nicht zurückgesetzt");
		if(!textArea.isEditable()) throw new Exception("TextArea kann noch bearbeitet werden");
	}
}
