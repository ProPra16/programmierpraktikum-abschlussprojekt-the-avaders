package de.hhu.propra16.avaders;

import de.hhu.propra16.avaders.catalogueLoader.ParserException;
import de.hhu.propra16.avaders.catalogueLoader.XMLExerciseLoader;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.FileReader;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.konfig.KonfigWerte;
import de.hhu.propra16.avaders.logik.Logik;
import de.hhu.propra16.avaders.testen.Tester;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Path;

public class MainController {
	private Main       main;
	private FileReader fileReader;
	private Logik      logic;

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
		Path exercisePath = main.getExercise();
		try {
			FileReader fileReader = new FileReader(exercisePath);
			XMLExerciseTokenizer xmlExerciseTokenizer = new XMLExerciseTokenizer(fileReader); //able to read tokens out of file
			ExerciseCatalogue    exerciseCatalogue    = new ExerciseCatalogue(); //empty catalogue
			XMLExerciseLoader    xmlExerciseLoader    = new XMLExerciseLoader(xmlExerciseTokenizer, exerciseCatalogue);
		} catch (IOException | ParserException | TokenException | SamePropertyTwiceException e) {
			e.printStackTrace();
			if(e instanceof ParserException)
				System.out.println("Caught null from main.getExercise: No file selected");
		}
	}

	@FXML
	public void initialize(){
		Tester      tester      = new Tester();
		KonfigWerte konfigWerte = new KonfigWerte();
		Logik       logic       = new Logik(tester, konfigWerte);
	}

	public void setMain(Main main){
		this.main = main;
	}



}
