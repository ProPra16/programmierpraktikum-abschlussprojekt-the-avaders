package de.hhu.propra16.avaders.extensions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import vk.core.api.*;

import java.util.Collection;

import static de.hhu.propra16.avaders.logik.Step.*;

public class TrackingTest {

	Tracking tracking;
	@Before
	public void initialize(){
		tracking = new Tracking();
	}
	@Test
	public void countGREEN() throws Exception{
		tracking.setState(GREEN);
		tracking.startGREEN();
		Thread.sleep(5000);
		tracking.finishedGREEN();
		Assert.assertEquals(5, tracking.getTimeForGREEN());
	}

	@Test
	public void countRED() throws Exception{
		tracking.setState(RED);
		tracking.startRED();
		Thread.sleep(6000);
		tracking.finishedGREEN();
		tracking.finishedRED();
		Assert.assertEquals(0, tracking.getTimeForGREEN());
		Assert.assertEquals(6,tracking.getTimeForRED());
	}

	@Test
	public void countREFACTOR() throws Exception{
		tracking.setState(REFACTOR1);
		tracking.startREFACTOR1();
		Thread.sleep(4000);
		tracking.finishedREFACTOR1();
		Assert.assertEquals(4, tracking.getTimeForREFACTOR1());
	}

	@Test
	public void testCycle() throws Exception{
		tracking.setState(GREEN);
		tracking.startGREEN();
		Thread.sleep(1000);
		tracking.finishedStepAndMoveOn(false);
		tracking.startRED();
		Thread.sleep(2000);
		tracking.finishedStepAndMoveOn(false);
		tracking.startREFACTOR1();
		Thread.sleep(1000);
		tracking.finishedREFACTOR1();
		Assert.assertEquals(1, tracking.getTimeForGREEN());
		Assert.assertEquals(2, tracking.getTimeForRED());
		Assert.assertEquals(1, tracking.getTimeForREFACTOR1());
	}

	@Test
	public void testCompileError() throws Exception{
		CompilationUnit compilationUnit = new CompilationUnit("penis", "public class penis{ public static äää(){return} pribate nonstatic öö(){machzurück();}}", false);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(compilationUnit);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		Collection<CompileError> compilerErrorsForCompilationUnit = compilerResult.getCompilerErrorsForCompilationUnit(compilationUnit);
		tracking.addCompileExceptions(compilerErrorsForCompilationUnit);
		tracking.randomPrintelnMethodlolRofl();
	}
}
