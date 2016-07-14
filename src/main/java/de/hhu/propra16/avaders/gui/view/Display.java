package de.hhu.propra16.avaders.gui.view;

import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;

/**
 * Defines output on InformationArea for  mode description when selecting an exercises item in treeview
 */
public class Display {
	/**
	 * Loads information of config into a String
	 * @param config The configuration where the information will be taken from
	 * @return A String with the information of the config
     */
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

}
