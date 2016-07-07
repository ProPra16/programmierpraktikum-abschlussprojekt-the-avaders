package de.hhu.propra16.avaders.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

abstract class Phase {
	//save userinput from textarea
	public String userInput; 
	
	//this method will be overriden by subclasses Red,Green,Refactor to set appropriate states 
	
	public void setStates(TextArea userFieldRed, TextArea userFieldCode, Button stepBack, Button stepFurther){
	}
	
	//listener for subclasses, to save userinput in textarea in this object. available in variable -> userInput
	public void listenTo(TextArea textArea){
		textArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				userInput = newValue;
			}
		});
	}		
}
