package de.hhu.propra16.avaders.projekt;

import java.io.IOException;
import java.text.ParseException;
import de.hhu.propra16.avaders.logik.ILogik;
import de.hhu.propra16.avaders.konfig.IWerteTabelle;
import de.hhu.propra16.avaders.konfig.EintragNichtGefunden;
import de.hhu.propra16.avaders.statistik.IStatistik;

/**
 * Diese Klasse soll ein vollständiges Projekt mit Quelltexten und Statuswerten verwalten. Ein
 * solches Projekt kann in eine {@Datei} geschrieben oder aus ihr gelesen werden. Außerdem kann
 * so die grafische Oberfläche besser getrennt werden. Diese verwaltet nur noch ein Projekt, keine
 * einzelnen Klassen mehr.
 */

public class Projekt {
	private ILogik fLogik;
	private IKlassen fKlassen;
	private IWerteTabelle fStatusWerte;
	private IStatistik fStatistik;

	/**
	 * Erstellt eine neues Projekt mit den übergebenen Instanzen
	 * @param pLogik Der Logikbaustein, der im Projekt verwendet werden soll
	 * @param pKlassen Die Datenstruktur, in der alle Quelltexte des Projekts verwaltet werden sollen
	 * @param pStatusWerte Die Wertetabelle, die das Projekt verwenden soll
	 * @return Das neue Projekt
	 */
	public Projekt(ILogik pLogik, IKlassen pKlassen, IWerteTabelle pStatusWerte, IStatistik pStatistik) {
		fLogik = pLogik;
		fKlassen = pKlassen;
		fStatusWerte = pStatusWerte;
		fStatistik = pStatistik;
	}

	/**
	 * {@InheritedDoc}
	 */
	public void zuruecksetzen() {
		fLogik.zuruecksetzen(fKlassen, fStatusWerte, fStatistik);
	}

	/**
	 * {@InheritedDoc}
	 */
	public void weiter() {
		fLogik.weiter(fKlassen, fStatusWerte, fStatistik);
	}

	/**
	 * {@inheritDoc} 
	 */
	public boolean boolAbfragen(String pName) throws EintragNichtGefunden {
		return fStatusWerte.boolAbfragen(pName);
	}

	/**
	 * {@inheritDoc} 
	 */
	public int intAbfragen(String pName) throws EintragNichtGefunden, ParseException {
		return fStatusWerte.intAbfragen(pName);
	}

	/**
	 * {@inheritDoc} 
	 */
	public String stringAbfragen(String pName) throws EintragNichtGefunden {
		return fStatusWerte.stringAbfragen(pName);
	}

	/**
	 * {@InheritedDoc}
	 */
	public void speichern(IDatei pDatei) throws IOException, KlasseNichtGefunden {
		pDatei.speichern(fKlassen, fStatusWerte);
	}

	/**
	 * {@InheritedDoc}
	 */
	public void laden(IDatei pDatei) throws IOException, EOFaufgetreten, KlasseNichtGefunden {
		pDatei.speichern(fKlassen, fStatusWerte);
	}
}
