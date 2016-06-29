package de.hhu.propra16.avaders;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

abstract class Phase {
	//save userinput from textarea
	public String userInput; 
	
	//this method will be overriden by subclasses Red,Green,Refactor to set appropriate states 
	public void setStates(TextArea userFieldRed, TextArea userFieldCode, Button stepBack, Button stepFurther){
	}
}
