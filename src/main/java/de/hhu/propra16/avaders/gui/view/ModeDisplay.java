package de.hhu.propra16.avaders.gui.view;

import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;


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

	//take exconf
	public void set(ExerciseConfig config){
		List<String> modes = new ArrayList<>();
		activatedModes.setText("");
		if(config.isBabySteps()) {
			timeLeft.setText("" + config.getBabyStepsTime());
			modes.add("BabySteps");
		}
		if(config.isTimeTracking())
			modes.add("TimeTracking");
		if(modes.size() == 0){
			activatedModes.setText("<None>");
			return;
		}
		if(modes.size() == 1){
			activatedModes.setText(modes.get(0));
			return;
		}
		activatedModes.setText(modes.get(0));
		for( int i = 1; i < modes.size(); i++)
			activatedModes.setText(activatedModes.getText() + ", " + modes.get(i));
	}


	public void enableTime(boolean state, ExerciseConfig exerciseConfig) {
		if (exerciseConfig.isBabySteps()) {
			timeLeftTitle.setVisible(state);
			timeLeft.setVisible(state);
		}
	}
	public void enableActiveModes(boolean state, ExerciseConfig exerciseConfig){
		if(exerciseConfig.isTimeTracking())
			activatedModes.setVisible(state);
	}
}
