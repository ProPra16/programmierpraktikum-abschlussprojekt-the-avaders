package de.hhu.propra16.avaders.gui.tools;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Tools for handling showing, enabling, disabling and hiding controls and areas
 */
public class ViewTools {

	/**
	 * Clearing TextAreas
	 * @param outputArea Areas to clear
     */
	public static void clearOutputAreas(TextArea... outputArea) {
		for(TextArea area : outputArea)
			area.setText("");
	}

	/**
	 * Edits construct of StackPane with a Label as Node.
	 * @param labelOnStackPane The Label you want to set a Text on
	 * @param text             The text for the Label
	 * @param textFill         The color for the Text on the Label
	 * @param backgroundFill   The color of the background of the StackPane
     */
	public static void editPhaseDisplay(Label labelOnStackPane, String text, Color textFill, Color backgroundFill ){
		editLabel(labelOnStackPane, text, textFill);
		((StackPane) labelOnStackPane.getParent()).setBackground(new Background(new BackgroundFill(backgroundFill, null, null)));
	}

	/**
	 * Edits a Label with text
	 * @param label The Label to edit
	 * @param text  The text to set on the Label
	 * @param color The color for the text
     */
	private static void editLabel(Label label, String text, Color color){
		label.setTextFill(color);
		label.setText(text);
	}

	/**
	 * Makes an undetermined amount of TextAreas uneditable
	 * @param outputAreas The areas to be set uneditable
     */
	public static void setUneditable(TextArea... outputAreas) {
		for(TextArea area : outputAreas)
			area.setEditable(false);
	}
}
