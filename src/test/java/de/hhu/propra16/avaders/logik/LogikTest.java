package de.hhu.propra16.avaders.logik;

import de.hhu.propra16.avaders.konfig.IKonfigEinstellung;
import de.hhu.propra16.avaders.konfig.IKonfigWerte;
import de.hhu.propra16.avaders.konfig.KonfigEinstellung;
import de.hhu.propra16.avaders.konfig.KonfigWerte;
import de.hhu.propra16.avaders.testen.CompilationUnitDummy;
import de.hhu.propra16.avaders.testen.CompilerResultDummy;
import de.hhu.propra16.avaders.testen.TestResultDummy;
import de.hhu.propra16.avaders.testen.TestenRueckgabeDummy;
import de.hhu.propra16.avaders.testen.TesterDummy;
import org.junit.Assert;
import org.junit.Test;
import vk.core.api.CompilationUnit;

public class LogikTest {
	@Test
	public void testOneCycle() {
		ILogik logik = new Logik(new TesterDummy(), new KonfigWerte());
		
		/* RED */
		
		// Start step should be RED
		Assert.assertEquals(logik.getSchritt(), Step.RED);
		
		// No advance without failed tests
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 0))));
		Assert.assertEquals(logik.getSchritt(), Step.RED);
		
		// Advance with at least one failed test
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1))));
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
		
		/* GREEN */
		
		// No advance with compilation error
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(true), new TestResultDummy(0, 0))));
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
		
		// No advance with failed tests
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1))));
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
		
		// Advance without failed tests
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(1, 0))));
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR1);
		
		/* REFACTOR1 */
		
		// No advance with compilation error
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(true), new TestResultDummy(0, 0))));
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR1);
		
		// No advance with failed tests
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1))));
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR1);
		
		// Advance without failed tests
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(1, 0))));
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR2);
		
		/* REFACTOR2 */
		
		// No advance with compilation error
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(true), new TestResultDummy(0, 0))));
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR2);
		
		// No advance with failed tests
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1))));
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR2);
		
		// Advance without failed tests
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(1, 0))));
		Assert.assertEquals(logik.getSchritt(), Step.RED);
	}
	
	@Test
	public void testOneATDDCycle() {
		IKonfigWerte werte = new KonfigWerte();
		IKonfigEinstellung einstellung = new KonfigEinstellung();
		einstellung.wertSetzen("True");
		werte.einstellungEintragen("ATDD", einstellung);
		ILogik logik = new Logik(new TesterDummy(), werte);
		
		CompilationUnit acceptanceTestFail = new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1)));
		CompilationUnit acceptanceTestPass = new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(1, 0)));
		
		/* ACCEPTANCE_RED */
		
		// Start step should be ACCEPTANCE_RED
		Assert.assertEquals(logik.getSchritt(), Step.ACCEPTANCE_RED);
		
		// No advance without failed tests
		logik.weiter(
			new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(true), new TestResultDummy(0, 0))),
			new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 0)))
		);
		Assert.assertEquals(logik.getSchritt(), Step.ACCEPTANCE_RED);
		
		// Advance with at least one failed test
		logik.weiter(
			new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1))),
			acceptanceTestFail
		);
		Assert.assertEquals(logik.getSchritt(), Step.RED);
		
		/* Continue to REFACTOR2 */
		
		logik.weiter(
			new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1))),
			acceptanceTestFail
		);
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
		
		logik.weiter(
			new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(1, 0))),
			acceptanceTestFail
		);
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR1);
		
		logik.weiter(
			new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(1, 0))),
			acceptanceTestFail
		);
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR2);
		
		/* REFACTOR2 */
		
		// Advance to RED with failing acceptance test
		logik.weiter(
			new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(1, 0))),
			acceptanceTestFail
		);
		Assert.assertEquals(logik.getSchritt(), Step.RED);
		
		/* Continue to REFACTOR2 */
		
		logik.weiter(
			new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(1, 1))),
			acceptanceTestFail
		);
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
		
		logik.weiter(
			new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(2, 0))),
			acceptanceTestPass
		);
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR1);
		
		logik.weiter(
			new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(2, 0))),
			acceptanceTestPass
		);
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR2);
		
		/* REFACTOR2 */
		
		// Advance to ACCEPTANCE_RED with passing acceptance test
		logik.weiter(
			new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(2, 0))),
			acceptanceTestPass
		);
		Assert.assertEquals(logik.getSchritt(), Step.ACCEPTANCE_RED);
	}
	
	@Test
	public void testAdvanceFromRedWithCompilationError() {
		ILogik logik = new Logik(new TesterDummy(), new KonfigWerte());
		
		// Start step should be RED
		Assert.assertEquals(logik.getSchritt(), Step.RED);
		
		// Advance with compilation errors
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(true), new TestResultDummy(0, 0))));
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
	}
	
	@Test
	public void testCancelGreen() {
		ILogik logik = new Logik(new TesterDummy(), new KonfigWerte());
		
		// Start step should be RED
		Assert.assertEquals(logik.getSchritt(), Step.RED);
		
		// Advance
		logik.weiter(new CompilationUnitDummy(new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1))));
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
		
		// Cancel GREEN and go back to RED
		logik.abbrechen();
		Assert.assertEquals(logik.getSchritt(), Step.RED);
	}
}
