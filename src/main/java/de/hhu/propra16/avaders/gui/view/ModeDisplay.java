package de.hhu.propra16.avaders.gui.view;

import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for handling the modesLabels
 */
public class ModeDisplay {
	private Label timeLeft;
	private Label timeLeftTitle;
	private Label activatedModes;

	/**
	 * Constructor initializes the labels and hides the by default
	 * @param activatedModes
	 * @param timeLeftTitle
	 * @param timeLeft
     */
	public ModeDisplay(Label activatedModes, Label timeLeftTitle, Label timeLeft){
		this.activatedModes = activatedModes;
		this.timeLeftTitle  = timeLeftTitle;
		this.timeLeft       = timeLeft;

		this.timeLeftTitle.setVisible(false);
		this.timeLeft.setVisible(false);
		this.activatedModes.setVisible(false);
	}

	/**
	 * According to states given in ExerciseConfig, the texts of the Labels will be set.
	 * The time-Label will be unbind as well
	 * @param config The configuration with the given states of the current exercise
     */
	public void set(ExerciseConfig config){
		List<String> modes = new ArrayList<>();
		activatedModes.setText("");
		if(config.isBabySteps()) {
			timeLeft.textProperty().unbind();
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

	/**
	 * En-or disables the time Labels depending on whether the extension BabySteps is activated or not
	 * @param state           True for switching on and false for switching off
	 * @param exerciseConfig  The configuration which defines if babySteps is enabled or not
     */
	public void enableTime(boolean state, ExerciseConfig exerciseConfig) {
		if (exerciseConfig.isBabySteps()) {
			timeLeftTitle.setVisible(state);
			timeLeft.setVisible(state);
		}
	}

	/**
	 * En-or disables the modes Label depending on which modes are activated
	 * @param state           True for switching on and false for switching off
	 * @param exerciseConfig  The configuration which defines which modes are enabled
     */
	public void enableActiveModes(boolean state, ExerciseConfig exerciseConfig){
		if(exerciseConfig.isTimeTracking())
			activatedModes.setVisible(state);
	}
}
