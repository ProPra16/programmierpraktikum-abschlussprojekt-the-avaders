package de.hhu.propra16.avaders;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Refactor extends Phase {

	//Sets values to controls according to refactor-phase  and records input
	@Override 
	public void setStates(TextArea userFieldRed, TextArea userFieldCode, Button stepBack, Button stepFurther){
		listenTo(userFieldCode);
		userFieldRed.setDisable(true);
		userFieldCode.setDisable(false);
		stepBack.setVisible(true);
		stepBack.setText("Green");
		stepFurther.setText("Finish");
	}
}
