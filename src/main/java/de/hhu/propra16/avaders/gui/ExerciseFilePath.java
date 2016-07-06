package de.hhu.propra16.avaders.gui;

import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static de.hhu.propra16.avaders.Main.getPrimaryStage;

/**
 * Created by Batman140 on 02.07.2016.
 */
public class ExerciseFilePath implements IExerciseFileService{
    /**
     * opens a window to choose an exercise (fxml-file)
     * @return path of selected file or null if no file selected
     **/
    @Override
    public Path getExercise() {
        FileChooser window = new FileChooser();
        window.setTitle("Choose exercise");
        window.getExtensionFilters().add( new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File exerciseFile = window.showOpenDialog(getPrimaryStage());
        if(exerciseFile != null)
            return Paths.get(exerciseFile.getAbsolutePath());
        System.err.println("No file selected! getExcercise() returns null");
        return null;
    }
}
