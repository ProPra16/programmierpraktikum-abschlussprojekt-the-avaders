package de.hhu.propra16.avaders.gui.phases;

import de.hhu.propra16.avaders.logik.Step;

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper to hold Phase-objects.
 */
public class Phases {
	private Map<Step,Phase> phases = new HashMap<>();

	/**
	 * {@inheritDoc}
     */
	public Phases(Welcome welcome, Test test, Code code, CodeRefactor codeRefactor, TestRefactor testRefactor){
		phases.put(Step.WELCOME, welcome);
		phases.put(Step.RED, test);
		phases.put(Step.GREEN, code);
		phases.put(Step.CODE_REFACTOR, codeRefactor);
		phases.put(Step.TEST_REFACTOR, testRefactor);
	}

	/**
	 * Setting view-states of class hold in phases-map
	 * @param step Depending on this the appropriate phase will be chosen to have its states set
     */
	public void setStates(Step step){
		phases.get(step).setStates();
	}

	/**
	 * Gets a hold phase according to given mode
	 * @param mode The mode for which you want to get the phase
	 * @return The phase according to the given mode
     */
	public Phase getPhase(Step mode) {return phases.get(mode);}

	/**
	 *  Gets phase according to Step.Welcome
	 * @return Welcome-Phase for setting up the Welcome-View
     */
	public Welcome getWelcome() {return (Welcome) phases.get(Step.WELCOME);}

}
