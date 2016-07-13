package de.hhu.propra16.avaders;

import de.hhu.propra16.avaders.catalogueLoader.ParserException;
import de.hhu.propra16.avaders.catalogueLoader.ExerciseCatalogueLoader;
import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseCatalogue;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.ExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.FileReader;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.XMLExerciseTokenizer;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.SamePropertyTwiceException;
import de.hhu.propra16.avaders.catalogueLoader.tokenizer.exceptions.TokenException;
import de.hhu.propra16.avaders.extensions.Babysteps;
import de.hhu.propra16.avaders.gui.*;
import de.hhu.propra16.avaders.gui.phases.*;
import de.hhu.propra16.avaders.gui.tools.FileTools;
import de.hhu.propra16.avaders.gui.tools.PathTools;
import de.hhu.propra16.avaders.gui.tools.ViewTools;
import de.hhu.propra16.avaders.gui.view.ButtonDisplay;
import de.hhu.propra16.avaders.gui.view.ModeDisplay;
import de.hhu.propra16.avaders.konfig.KonfigWerte;
import de.hhu.propra16.avaders.logik.Logik;
import de.hhu.propra16.avaders.logik.Step;
import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import de.hhu.propra16.avaders.testen.Tester;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import vk.core.api.CompilationUnit;

import java.io.IOException;
import java.nio.file.Path;


public class MainController {
	//menuItems
	@FXML private MenuItem newCatalogue;
	@FXML private MenuItem restart;
	@FXML private MenuItem quit;
	@FXML private MenuItem endCycleMenuItem;

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

	@FXML private TabPane tabPane;
	@FXML private Tab informationTab;
	@FXML private Tab consoleTab;
	@FXML private Tab codeTab;
	@FXML private Tab codeRefactorTab;
	@FXML private Tab testRefactorTab;
	@FXML private Tab testTab;
	@FXML private TextArea informationOutputArea;
	@FXML private TextArea consoleOutputArea;
	@FXML private TextArea testOutputArea;
	@FXML private TextArea codeOutputArea;
	@FXML private TextArea codeRefactorOutputArea;
	@FXML private TextArea testRefactorOutputArea;
	@FXML private TextArea userInputField;

	@FXML private HBox exercisesHead;
	@FXML private SplitPane vericalSplitBase;
	@FXML private AnchorPane exerciseTreeBase;
	@FXML private TreeView<String> exercisesTree;

	private Main main;
	private Babysteps babysteps;
	private ExerciseCatalogue exerciseCatalogue;
	private Phases phases;
	private Logik logic;
	private ButtonDisplay buttonDisplay;
	private ModeDisplay modeDisplay;
	private Information information;
	private Console console;
	private SubTask subTask;
	private CompilationUnits compilationUnits;
	private AnchorPane exerciseTreeBaseTemp;



	@FXML
	public void initialize() {
		this.buttonDisplay = new ButtonDisplay(stepBack, stepFurther, start, new Button("save"));
		this.phases = new Phases(
				new Welcome(userInputField, informationOutputArea, buttonDisplay, currentPhaseLabel),
				new Test(userInputField, testOutputArea, buttonDisplay, currentPhaseLabel),
				new Code(userInputField, codeOutputArea, buttonDisplay, currentPhaseLabel),
				new CodeRefactor(userInputField, codeRefactorOutputArea, buttonDisplay, currentPhaseLabel),
				new TestRefactor(userInputField, testRefactorOutputArea, buttonDisplay, currentPhaseLabel));
		this.modeDisplay = new ModeDisplay(activatedModes, timeLeftTitle, timeLeft);
		this.information = new Information(informationOutputArea);
		this.console = new Console(consoleOutputArea);
		this.logic = initializeLogic();

		phases.setStates(Step.WELCOME);
	}


	//Handler
	@FXML
	void handleStart() {
		TreeItem<String> selection = exercisesTree.getSelectionModel().getSelectedItem();
		this.subTask = new SubTask();
		this.subTask.load(selection, exerciseCatalogue);
		this.subTask.createPaths(selection);
		CompilationUnit initTestUnit  = new CompilationUnit(subTask.getName(Step.RED),   subTask.getTestTemplate(),  true);
		CompilationUnit initClassUnit = new CompilationUnit(subTask.getName(Step.GREEN), subTask.getClassTemplate(), false);
		this.compilationUnits = new CompilationUnits(initTestUnit,initClassUnit);
		this.modeDisplay      = new ModeDisplay(activatedModes,timeLeftTitle,timeLeft);
		modeDisplay.set(subTask.getExerciseConfig()); //TODO activate displays method
		modeDisplay.enableActiveModes(true, subTask.getExerciseConfig());
		modeDisplay.enableTime(true, subTask.getExerciseConfig());

		if(subTask.getExerciseConfig().isBabySteps()) {//TODO 2 methods
			this.babysteps = new Babysteps();
			this.babysteps.setCurrentlyEditableArea(userInputField);
			this.timeLeft.textProperty().bind(Bindings.concat("", babysteps.getRemainingBinding()));
			this.babysteps.setOnTimeout(() -> {
				if(logic.getSchritt() == Step.GREEN){
					logic.abbrechen();
					phases.setStates(logic.getSchritt());
					ViewTools.clearOutputAreas(consoleOutputArea,testOutputArea,codeOutputArea,codeRefactorOutputArea,testRefactorOutputArea);
					tabPane.getSelectionModel().select(informationTab);
				}
				userInputField.setText(babysteps.getOldText());
				babysteps.restart();
			});
			babysteps.startTimer(60);
		}

		phases.setStates(logic.getSchritt());

		ViewTools.clearOutputAreas(consoleOutputArea,testOutputArea,codeOutputArea,codeRefactorOutputArea,testRefactorOutputArea);
		ViewTools.setUneditable(informationOutputArea,consoleOutputArea,codeOutputArea,
				testOutputArea,codeRefactorOutputArea,testRefactorOutputArea);
	}


