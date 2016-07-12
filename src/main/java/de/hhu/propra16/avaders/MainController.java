package de.hhu.propra16.avaders;

import de.hhu.propra16.avaders.catalogueLoader.ParserException;
import de.hhu.propra16.avaders.catalogueLoader.ExerciseCatalogueLoader;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.ExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.FileReader;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.gui.*;
import de.hhu.propra16.avaders.gui.phases.*;
import de.hhu.propra16.avaders.gui.tools.BindTools;
import de.hhu.propra16.avaders.gui.tools.FileTools;
import de.hhu.propra16.avaders.gui.tools.PathTools;
import de.hhu.propra16.avaders.gui.tools.ViewTools;
import de.hhu.propra16.avaders.gui.view.ButtonDisplay;
import de.hhu.propra16.avaders.gui.view.ModeDisplay;
import de.hhu.propra16.avaders.konfig.KonfigWerte;
import de.hhu.propra16.avaders.logik.Logik;
import de.hhu.propra16.avaders.logik.Step;
import de.hhu.propra16.avaders.testen.Tester;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.nio.file.Path;


public class MainController {
	private Main              main;
	private ExerciseCatalogue exerciseCatalogue;
	private Phases phases;
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
	@FXML private TableColumn exerciseColumn;
	@FXML private TableColumn classColumn;
	@FXML private TableColumn testColumn;

	@FXML private Tab      informationTab;
	@FXML private Tab      consoleTab;
	@FXML private Tab      codeTab;
	@FXML private Tab      codeRefactorTab;
	@FXML private Tab      testRefactorTab;
	@FXML private Tab      testTab;
	@FXML private TextArea informationOutputArea;
	@FXML private TextArea consoleOutputArea;
	@FXML private TextArea testOutputArea;
	@FXML private TextArea codeOutputArea;
	@FXML private TextArea codeRefactorOutputArea;
	@FXML private TextArea testRefactorOutputArea;
	@FXML private TextArea userInputField;

	@FXML private HBox exercisesHead;
	@FXML private TreeView<String> exercisesTree;

	private ButtonDisplay buttonDisplay;
	private ModeDisplay modeDisplay;
	private Information   information;
	private Console       console;
	private SubTask       subTask;


	@FXML public void initialize(){
		this.buttonDisplay = new ButtonDisplay(stepBack,stepFurther,start,new Button("save"));
		this.phases        = new Phases(
				             new Welcome(userInputField,informationOutputArea,buttonDisplay,currentPhaseLabel),
				             new Test( userInputField,testOutputArea,buttonDisplay,currentPhaseLabel),
				             new Code(userInputField,codeOutputArea,buttonDisplay,currentPhaseLabel),
				             new CodeRefactor(userInputField,codeRefactorOutputArea,buttonDisplay,currentPhaseLabel),
				             new TestRefactor(userInputField,testRefactorOutputArea,buttonDisplay,currentPhaseLabel));
		this.modeDisplay   = new ModeDisplay(activatedModes,timeLeftTitle,timeLeft);
		this.information   = new Information(informationOutputArea);
		this.console       = new Console(consoleOutputArea);
		this.logic         = initializeLogic();

		phases.setStates(Step.WELCOME);
	}


	//Handler
    @FXML void handleStart(){
		TreeItem<String> selection = exercisesTree.getSelectionModel().getSelectedItem();
		this.subTask = new SubTask();
		subTask.load(selection, exerciseCatalogue);
		System.out.println(subTask);
		phases.setStates(logic.getSchritt());
		setTabAreaConnection(logic.getSchritt());

	}

	@FXML void handleTreeViewMouseClicked(MouseEvent event) {
		if(event.getButton() == MouseButton.PRIMARY){
			TreeItem<String> item = exercisesTree.getSelectionModel().getSelectedItem();
			if(item == null){
				System.err.println("item not initialized yet");
				return;
			}
			showClassContent(item,"Test");
			if(isTestSelection(item, "Test"))
				start.setDisable(false);
			phases.getWelcome().setInformation(item, exerciseCatalogue);
		}
	}

