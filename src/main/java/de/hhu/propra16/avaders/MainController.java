package de.hhu.propra16.avaders;

import de.hhu.propra16.avaders.gui.ExerciseFilePath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.nio.file.Path;

public class MainController {

	private static BorderPane mainBase;
	private        Path       exerciseFilePath;

	//excercise-menuItems
    @FXML private MenuItem newExercise;
    @FXML private MenuItem restart;
    
    //phase-switcher
    @FXML private Button stepBack;
    @FXML private Button stepFurther;
    
    //Userinformation about activated modes and time left due activated babysteps
    @FXML private Text activatedModes;
    @FXML private Text timeLeft;

    //input-areas for user
    @FXML private TextArea greenRefactor;
    @FXML private TextArea userFieldRed;
    @FXML private TextArea userFieldCode;

    //Handler
    @FXML void handleRestart(ActionEvent event)  {}
    @FXML void handlePrePhase(ActionEvent event) {}
    @FXML void handleNextPhase(ActionEvent event){}


    @FXML void handleNewExercise(ActionEvent event) {
        this.exerciseFilePath = new ExerciseFilePath().getExercise();
        userFieldRed.setEditable(false);
        userFieldCode.setDisable(false);
    }



}
