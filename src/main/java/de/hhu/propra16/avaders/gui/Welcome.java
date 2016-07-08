package de.hhu.propra16.avaders.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

/**
 * Created by Batman140 on 08.07.2016.
 */
public class Welcome extends Phase{
	//Sets values to controls according to red-phase and records input
	@Override
	public void setStates(TextArea userFieldRed, TextArea userFieldCode, Button stepBack, Button stepFurther, StackPane currentPhaseDisplay){
		userFieldRed.setEditable(false);
		userFieldCode.setEditable(false);
		ViewTools.disable(currentPhaseDisplay);
		ViewTools.disable(stepBack);
		ViewTools.enable(stepFurther, "Start");
	}
}
