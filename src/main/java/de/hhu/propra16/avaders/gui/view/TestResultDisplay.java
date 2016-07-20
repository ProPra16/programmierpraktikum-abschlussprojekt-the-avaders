package de.hhu.propra16.avaders.gui.view;

import vk.core.api.*;

import java.util.Collection;

/**
 * A class which holds methods for displaying error and failure messages according to tests
 */
public class TestResultDisplay {
	/**
	 * Loads testResults in a String
	 * @param result The TestresultObject with failure-information
	 * @return A String with the failure-information to be shown
     */
	public static String showTestResults(TestResult result){
		if(result == null){
			return "";
		}
		String output;
		output = "Tests:" + "\n" +
				"> Successfull: " + result.getNumberOfSuccessfulTests() + "\n" +
				"> Failed: " + result.getNumberOfFailedTests() + "\n" +
				"> Ignored: " + result.getNumberOfIgnoredTests() + "\n" +
				"> Duration: " + result.getTestDuration() + "\n\n" ;
		Collection<TestFailure> testFailures = result.getTestFailures();
		for(TestFailure failure : testFailures){
			output += "TestFailures:" + "\n" +
					  "> Class: " + failure.getTestClassName() + "\n" +
					  "> Method: " + failure.getMethodName()  + "\n" +
					  "> Message: " + failure.getMessage()  + "\n\n";
		}
		return output;
	}

	/**
	 * Loads CompilerResults depending on a compilationUnit in a String
	 * @param result The CompilerResult which holds the error-information
	 * @param unit   The to the tests accordinf compilationUnits
     * @return A String with the error-information to be shown
     */
	public static String showCompilerResult(CompilerResult result, CompilationUnit unit){
		String output = "";
		Collection<CompileError> compileFailures = result.getCompilerErrorsForCompilationUnit(unit);
		for(CompileError error : compileFailures){
			output += "CompileErrors:" + "\n" +
					  "> Line: <L=" + error.getLineNumber() + ",C="+ error.getColumnNumber() +"> " + error.getCodeLineContainingTheError() + "\n" +
                      "> Message: " + error.getMessage() + "\n\n";
		}
		return output;
	}
}
