package de.hhu.propra16.avaders.extensions;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

public class Babysteps {
	private Timeline timeline;
	private volatile TextArea currentlyEditableArea;
	private String oldText;
	private IntegerProperty seconds = new SimpleIntegerProperty(0), maxSeconds = new SimpleIntegerProperty(0);
	private NumberBinding remaining = Bindings.subtract(maxSeconds,seconds);
	private BooleanBinding hasTimeLeft = Bindings.notEqual(seconds,maxSeconds);
	public Babysteps(TextArea t){
		currentlyEditableArea = t;
		oldText = currentlyEditableArea.getText();
		timeline = new Timeline(new KeyFrame(Duration.seconds(1),
											ae -> {seconds.set(seconds.get()+1);
												System.out.println(seconds.get());
											if(!hasTimeLeft.get()){
												currentlyEditableArea.setEditable(false);
												currentlyEditableArea.setText(oldText);
												System.out.println("Ich wollte text machen");
											}}));
	}

	public void startTimer(int maxSeconds){
		this.maxSeconds.set(maxSeconds);
		timeline.setCycleCount(maxSeconds);
		timeline.play();
		System.out.println("timeline läuft");
	}

	public int getRemainingSeconds(){
		return remaining.intValue();
	}
	public boolean hasTimeLeft(){
		return hasTimeLeft.get();
	}

	public String getOldText(){
		return oldText;
	}

}
