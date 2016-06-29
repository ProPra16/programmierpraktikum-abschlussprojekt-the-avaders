package de.hhu.propra16.avaders.testen;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerResult;

import java.time.Duration;
import java.util.Collection;

public class CompilerResultDummy implements CompilerResult {
	private final boolean hasCompileErrors;
	
	public CompilerResultDummy(final boolean hasCompileErrors) {
		this.hasCompileErrors = hasCompileErrors;
	}
	
	@Override
	public boolean hasCompileErrors() {
		return hasCompileErrors;
	}
	
	@Override
	public Duration getCompileDuration() {
		return null;
	}
	
	@Override
	public Collection<CompileError> getCompilerErrorsForCompilationUnit(CompilationUnit cu) {
		return null;
	}
}
