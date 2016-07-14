package de.hhu.propra16.avaders.gui.phases;

import de.hhu.propra16.avaders.gui.view.ButtonDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import vk.core.api.CompilationUnit;

/**
 * Abstraction of phases Welcome,Test,Code,CodeRefactor and TestRefactor
 * Sets main controls, areas and labels for phases
 */
abstract public class Phase {
	TextArea      userInputArea;
	TextArea      phaseOutputArea;
	ButtonDisplay buttonDisplay;
	Label         phase;

	/**
	 * @param userInputArea   Area where the user can edit classes
	 * @param phaseOutputArea Area where the in userInputArea edited class will be loaded to
	 * @param buttonDisplay   Class where the buttons will be set depending on current phase
     * @param phase           Label for the phase. Appearance depends on current phase.
     */
	public Phase(TextArea userInputArea, TextArea phaseOutputArea, ButtonDisplay buttonDisplay, Label phase){
		this.userInputArea   = userInputArea;
		this.phaseOutputArea = phaseOutputArea;
		this.buttonDisplay   = buttonDisplay;
		this.phase           = phase;
		this.phaseOutputArea.setEditable(false);
	}

	/**
	 * Indicates whether this phase handles with Unit-Tests or not
	 * @return false If class of current phase is no Test-Class otherwise true
     */
	abstract public boolean hasUnitTests();

	/**
	 * Sets up Phase-Label, shows buttons according to this phase and gives write-access for user-input
	 */
	abstract public void setStates();
}
