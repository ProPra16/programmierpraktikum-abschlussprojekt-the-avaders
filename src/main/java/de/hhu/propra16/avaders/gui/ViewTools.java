package de.hhu.propra16.avaders.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;

/**
 * Created by Batman140 on 08.07.2016.
 */
public class ViewTools {
	public static void enable(Node node){
		node.setVisible(true);
		node.setDisable(false);
	}

	public static void editPhaseDisplay(Label labelOnStackPane, String text, Color textFill, Color backgroundFill ){
		editLabel(labelOnStackPane, text, textFill);
		((StackPane) labelOnStackPane.getParent()).setBackground(new Background(new BackgroundFill(backgroundFill, null, null)));
	}

	public static void editLabel(Label label, String text, Color color){
		label.setTextFill(color);
		label.setText(text);
	}

	public static void setEditable(boolean state, TextInputControl... nodes){
		Arrays.stream(nodes).forEach(node -> {
			node.setEditable(state);
		});
	}

	public static <T extends Labeled> void enable(T labeled, String text){
		labeled.setVisible(true);
		labeled.setDisable(false);
		labeled.setText(text);
	}


	public static void disable(Node node){
		node.setVisible(false);
		node.setDisable(true);
	}
}
