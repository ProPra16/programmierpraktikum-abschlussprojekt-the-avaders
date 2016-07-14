package de.hhu.propra16.avaders.gui.phases;

import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.gui.tools.ExerciseTools;
import de.hhu.propra16.avaders.gui.tools.FileTools;
import de.hhu.propra16.avaders.gui.tools.PathTools;
import de.hhu.propra16.avaders.gui.tools.ViewTools;
import de.hhu.propra16.avaders.gui.view.ButtonDisplay;
import de.hhu.propra16.avaders.gui.view.Display;
import de.hhu.propra16.avaders.logik.Step;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Sets controls, areas and labels for Welcome-phase
 */
public class Welcome extends Phase {
	/**
	 * {@inheritDoc}
     */
	public Welcome(TextArea userInputArea, TextArea phaseOutputArea, ButtonDisplay buttonDisplay, Label phase) {
		super(userInputArea, phaseOutputArea, buttonDisplay, phase);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStates() {
		ViewTools.editPhaseDisplay(phase, "WELCOME", Color.WHITE, Color.DARKORANGE);
		buttonDisplay.show(Step.WELCOME);
		userInputArea.setEditable(false);
	}

	/**
	 * {@inheritDoc}
	 * @return true
	 */
	@Override
	public boolean hasUnitTests() {
		return false;
	}

	/**
	 * Shows description and enabled extensions on the informationOutputArea depending on chosen exercise.
	 * @param item               Selected Item in the exercisestree
	 * @param exerciseCatalogue  The catalogue where all information of the exercises is hold in
     */
	public void setInformation(TreeItem<String> item, ExerciseCatalogue exerciseCatalogue) {
		Path descriptionPath = PathTools.getDescriptionPath(item);
		System.out.println(descriptionPath.toString());
		if(!Files.exists(descriptionPath)) {
			phaseOutputArea.setText("No description available");
			return;
		}
		String exerciseName = descriptionPath.getParent().getFileName().toString();
		String headMessage = "Exercise:\n" + exerciseName + "\n\nDescription:\n";
		String configMessage = Display.getConfigDisplay(ExerciseTools.getConfig(exerciseName, exerciseCatalogue));
		phaseOutputArea.setText(headMessage + FileTools.readFile(descriptionPath) + configMessage);
	}
}
