package de.hhu.propra16.avaders.extensions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import vk.core.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		Assert.assertEquals(5, tracking.getTimeForGREEN(),1);
	}

	@Test
	public void countRED() throws Exception{
		tracking.setState(RED);
		tracking.startRED();
		Thread.sleep(6000);
		tracking.finishedGREEN();
		tracking.finishedRED();
		Assert.assertEquals(0, tracking.getTimeForGREEN());
		Assert.assertEquals(6,tracking.getTimeForRED(),1);
	}

	@Test
	public void countREFACTOR() throws Exception{
		tracking.setState(CODE_REFACTOR);
		tracking.startREFACTOR1();
		Thread.sleep(4000);
		tracking.finishedREFACTOR1();
		Assert.assertEquals(4, tracking.getTimeForREFACTOR1(),1);
	}

	@Test
	public void testCycle() throws Exception{
		tracking.setState(GREEN);
		tracking.startGREEN();
		Thread.sleep(2000);
		tracking.finishedStepAndMoveOn(true);
		Assert.assertEquals(2, tracking.getTimeForGREEN(),1);
		tracking.startREFACTOR1();
		Thread.sleep(4000);
		tracking.finishedStepAndMoveOn(true);
		Assert.assertEquals(4, tracking.getTimeForREFACTOR1(),1);
		tracking.startREFACTOR2();
		Thread.sleep(3000);
		tracking.finishedStepAndMoveOn(true);
		Assert.assertEquals(3, tracking.getTimeForREFACTOR2(), 1);
		tracking.startACCEPTANCE();
		Thread.sleep(5000);
		tracking.finishedStepAndMoveOn(true);
		Assert.assertEquals(5, tracking.getTimeForACCEPTANCE(), 1);
		tracking.startRED();
		Thread.sleep(2000);
		tracking.finishedRED();
		Assert.assertEquals(2, tracking.getTimeForRED(),1);
	}

	@Test
	public void testCompileError() throws Exception{
		tracking.setState(RED);
		CompilationUnit compilationUnit = new CompilationUnit("penis", "public class penis{ " +
																			"public penis(){ new penis();}"+
																			"public int äää(){" +
																				"return \"hohoho\";" +
																			"} " +
																			"private void öö(){" +
																				"String[][] dsa = machzurück(3); return new penis();" +
																			"}" +
																			"protected int[][] machzurück(int öärg;){}" +
																		"}", false);
		JavaStringCompiler compiler = CompilerFactory.getCompiler(compilationUnit);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		Collection<CompileError> compilerErrorsForCompilationUnit = compilerResult.getCompilerErrorsForCompilationUnit(compilationUnit);
		tracking.addCompileExceptions(compilerErrorsForCompilationUnit);
		tracking.printCMap();
	}

	@Test
	public void diffTest() throws Exception{
		List<String> origin = new ArrayList<>();
		List<String> current = new ArrayList<>();
		origin.add("public class penis{");
		origin.add("static äää(){return}");
		origin.add("public  pribate nonstatic öö(){");
		origin.add("machzurück();}");
		origin.add("}");

		current.add("public class penis{");
		current.add("static äää(){");
		current.add("	return;");
		current.add("}");
		current.add("public private nonstatic öö(){");
		current.add("machzurück();}");
		current.add("}");
		current.add("");

		tracking.diff(origin,current, "Brot");
	}
}