	@FXML
	void handleTreeViewMouseClicked(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY & start.isVisible()) {
			tabPane.getSelectionModel().select(informationTab);
			TreeItem<String> item = exercisesTree.getSelectionModel().getSelectedItem();
			if (item == null) {
				System.err.println("item not initialized yet");
				return;
			}
			showClassContent(item, "Test");
			if (isTestSelection(item, "Test")) {
				start.setDisable(false);
			} else {
				start.setDisable(true);
			}
			phases.getWelcome().setInformation(item, exerciseCatalogue);
		}
	}

	@FXML
	void handleNextPhase() {
		Step currentStep = logic.getSchritt();

		compilationUnits.update(currentStep, new CompilationUnit(subTask.getName(currentStep), userInputField.getText(),
				phases.getPhase(currentStep).hasUnitTests()));

		ITestenRueckgabe results = compilationUnits.test(logic); //logic goes ahead!
		compilationUnits.showResultsOn(consoleOutputArea, results);

		if((currentStep != Step.RED & (!results.isSuccessful() | results.getCompilerResult().hasCompileErrors() | currentStep == logic.getSchritt()))
				|| (currentStep == Step.RED & currentStep == logic.getSchritt()) ) {
			tabPane.getSelectionModel().select(consoleTab);
			return;
		}
		updateAreas(logic.getSchritt());
		phases.setStates(logic.getSchritt());
	}

	@FXML
	public void handleEndCycle(){
		updateAreas(Step.WELCOME);
	}


	@FXML
	void handleNewCatalogue() {
		Path              cataloguePath       = main.getCatalogue();
		ExerciseCatalogue notProofedCatalogue = new ExerciseCatalogue();
		try {
			FileReader              fileReader        = new FileReader(cataloguePath);
			ExerciseTokenizer       exerciseTokenizer = new XMLExerciseTokenizer(fileReader);
			ExerciseCatalogueLoader xmlExerciseLoader = new ExerciseCatalogueLoader(exerciseTokenizer, notProofedCatalogue);
			xmlExerciseLoader.loadCatalogue();
		} catch (IOException | ParserException | TokenException | SamePropertyTwiceException e) {
			if (e instanceof ParserException) {
				System.err.println("Caught null from main.getCatalogue: No file selected");
				return;
			}
			informationOutputArea.setText("Unable to load specified catalogue.\nInvalid Catalogue.");
			return;
		}
		if(!PathTools.checkCatalogue(notProofedCatalogue, informationOutputArea)) {
			return;
		}
		this.exerciseCatalogue  = notProofedCatalogue;
		ExercisesTree exercises = new ExercisesTree(exerciseCatalogue, exercisesTree);
		exercises.fill(PathTools.getFileNamePrefix(cataloguePath, ".xml") + "Catalogue");
		updateAreas(Step.WELCOME);
	}




	@FXML void handleQuit(){
		System.exit(0);
	}

	@FXML void handlePrePhase() {
		logic.abbrechen();
		phases.setStates(logic.getSchritt());
		userInputField.setText(testOutputArea.getText());
		testOutputArea.setText("");
		tabPane.getSelectionModel().select(consoleTab);
	}

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

	private void updateAreas(Step mode){
		switch (mode){
			case RED:
				if(subTask.getExerciseConfig().isBabySteps()) {
					babysteps.startTimer(subTask.getExerciseConfig().getBabyStepsTime());
					modeDisplay.enableTime(true, subTask.getExerciseConfig());
				}
				testRefactorOutputArea.setText(userInputField.getText());
				subTask.updateForNextCycle(codeRefactorOutputArea,testRefactorOutputArea);
				subTask.saveToFiles();
				informationOutputArea.setText(informationOutputArea.getText() + "\n\n" + "Last cycle has been saved!");
				userInputField.setText(testRefactorOutputArea.getText());
				tabPane.getSelectionModel().select(codeRefactorTab);
				break;
			case GREEN:
				testOutputArea.setText(userInputField.getText());
				userInputField.setText(subTask.getTemplate(mode));
				tabPane.getSelectionModel().select(testTab);
				break;
			case CODE_REFACTOR:
				if(subTask.getExerciseConfig().isBabySteps()) {
					babysteps.resetAndStop();
					modeDisplay.enableTime(false, subTask.getExerciseConfig());
				}
				codeOutputArea.setText(userInputField.getText());
				tabPane.getSelectionModel().select(codeTab);
				break;
			case TEST_REFACTOR:
				codeRefactorOutputArea.setText(userInputField.getText());
				userInputField.setText(testOutputArea.getText());
				tabPane.getSelectionModel().select(testTab);
				break;
			case WELCOME:
				logic = initializeLogic();
				ViewTools.clearOutputAreas(userInputField,consoleOutputArea,testOutputArea,codeOutputArea,codeRefactorOutputArea,testRefactorOutputArea);
				phases.setStates(Step.WELCOME);
		}
	}

}
