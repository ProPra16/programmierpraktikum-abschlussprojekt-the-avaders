package de.hhu.propra16.avaders.projekt;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Testet, ob  {@link Klassen} ordnungsgemäß Klasse mit ihrem Namen und Typ verwaltet. Dazu werden
 * mehrere Klassen erstellt und anschließend der Typ abgefragt. Dabei wird geprüft, ob der richtige
 * Typ zurückgegeben wird
 *
 * @author Florian-Dt
 * @see KlassenTestTyp
 * @see KlassenTestQuelltext
 */

public class KlassenTestTyp {
	private Klassen klassen;
	private String[] fName = {"foo", "bar", "fiz", "buz"};
	private Klassentyp[] fTyp = {Klassentyp.QUELLTEXT, Klassentyp.PRUEFUNG, Klassentyp.QUELLTEXT, Klassentyp.AKZEPTANZTEST};

	@Before
	public void setup() {
		Klassen klassen = new Klassen();
		klassen.neueKlasse(fName[0], fTyp[0]);
		klassen.neueKlasse(fName[1], fTyp[1]);
		klassen.neueKlasse(fName[2], fTyp[2]);
		klassen.neueKlasse(fName[3], fTyp[3]);
	}

	@Test
	public void test0() throws KlasseNichtGefunden {
		assertEquals(true, klassen.typAbfragen(fName[0]) == fTyp[0]);
	}

	@Test
	public void test1() throws KlasseNichtGefunden {
		assertEquals(true, klassen.typAbfragen(fName[1]) == fTyp[1]);
	}

	@Test
	public void test2() throws KlasseNichtGefunden {
		assertEquals(true, klassen.typAbfragen(fName[2]) == fTyp[2]);
	}

	@Test
	public void test3() throws KlasseNichtGefunden {
		assertEquals(true, klassen.typAbfragen(fName[3]) == fTyp[3]);
	}
}
