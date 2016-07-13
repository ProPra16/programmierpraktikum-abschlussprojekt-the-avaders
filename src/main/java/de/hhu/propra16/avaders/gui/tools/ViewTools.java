package de.hhu.propra16.avaders.gui.tools;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ViewTools {
	//finished
	public static void enable(Node node){
		node.setVisible(true);
		node.setDisable(false);
	}

	public static void clearOutputAreas(TextArea... outputArea) {
		for(TextArea area : outputArea)
			area.setText("");
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

	public static void setUneditable(TextArea... outputAreas) {
		for(TextArea area : outputAreas)
			area.setEditable(false);
	}

	public static void enableExerciseTree(boolean status, HBox exercisesHead, TreeView<String> exercisesTree, AnchorPane exerciseTreeBase){
		if(status){
			exercisesTree.setMaxWidth(400);
			exercisesHead.setMaxWidth(400);
			exerciseTreeBase.setMaxWidth(400);
			exercisesTree.setMinWidth(225);
			exercisesHead.setMinWidth(225);
			exerciseTreeBase.setMinWidth(225);
		} else {
			exercisesTree.setMaxWidth(0);
			exercisesHead.setMaxWidth(0);
			exerciseTreeBase.setMaxWidth(0);
			exercisesTree.setMinWidth(0);
			exercisesHead.setMinWidth(0);
			exerciseTreeBase.setMinWidth(0);
		}
	}
}
