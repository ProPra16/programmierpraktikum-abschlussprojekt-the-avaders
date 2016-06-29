package de.hhu.propra16.avaders;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Green extends Phase {
	
	@Override 
	public void setStates(TextArea redPhase, TextArea codePhase, Button prePhase, Button nextPhase){
		redPhase.setDisable(true);
		codePhase.setDisable(false);
		prePhase.setVisible(true);
		prePhase.setText("Red");
		nextPhase.setText("Refactor");
	}
}
