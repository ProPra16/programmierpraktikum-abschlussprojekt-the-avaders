package de.hhu.propra16.avaders.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Red extends Phase {
	//Sets values to controls according to red-phase and records input
	@Override 
	public void setStates(TextArea userFieldRed, TextArea userFieldCode, Button stepBack, Button stepFurther){
		listenTo(userFieldRed);
		userFieldRed.setDisable(false);
		userFieldCode.setDisable(true);
		stepBack.setVisible(false);
		stepFurther.setText("Green");
	}
}
