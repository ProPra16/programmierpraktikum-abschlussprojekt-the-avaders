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
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


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
	@FXML private Tab      codeRefactorTab;
	@FXML private Tab      testRefactorTab;
	@FXML private Tab      testTab;
	@FXML private TextArea consoleInputArea;
	@FXML private TextArea testInputArea;
	@FXML private TextArea codeInputArea;
	@FXML private TextArea codeRefactorInputArea;
	@FXML private TextArea testRefactorInputArea;
	@FXML private TextArea userInputField;

	//ExercisesTree
	@FXML private TreeView<String> exercisesTree;

	//Handler
    @FXML void handleStart(ActionEvent event)  {}
	@FXML void handleProgress(ActionEvent event)  {}

	@FXML void handleTreeViewMouseClicked(MouseEvent event) {
		if(event.getButton() == MouseButton.PRIMARY){
			TreeItem<String> item = exercisesTree.getSelectionModel().getSelectedItem();
			System.out.println("Selected item: " + item.getValue());
			System.out.println(PathTools.getPath(item));

			Path descriptionPath = getDescriptionPath(item);
			if(Files.exists(descriptionPath)){
				String          exerciseName = descriptionPath.getParent().getFileName().toString();
				String          headMessage   = "Exercise:\n" + exerciseName + "\n\nDescription:\n";
				ExerciseConfig  config = getConfig(exerciseName);
				String          configMessage =   "\nExtensions:\n";
				if(config.isTimeTracking())
					configMessage += "->Time-Tracking\n";
				if(config.isAtdd())
					configMessage += "->ATDD\n";
				if(config.isBabySteps())
					configMessage += "->Babysteps, " + config.getBabyStepsTime() + " sec\n";
				consoleInputArea.setText(headMessage + FileTools.readFile(descriptionPath) + configMessage);
			}

		}
	}

	public Path getDescriptionPath(TreeItem<String> item){
		Path itemPath = PathTools.getPath(item);
		//no guarantee that file exists
		if(itemPath.getNameCount() < 2) {
			System.err.println("Bad structure of directories. Has to be: root->exercise-> <Files> and description.txt");
			return null;
		}
		return Paths.get(itemPath.subpath(0,2) + File.separator + "description.txt");
	}

	@FXML void handleQuit(ActionEvent event){
		System.exit(0);
	}

	//initializer
	@FXML public void initialize(){
		this.phases = new Phases(new Welcome(), new Test(), new Code(), new CodeRefactor(), new TestRefactor());
		this.logic  = initLogic();
		setupStart();
	}


	@FXML void handlePrePhase(ActionEvent event) {
		/*logic.abbrechen();
		phases.setStates(logic.getSchritt(), userFieldRed, userFieldCode, stepBack, stepFurther, currentPhaseDisplay);*/
	}

    @FXML void handleNextPhase(ActionEvent event){
		/*Exercise current = exerciseCatalogue.getCatalogue(1);
		logic.weiter(new CompilationUnit(current.getTestName(0), current.getTestTemplates(0), true ));
		phases.setStates(logic.getSchritt(), userFieldRed, userFieldCode, stepBack, stepFurther, currentPhaseDisplay);*/
	}

    @FXML void handleNewCatalogue(ActionEvent event) {
		Path cataloguePath = main.getCatalogue();

		try {
			FileReader           fileReader           = new FileReader(cataloguePath);
			XMLExerciseTokenizer xmlExerciseTokenizer = new XMLExerciseTokenizer(fileReader); //able to read tokens out of file
			ExerciseCatalogue    exerciseCatalogue    = new ExerciseCatalogue(); //empty catalogue
			XMLExerciseLoader    xmlExerciseLoader    = new XMLExerciseLoader(xmlExerciseTokenizer, exerciseCatalogue);
			this.exerciseCatalogue = xmlExerciseLoader.loadExerciseCatalogue();
		} catch (IOException | ParserException | TokenException | SamePropertyTwiceException e) {
			if(e instanceof ParserException) {
				System.out.println("Caught null from main.getCatalogue: No file selected");
				return;
			}
			e.printStackTrace();
		}

		ExercisesTree exercises = new ExercisesTree(exerciseCatalogue, exercisesTree);
		exercises.fill(cataloguePath.getFileName().toString().replace(".xml","") + "Catalogue");

		//console-sampleoutput
		System.out.println("Exercises: " + exerciseCatalogue.size());
		System.out.println("Name of Exercise 1: " + exerciseCatalogue.getExercise(0).getExerciseName());
		System.out.println("Name of First Class: " + exerciseCatalogue.getExercise(0).getClassName(0));
		System.out.println("Classes in Exercise 1: " + exerciseCatalogue.getExercise(0).getNumberOfClasses());
		System.out.println("Name of First Test: " + exerciseCatalogue.getExercise(0).getTestName(0));
		System.out.println("Tests in Exercise 1: " + exerciseCatalogue.getExercise(0).getNumberOfTests());

		/*Exercise exercise = exerciseCatalogue.getCatalogue(1);
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
		ViewTools.setUneditable(consoleInputArea,testInputArea,codeInputArea,codeRefactorInputArea,testRefactorInputArea);
		ViewTools.hideNodes(stepBack,stepFurther,timeLeft,timeLeftTitle,activatedModes);
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
			ViewTools.enable(timeLeft, "" + exercise.getExerciseConfig().getBabyStepsTime());
		}
	}

	private ExerciseConfig getConfig(String exerciseName){
		for(int exercise = 0; exercise < exerciseCatalogue.size(); exercise++){
			if(exerciseName.contentEquals(exerciseCatalogue.getExercise(exercise).getExerciseName()))
				return exerciseCatalogue.getExercise(exercise).getExerciseConfig();
		}
		System.err.println("Invalid name of exercise: " + exerciseName);
		return null;
	}
}
