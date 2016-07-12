package de.hhu.propra16.avaders.gui.view;

import vk.core.api.*;

import java.util.Collection;

public class TestResultDisplay {
	public static String showTestResults(TestResult result){
		if(result == null){
			return "testresult is null";
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
