package de.hhu.propra16.avaders.logik;

public class Logik {
	private ILogikKonfig fKonfig;
	private ITester fTester;
	private int schritt;
	private boolean atdd;
	private boolean refactor2;

      //Konstruktor: Erstellt eine Instanz von Logik
	public Logik(ITester tester, ILogikKonfig konfig) {
			fKonfig = konfig;                                       //Uebergabewerte speichern
			fTester = tester;                                       //und Variablen initialisieren
			schritt = 0;
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
			case 0: return redWeiter(uebergabe);
			case 1: return greenWeiter(uebergabe);
			case 2: return refactor1Weiter(uebergabe);
			case 3: return refactor2Weiter(uebergabe);
			case 4: endeWeiter();
		}
		return null;
	}

      //Versucht im Schritt RED, weiterzugehen
	ITestenRueckgabe redWeiter(ITestenUebergabe uebergabe) {
		ITestenRueckgabe rueckgabe = fTester.Testen(uebergabe);       //Test ausführen
		if (rueckgabe.testBestanden()) schritt = 1;                   //Red -> Green
		return rueckgabe;                                             //Testergebnisse zurückgeben
	}

      //Versucht im Schritt GREEN, weiterzugehen
	ITestenRueckgabe greenWeiter(ITestenUebergabe uebergabe) {
		ITestenRueckgabe rueckgabe = fTester.Testen(uebergabe);       //Test ausführen
		if (rueckgabe.testBestanden()) schritt = 2;                   //Green -> Refactor 1
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
			if (rueckgabe.testBestanden()) schritt = 3;             //Refactor 1 -> Refactor 2
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
				if (fKonfig.naechsterTest()) schritt = 0;          //Ende -> Red
				else schritt = 5;                                  //Entwicklungsende
			} else {
				return rueckgabe;                                  //Testergebnisse zurückgeben
			}
		} else {
			schritt = 0;                                             //Ende -> Red
		}
		return null;                                                   //Kein Test ausgeführt, nichts zurückgeben
	}

      //Startet den Zyklus mit einem neuen Test
	void endeWeiter() {
		schritt = 0;                                                   //Ende -> Red
	}

      //Bricht den aktuellen Test ab und setzt das Programm zum letzten bestandendenen Test zurück
	public void abbrechen() {
		schritt = 0;
	}

      //Bricht den aktuellen Test ab und setzt das Programm zum letzten bestandendenen Akzeptanztest zurück
	public void zuruecksetzen() {
		schritt = 0;
		fKonfig.ErstenTestAuswaehlen();
	}
}