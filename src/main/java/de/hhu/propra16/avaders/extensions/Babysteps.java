package de.hhu.propra16.avaders.extensions;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;
import javafx.util.Duration;

/**
 * Diese Klasse implementiert die {@link Babysteps}-Erweiterung.
 * Mit dieser kann eine bestimmte (oder eine standard) Methode nach einer bestimmten Zeit ausgeführt werden
 */
public class Babysteps {
	private Timeline timeline;
	private volatile TextArea currentlyEditableArea;
	private String oldText;
	private IntegerProperty seconds = new SimpleIntegerProperty(0), maxSeconds = new SimpleIntegerProperty(0);
	private NumberBinding remaining = Bindings.subtract(maxSeconds,seconds);
	private BooleanBinding hasTimeLeft = Bindings.notEqual(seconds,maxSeconds);
	private Runnable onTimeout;
	private HTMLEditor currentlyEditableHtmlEditor;

	/**
	 * Erzeugt eine neue Instanz von {@link Babysteps}, welche mit dem übergebenen {@link TextArea} verknüpft wird.
	 * Hierbei wird die Standard-Methode, welche aufgerufen wird, wenn die Zeit ausläuft, geladen, welche
	 * das {@link TextArea} sperrt und den vorherigen Text wiederherstellt.
	 * @param t Das {@link TextArea} mit welchem diese Instanz verknüpft wird.
	 */
	public Babysteps(TextArea t){
		currentlyEditableArea = t;
		oldText = currentlyEditableArea.getText();
		onTimeout = () -> {currentlyEditableArea.setEditable(false);currentlyEditableArea.setText(oldText);};
		setTimeline();
	}

	/**
	 * Erzeugt eine neue Instanz von {@link Babysteps}.
	 */
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
	 * Erzeugt eine neue Instanz von {@link Babysteps}, welche mit dem übergebenen {@link HTMLEditor} verknüpft wird.
	 * Hierbei wird die Standard-Methode, welche aufgerufen wird, wenn die Zeit ausläuft, geladen, welche
	 * das {@link TextArea} sperrt und den vorherigen Text wiederherstellt.
	 * @param htmlEditor Der {@link HTMLEditor} mit welchem diese Instanz verknüpft wird.
	 */
	public Babysteps(HTMLEditor htmlEditor){
		currentlyEditableHtmlEditor = htmlEditor;
		oldText = htmlEditor.getHtmlText();
		timeline = new Timeline(new KeyFrame(Duration.seconds(1),
				ae -> {seconds.set(seconds.get()+1);
					if(!hasTimeLeft.get()){
						currentlyEditableHtmlEditor.setHtmlText(oldText.replace("contenteditable=\"true\"", "contenteditable=\"false\""));
						htmlEditor.setDisable(true);
						//System.out.println("fertig\n"+htmlEditor.getHtmlText());
					}}));
	}

	/**
	 * Startet den Timer welcher von der übergebenen Zeit bis 0 runter zählt,
	 * wenn dei Zeit abgelaufen ist, wird die mit der {@link Babysteps#setOnTimeout(Runnable) setOnTimeout}-Methode gesetzten oder die Standard-Methode ausgeführt.
	 * @param maxSeconds Die Zeit in Sekunden, wie lange der Timer laufen soll.
	 */
	public void startTimer(int maxSeconds){
		this.maxSeconds.set(maxSeconds);
		timeline.setCycleCount(maxSeconds);
		timeline.play();
	}

	/**
	 * Stoppt den Timer.
	 */
	public void stopTimer(){
		timeline.stop();
	}

	/**
	 * Setzt die verstrichene Zeit auf 0, aber stoppt den Timer nicht.
	 */
	public void reset(){
		seconds.set(0);
	}

	/**
	 * Stoppt den Timer und setzt die vertrichene Zeit auf 0.
	 */
	public void resetAndStop(){
		timeline.stop();
		seconds.set(0);
	}

	/**
	 * Setzt die verstrichene Zeit auf 0 und startet ihn erneut.
	 */
	public void restart(){
		timeline.stop();
		seconds.set(0);
		startTimer(maxSeconds.get());
	}

	/**
	 * Ändert das verknüpfte {@link TextArea}
	 * @param t Das zu verknüpfende {@link TextArea}
	 */
	public void setCurrentlyEditableArea(TextArea t){
		currentlyEditableArea = t;
		oldText = t.getText();
	}

	/**
	 * Gibt die verbleibende Zeit, bis der Timer ausgelaufen ist, zurück.
	 * @return Die verbleibenden Sekunden
	 */
	public int getRemainingSeconds(){
		return remaining.intValue();
	}


	/**
	 * Gibt die Verbleibene Zeit als {@link NumberBinding} zurück.
	 * @return Die NumberBinding
	 */
	public NumberBinding getRemainingBinding(){
		return remaining;
	}

	/**
	 * Gibt einen Wahrheitswert zurück, welcher zeigt, ob der Timer noch läuft.
	 * @return der Wahrheitswert
	 */
	public boolean hasTimeLeft(){
		return hasTimeLeft.get();
	}

	/**
	 * Gibt den Text, welcher in der Textkomponente während der verknüpfung enthalten war, zurück.
	 * @return Der ursprüngliche Text
	 */
	public String getOldText(){
		return oldText;
	}

	/**
	 * Setzt die Methode, welche aufgerufen wird, wenn der Timer ausläuft.
	 * @param onTimeout Die Methode, welche aufgerufen wird, wenn der Timer ausläuft.
	 */
	public void setOnTimeout(Runnable onTimeout) {
		this.onTimeout = onTimeout;
	}
}
