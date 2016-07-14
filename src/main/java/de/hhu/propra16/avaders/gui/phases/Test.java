package de.hhu.propra16.avaders.gui.phases;


import de.hhu.propra16.avaders.gui.tools.ViewTools;
import de.hhu.propra16.avaders.gui.view.ButtonDisplay;
import de.hhu.propra16.avaders.logik.Step;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

/**
 * Sets controls, areas and labels for test-phase
 */
public class Test extends Phase {

	/**
	 * {@inheritDoc}
	 * */
	public Test(TextArea userInputArea, TextArea phaseOutputArea, ButtonDisplay buttonDisplay, Label phase) {
		super(userInputArea,phaseOutputArea,buttonDisplay,phase);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override 
	public void setStates(){
		ViewTools.editPhaseDisplay(phase, "TEST", Color.WHITE, Color.DARKRED);
		buttonDisplay.show(Step.RED);
		userInputArea.setEditable(true);
	}

	/**
	 * {@inheritDoc}
	 * @return true
	 */
	@Override
	public boolean hasUnitTests(){
		return true;
	}
}
