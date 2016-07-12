package de.hhu.propra16.avaders.gui.view;

import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;


public class Display {
	//finished
	public static String getConfigDisplay(ExerciseConfig config){
		String  configMessage =   "\nExtensions:\n";
		if(config.isTimeTracking())
			configMessage += "->Time-Tracking\n";
		if(config.isAtdd())
			configMessage += "->ATDD\n";
		if(config.isBabySteps())
			configMessage += "->Babysteps, " + config.getBabyStepsTime() + " sec\n";
		return configMessage;
	}

	//TODO set assignment
	public static String getModesDisplay(ExerciseConfig config){
		String modesDisplay = "";
		if(config.isBabySteps())
			modesDisplay += "Babysteps, ";
		if(config.isTimeTracking())
			modesDisplay += "Tracking, ";
		if(config.isAtdd())
			modesDisplay += "ATDD, ";
		if(modesDisplay.contentEquals(""))
			return "<None>";
		System.out.println(modesDisplay);
		return modesDisplay.substring(0, modesDisplay.length() - 2);
	}

}
