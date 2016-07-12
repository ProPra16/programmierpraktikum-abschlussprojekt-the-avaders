package de.hhu.propra16.avaders.gui;

import de.hhu.propra16.avaders.catalogueLoader.exercises.Exercise;

import java.nio.file.Path;

/**
 * Created by Batman140 on 11.07.2016.
 */
public class Cycle {
	private Exercise exercise;
	private Path currentTest;
	private Path currentClass;

	public Cycle(Exercise exercise){
		this.exercise = exercise;
	}

	public void setCurrentTest(Path currentTest) {
		this.currentTest = currentTest;
	}

	public void setCurrentClass(Path currentClass) {
		this.currentClass = currentClass;
	}
}
