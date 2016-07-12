package de.hhu.propra16.avaders.gui.view;

import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.gui.tools.ViewTools;
import javafx.scene.control.Label;


public class ModeDisplay {
	private Label timeLeft;
	private Label timeLeftTitle;
	private Label activatedModes;

	public ModeDisplay(Label activatedModes, Label timeLeftTitle, Label timeLeft){
		this.activatedModes = activatedModes;
		this.timeLeftTitle  = timeLeftTitle;
		this.timeLeft       = timeLeft;

		this.timeLeftTitle.setVisible(false);
		this.timeLeft.setVisible(false);
		this.activatedModes.setVisible(false);
	}

	public void setTime(Exercise exercise){
		if(exercise.getExerciseConfig().isBabySteps()){
			ViewTools.enable(timeLeftTitle);
			ViewTools.enable(timeLeft, "" + exercise.getExerciseConfig().getBabyStepsTime());
		}
	}

}
