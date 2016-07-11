package de.hhu.propra16.avaders.gui;

import javafx.scene.control.TextInputControl;

/**
 * Created by Batman140 on 11.07.2016.
 */
public class BindTools {
	public static void setUniqueBinding(TextInputControl source, TextInputControl target, TextInputControl... toUnbind){
		unbindAreas(toUnbind);
		target.textProperty().bind(source.textProperty());
	}

	public  static void unbindAreas(TextInputControl... toUnbind){
		for(TextInputControl node : toUnbind)
			node.textProperty().unbind();
	}
}

