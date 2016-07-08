package de.hhu.propra16.avaders.gui;

import de.hhu.propra16.avaders.logik.Step;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Batman140 on 08.07.2016.
 */
public class Phases {
	Map<Step,Phase> phases = new HashMap<>();

	public Phases(Welcome welcome, Red red, Green green, CodeRefactor codeRefactor, TestRefactor testRefactor){
		phases.put(Step.WELCOME, welcome);
		phases.put(Step.RED, red);
		phases.put(Step.GREEN, green);
		phases.put(Step.CODE_REFACTOR, codeRefactor);
		phases.put(Step.TEST_REFACTOR, testRefactor);
	}

	public void setStates(Step step, TextArea userFieldRed, TextArea userFieldCode, Button stepBack, Button stepFurther){
		phases.get(step).setStates(userFieldRed, userFieldCode, stepBack, stepFurther);
	}
}
