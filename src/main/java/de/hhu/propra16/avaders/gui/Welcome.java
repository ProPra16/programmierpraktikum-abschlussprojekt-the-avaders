package de.hhu.propra16.avaders.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Created by Batman140 on 08.07.2016.
 */
public class Welcome extends Phase{
	//Sets values to controls according to red-phase and records input
	@Override
	public void setStates(TextArea userInputField, TextArea targetField, Button stepBack, Button stepFurther, Label currentPhase){
		ViewTools.editPhaseDisplay(currentPhase, "WELCOME", Color.WHITE, Color.DARKORANGE);
	}
}
