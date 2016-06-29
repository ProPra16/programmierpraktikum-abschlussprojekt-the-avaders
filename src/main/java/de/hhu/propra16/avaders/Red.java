package de.hhu.propra16.avaders;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Red extends Phase {
	
	//Sets values to controls according to red-phase
	@Override 
	public void setStates(TextArea userFieldRed, TextArea userFieldCode, Button stepBack, Button stepFurther){
		userFieldRed.setDisable(false);
		userFieldCode.setDisable(true);
		stepBack.setVisible(false);
		stepFurther.setText("Green");
	}
}
