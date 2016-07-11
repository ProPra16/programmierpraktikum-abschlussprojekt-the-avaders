package de.hhu.propra16.avaders.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Code extends Phase {
	//Sets values to controls according to green-phase  and records input
	@Override 
	public void setStates(TextArea userInputField, TextArea targetField, Button stepBack, Button stepFurther, Label currentPhase){
		listenTo(targetField);
		ViewTools.editPhaseDisplay(currentPhase, "CODE", Color.WHITE, Color.DARKGREEN);
		stepBack.setText("BACK");
		ViewTools.enable(stepBack);
		stepFurther.setText("NEXT");
		ViewTools.enable(stepFurther);
	}
}
