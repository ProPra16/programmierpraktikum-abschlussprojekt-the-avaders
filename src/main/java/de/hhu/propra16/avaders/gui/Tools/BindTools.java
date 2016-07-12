package de.hhu.propra16.avaders.gui.tools;

import javafx.scene.control.TextInputControl;

public class BindTools {
	//finished
	public  static void unbindAreas(TextInputControl... toUnbind){
		for(TextInputControl node : toUnbind)
			node.textProperty().unbind();
	}
}

