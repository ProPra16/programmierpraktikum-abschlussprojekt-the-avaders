package de.hhu.propra16.avaders;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class MainController {
	
	//access to main pane of primarystage where scene is set
	private static BorderPane mainBase;
	
	//access to every phase-object
	private static Map<PhaseName,Phase> phases;
	
	/**
	 * main cycle-control datastructure:
	 * Objects Red,Green and Refactor will be inserted in this order. The first 
	 * Element represents the current Phase. We do EVER access first element to 
	 * set phase.
	 * Switching can be achieved due shifting the list with the shiftoperations below.
	 * For example:
	 * list(Red,Green,Refactor) -> Red-phase --->shiftLeft---> list(Green,Refactor,Red) -> 
	 * Green-Phase.
	 */
	private static LinkedList<Phase> phase = new LinkedList<Phase>();
	
	
	
	//excercise-menuItems
    @FXML private MenuItem newExercise;
    @FXML private MenuItem restart;
    
    //phase-switcher
    @FXML private Button prePhase;
    @FXML private Button nextPhase;
    
    //Userinformation about activated modes and time left due activated babysteps
    @FXML private Text activatedModes;
    @FXML private Text timeLeft;

    
    @FXML private TextArea greenRefactor;
    @FXML private TextArea redPhase;
    @FXML private TextArea codePhase;
    
   
    //Handler
    @FXML void handleRestart(ActionEvent event) { }
    
   
    @FXML void handlePrePhase(ActionEvent event) {
    	shiftRight(phase);
    	phase.getFirst().setStates(redPhase, codePhase, prePhase, nextPhase);
    }
    
    @FXML void handleNextPhase(ActionEvent event){
    	shiftLeft(phase);
    	phase.getFirst().setStates(redPhase, codePhase, prePhase, nextPhase);
    }
    
    /**
     * will be triggered if click on excercise->new... in the menubar.
     * takes the mainbase makes controls visible to user, creates map for
     * general access to phase objects and initializes phase-control-list.
     * finally sets first phase ->Red-phase.
     * @param event
     */
    @FXML void handleNewExcercise(ActionEvent event) {
    	mainBase = Main.getMainBase();
    	mainBase.getCenter().setVisible(true);
    	mainBase.getBottom().setVisible(true);
     	phases   = new HashMap<>();
     	
    	phases.put(PhaseName.RED, new Red());
    	phases.put(PhaseName.GREEN, new Green());
    	phases.put(PhaseName.REFACTOR, new Refactor());
  
    	phase.add(phases.get(PhaseName.RED));
    	phase.addLast(phases.get(PhaseName.GREEN));
    	phase.addLast(phases.get(PhaseName.REFACTOR));
    	
    	phase.getFirst().setStates(redPhase, codePhase, prePhase, nextPhase);
    }
    
 
    //Shift-operations mentioned above 
    private void shiftLeft(LinkedList<Phase> phases){
    	phase.addLast(phase.getFirst());
    	phase.removeFirst();
    }
    private void shiftRight(LinkedList<Phase> phases){
    	phase.addFirst(phase.getLast());
    	phase.removeLast();
    }
}
