package de.hhu.propra16.avaders.gui;

import com.sun.org.apache.regexp.internal.REDebugCompiler;
import de.hhu.propra16.avaders.gui.phases.Phases;
import de.hhu.propra16.avaders.gui.view.TestResultDisplay;
import de.hhu.propra16.avaders.logik.Logik;
import de.hhu.propra16.avaders.logik.Step;
import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import javafx.scene.control.TextArea;
import vk.core.api.CompilationUnit;
import vk.core.api.TestResult;

public class CompilationUnits {
	private CompilationUnit testUnit;
	private CompilationUnit classUnit;



	public void update(Step mode, CompilationUnit unit){
		switch (mode){
			case RED:   testUnit = unit; classUnit = null; break;
			case GREEN:
			case CODE_REFACTOR: classUnit = unit; break;
			case TEST_REFACTOR: testUnit  = unit; break;
		}
	}

	public ITestenRueckgabe test(Step mode, Logik logic){
		if(mode == Step.RED)
			return logic.weiter(testUnit);
		return logic.weiter(testUnit, classUnit);
	}

	public void showResultsOn(TextArea outputArea, ITestenRueckgabe results){
		outputArea.setText(TestResultDisplay.showTestResults(results.getTestResult())+
				TestResultDisplay.showCompilerResult(results.getCompilerResult(), testUnit) +
				TestResultDisplay.showCompilerResult(results.getCompilerResult(), classUnit));
	}
}
