package de.hhu.propra16.avaders.gui.phases;

import de.hhu.propra16.avaders.logik.Step;

import java.util.HashMap;
import java.util.Map;

public class Phases {
	private Map<Step,Phase> phases = new HashMap<>();

	public Phases(Welcome welcome, Test test, Code code, CodeRefactor codeRefactor, TestRefactor testRefactor){
		phases.put(Step.WELCOME, welcome);
		phases.put(Step.RED, test);
		phases.put(Step.GREEN, code);
		phases.put(Step.CODE_REFACTOR, codeRefactor);
		phases.put(Step.TEST_REFACTOR, testRefactor);
	}

	//finished
	public void setStates(Step step){
		phases.get(step).setStates();
	}
	public Phase   getPhase(Step mode) {return phases.get(mode);}
	public Welcome getWelcome() {return (Welcome) phases.get(Step.WELCOME);}

}
