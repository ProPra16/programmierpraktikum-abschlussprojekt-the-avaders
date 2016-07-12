package de.hhu.propra16.avaders.gui.phases;

import de.hhu.propra16.avaders.gui.view.ButtonDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

abstract public class Phase {
	TextArea      userInputArea;
	TextArea      phaseOutputArea;
	ButtonDisplay buttonDisplay;
	Label         phase;

	public Phase(TextArea userInputArea, TextArea phaseOutputArea, ButtonDisplay buttonDisplay, Label phase){
		this.userInputArea   = userInputArea;
		this.phaseOutputArea = phaseOutputArea;
		this.buttonDisplay   = buttonDisplay;
		this.phase           = phase;
		this.phaseOutputArea.setEditable(false);
	}

	//finished
	abstract public void setStates();
}
