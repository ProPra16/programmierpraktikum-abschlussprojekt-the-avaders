package de.hhu.propra16.avaders.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Batman140 on 02.07.2016.
 */
public interface IPhaseViewService {
    public void setTextAreas(TextArea userFieldRed, TextArea userFieldCode);
    public void setButtons(Button stepBack, Button stepFurther);
}
