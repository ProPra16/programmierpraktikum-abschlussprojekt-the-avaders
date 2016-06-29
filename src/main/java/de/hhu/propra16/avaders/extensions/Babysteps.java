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
	private Runnable onTimeout;

	/**
	 * Creates a new instance of Babysteps.
	 * @param t the TextAre which you would like to attatch the Babysteps
	 */
	public Babysteps(TextArea t){
		currentlyEditableArea = t;
		oldText = currentlyEditableArea.getText();
		onTimeout = () -> {currentlyEditableArea.setEditable(false);currentlyEditableArea.setText(oldText);};
		setTimeline();
	}

	public Babysteps(){
		onTimeout = () -> {};
		setTimeline();
	}

	private void setTimeline(){
		timeline = new Timeline(new KeyFrame(Duration.seconds(1),
				ae -> {seconds.set(seconds.get()+1);
					if(!hasTimeLeft.get()){
						onTimeout.run();
					}}));
	}

	/**
	 * starts the timer which counts the remaining time
	 * if the timer runs out, it will lock the TextArea and restore the old text
	 * @param maxSeconds the maximum amount of seconds for the user to edit the code
	 */
	public void startTimer(int maxSeconds){
		this.maxSeconds.set(maxSeconds);
		timeline.setCycleCount(maxSeconds);
		timeline.play();
	}

	/**
	 * stops the Timer
	 */
	public void stopTimer(){
		timeline.stop();
	}

	/**
	 * resets the timer, but does not stops it.
	 */
	public void reset(){
		seconds.set(0);
	}

	/**
	 * restarts the timer
	 */
	public void restart(){
		timeline.stop();
		seconds.set(0);
		startTimer(maxSeconds.get());
	}

	/**
	 * changes the current TextAre
	 * @param t the TextArea
	 */
	public void setCurrentlyEditableArea(TextArea t){
		currentlyEditableArea = t;
		oldText = t.getText();
	}

	/**
	 * returns the remaining seconds for the user to edit his code
	 * @return remaining seconds
	 */
	public int getRemainingSeconds(){
		return remaining.intValue();
	}

	/**
	 * returns a boolean represents, whether the user has still time left or not
	 * @return current value
	 */
	public boolean hasTimeLeft(){
		return hasTimeLeft.get();
	}

	/**
	 * returns the text placed in the TextArea when the instance of this Object were created
	 * @return old text
	 */
	public String getOldText(){
		return oldText;
	}

	public void setOnTimeout(Runnable onTimeout) {
		this.onTimeout = onTimeout;
	}
}
