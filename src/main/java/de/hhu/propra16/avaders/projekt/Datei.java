package de.hhu.propra16.avaders.projekt;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import de.hhu.propra16.avaders.logik.Step;
import de.hhu.propra16.avaders.konfig.IWerteTabelle;

/**
 * Ermöglicht das Speichern des Projekts mit seinen Klassen und Statuswerten in eine Datei
 * und umgekehrt das Laden aus einer Datei. Diese Klasse implementiert das Interface
 * {@link IDatei}, aus dem die meisten Funktionen stammen.
 *
 * @author Florian-Dt
 * @see Klassen
 * @see StatusWerte
 */

public class Datei implements IDatei {
	private URI fAnschrift;

	/**
	 * Erstellt eine neue Instanz der Klasse Datei. Als Parameter wird die URI der Datei übergeben,
	 * die anschließend nicht mehr geändert werden kann. Für jeden Lade- und Speichervorgang sollte
	 * eine neue Instanz erstellt werden.
	 *
	 * @param pAnschrift Die URI der zu ladenden oder zu speichernden Datei
	 * @return Eine Instanz von Datei
	 */
	public Datei(URI pAnschrift) {
		fAnschrift = pAnschrift;
	}

	/**
	 * {@inheritDoc} 
	 */
	public void Speichern(IKlassen pKlassen, IWerteTabelle pStatusWerte) throws IOException, KlasseNichtGefunden {
		//Step schritt = projekte.schrittAbfragen();
		Path datei = Paths.get(fAnschrift);
		String[] klassenNamen = pKlassen.alleKlassennamen();
		int klassenAnzahl = klassenNamen.length;
		int Groesse = 4;
		for (int i=0; i<klassenAnzahl; i++)
			Groesse += klassePlatzbedarf(pKlassen, klassenNamen[i]);
		byte[] daten = new byte[Groesse];
		int Position = 4;
		intSpeichern(daten, 0, klassenAnzahl);
		for (int i=0; i<klassenAnzahl; i++)
			Position = klasseSpeichern(pKlassen, daten, Position, klassenNamen[i]);
		Files.write(datei, daten);
	}

	/**
	 * {@inheritDoc} 
	 */
	public void Laden(IKlassen pKlassen, IWerteTabelle pStatusWerte) throws IOException, EOFaufgetreten, KlasseNichtGefunden  {
		Path datei = Paths.get(fAnschrift);
		byte[] daten = Files.readAllBytes(datei);
		int position = 4;
		eofPruefen(daten, 4);
		int klassenAnzahl = intLaden(daten, 0);                                //Das erste DWord speichert die Anzahl der Klassen
		for (int nummer=0; nummer<klassenAnzahl; nummer++)                     //Danach wird jede Klasse einzeln geladen
			position = klasseLaden(pKlassen, daten, position);
	}

	//Lädt eine Klasse aus pDaten ab pPosition in pKlassen
	int klasseLaden(IKlassen pKlassen, byte[] pDaten, int pPosition) throws EOFaufgetreten, KlasseNichtGefunden {
		eofPruefen(pDaten, pPosition+8);
		int bTyp = intLaden(pDaten, pPosition);                            //Der Typ der Klasse ist als int im ersten DWord gespeichert
		Klassentyp typ = intZuKlassentyp(bTyp);                            //int in Klassentyp umwandeln
		pPosition += 4;
		int groesse = intLaden(pDaten, pPosition);                         //Länge des Klassennamens einlesen
		pPosition += 4;
		eofPruefen(pDaten, pPosition+groesse);
		String name = stringLaden(pDaten, pPosition, groesse);             //Klassennamen einlesen
		pPosition += groesse;
		eofPruefen(pDaten, pPosition+4);
		groesse = intLaden(pDaten, pPosition);                             //Länge des Quelltextes einlesen
		pPosition += 4;
		eofPruefen(pDaten, pPosition+groesse);
		String quelltext = stringLaden(pDaten, pPosition, groesse);        //Quelltext einlesen
		pPosition += groesse;
		pKlassen.neueKlasse(name, typ);
		pKlassen.quelltextSetzen(name, quelltext);
		return pPosition;
	}

