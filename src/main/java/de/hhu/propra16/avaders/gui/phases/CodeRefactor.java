package de.hhu.propra16.avaders.gui.phases;

import de.hhu.propra16.avaders.gui.tools.ViewTools;
import de.hhu.propra16.avaders.gui.view.ButtonDisplay;
import de.hhu.propra16.avaders.logik.Step;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
/**
 * Sets controls, areas and labels for CodeRefactor-phase
 */
public class CodeRefactor extends Phase {

	/**
	 * {@inheritDoc}
     */
	public CodeRefactor(TextArea userInputArea, TextArea phaseOutputArea, ButtonDisplay buttonDisplay, Label phase) {
		super(userInputArea,phaseOutputArea,buttonDisplay,phase);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStates(){
		ViewTools.editPhaseDisplay(phase, "CODE-REFACTOR", Color.WHITE, Color.BLACK);
		buttonDisplay.show(Step.CODE_REFACTOR);
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
