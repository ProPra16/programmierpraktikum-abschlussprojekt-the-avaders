package de.hhu.propra16.avaders.extensions;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;

public class BabystepsTest extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(new GridPane(),1,1));
		primaryStage.show();
		startTimer();
	}

	//@Test
	public void startTimer() throws Exception {
		TextArea textArea = new TextArea("test");
		Babysteps babysteps = new Babysteps(textArea);
		babysteps.startTimer(10);
		textArea.setText("ich bin anders");
		//assertEquals("test",textArea.getText());
		//assertEquals(false, textArea.isEditable());
		System.out.println(textArea.getText()+" re: "+babysteps.getRemainingSeconds());
		//if(!textArea.getText().equals("test")) throw new Exception("TextArea wurde nicht zurückgesetzt");
		//if(!textArea.isEditable()) throw new Exception("TextArea kann noch bearbeitet werden");
	}
}