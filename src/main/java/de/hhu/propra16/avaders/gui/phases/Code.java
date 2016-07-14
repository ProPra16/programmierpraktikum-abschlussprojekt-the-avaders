package de.hhu.propra16.avaders.gui.phases;

import de.hhu.propra16.avaders.gui.tools.ViewTools;
import de.hhu.propra16.avaders.gui.view.ButtonDisplay;
import de.hhu.propra16.avaders.logik.Step;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
/**
 * Sets controls, areas and labels for Code-phase
 */
public class Code extends Phase {

	/**
	 * {@inheritDoc}
	 */
	public Code(TextArea userInputArea, TextArea phaseOutputArea, ButtonDisplay buttonDisplay, Label phase) {
		super(userInputArea,phaseOutputArea,buttonDisplay,phase);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStates(){
		ViewTools.editPhaseDisplay(phase, "CODE", Color.WHITE, Color.DARKGREEN);
		buttonDisplay.show(Step.GREEN);
		userInputArea.setEditable(true);
	}

	/**
	 * {@inheritDoc}
	 * @return false
     */
	@Override
	public boolean hasUnitTests(){
		return false;
	}
}
