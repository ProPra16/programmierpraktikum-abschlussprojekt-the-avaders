package de.hhu.propra16.avaders.gui;


import de.hhu.propra16.avaders.gui.view.TestResultDisplay;
import de.hhu.propra16.avaders.logik.Logik;
import de.hhu.propra16.avaders.logik.Step;
import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import javafx.scene.control.TextArea;
import vk.core.api.CompilationUnit;

public class CompilationUnits {
	private CompilationUnit testUnit;
	private CompilationUnit classUnit;

	public CompilationUnits(CompilationUnit testUnit, CompilationUnit classUnit){
		this.testUnit  = testUnit;
		this.classUnit = classUnit;
	}

	public void update(Step mode, CompilationUnit unit){
		switch (mode){
			case RED:
			case TEST_REFACTOR: testUnit  = unit; break;
			case GREEN:
			case CODE_REFACTOR: classUnit = unit; break;
		}
	}

	public ITestenRueckgabe test(Logik logic){
		return logic.weiter(testUnit, classUnit);
	}

	public void showResultsOn(TextArea outputArea, ITestenRueckgabe results){
		outputArea.setText(TestResultDisplay.showTestResults(results.getTestResult())+
				TestResultDisplay.showCompilerResult(results.getCompilerResult(), testUnit) +
				TestResultDisplay.showCompilerResult(results.getCompilerResult(), classUnit));
	}
}