	//Speichert die Klasse mit pName aus pKlasse ab pPosition in pDaten
	int klasseSpeichern(IKlassen pKlassen, byte[] pDaten, int pPosition, String pName) throws KlasseNichtGefunden {
		Klassentyp typ = pKlassen.typAbfragen(pName);                          //Klassentyp abfragen,
		int bTyp = klassentypZuInt(typ);                                       //in int umwandeln
		intSpeichern(pDaten, pPosition, bTyp);                                 //und speichern
		pPosition += 4;
		int groesse = pName.length();
		intSpeichern(pDaten, pPosition, groesse);                              //Länge des Klassennamens
		pPosition += 4;
		stringSpeichern(pDaten, pPosition, groesse, pName);                    //und den Klassennamen selbst speichern
		String quelltext = pKlassen.quelltextAbfragen(pName);
		groesse = quelltext.length();
		intSpeichern(pDaten, pPosition, groesse);                              //Länge des Quelltextes
		pPosition += 4;
		stringSpeichern(pDaten, pPosition, groesse, quelltext);                //und den Quelltext selbst speichern
		return pPosition;
	}

	//Gibt zurück, wie viele Bytes zum Speichern der Klasse mit pName benötigt werden
	int klassePlatzbedarf(IKlassen pKlassen, String pName) throws KlasseNichtGefunden {
		String quelltext = pKlassen.quelltextAbfragen(pName);
		return 12+pName.length()+quelltext.length();
	}

	//Prüft, ob das Dateiende erreicht wird, wenn ab pPosition gelesen wird
	void eofPruefen(byte[] pDaten, int pPosition) throws EOFaufgetreten {
		if (pDaten.length<pPosition)
			throw new EOFaufgetreten(pPosition);
	}

	//Lädt einen Integer-Wert ab pPosition aus pDaten und gibt es zurück
	int intLaden(byte[] pDaten, int pPosition) {
		return (pDaten[pPosition+3]<<24) | (pDaten[pPosition+2]<<16)
		 | (pDaten[pPosition+1]<<8) | pDaten[pPosition];
	}

	//Speichert pInt ab pPostition in pDaten. Vorher sollte eofPruefen(pDaten, pPosition+4) ausgeführt werden
	void intSpeichern(byte[] pDaten, int pPosition, int pInt) {
		pDaten[pPosition] = (byte) pInt;
		pDaten[pPosition+1] = (byte) (pInt>>8);
		pDaten[pPosition+2] = (byte) (pInt>>16);
		pDaten[pPosition+3] = (byte) (pInt>>24);
	}

	//Wandelt einen Klassentyp in einen Integer-Wert ein, der dann gespeichert werden kann
	int klassentypZuInt(Klassentyp pEnum) {
		return pEnum.ordinal();
	}

	//Wandelt einen (eingelesenen) Integer-Wert in einen Klassentyp um
	Klassentyp intZuKlassentyp(int pInt) {
		return Klassentyp.values()[pInt];
	}

	//Lädt einen String mit der Länge pGroesse ab pPosition aus pDaten und gibt ihn zurück
	//Vorher sollte pGroesse mit intLaden eingelesen werden
	String stringLaden(byte[] pDaten, int pPosition, int pGroesse) {
		String string = "";
		for (int i=0; i<pGroesse; i++)
			string += (char) (pDaten[pPosition+i]);
		return string;
	}

	//Speichert  pString mit pGroesse ab pPosition in pDaten. pGroesse muss vorher auf einen korrekten
	//Wert gesetzt werden. Außerdem sollte vorher intSpeichern mit pGroesse ausgeführt werden
	void stringSpeichern(byte[] pDaten, int pPosition, int pGroesse, String pString) {
		for (int i=0; i<pGroesse; i++)
			pDaten[pPosition+i] = (byte) pString.charAt(i);
	}
}
