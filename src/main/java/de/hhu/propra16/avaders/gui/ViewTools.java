package de.hhu.propra16.avaders.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Created by Batman140 on 08.07.2016.
 */
public class ViewTools {
	public static void enable(Node node){
		node.setVisible(true);
		node.setDisable(false);
	}

	public static void enable(Button button, String text){
		button.setVisible(true);
		button.setDisable(false);
		button.setText(text);
	}


	public static void disable(Node node){
		node.setVisible(false);
		node.setDisable(true);
	}
}
