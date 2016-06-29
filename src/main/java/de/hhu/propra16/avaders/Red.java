package de.hhu.propra16.avaders;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Red extends Phase {
	
	@Override 
	public void setStates(TextArea redPhase, TextArea codePhase, Button prePhase, Button nextPhase){
		redPhase.setDisable(false);
		codePhase.setDisable(true);
		prePhase.setVisible(false);
		nextPhase.setText("Green");
	}
}
