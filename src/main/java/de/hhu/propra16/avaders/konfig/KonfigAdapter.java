package de.hhu.propra16.avaders.konfig;

import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;

/*
 * Klasse, die als Adapter zum XML-Lader fungieren soll
 * Erstellt mit einem XML-Lader eine Instanz von KonfigWerte
 * @author Florian-Dt
 */

public class KonfigAdapter {
	private IKonfigWerte fWerte;

	/*
	 * Erstelle eine neue Instanz von KonfigAdapter
	 * @param werte Eine Instanz von {@link ExerciseConfig}, aus dem die Einstellungen gelesen werden
	 */
	public KonfigAdapter(ExerciseConfig config) {
		fWerte = new KonfigWerte();
		setzeATDD(config.isAtdd());
		setzeRefactor2(true);        //Bisher noch nicht in ExerciseConfig implementiert
	}

	/*
	 * Setzt die Einstellung ATDD
	 * @param wert Wert, auf den die Einstellung ATDD gesetzt werden soll
	 * @return <i>Nichts</i>
	 */
	public void setzeATDD(boolean wert) {
		IKonfigEintrag eintrag = new KonfigEintrag();
		eintrag.wertSetzen(konvertieren(wert));
		fWerte.einstellungEintragen("ATDD", eintrag);
	}

	/*
	 * Setzt die Einstellung Refactor2
	 * @param wert Wert, auf den die Einstellung Refactor2 gesetzt werden soll
	 * @return <i>Nichts</i>
	 */
	public void setzeRefactor2(boolean wert) {
		IKonfigEintrag eintrag = new KonfigEintrag();
		eintrag.wertSetzen(konvertieren(wert));
		fWerte.einstellungEintragen("Refactor2", eintrag);
	}

	/*
	 * Gibt die Instanz von ExerciseConfig zurück, in die die Einstellungen geladen wurden
	 * @param <i>Keine</i>
	 * @return IKonfigWerte
	 */
	public IKonfigWerte konfigWerte() {
		return fWerte;
	}

	/* Wandelt den angegebenen Wahrheitswert in eine Zeichenkette um */
	String konvertieren(boolean wert) {
		if (wert) return "True";
		return "False";
	}
}
