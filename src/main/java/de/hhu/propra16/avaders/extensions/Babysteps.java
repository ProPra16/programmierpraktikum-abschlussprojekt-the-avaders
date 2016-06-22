package de.hhu.propra16.avaders.extensions;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

public class Babysteps {
    private Timeline timeline;
    private TextArea currentlyEditableArea;
    private String oldText;
    private int seconds = 0;
    private IntegerProperty remaining;
    private BooleanProperty hasTimeLeft;
    public Babysteps(TextArea t){
        currentlyEditableArea = t;
        oldText = currentlyEditableArea.getText();
        timeline = new Timeline(new KeyFrame( Duration.seconds(1), ae -> {seconds++; System.out.println("Seconds left: "+remaining.getValue()+"\nhasTimeLeft: "+hasTimeLeft.getValue());}));
    }

    public void startTimer(int maxSeconds){
        remaining = new SimpleIntegerProperty(maxSeconds-seconds);
        hasTimeLeft = new SimpleBooleanProperty(remaining.getValue() != 0);
        timeline.setCycleCount(maxSeconds);
        timeline.play();
    }

}
