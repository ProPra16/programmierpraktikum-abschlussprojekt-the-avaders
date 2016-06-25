package de.hhu.propra16.avaders.logik;

import static de.hhu.propra16.avaders.logik.Logik.Step.*;

public class Logik {
	public static enum Step {
		RED,
		GREEN,
		REFACTOR1,
		REFACTOR2,
		END,
		DONE
	}
	
	private ILogikKonfig fKonfig;
	private ITester fTester;
	private Step schritt;
	private boolean atdd;
	private boolean refactor2;

      //Konstruktor: Erstellt eine Instanz von Logik
	public Logik(ITester tester, ILogikKonfig konfig) {
		fKonfig = konfig;                                       //Uebergabewerte speichern
		fTester = tester;                                       //und Variablen initialisieren
		schritt = RED;
		konfigLaden();
	}

	//Lädt die relevanten Einstellungen aus der Konfiguration
	public void konfigLaden() {
		ILogikKonfigDaten einstellung = fKonfig.Einstellung("ATDD");  //Einstellung holen
		atdd = einstellung.toBoolean();                               //und konvertieren
		einstellung = fKonfig.Einstellung("REFACTOR2");
		refactor2 = einstellung.toBoolean();
	}

      //weiter: wird von GUI aufgerufen, testet und geht gegebenenfalls einen Zyklenschritt weiter
	public ITestenRueckgabe weiter(ITestenUebergabe uebergabe) {
		switch(schritt) {                                             //Aktuellen Schritt ermitteln
			case RED: return redWeiter(uebergabe);
			case GREEN: return greenWeiter(uebergabe);
			case REFACTOR1: return refactor1Weiter(uebergabe);
			case REFACTOR2: return refactor2Weiter(uebergabe);
			case END: endeWeiter();
		}
		return null;
	}

      //Versucht im Schritt RED, weiterzugehen
	ITestenRueckgabe redWeiter(ITestenUebergabe uebergabe) {
		ITestenRueckgabe rueckgabe = fTester.Testen(uebergabe);       //Test ausführen
		if (rueckgabe.testBestanden()) schritt = GREEN;
		return rueckgabe;                                             //Testergebnisse zurückgeben
	}

      //Versucht im Schritt GREEN, weiterzugehen
	ITestenRueckgabe greenWeiter(ITestenUebergabe uebergabe) {
		ITestenRueckgabe rueckgabe = fTester.Testen(uebergabe);       //Test ausführen
		if (rueckgabe.testBestanden()) schritt = REFACTOR1;                   //Green -> Refactor 1
		return rueckgabe;                                             //Testergebnisse zurückgeben
	}

      //Versucht im Schritt REFACTOR1, weiterzugehen
	ITestenRueckgabe refactor1Weiter(ITestenUebergabe uebergabe) {
		ITestenRueckgabe rueckgabe = fTester.Testen(uebergabe);       //Test ausführen
		Refactor2Aufrufen(rueckgabe, uebergabe);                      //Zusatz Refactor 2 ggf. ausführen
		return rueckgabe;                                             //Testergebnisse zurückgeben
	}

      //Geht im Schritt REFACTOR1 weiter zu REFACTOR 2 oder zum Ende
	void Refactor2Aufrufen(ITestenRueckgabe rueckgabe, ITestenUebergabe uebergabe) {
		if (refactor2) {                                              //Zusatz: Refactor 2
			if (rueckgabe.testBestanden()) schritt = REFACTOR2;             //Refactor 1 -> Refactor 2
		} else {
			if (rueckgabe.testBestanden()) endeAufrufen(uebergabe); //Refactor 1 -> Ende
		}
	}

      //Versucht im Schritt REFACTOR2, weiterzugehen
	ITestenRueckgabe refactor2Weiter(ITestenUebergabe uebergabe) {
		ITestenRueckgabe  rueckgabe = fTester.Testen(uebergabe);       //Test ausführen
		if (rueckgabe.testBestanden()) endeAufrufen(uebergabe);        //Refactor 2 -> Ende
		return rueckgabe;                                              //Testergebnisse zurückgeben
	}

      //Geht weiter zum Ende
	ITestenRueckgabe endeAufrufen(ITestenUebergabe uebergabe) {
		if (atdd) {                                                    //Zusatz: Akzeptanztest ausführen
			ITestenRueckgabe rueckgabe = fTester.Testen(uebergabe);  //Testen
			if (rueckgabe.testBestanden()) {                         //Falls der Test bestanden wurde
				if (fKonfig.naechsterTest()) schritt = RED;          //Ende -> Red
				else schritt = DONE;                                  //Entwicklungsende
			} else {
				return rueckgabe;                                  //Testergebnisse zurückgeben
			}
		} else {
			schritt = RED;                                             //Ende -> Red
		}
		return null;                                                   //Kein Test ausgeführt, nichts zurückgeben
	}

      //Startet den Zyklus mit einem neuen Test
	void endeWeiter() {
		schritt = RED;                                                   //Ende -> Red
	}

      //Bricht den aktuellen Test ab und setzt das Programm zum letzten bestandendenen Test zurück
	public void abbrechen() {
		schritt = RED;
	}

      //Bricht den aktuellen Test ab und setzt das Programm zum letzten bestandendenen Akzeptanztest zurück
	public void zuruecksetzen() {
		schritt = RED;
		fKonfig.ErstenTestAuswaehlen();
	}
}
