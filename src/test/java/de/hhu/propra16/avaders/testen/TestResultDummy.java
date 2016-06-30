package de.hhu.propra16.avaders.testen;

import vk.core.api.TestFailure;
import vk.core.api.TestResult;

import java.time.Duration;
import java.util.Collection;

public class TestResultDummy implements TestResult {
	private final int successful;
	private final int failed;
	private final int ignored;
	
	public TestResultDummy(final int successful, final int failed, final int ignored) {
		this.successful = successful;
		this.failed = failed;
		this.ignored = ignored;
	}
	
	@Override
	public int getNumberOfSuccessfulTests() {
		return successful;
	}
	
	@Override
	public int getNumberOfFailedTests() {
		return failed;
	}
	
	@Override
	public int getNumberOfIgnoredTests() {
		return ignored;
	}
	
	@Override
	public Duration getTestDuration() {
		return null;
	}
	
	@Override
	public Collection<TestFailure> getTestFailures() {
		return null;
	}
}
