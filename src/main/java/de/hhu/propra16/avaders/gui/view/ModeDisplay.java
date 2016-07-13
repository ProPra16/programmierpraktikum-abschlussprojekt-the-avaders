package de.hhu.propra16.avaders.gui.view;

import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;
import de.hhu.propra16.avaders.gui.tools.ViewTools;
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
		if(config.isBabySteps()) {
			setTime(config);
			modes.add("BabySteps");
		}
		if(config.isTimeTracking())
			modes.add("TimeTracking");
		if(config.isAtdd())
			modes.add("ATDD");
		if(modes.size() == 0){
			activatedModes.setText("<None>");
			return;
		}
		for(String mode : modes)
			activatedModes.setText(activatedModes.getText() + "  " + mode);
		activatedModes.setText(activatedModes.getText().replaceAll("  ", ", "));
	}

	public void setTime(ExerciseConfig config){
		if(config.isBabySteps()){
			ViewTools.enable(timeLeftTitle);
			ViewTools.enable(timeLeft, "" + config.getBabyStepsTime());
		}
	}

}