    @FXML void handleNextPhase(){
//		Step currentStep = logic.getSchritt();
//		System.out.println("Current TestName " + currentTestName + " CurrentStep " + currentStep);
//		CompilationUnit unit = new CompilationUnit(currentTestName, userInputField.getText(), true);
//		ITestenRueckgabe returnValue = logic.weiter(unit);
//
//		consoleOutputArea.setText(TestResultDisplay.showCompilerResult(
//				returnValue.getCompilerResult(),unit) + TestResultDisplay.showTestResults(returnValue.getTestResult()));
//
//		if(returnValue.getCompilerResult().hasCompileErrors()){
//			System.err.println("There are compileErrors");
//			return;
//		}
//
//		Step nextStep = logic.getSchritt();
//		if(currentStep == nextStep) {
//			System.err.println("Next equals current step");
//			return;
//		}
//		setTabAreaConnection(nextStep);
//		switch (nextStep){
//			case RED:           setFinish(); break;
//			case GREEN:         setClassTemplateToUserInputArea(); break;
//			case TEST_REFACTOR: userInputField.setText(testOutputArea.getText()); break;
//			case CODE_REFACTOR: break;
//		}
//		System.out.println(nextStep);
//		if(nextStep!= Step.RED)
//			phases.setStates(nextStep);
	}


    @FXML void handleNewCatalogue() {
		Path cataloguePath = main.getCatalogue();
		try {
			FileReader           fileReader           = new FileReader(cataloguePath);
			ExerciseTokenizer    exerciseTokenizer    = new XMLExerciseTokenizer(fileReader); //able to read tokens out of file
			ExerciseCatalogue    exerciseCatalogue    = new ExerciseCatalogue(); //empty catalogue
			ExerciseCatalogueLoader xmlExerciseLoader = new ExerciseCatalogueLoader(exerciseTokenizer, exerciseCatalogue);
			this.exerciseCatalogue = xmlExerciseLoader.loadCatalogue();
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

	@FXML void handleQuit(){
		System.exit(0);
	}
	@FXML void handlePrePhase() {}
	@FXML void handleProgress() {}

	void setMain(Main main){
		this.main = main;
	}


	//HelpMethods
	private Logik initializeLogic(){
		Tester      tester      = new Tester();
		KonfigWerte konfigWerte = new KonfigWerte();
		return new Logik(tester, konfigWerte);
	}

	//start
	private void showClassContent(TreeItem<String> item, String parentName){
		if(item.isLeaf()){
			Path itemPath = PathTools.getPath(item);
			userInputField.setText(FileTools.readFile(itemPath));
		}
	}
	private boolean isTestSelection(TreeItem<String> item, String parentName){
		return PathTools.hasParentName(item, parentName);
	}

	private void setTabAreaConnection(Step mode){
		BindTools.unbindAreas(testOutputArea, codeOutputArea, codeRefactorOutputArea, testRefactorOutputArea);
		switch (mode){
			case RED:           testOutputArea.textProperty().bind(userInputField.textProperty()); break;
			case GREEN:         codeOutputArea.textProperty().bind(userInputField.textProperty()); break;
			case CODE_REFACTOR: codeRefactorOutputArea.textProperty().bind(userInputField.textProperty()); break;
			case TEST_REFACTOR: testRefactorOutputArea.textProperty().bind(userInputField.textProperty()); break;
		}
	}
	//end


	//start
	private void setClassTemplateToUserInputArea(){
		userInputField.setText(exerciseCatalogue.getExercise(0).getClassTemplate(0));
	}


	private void setFinish(){
		setTabAreaConnection(Step.WELCOME);
		phases.setStates(Step.WELCOME);
		ViewTools.showAreas(testRefactorOutputArea);
		ViewTools.hideNode(stepFurther);
		ViewTools.enable(start);
		userInputField.setText("Exercise done!");
	}
	//end
}
