package de.hhu.propra16.avaders.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.util.converter.CurrencyStringConverter;

import javax.swing.text.View;

public class Red extends Phase {
	//Sets values to controls according to red-phase and records input
	@Override 
	public void setStates(TextArea userFieldRed, TextArea userFieldCode, Button stepBack, Button stepFurther, StackPane currentPhaseDisplay){
		listenTo(userFieldRed);
		userFieldRed.setEditable(true);
		ViewTools.enable(userFieldRed);
		ViewTools.disable(userFieldCode);
		ViewTools.disable(stepBack);
		ViewTools.enable(stepFurther, "Next");
	}
}
