package de.hhu.propra16.avaders.gui.phases;

import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.gui.tools.ExerciseTools;
import de.hhu.propra16.avaders.gui.tools.FileTools;
import de.hhu.propra16.avaders.gui.tools.PathTools;
import de.hhu.propra16.avaders.gui.tools.ViewTools;
import de.hhu.propra16.avaders.gui.view.ButtonDisplay;
import de.hhu.propra16.avaders.gui.view.Display;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;

import java.nio.file.Files;
import java.nio.file.Path;


public class Welcome extends Phase {

	public Welcome(TextArea userInputArea, TextArea phaseOutputArea, ButtonDisplay buttonDisplay, Label phase) {
		super(userInputArea, phaseOutputArea, buttonDisplay, phase);
	}

	//finished
	@Override
	public void setStates() {
		userInputArea.setEditable(false);
		ViewTools.editPhaseDisplay(phase, "WELCOME", Color.WHITE, Color.DARKORANGE);
	}

	//finished
	public void setInformation(TreeItem<String> item, ExerciseCatalogue exerciseCatalogue) {
		Path descriptionPath = PathTools.getDescriptionPath(item);
		if (Files.exists(descriptionPath)) {
			String exerciseName = descriptionPath.getParent().getFileName().toString();
			String headMessage = "Exercise:\n" + exerciseName + "\n\nDescription:\n";
			String configMessage = Display.getConfigDisplay(ExerciseTools.getConfig(exerciseName, exerciseCatalogue));
			phaseOutputArea.setText(headMessage + FileTools.readFile(descriptionPath) + configMessage);
		}
		phaseOutputArea.setText("No description available");
	}
}
