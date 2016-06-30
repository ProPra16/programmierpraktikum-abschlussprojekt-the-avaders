package de.hhu.propra16.avaders.konfig;

/*
 * Klasse, die als Adapter zum XML-Lader fungieren soll
 * Der XML-Lader ruft dabei die Funktionen zum Setzen von Einstellungen auf
 * @author Florian-Dt
 */

public class KonfigAdapter {
	private IKonfigWerte fWerte;

	/*
	 * Erstelle eine neue Instanz von KonfigAdapter
	 * @param werte Eine Instanz von {@link IKonfigWerte}, in die die Einstellungen geschrieben werden
	 */
	public KonfigAdapter(IKonfigWerte werte) {
		fWerte = werte;
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

	/* Wandelt den angegebenen Wahrheitswert in eine Zeichenkette um */
	String konvertieren(boolean wert) {
		if (wert) return "True";
		return "False";
	}
}