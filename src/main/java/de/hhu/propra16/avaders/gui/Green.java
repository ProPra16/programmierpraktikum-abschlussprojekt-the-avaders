package de.hhu.propra16.avaders.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import javax.swing.text.View;

public class Green extends Phase {
	//Sets values to controls according to green-phase  and records input
	@Override 
	public void setStates(TextArea userFieldRed, TextArea userFieldCode, Button stepBack, Button stepFurther, StackPane currentPhaseDisplay){
		listenTo(userFieldCode);
		userFieldRed.setEditable(false);
		ViewTools.enable(userFieldCode);
		ViewTools.enable(stepBack, "Back to Test");
		ViewTools.enable(stepFurther, "Next");
	}
}
