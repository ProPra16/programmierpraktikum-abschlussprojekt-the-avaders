package de.hhu.propra16.avaders.gui.tools;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ViewTools {
	//finished
	public static void enable(Node node){
		node.setVisible(true);
		node.setDisable(false);
	}

	//finished
	public static void showAreas(TextInputControl... nodes){
		for(TextInputControl node : nodes) {
			node.setVisible(true);
			node.setDisable(false);
			node.setEditable(false);
		}
	}

	//finished
	public static void hideNode(Node node){
		node.setVisible(false);
		node.setDisable(true);
	}

	//finished
	public static <T extends Labeled> void enable(T labeled, String text){
		labeled.setVisible(true);
		labeled.setDisable(false);
		labeled.setText(text);
	}

	//finished
	public static void editPhaseDisplay(Label labelOnStackPane, String text, Color textFill, Color backgroundFill ){
		editLabel(labelOnStackPane, text, textFill);
		((StackPane) labelOnStackPane.getParent()).setBackground(new Background(new BackgroundFill(backgroundFill, null, null)));
	}

	//finished
	private static void editLabel(Label label, String text, Color color){
		label.setTextFill(color);
		label.setText(text);
	}
}
