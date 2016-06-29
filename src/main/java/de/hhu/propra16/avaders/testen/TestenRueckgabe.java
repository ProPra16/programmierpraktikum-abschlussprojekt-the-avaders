package de.hhu.propra16.avaders.testen;

import vk.core.api.CompilerResult;
import vk.core.api.TestResult;

public class TestenRueckgabe implements ITestenRueckgabe {
	private final CompilerResult compilerResult;
	private final TestResult testResult;
	
	public TestenRueckgabe(final CompilerResult compilerResult, final TestResult testResult) {
		this.compilerResult = compilerResult;
		this.testResult = testResult;
	}
	
	@Override
	public CompilerResult getCompilerResult() {
		return compilerResult;
	}
	
	@Override
	public TestResult getTestResult() {
		return testResult;
	}
}
