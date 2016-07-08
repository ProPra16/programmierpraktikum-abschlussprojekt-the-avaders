package de.hhu.propra16.avaders.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
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
