package de.hhu.propra16.avaders;

import de.hhu.propra16.avaders.catalogueLoader.ParserException;
import de.hhu.propra16.avaders.catalogueLoader.XMLExerciseLoader;
import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.FileReader;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.gui.*;
import de.hhu.propra16.avaders.konfig.KonfigWerte;
import de.hhu.propra16.avaders.logik.Logik;
import de.hhu.propra16.avaders.logik.Step;
import de.hhu.propra16.avaders.testen.Tester;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import vk.core.api.CompilationUnit;

import java.io.IOException;
import java.nio.file.Path;



public class MainController {
	private Main              main;
	private ExerciseCatalogue exerciseCatalogue;
	private Phases            phases;
	private Logik             logic;

	//exercise-menuItems
    @FXML private MenuItem newExercise;
    @FXML private MenuItem restart;
    
    //phase-switcher
    @FXML private Button stepBack;
    @FXML private Button stepFurther;
    
    //User-information about activated modes and time left due activated 'babysteps'
    @FXML private Label activatedModes;
    @FXML private Label timeLeft;
	@FXML private Label timeLeftTitle;
	@FXML private Label currentPhaseLabel;
	@FXML private StackPane currentPhaseDisplay;

    //input-areas for user
    @FXML private TextArea greenRefactor;
    @FXML private TextArea userFieldRed;
    @FXML private TextArea userFieldCode;

	//Handler
    @FXML void handleRestart(ActionEvent event)  {}
    @FXML void handlePrePhase(ActionEvent event) {
		logic.abbrechen();
		phases.setStates(logic.getSchritt(), userFieldRed, userFieldCode, stepBack, stepFurther);
	}

    @FXML void handleNextPhase(ActionEvent event){
		Exercise current = exerciseCatalogue.getExercise(1);
		logic.weiter(new CompilationUnit(current.getTestName(0), current.getTestTemplates(0), true ));
		phases.setStates(logic.getSchritt(), userFieldRed, userFieldCode, stepBack, stepFurther);
	}

    @FXML void handleNewExercise(ActionEvent event) {
		Path exercisePath = main.getExercise();
		try {
			FileReader           fileReader           = new FileReader(exercisePath);
			XMLExerciseTokenizer xmlExerciseTokenizer = new XMLExerciseTokenizer(fileReader); //able to read tokens out of file
			ExerciseCatalogue    exerciseCatalogue    = new ExerciseCatalogue(); //empty catalogue
			XMLExerciseLoader    xmlExerciseLoader    = new XMLExerciseLoader(xmlExerciseTokenizer, exerciseCatalogue);
			this.exerciseCatalogue = xmlExerciseLoader.loadExerciseCatalogue();
		} catch (IOException | ParserException | TokenException | SamePropertyTwiceException e) {
			if(e instanceof ParserException) {
				System.out.println("Caught null from main.getExercise: No file selected");
				return;
			}
			e.printStackTrace();
		}

		Exercise exercise = exerciseCatalogue.getExercise(1);
		this.userFieldRed.setText(exercise.getTestTemplates(0));
		this.activatedModes.setText(getModes(exercise.getExerciseConfig()));
		setTime(exercise);
		phases.setStates(logic.getSchritt(), userFieldRed, userFieldCode, stepBack, stepFurther);
	}



	@FXML
	public void initialize(){
		this.phases = new Phases(new Welcome(), new Red(), new Green(), new CodeRefactor(), new TestRefactor());
		this.logic  = initLogic();
	}

	public void setMain(Main main){
		this.main = main;
	}





	//HelpMethods
	private Logik initLogic(){
		Tester      tester      = new Tester();
		KonfigWerte konfigWerte = new KonfigWerte();
		return new Logik(tester, konfigWerte);
	}

	private String getModes(ExerciseConfig config){
		String modesDisplay = "";
		if(config.isBabySteps())
			modesDisplay += "Babysteps, ";
		if(config.isTimeTracking())
			modesDisplay += "Tracking, ";
		if(config.isAtdd())
			modesDisplay += "ATDD, ";
		if(modesDisplay.contentEquals(""))
			return "<None>";
		System.out.println(modesDisplay);
		return modesDisplay.substring(0, modesDisplay.length() - 2);
	}

	private void setTime(Exercise exercise){
		if(exercise.getExerciseConfig().isBabySteps()){
			ViewTools.enable(timeLeftTitle);
			ViewTools.enable(timeLeft, "<Time>");
		}
	}
}
