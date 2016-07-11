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
import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import de.hhu.propra16.avaders.testen.Tester;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import vk.core.api.CompilationUnit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class MainController {
	private Main              main;
	private ExerciseCatalogue exerciseCatalogue;
	private String            currentTestName;
	private Exercise          currentExercise;
	private Path              currentClass;
	private Path              currentTest;
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

	@FXML private Tab      informationTab;
	@FXML private Tab      consoleTab;
	@FXML private Tab      codeTab;
	@FXML private Tab      codeRefactorTab;
	@FXML private Tab      testRefactorTab;
	@FXML private Tab      testTab;
	@FXML private TextArea informationInputArea;
	@FXML private TextArea consoleInputArea;
	@FXML private TextArea testInputArea;
	@FXML private TextArea codeInputArea;
	@FXML private TextArea codeRefactorInputArea;
	@FXML private TextArea testRefactorInputArea;
	@FXML private TextArea userInputField;

	@FXML private TreeView<String> exercisesTree;

	//initializer
	@FXML public void initialize(){
		this.phases = new Phases(new Welcome(), new Test(), new Code(), new CodeRefactor(), new TestRefactor());
		this.logic  = initLogic();
		this.start.setDisable(true);
		ViewTools.hideNodes(testInputArea,codeInputArea,codeRefactorInputArea,testRefactorInputArea);
		setInitialStates();
	}


	//Handler
    @FXML void handleStart(ActionEvent event){
		ViewTools.hideNode(start);
		TreeItem<String> selection = exercisesTree.getSelectionModel().getSelectedItem();
		this.currentExercise       = getExercise(PathTools.getPath(selection).getName(1).toString());
		this.currentTestName       = PathTools.getPath(selection).getFileName().toString().replace(".java", "");
		phases.setStates(logic.getSchritt(), userInputField, testInputArea, stepBack, stepFurther, currentPhaseLabel);
		setVisibleTabs(Step.RED);
		setTabAreaConnection(Step.RED);
        //System.out.println("CurrentTestname " + currentTestName);
		//System.out.println("ExerciseName: " + PathTools.getPath(selection).getName(1).toString());
	}

	@FXML void handleTreeViewMouseClicked(MouseEvent event) {
		if(event.getButton() == MouseButton.PRIMARY){
			TreeItem<String> item = exercisesTree.getSelectionModel().getSelectedItem();
			if(item == null){
				System.err.println("item not initialized yet");
				return;
			}
			setUserInputFieldOnParentNameCondition(item,"Test");
			setInformationInputArea(item);
		}
	}

	@FXML void handleQuit(ActionEvent event){
		System.exit(0);
	}

	@FXML void handlePrePhase(ActionEvent event) {
		/*logic.abbrechen();
		phases.setStates(logic.getSchritt(), userFieldRed, userFieldCode, stepBack, stepFurther, currentPhaseDisplay);*/
	}

    @FXML void handleNextPhase(ActionEvent event){
		Step currentStep = logic.getSchritt();
		System.out.println("Current TestName " + currentTestName + " CurrentStep " + currentStep);
		CompilationUnit unit = new CompilationUnit(currentTestName, userInputField.getText(), true);
		ITestenRueckgabe returnValue = logic.weiter(unit);

		consoleInputArea.setText(TestResultDisplay.showCompilerResult(
				returnValue.getCompilerResult(),unit) + "\n\n" + TestResultDisplay.showTestResults(returnValue.getTestResult()));
		if(returnValue.getCompilerResult().hasCompileErrors()==true){
			System.err.println("There are compileErrors");
		}

		Step nextStep = logic.getSchritt();
		if(currentStep == nextStep) {
			System.err.println("Next equals current step");
			return;
		}
		switch (nextStep){
			case RED:           setFinish(); break;
			case GREEN:         setClassTemplateToUserInputArea(); break;
			case TEST_REFACTOR: userInputField.setText(testInputArea.getText()); break;
			case CODE_REFACTOR: break;
		}
		if(nextStep!= Step.RED)
			phases.setStates(nextStep, userInputField, codeInputArea, stepBack, stepFurther, currentPhaseLabel);
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
		exercises.fill(PathTools.getFileNamePrefix(cataloguePath,".xml") + "Catalogue");
	}

	@FXML void handleProgress(ActionEvent event)  {}





	//HelpMethods
	public void setMain(Main main){
		this.main = main;
	}

	private Logik initLogic(){
		Tester      tester      = new Tester();
		KonfigWerte konfigWerte = new KonfigWerte();
		return new Logik(tester, konfigWerte);
	}

	private void setTime(Exercise exercise){
		if(exercise.getExerciseConfig().isBabySteps()){
			ViewTools.enable(timeLeftTitle);
			ViewTools.enable(timeLeft, "" + exercise.getExerciseConfig().getBabyStepsTime());
		}
	}

	//start
	private Exercise getExercise(String exerciseName){
		for(int exercise = 0; exercise < exerciseCatalogue.size(); exercise++){
			if(exerciseName.contentEquals(exerciseCatalogue.getExercise(exercise).getExerciseName()))
				return exerciseCatalogue.getExercise(exercise);
		}
		System.err.println("Invalid name of exercise: " + exerciseName);
		return null;
	}

	private ExerciseConfig getConfig(String exerciseName){
		for(int exercise = 0; exercise < exerciseCatalogue.size(); exercise++){
			if(exerciseName.contentEquals(exerciseCatalogue.getExercise(exercise).getExerciseName()))
				return exerciseCatalogue.getExercise(exercise).getExerciseConfig();
		}
		System.err.println("Invalid name of exercise: " + exerciseName);
		return null;
	}
	//end



	//start
	private void setUserInputFieldOnParentNameCondition(TreeItem<String> item, String parentName){
		if(item.isLeaf()){
			Path itemPath = PathTools.getPath(item);
			userInputField.setText(FileTools.readFile(itemPath));
			checkStartCondition(item, parentName);
		}
	}

	private void setInformationInputArea(TreeItem<String> item){
		Path descriptionPath = PathTools.getDescriptionPath(item);
		if(Files.exists(descriptionPath)){
			String exerciseName  = descriptionPath.getParent().getFileName().toString();
			String headMessage   = "Exercise:\n" + exerciseName + "\n\nDescription:\n";
			String configMessage = Display.getConfigDisplay(getConfig(exerciseName));
			informationInputArea.setText(headMessage + FileTools.readFile(descriptionPath) + configMessage);
		}
	}

	private void setTabAreaConnection(Step mode){
		TextArea[] infoAreas = new TextArea[]{testInputArea,codeInputArea,codeRefactorInputArea,testRefactorInputArea};
		switch (mode){
			case WELCOME:       BindTools.unbindAreas(infoAreas); break;
			case RED:           BindTools.setUniqueBinding(userInputField, testInputArea, infoAreas); break;
			case GREEN:         BindTools.setUniqueBinding(userInputField, codeInputArea, infoAreas); break;
			case CODE_REFACTOR: BindTools.setUniqueBinding(userInputField, codeRefactorInputArea, infoAreas); break;
			case TEST_REFACTOR: BindTools.setUniqueBinding(userInputField, testRefactorInputArea, infoAreas); break;
		}
	}
	//end

	//start
	private void checkStartCondition(TreeItem<String> item, String parentName){
		if(PathTools.hasParentName(item, parentName)) {
			start.setDisable(false);
		} else {
			start.setDisable(true);
		}
	}
	//end

	//start
	private void setClassTemplateToUserInputArea(){
		userInputField.setText(exerciseCatalogue.getExercise(0).getClassTemplate(0));
	}

	private void setInitialStates(){
		phases.setStates(Step.WELCOME,  userInputField, consoleInputArea, stepBack, stepFurther, currentPhaseLabel);
		ViewTools.setUneditable(consoleInputArea,testInputArea,codeInputArea,codeRefactorInputArea,testRefactorInputArea);
		ViewTools.hideNodes(stepBack,stepFurther,timeLeft,timeLeftTitle,activatedModes);
	}

	private void setFinish(){
		setTabAreaConnection(Step.WELCOME);
		setVisibleTabs(Step.FINISHED);
		phases.setStates(Step.WELCOME, userInputField, codeInputArea, stepBack, stepFurther, currentPhaseLabel);
		ViewTools.showAreas(testRefactorInputArea);
		ViewTools.hideNode(stepFurther);
		ViewTools.enable(start);
		userInputField.setText("Exercise done!");
	}

	private void setVisibleTabs(Step mode){
		switch (mode){
			case WELCOME:       ViewTools.hideNodes(testInputArea,codeInputArea,codeRefactorInputArea,testRefactorInputArea); break;
			case RED:           ViewTools.hideNodes(testInputArea,codeInputArea,codeRefactorInputArea,testRefactorInputArea); break;
			case GREEN:         ViewTools.enable(testInputArea); break;
			case CODE_REFACTOR: ViewTools.enable(codeInputArea); break;
			case TEST_REFACTOR: ViewTools.enable(codeRefactorInputArea); break;
			case FINISHED:      ViewTools.showAreas(testInputArea,codeInputArea,codeRefactorInputArea,testRefactorInputArea);
		}
	}
	//end
}
