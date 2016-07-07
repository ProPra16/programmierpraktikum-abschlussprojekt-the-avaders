package de.hhu.propra16.avaders.testen;

import java.time.Duration;
import java.util.Collection;

import vk.core.api.TestFailure;
import vk.core.api.TestResult;

public class TestResultDummy implements TestResult {
	private final int numberOfSuccessfulTests;
	private final int numberOfFailedTests;
	private final int numberOfIgnoredTests;
	private final Duration testDuration;
	private final Collection<TestFailure> testFailures;
	
	public TestResultDummy(
		final int numberOfSuccessfulTests,
		final int numberOfFailedTests,
		final int numberOfIgnoredTests,
		final Duration testDuration,
		final Collection<TestFailure> testFailures
	) {
		this.numberOfSuccessfulTests = numberOfSuccessfulTests;
		this.numberOfFailedTests = numberOfFailedTests;
		this.numberOfIgnoredTests = numberOfIgnoredTests;
		this.testDuration = testDuration;
		this.testFailures = testFailures;
	}
	
	public TestResultDummy(
		final int numberOfSuccessfulTests,
		final int numberOfFailedTests,
		final int numberOfIgnoredTests,
		final Duration testDuration
	) {
		this(numberOfSuccessfulTests, numberOfFailedTests, numberOfIgnoredTests, testDuration, null);
	}
	
	public TestResultDummy(
		final int numberOfSuccessfulTests,
		final int numberOfFailedTests,
		final int numberOfIgnoredTests
	) {
		this(numberOfSuccessfulTests, numberOfFailedTests, numberOfIgnoredTests, null);
	}
	
	public TestResultDummy(final int numberOfSuccessfulTests, final int numberOfFailedTests) {
		this(numberOfSuccessfulTests, numberOfFailedTests, 0);
	}
	
	@Override
	public int getNumberOfSuccessfulTests() {
		return numberOfSuccessfulTests;
	}
	
	@Override
	public int getNumberOfFailedTests() {
		return numberOfFailedTests;
	}
	
	@Override
	public int getNumberOfIgnoredTests() {
		return numberOfIgnoredTests;
	}
	
	@Override
	public Duration getTestDuration() {
		return testDuration;
	}
	
	@Override
	public Collection<TestFailure> getTestFailures() {
		return testFailures;
	}
}
