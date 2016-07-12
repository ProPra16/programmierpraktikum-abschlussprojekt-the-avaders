package de.hhu.propra16.avaders.projekt;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

/**
 * Testet, ob  {@link Klassen} ordnungsgemäß Klasse mit ihrem Namen und Typ verwaltet. Dazu werden
 * mehrere Klassen erstellt und anschließend der Typ abgefragt. Dabei wird geprüft, ob die Daten in
 * der richtigen Reihenfolge zurückgegeben werden
 *
 * @author Florian-Dt
 * @see KlassenTestName
 * @see KlassenTestQuelltext
 */

@RunWith(Parameterized.class)
public class KlassenTestName {
	private Klassen fKlassen;
	private String fName;
	private Klassentyp fTyp;
	private String fQuelltext;

	public KlassenTestName(String pName, Klassentyp pTyp, String pQuelltext) {
		fName = pName;
		fTyp = pTyp;
		fQuelltext = pQuelltext;
		fKlassen = new Klassen();
	}

	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][]{
			{"foo", Klassentyp.QUELLTEXT, "bar"},
			{"fiz", Klassentyp.AKZEPTANZTEST, "buz"},
			{"begin", Klassentyp.PRUEFUNG, "end"}};
		return Arrays.asList(data);
	}

	@Test
	public void test() throws KlasseNichtGefunden {
		fKlassen.neueKlasse(fName, fTyp);
		assertEquals(true, fKlassen.typAbfragen(fName) == fTyp);
	}
}
