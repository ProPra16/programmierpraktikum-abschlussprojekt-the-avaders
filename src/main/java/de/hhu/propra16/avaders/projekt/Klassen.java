package de.hhu.propra16.avaders.projekt;

import java.util.List;
import java.util.ArrayList;
import vk.core.api.CompilationUnit;

/**
 * Verwaltet alle Klassen eines Projekts. Vor jedem Projektschritt müssen alle externen Änderungen
 * an einzelnen Klassen Quelltext zurückgespielt werden. Diese Klasse implementiert das Interface
 * {@link IKlassen}, aus dem die meisten Funktionen stammen.
 *
 * @author Florian-Dt
 * @see Klasse
 */

public class Klassen implements IKlassen {
	private List<IKlasse> fKlassen;

	/**
	 * Erstellt eine neue, leere Klassenstruktur
	 * @return Neue Instanz von Klassen
	 */
	public Klassen() {
		fKlassen = new ArrayList<>();
	}

	/**
	 * {@inheritDoc} 
	 */
	public void neueKlasse(String pKlassenname,  Klassentyp pTyp) {
		for (IKlasse klasse: fKlassen)
			if (klasse.namenAbfragen().equals(pKlassenname))
				fKlassen.remove(klasse);
		fKlassen.add(new Klasse(pKlassenname, pTyp));
	}

	/**
	 * {@inheritDoc} 
	 */
	public void quelltextSetzen(String pKlassenname, String pQuelltext) throws KlasseNichtGefunden {
		for (IKlasse klasse: fKlassen)
			if (klasse.namenAbfragen().equals(pKlassenname)) {
				klasse.quelltextSetzen(pQuelltext);
				return;
			}
		throw new KlasseNichtGefunden(pKlassenname);
	}

	/**
	 * {@inheritDoc} 
	 */
	public String quelltextAbfragen(String pKlassenname) throws KlasseNichtGefunden {
		for (IKlasse klasse: fKlassen)
			if (klasse.namenAbfragen().equals(pKlassenname))
				return klasse.quelltextAbfragen();
		throw new KlasseNichtGefunden(pKlassenname);
	}

	/**
	 * {@inheritDoc} 
	 */
	public Klassentyp typAbfragen(String pKlassenname) throws KlasseNichtGefunden {
		for (IKlasse klasse: fKlassen)
			if (klasse.namenAbfragen().equals(pKlassenname))
				return klasse.typAbfragen();
		throw new KlasseNichtGefunden(pKlassenname);
	}

	/**
	 * {@inheritDoc} 
	 */
	public String[] alleKlassennamen() {
		int anzahl = fKlassen.size();
		String[] liste = new String[anzahl];
		for (int i=0; i<anzahl; i++)
			liste[i] = fKlassen.get(i).namenAbfragen();
		return liste;
	}

	/**
	 * {@inheritDoc} 
	 */
	public CompilationUnit[] umwandeln() {
		int klassenAnzahl = fKlassen.size();
		CompilationUnit[] liste = new CompilationUnit[klassenAnzahl];
		for (int i=0; i<klassenAnzahl; i++)
			liste[i] = fKlassen.get(i).umwandeln();
		return liste;
	}
}
