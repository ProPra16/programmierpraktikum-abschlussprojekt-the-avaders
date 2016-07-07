package de.hhu.propra16.avaders.testen;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerResult;

public class CompilerResultDummy implements CompilerResult {
	private final boolean hasCompileErrors;
	private final Duration compileDuration;
	private final Map<? extends CompilationUnit, ? extends Collection<CompileError>> compilerErrors;
	
	public CompilerResultDummy(
		final boolean hasCompileErrors,
		final Duration compileDuration,
		final Map<? extends CompilationUnit, ? extends Collection<CompileError>> compilerErrors
	) {
		this.hasCompileErrors = hasCompileErrors;
		this.compileDuration = compileDuration;
		this.compilerErrors = compilerErrors;
	}
	
	public CompilerResultDummy(final boolean hasCompileErrors, final Duration compileDuration) {
		this(hasCompileErrors, compileDuration, null);
	}
	
	public CompilerResultDummy(final boolean hasCompileErrors) {
		this(hasCompileErrors, null);
	}
	
	@Override
	public boolean hasCompileErrors() {
		return this.hasCompileErrors;
	}
	
	@Override
	public Duration getCompileDuration() {
		return this.compileDuration;
	}
	
	@Override
	public Collection<CompileError> getCompilerErrorsForCompilationUnit(final CompilationUnit cu) {
		return this.compilerErrors.get(cu);
	}
}
