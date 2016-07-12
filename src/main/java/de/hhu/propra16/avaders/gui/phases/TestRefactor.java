package de.hhu.propra16.avaders.gui.phases;

import de.hhu.propra16.avaders.gui.tools.ViewTools;
import de.hhu.propra16.avaders.gui.view.ButtonDisplay;
import de.hhu.propra16.avaders.logik.Step;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class TestRefactor extends Phase {

	public TestRefactor(TextArea userInputArea, TextArea phaseOutputArea, ButtonDisplay buttonDisplay, Label phase) {
		super(userInputArea,phaseOutputArea,buttonDisplay,phase);
	}
	//finished
	@Override
	public void setStates(){
		ViewTools.editPhaseDisplay(phase, "TEST-REFACTOR", Color.WHITE, Color.DARKCYAN);
		buttonDisplay.show(Step.TEST_REFACTOR);
		userInputArea.setEditable(true);
		phaseOutputArea.textProperty().bind(userInputArea.textProperty());
	}
}