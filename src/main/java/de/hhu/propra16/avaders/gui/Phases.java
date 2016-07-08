package de.hhu.propra16.avaders.gui;

import de.hhu.propra16.avaders.logik.Step;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Batman140 on 08.07.2016.
 */
public class Phases {
	Map<Step,Phase> phases = new HashMap<>();

	public Phases(Red red, Green green, Refactor refactor1, Refactor refactor2){
		phases.put(Step.RED, red);
		phases.put(Step.GREEN, green);
		phases.put(Step.REFACTOR1, refactor1);
		phases.put(Step.REFACTOR2, refactor2);
	}

	public void setStates(Step step, TextArea userFieldRed, TextArea userFieldCode, Button stepBack, Button stepFurther){
		phases.get(step).setStates(userFieldRed, userFieldCode, stepBack, stepFurther);
	}
}
