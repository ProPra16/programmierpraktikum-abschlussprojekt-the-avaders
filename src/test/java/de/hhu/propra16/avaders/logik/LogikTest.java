package de.hhu.propra16.avaders.logik;

import de.hhu.propra16.avaders.konfig.KonfigWerte;
import de.hhu.propra16.avaders.testen.CompilerResultDummy;
import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import de.hhu.propra16.avaders.testen.TestResultDummy;
import de.hhu.propra16.avaders.testen.TestenRueckgabeDummy;
import de.hhu.propra16.avaders.testen.TesterDummy;
import org.junit.Assert;
import org.junit.Test;

public class LogikTest {
	@Test
	public void testOneCycle() {
		ILogik logik = new Logik(new TesterDummy(new ITestenRueckgabe[] {
			// RED
			new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 0)),
			new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1)),
			
			// GREEN
			new TestenRueckgabeDummy(new CompilerResultDummy(true), new TestResultDummy(0, 0)),
			new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1)),
			new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(1, 0)),
			
			// REFACTOR1
			new TestenRueckgabeDummy(new CompilerResultDummy(true), new TestResultDummy(0, 0)),
			new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1)),
			new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(1, 0)),
			
			// REFACTOR2
			new TestenRueckgabeDummy(new CompilerResultDummy(true), new TestResultDummy(0, 0)),
			new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1)),
			new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(1, 0)),
		}), new KonfigWerte());
		
		/* RED */
		
		// Start step should be RED
		Assert.assertEquals(logik.getSchritt(), Step.RED);
		
		// No advance without failed tests
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.RED);
		
		// Advance with at least one failed test
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
		
		/* GREEN */
		
		// No advance with compilation error
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
		
		// No advance with failed tests
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
		
		// Advance without failed tests
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR1);
		
		/* REFACTOR1 */
		
		// No advance with compilation error
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR1);
		
		// No advance with failed tests
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR1);
		
		// Advance without failed tests
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR2);
		
		/* REFACTOR2 */
		
		// No advance with compilation error
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR2);
		
		// No advance with failed tests
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.REFACTOR2);
		
		// Advance without failed tests
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.RED);
	}
	
	@Test
	public void testAdvanceFromRedWithCompilationError() {
		ILogik logik = new Logik(new TesterDummy(new ITestenRueckgabe[] {
			new TestenRueckgabeDummy(new CompilerResultDummy(true), new TestResultDummy(0, 0)),
		}), new KonfigWerte());
		
		// Start step should be RED
		Assert.assertEquals(logik.getSchritt(), Step.RED);
		
		// Advance with compilation errors
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
	}
	
	@Test
	public void testCancelGreen() {
		ILogik logik = new Logik(new TesterDummy(new ITestenRueckgabe[] {
			new TestenRueckgabeDummy(new CompilerResultDummy(false), new TestResultDummy(0, 1)),
		}), new KonfigWerte());
		
		// Start step should be RED
		Assert.assertEquals(logik.getSchritt(), Step.RED);
		
		// Advance
		logik.weiter(null);
		Assert.assertEquals(logik.getSchritt(), Step.GREEN);
		
		// Cancel GREEN and go back to RED
		logik.abbrechen();
		Assert.assertEquals(logik.getSchritt(), Step.RED);
	}
}
