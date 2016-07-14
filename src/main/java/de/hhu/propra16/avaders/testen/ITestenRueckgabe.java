package de.hhu.propra16.avaders.testen;

import vk.core.api.CompilerResult;
import vk.core.api.TestResult;

public interface ITestenRueckgabe {
	/**
	 * Gibt einen {@link CompilerResult} mit weiteren Daten über mögliche Compilerfehler zurück.
	 * 
	 * @return der {@link CompilerResult}
	 */
	public CompilerResult getCompilerResult();
	
	/**
	 * Gibt einen {@link TestResult} mit weiteren Daten über die Testergebnisse zurück.
	 * 
	 * @return der {@link TestResult}
	 */
	public TestResult getTestResult();
	
	/**
	 * Gibt zurück, ob der Test erfolgreich war, d. h. ob es keine Compilerfehler gab und alle Tests erfolgreich waren.
	 * 
	 * @return ob der Test erfolgreich war
	 */
	public default boolean isSuccessful() {
		return !this.getCompilerResult().hasCompileErrors() && this.getTestResult().getNumberOfFailedTests() == 0;
	}
	
	/**
	 * Gibt zurück, ob ein neuer fehlschlagender Test vorliegt, d. h. ob es Compilerfehler oder genau einen fehlschlagenden Test gibt.
	 * 
	 * @return ob ein neuer fehlschlagender Test vorliegt
	 */
	public default boolean hasNewFailingTest() {
		return this.getCompilerResult().hasCompileErrors() || this.getTestResult().getNumberOfFailedTests() == 1;
	}
}
