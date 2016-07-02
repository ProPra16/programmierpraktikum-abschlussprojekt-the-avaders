package de.hhu.propra16.avaders.konfig;

/**
 * Verwaltet einen Eintrag in KonfigWerte
 * Der Wert wird zuerst gesetzt und kann dann abgefragt werden
 * @author Florian-Dt
 */
public class KonfigEintrag implements IKonfigEintrag {
	private String fWert;
	private boolean gesetzt;

	/**
	 * Erstellt eine Instanz von KonfigEintrag
	 * @param <i>Keine</i>
	 */
	public KonfigEintrag() {
		fWert = "";
		gesetzt = false;
	}

	/*
	 * Setzt den Wert des Eintrags. Kann nicht mit dem Konstruktor zusammengelegt werden,
       * da dieser nicht im Interface gefordert werden kann.
	 * @param wert Der Wert, auf den der Wert des Eintrags gesetzt werden soll
	 * @return <i>Nichts</i>
	 */
	public void wertSetzen(String wert) {
		fWert = wert;
		gesetzt = true;
	}

	/*
	 * Gibt falls möglich den Wert als Boolean zurück, wirft sonst eine Exception
	 * @param <i>Keine</i>
	 * @return boolean Der Wert des Eintrags als Wahrheitswert
	 * @exception EintragKonvertierenFehler Falls der Wert nicht gesetzt ist oder nicht konvertiert werden konnte
	 */
	public boolean BooleanAbfragen() throws EintragKonvertierenFehler {
		if (!gesetzt) throw new EintragKonvertierenFehler(fWert);
		if (fWert.equals("True")) return true;
		if (fWert.equals("False")) return false;
		throw new EintragKonvertierenFehler(fWert);
	}

	/*
	 * Gibt falls möglich den Wert als Integer zurück, wirft sonst eine Exception
	 * @param <i>Keine</i>
	 * @return int Der Wert des Eintrags als Integer
	 * @exception EintragKonvertierenFehler Falls der Wert nicht gesetzt ist oder nicht konvertiert werden konnte
	 */
	public int IntegerAbfragen() throws EintragKonvertierenFehler {
		if (!gesetzt) throw new EintragKonvertierenFehler(fWert);
		try {
			return Integer.parseInt(fWert);
		} catch(Exception e) {
			throw new EintragKonvertierenFehler(fWert);
		}
	}

	/*
	 * Gibt falls möglich den Wert als String zurück, wirft sonst eine Exception
	 * @param <i>Keine</i>
	 * @return String Der Wert des Eintrags als Zeichenkette
	 * @exception EintragKonvertierenFehler Falls der Wert nicht gesetzt ist oder nicht konvertiert werden konnte
	 */
	public String StringAbfragen() throws EintragKonvertierenFehler {
		if (!gesetzt) throw new EintragKonvertierenFehler(fWert);
		return fWert;
	}
}
