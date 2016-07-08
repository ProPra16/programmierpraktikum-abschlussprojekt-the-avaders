package de.hhu.propra16.avaders;

import de.hhu.propra16.avaders.catalogueLoader.ParserException;
import de.hhu.propra16.avaders.catalogueLoader.ExerciseCatalogueLoader;
import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.ExerciseTokenizer;
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
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.nio.file.Path;


public class MainController {
	private Main              main;
	private ExerciseCatalogue exerciseCatalogue;
	private Phases            phases;
	private Logik             logic;

	//menuItems
    @FXML private MenuItem newCatalogue;
    @FXML private MenuItem restart;
	@FXML private MenuItem quit;
    
    //phase-switcher
	@FXML private Button start;
    @FXML private Button stepBack;
    @FXML private Button stepFurther;
    
    //User-information about activated modes and time left due activated 'babysteps'
    @FXML private Label activatedModes;
	@FXML private ProgressBar progress;
    @FXML private Label timeLeft;
	@FXML private Label timeLeftTitle;
	@FXML private Label currentPhaseLabel;
	@FXML private StackPane currentPhaseDisplay;

    //areas for user
	@FXML private TableView exerciseTable;
	@FXML private TableColumn exerciseColumn;
	@FXML private TableColumn classColumn;
	@FXML private TableColumn testColumn;
    @FXML private TextArea greenRefactor;
    @FXML private TextArea userFieldRed;
    @FXML private TextArea userFieldCode;

	@FXML private Tab      consoleTab;
	@FXML private Tab      codeTab;
	@FXML private Tab      refactorTab;
	@FXML private Tab      testTab;
	@FXML private TextArea testInputArea;
	@FXML private TextArea codeInputArea;
	@FXML private TextArea refactorInputArea;
	@FXML private TextArea userInputField;


	//Handler
    @FXML void handleStart(ActionEvent event)  {}
	@FXML void handleProgress(ActionEvent event)  {}
	@FXML void handleQuit(ActionEvent event)  {}

	//initializer
	@FXML public void initialize(){
		this.phases = new Phases(new Welcome(), new Red(), new Green(), new CodeRefactor(), new TestRefactor());
		this.logic  = initLogic();
		setupStart();
	}


	@FXML void handlePrePhase(ActionEvent event) {
		/*logic.abbrechen();
		phases.setStates(logic.getSchritt(), userFieldRed, userFieldCode, stepBack, stepFurther, currentPhaseDisplay);*/
	}

    @FXML void handleNextPhase(ActionEvent event){
		/*Exercise current = exerciseCatalogue.getExercise(1);
		logic.weiter(new CompilationUnit(current.getTestName(0), current.getTestTemplates(0), true ));
		phases.setStates(logic.getSchritt(), userFieldRed, userFieldCode, stepBack, stepFurther, currentPhaseDisplay);*/
	}

    @FXML void handleNewCatalogue(ActionEvent event) {
		Path cataloguePath = main.getExercise();
		try {
			FileReader           fileReader           = new FileReader(cataloguePath);
			ExerciseTokenizer exerciseTokenizer = new XMLExerciseTokenizer(fileReader); //able to read tokens out of file
			ExerciseCatalogue    exerciseCatalogue    = new ExerciseCatalogue(); //empty catalogue
			ExerciseCatalogueLoader xmlExerciseLoader    = new ExerciseCatalogueLoader(exerciseTokenizer, exerciseCatalogue);
			this.exerciseCatalogue = xmlExerciseLoader.loadCatalogue();
		} catch (IOException | ParserException | TokenException | SamePropertyTwiceException e) {
			if(e instanceof ParserException) {
				System.out.println("Caught null from main.getExercise: No file selected");
				return;
			}
			e.printStackTrace();
		}

		/*Exercise exercise = exerciseCatalogue.getExercise(1);
		this.userFieldCode.setText(exercise.getTestTemplates(0));
		this.userFieldCode.setText(exercise.getClassTemplate(0));
		this.activatedModes.setText(getModes(exercise.getExerciseConfig()));
		setTime(exercise);*/
	}

	public void setMain(Main main){
		this.main = main;
	}





	//HelpMethods
	private void setupStart(){
		phases.setStates(Step.WELCOME,  userFieldRed, userFieldCode, stepBack, stepFurther, currentPhaseLabel);
		ViewTools.disable(stepBack);
		ViewTools.disable(stepFurther);
		ViewTools.disable(timeLeftTitle);
		ViewTools.disable(timeLeft);
		ViewTools.disable(activatedModes);
	}

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
			ViewTools.enable(timeLeft, "m:ss");
		}
	}
}
