package de.hhu.propra16.avaders.projekt;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Testet, ob  {@link Klassen} die Quelltexte einer Klasse ordnungsgemäß verwaltet. Dazu werden
 * mehrere Klassen mit Quelltext erstellt und anschließend der Quelltext abgefragt.
 *
 * @author Florian-Dt
 * @see KlassenTestName
 * @see KlassenTestTyp
 */

public class KlassenTestQuelltext {
	private Klassen klassen;
	private String[] fName = {"foo", "bar", "fiz", "buz"};
	private Klassentyp[] fTyp = {Klassentyp.QUELLTEXT, Klassentyp.PRUEFUNG, Klassentyp.QUELLTEXT, Klassentyp.AKZEPTANZTEST};
	private String[] fQuelltext = {"Before", "Begin", "End", "After"};

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
		klassen.quelltextSetzen(fName[0], fQuelltext[0]);
		assertEquals(true, klassen.quelltextAbfragen(fName[0]).equals(fQuelltext[0]));
	}

	@Test
	public void test1() throws KlasseNichtGefunden {
		klassen.quelltextSetzen(fName[1], fQuelltext[1]);
		assertEquals(true, klassen.quelltextAbfragen(fName[1]).equals(fQuelltext[1]));
	}

	@Test
	public void test2() throws KlasseNichtGefunden {
		klassen.quelltextSetzen(fName[2], fQuelltext[2]);
		assertEquals(true, klassen.quelltextAbfragen(fName[2]).equals(fQuelltext[2]));
	}

	@Test
	public void test3() throws KlasseNichtGefunden {
		klassen.quelltextSetzen(fName[3], fQuelltext[3]);
		assertEquals(true, klassen.quelltextAbfragen(fName[3]).equals(fQuelltext[3]));
	}
}
