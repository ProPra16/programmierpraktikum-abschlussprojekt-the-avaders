package de.hhu.propra16.avaders;

import de.hhu.propra16.avaders.konfig.KonfigWerte;
import de.hhu.propra16.avaders.logik.Logik;
import de.hhu.propra16.avaders.testen.Tester;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.nio.file.Path;

public class MainController {
	private Main main;
	private Path exerciseFilePath;

	//exercise-menuItems
    @FXML private MenuItem newExercise;
    @FXML private MenuItem restart;
    
    //phase-switcher
    @FXML private Button stepBack;
    @FXML private Button stepFurther;
    
    //User-information about activated modes and time left due activated 'babysteps'
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
        this.exerciseFilePath = main.getExercise();
        userFieldRed.setEditable(false);
        userFieldCode.setDisable(false);
    }

	@FXML
	public void initialize(){
		//build up logic
		Tester      tester      = new Tester();
		KonfigWerte konfigWerte = new KonfigWerte();
		Logik       logic       = new Logik(tester, konfigWerte);
	}

	public void setMain(Main main){
		this.main = main;
	}



}
