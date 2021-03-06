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
import de.hhu.propra16.avaders.extensions.Tracking;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import vk.core.api.CompilationUnit;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main controller handling nearly anything
 */
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

	@FXML private Label   progressLabel;
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
	private SubTask subTask;
	private CompilationUnits compilationUnits;
	private AnchorPane exerciseTreeBaseTemp;
	private Tracking timeTracking;
	private String currentClass;

	/**
	 * Initializes the states. Creates all required instances and sets up the controls and areas to default
	 */
	@FXML
	public void initialize() {
		this.progressLabel.setVisible(false);
		this.progress.setVisible(false);
		this.buttonDisplay = new ButtonDisplay(stepBack, stepFurther, start, new Button("save"),endCycleMenuItem, newCatalogue);
		this.phases = new Phases(
				new Welcome(userInputField, informationOutputArea, buttonDisplay, currentPhaseLabel),
				new Test(userInputField, testOutputArea, buttonDisplay, currentPhaseLabel),
				new Code(userInputField, codeOutputArea, buttonDisplay, currentPhaseLabel),
				new CodeRefactor(userInputField, codeRefactorOutputArea, buttonDisplay, currentPhaseLabel),
				new TestRefactor(userInputField, testRefactorOutputArea, buttonDisplay, currentPhaseLabel));
		this.modeDisplay = new ModeDisplay(activatedModes, timeLeftTitle, timeLeft);
		this.logic = initializeLogic();
		phases.setStates(Step.WELCOME);
	}


	/**
	 * Will be called when Start-Button is pressed and handles start of a cycle
	 */
	@FXML
	void handleStart() {
		TreeItem<String> selection = exercisesTree.getSelectionModel().getSelectedItem();
		initializeSubTask(selection);
		initializeCompilationUnits();
		initializeExtensions();
		initializeAreas();
		currentClass = subTask.getTemplate(Step.RED);
	}


	/**
	 * This resets test and green phase when extension BabySteps is enabled and time run out
	 */
	private void resetOnTimeOut(){
		if(logic.getSchritt() == Step.GREEN){
			logic.abbrechen();
			phases.setStates(logic.getSchritt());
			ViewTools.clearOutputAreas(consoleOutputArea,testOutputArea,codeOutputArea,codeRefactorOutputArea,testRefactorOutputArea);
			tabPane.getSelectionModel().select(informationTab);
		}
		userInputField.setText(babysteps.getOldText());
		babysteps.restart();
	}


	/**
	 * Will be called when clicked on the exerciseTree. On informationOutputArea will be information
	 * according to this exercise and on the userInputArea the class- or test-template shown
	 * @param event
     */
	@FXML
	void handleTreeViewMouseClicked(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY & start.isVisible()) {
			tabPane.getSelectionModel().select(informationTab);
			TreeItem<String> item = exercisesTree.getSelectionModel().getSelectedItem();
			if (item == null) {
				//System.err.println("item not initialized yet");
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

	/**
	 * Handles the next phase. Next step will be determined by evaluating tests on current input in testTemplate and
	 * classTemplate. Depending on the instructions in our project the next phase will be chosen
	 */
	@FXML
	void handleNextPhase() {
		Step currentStep = logic.getSchritt();
		if(subTask.getExerciseConfig().isTimeTracking()){
			diffWriter(currentClass,userInputField.getText(),subTask.getName(currentStep));
		}

		compilationUnits.update(currentStep, new CompilationUnit(subTask.getName(currentStep), userInputField.getText(),
				phases.getPhase(currentStep).hasUnitTests()));

		ITestenRueckgabe results = compilationUnits.test(logic);//logic goes ahead!

		//if(subTask.getExerciseConfig().isTimeTracking())
		//		compilationUnits.addToTracking(timeTracking);

		Step nextStep = logic.getSchritt();

		if(subTask.getExerciseConfig().isTimeTracking()){
			currentClass = subTask.getTemplate(nextStep);
		}

		String errorString = compilationUnits.showResultsOn(consoleOutputArea, results);
		if(subTask.getExerciseConfig().isTimeTracking() & errorString.length() > 0 ){
			errorWriter(errorString + "\n\n\n ");
		}

		if((currentStep != Step.RED & (!results.isSuccessful() | results.getCompilerResult().hasCompileErrors() | currentStep == nextStep))
				|| (currentStep == Step.RED & currentStep == nextStep) ) {
			tabPane.getSelectionModel().select(consoleTab);
			return;
		}
		if(currentStep != nextStep & subTask.getExerciseConfig().isTimeTracking()){
			switch (currentStep){
				case RED:           timeTracking.finishedRED();       timeTracking.setState(Step.GREEN);         timeTracking.startGREEN();     break;
				case GREEN:         timeTracking.finishedGREEN();     timeTracking.setState(Step.CODE_REFACTOR); timeTracking.startREFACTOR1(); break;
				case CODE_REFACTOR: timeTracking.finishedREFACTOR1(); timeTracking.setState(Step.TEST_REFACTOR); timeTracking.startREFACTOR2(); break;
				case TEST_REFACTOR: timeTracking.finishedREFACTOR2(); timeTracking.setState(Step.RED);           timeTracking.startRED();       break;
			}
		}

		updateAreas(nextStep);
		phases.setStates(nextStep);
	}

	/**
	 * This method can be invoked everytime due a cycle and must be invoked to end.
	 * TimeTracking-information will be shown if enabled and the WelcomePhase will be restored
	 */
	@FXML
	public void handleEndCycle(){
		if(subTask.getExerciseConfig().isTimeTracking() && ( timeTracking.getTimeForGREEN() > 0 & timeTracking.getTimeForRED() > 0)) {
			Stage root = new Stage();
			BorderPane pane = new BorderPane();
			//timeTracking.showTimeChart(false)
			pane.setCenter(timeTracking.showTimeChart(false));
			root.setScene(new Scene(pane, 500, 500));
			root.setTitle("Tracking Results");
			root.initOwner(main.getPrimaryStage());
			root.initModality(Modality.WINDOW_MODAL);
			root.show();
			root.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
						updateAreas(Step.WELCOME);
					}
				}
			});
		}
		else {
			updateAreas(Step.WELCOME);
		}
		if(subTask.getExerciseConfig().isBabySteps()) {
			babysteps.resetAndStop();
			modeDisplay.enableTime(false, subTask.getExerciseConfig());
		}
	}


	/**
	 * This method handles main loading instructions. It calls the catalogue-path from main and verifies the correctness
	 * of this file and loads it to the hard-drive and the exercisesTree.
	 */
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


	/**
	 * Quits the whole program
	 */
	@FXML void handleQuit(){
		System.exit(0);
	}


	/**
	 * Only callable due Code-Phase
	 * CodePhase will be reseted and user thrown to test-phase
	 */
	@FXML void handlePrePhase() {
		if(subTask.getExerciseConfig().isTimeTracking()){
			timeTracking.finishedGREEN();
			timeTracking.setState(Step.RED);
			timeTracking.startRED();
		}
		logic.abbrechen();
		phases.setStates(logic.getSchritt());
		compilationUnits.updateClassUnit(subTask.getName(Step.GREEN), subTask.getClassTemplate(), false);
		currentClass = testOutputArea.getText();
		userInputField.setText(testOutputArea.getText());
		testOutputArea.setText("");
		tabPane.getSelectionModel().select(consoleTab);
	}

	@FXML void handleProgress() {}

	/**
	 * Gives access to main
	 * @param main The main-class
     */
	void setMain(Main main){
		this.main = main;
	}


	/**
	 * Creates and initializes an instance of logic
	 * @return Initialized logic
     */
	private Logik initializeLogic(){
		Tester      tester      = new Tester();
		KonfigWerte konfigWerte = new KonfigWerte();
		return new Logik(tester, konfigWerte);
	}

	/**
	 * Handles connecting content of templates to userInputArea so that if one clickes on
	 * the exercisesTree one cann see the content on the userInputArea
	 * @param item        TreeItem to be clicked
	 * @param parentName  To be deleted
     */
	private void showClassContent(TreeItem<String> item, String parentName){
		if(item.isLeaf()){
			Path itemPath = PathTools.getPath(item);
			userInputField.setText(FileTools.readFile(itemPath));
		}
	}

	/**
	 * Checks if selected item is test-item to switch the start-button on
	 * @param item       Selected item
	 * @param parentName Parent-name of selected Item
     * @return True if parent-name equals 'Test', otherwise false
     */
	private boolean isTestSelection(TreeItem<String> item, String parentName){
		return PathTools.hasParentName(item, parentName);
	}


	/**
	 * Sets the states of the areas and extensions according to the next step
	 * @param mode Next step
     */
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

	/**
	 * Initializes the extensions
	 */
	private void initializeExtensions(){
		if(subTask.getExerciseConfig().isBabySteps())
			initializeBabySteps();
		if(subTask.getExerciseConfig().isTimeTracking())
			initializeTimeTracking();
	}

	/**
	 * Initializes TimeTracking
	 */
	private void initializeTimeTracking() {
		this.timeTracking = new Tracking(Step.RED);
		timeTracking.startRED();
	}

	/**
	 * Initializes a subTask according to selection in exercisesTree
	 * @param selection Selected TreeItem
     */
	private void initializeSubTask(TreeItem<String> selection){
		this.subTask = new SubTask();
		this.subTask.load(selection, exerciseCatalogue);
		this.subTask.createPaths(selection);
	}

	/**
	 * Initializes compilationUnits
	 */
	private void initializeCompilationUnits(){
		CompilationUnit initTestUnit  = new CompilationUnit(subTask.getName(Step.RED),   subTask.getTestTemplate(),  true);
		CompilationUnit initClassUnit = new CompilationUnit(subTask.getName(Step.GREEN), subTask.getClassTemplate(), false);
		this.compilationUnits = new CompilationUnits(initTestUnit,initClassUnit);
		initializeModesDisplay();
	}

	/**
	 * Initializes the ModesDisplay for the extensions
	 */
	private void initializeModesDisplay(){
		this.modeDisplay      = new ModeDisplay(activatedModes,timeLeftTitle,timeLeft);
		modeDisplay.set(subTask.getExerciseConfig());
		modeDisplay.enableActiveModes(true, subTask.getExerciseConfig());
		modeDisplay.enableTime(true, subTask.getExerciseConfig());
	}

	/**
	 * Initializes the TextAreas to default
	 */
	private void initializeAreas(){
		phases.setStates(logic.getSchritt());
		ViewTools.clearOutputAreas(consoleOutputArea,testOutputArea,codeOutputArea,codeRefactorOutputArea,testRefactorOutputArea);
		ViewTools.setUneditable(informationOutputArea,consoleOutputArea,codeOutputArea,
				testOutputArea,codeRefactorOutputArea,testRefactorOutputArea);
	}

	/**
	 * Initializes BabySteps-extension
	 */
	private void initializeBabySteps(){
		this.babysteps = new Babysteps();
		this.babysteps.setCurrentlyEditableArea(userInputField);
		this.timeLeft.textProperty().bind(Bindings.concat("", babysteps.getRemainingBinding()));
		this.babysteps.setOnTimeout(() -> resetOnTimeOut());
		babysteps.startTimer(subTask.getExerciseConfig().getBabyStepsTime());
	}

	/**
	 * Tool for Tracking-extension. Everytime the user clicks next or back button this writes diffs to a default file
	 * @param origin    The text at the beginning of a phase
	 * @param current   The edited text
	 * @param className Name of the class
     */
	private void diffWriter(String origin, String current, String className ){
		List<String> originList  = Arrays.asList(origin.split("\n"));
		List<String> currentList = Arrays.asList(current.split("\n"));

		Path saveTo = Paths.get(subTask.getClassPath().toString().replace(".java", "") + "diffTracking.txt");
		Pair<LocalDateTime, List<String>> diffs = timeTracking.diff(originList, currentList, className);
		List<String> saveList = new ArrayList<>();

		saveList.add(diffs.getKey().toString());
		saveList.addAll(diffs.getValue());
		saveList.add("\n");

		try {
			if(Files.exists(saveTo))
				Files.write(saveTo, saveList, StandardOpenOption.APPEND);
			else Files.write(saveTo, saveList, StandardOpenOption.CREATE);
		} catch (IOException e) {
			System.err.println("Unable to save diffTracking to " + saveTo);
			e.printStackTrace();
		}
	}

	private void errorWriter(String errorString){
		Path saveTo = Paths.get(subTask.getClassPath().toString().replace(".java", "") + "diffTracking.txt");
		List<String> saveList = Arrays.asList(errorString.split("\n"));
		try {
			Files.write(saveTo, saveList, StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.err.println("Unable to save diffTracking to " + saveTo);
			e.printStackTrace();
		}
	}

}
