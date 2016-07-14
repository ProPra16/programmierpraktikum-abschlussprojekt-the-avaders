package de.hhu.propra16.avaders.gui;


import de.hhu.propra16.avaders.extensions.Tracking;
import de.hhu.propra16.avaders.gui.view.TestResultDisplay;
import de.hhu.propra16.avaders.logik.Logik;
import de.hhu.propra16.avaders.logik.Step;
import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import javafx.scene.control.TextArea;
import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.TestFailure;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class combines a compilationUnit for a non-Test-Class and a Test-Class
 */
public class CompilationUnits {
	private CompilationUnit testUnit;
	private CompilationUnit classUnit;
	private Collection<TestFailure>  testFailures;
	private Collection<CompileError> compileErrorsForTest;
	private Collection<CompileError> compileErrorsForClass;


	/**
	 * Constructor initializes the units
	 * @param testUnit   CompilationUnit for test-class
	 * @param classUnit  CompilationUnit for non-test-class
     */
	public CompilationUnits(CompilationUnit testUnit, CompilationUnit classUnit){
		this.testUnit  = testUnit;
		this.classUnit = classUnit;
	}

	/**
	 * Adds compileErrors and testFailures to tracking-extension if enabled
	 * @param tracking The Tracking-extension
     */
	public void addToTracking(Tracking tracking){
		if(compileErrorsForClass != null  && compileErrorsForClass.size() > 0)
			tracking.addCompileExceptions(compileErrorsForClass);
		if(compileErrorsForTest != null  && compileErrorsForTest.size() > 0)
			tracking.addCompileExceptions(compileErrorsForTest);
		if(testFailures != null && testFailures.size() > 0)
			tracking.addTestExceptions(testFailures);
	}

	/**
	 * Sets unit according to current step
	 * @param mode  The current step
	 * @param unit  The unit which should be assigened according to current step
     */
	public void update(Step mode, CompilationUnit unit){
		switch (mode){
			case RED:
			case TEST_REFACTOR: testUnit  = unit; break;
			case GREEN:
			case CODE_REFACTOR: classUnit = unit; break;
		}
	}

	/**
	 * Tries to move further in cycle by invoking logic with current units
	 * @param logic The method to move phase further
	 * @return TestResults determined in logic
     */
	public ITestenRueckgabe test(Logik logic){
		ITestenRueckgabe results   = logic.weiter(testUnit, classUnit);
		if(logic.getSchritt() == Step.RED)
			logic.weiter(testUnit);
		if(results.getTestResult() != null) {
			this.testFailures = new ArrayList<TestFailure>();
			this.testFailures.addAll(results.getTestResult().getTestFailures());
		} else this.testFailures = null;

		if(results.getCompilerResult() != null && results.getCompilerResult().getCompilerErrorsForCompilationUnit(testUnit) != null) {
			this.compileErrorsForTest = new ArrayList<CompileError>();
			this.compileErrorsForTest.addAll(results.getCompilerResult().getCompilerErrorsForCompilationUnit(testUnit));
		} else compileErrorsForTest = null;

		if(results.getCompilerResult() != null && results.getCompilerResult().getCompilerErrorsForCompilationUnit(classUnit) != null){
			this.compileErrorsForClass = new ArrayList<CompileError>();
			this.compileErrorsForClass.addAll(results.getCompilerResult().getCompilerErrorsForCompilationUnit(classUnit));
		} else compileErrorsForClass = null;

		return results;
	}

	/**
	 * Showing testResults on a specified TextArea
	 * @param outputArea Target area where the results should be shown
	 * @param results    Current testResults
     */
	public void showResultsOn(TextArea outputArea, ITestenRueckgabe results){
		outputArea.setText(TestResultDisplay.showTestResults(results.getTestResult())+
				TestResultDisplay.showCompilerResult(results.getCompilerResult(), testUnit) +
				TestResultDisplay.showCompilerResult(results.getCompilerResult(), classUnit));
	}
}
