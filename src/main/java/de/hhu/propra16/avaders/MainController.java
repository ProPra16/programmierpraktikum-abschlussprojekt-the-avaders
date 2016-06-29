package de.hhu.propra16.avaders;

import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class MainController {
	private static BorderPane mainBase;
	private static Map<Name,Phase> phases;
	private static Name current = Name.RED;
	
    @FXML
    private MenuItem newExercise;
    @FXML
    private Button prePhase;
    @FXML
    private Text activatedModes;
    @FXML
    private MenuItem restart;
    @FXML
    private Button nextPhase;
    @FXML
    private TextArea greenRefactor;
    @FXML
    private Text timeLeft;
    @FXML
    private TextArea redPhase;
    @FXML
    private TextArea codePhase;
    
    

    
    //Handler
    @FXML
    void handlePrePhase(ActionEvent event) {
    	redPhase.setDisable(false);
    	codePhase.setDisable(true);
    	prePhase.setVisible(false);
    	
    }
    
    enum Name{
    	RED,GREEN,REFACTOR;
    }
    
    @FXML
    void handleNextPhase(ActionEvent event){
 
    	
    	
    }
    @FXML
    void handleNewExcercise(ActionEvent event) {
    	this.phases = new HashMap<>();
    	Red      red      = new Red();
    	Green    green    = new Green();
    	Refactor refactor = new Refactor();
    	phases.put(Name.RED, red);
    	phases.put(Name.GREEN, green);
    	phases.put(Name.REFACTOR, refactor);
    }
    
    @FXML
    void handleRestart(ActionEvent event) {

    }
}
