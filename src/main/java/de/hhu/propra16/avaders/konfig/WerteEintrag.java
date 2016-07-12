package de.hhu.propra16.avaders.konfig;

import java.util.HashMap;
import java.text.ParseException

/**
 * Ein WerteEintrag speichert einen einzelnen Wert in seinem originalen {@link Datentypen} und
 * konvertiert ihn falls nötig in anderen Datentypen. Der Wert bleibt jedoch immer als
 * Original erhalten und wir aus diesem konvertiert. Instanzen von WerteEintrag werden in einer
 * {@link WerteTabelle} mit einem Namen versehen gespeichert und verwalten dort einzelne
 * Einstellungen oder Statuswerte des Programms
 *
 * @author Florian-Dt
 */

public class WerteEintrag implements IWerteEintrag {
	private boolean fWertBool;
	private boolean fVorhandenBool;
	private Datentyp fWertQuelle;
	private int fWertInt;
	private boolean fVorhandenInt;
	private String fWertString;
	private boolean fVorhandenString;

	/**
	 * Erstellt einen neuen WerteEintrag und initialisiert ihn mit dem übergebenen Wahrheitswert
	 *
	 * @param pWert Wahrheitswert, mit dem dieser WerteEintrag initialisiert wird
	 * @return Eine neue Instanz von WerteEintrag, die mit pWert initialisiert wurde
	 */
	public WerteEintrag(boolean pWert) {
		boolSetzen(pWert);
	}

	/**
	 * Erstellt einen neuen WerteEintrag und initialisiert ihn mit dem übergebenen Integer-Wert
	 *
	 * @param pWert Integer-Wert, mit dem dieser WerteEintrag initialisiert wird
	 * @return Eine neue Instanz von WerteEintrag, die mit pWert initialisiert wurde
	 */
	public WerteEintrag(int pWert) {
		intSetzen(pWert);
	}

	/**
	 * Erstellt einen neuen WerteEintrag und initialisiert ihn mit dem übergebenen String
	 *
	 * @param pWert String, mit dem dieser WerteEintrag initialisiert wird
	 * @return Eine neue Instanz von WerteEintrag, die mit pWert initialisiert wurde
	 */
	public WerteEintrag(String pWert) {
		stringSetzen(pWert);
	}

	/**
	 * {@inheritDoc} 
	 */
	public void boolSetzen(boolean pWert) {
		zuruecksetzen();
		fWertBool = pWert;
		fWertQuelle = Datentyp.BOOLEAN;
	}


	/**
	 * {@inheritDoc} 
	 */
	public void intSetzen(String pWert) {
		zuruecksetzen();
		fWertInt = pWert;
		fWertQuelle = Datentyp.INTEGER;
	}

	/**
	 * {@inheritDoc} 
	 */
	public void stringSetzen(String pWert) {
		zuruecksetzen();
		fWertString = pWert;
		fWertQuelle = Datentyp.STRING;
	}

	/**
	 * {@inheritDoc} 
	 */
	public int boolAbfragen() {
		if (!fVorhandenBool) inBoolKonvertieren()
		return fWertBool;
	}

	/**
	 * {@inheritDoc} 
	 */
	public int intAbfragen() throws ParseException {
		if (!fVorhandenInt) inIntKonvertieren()
		return fWertInt;
	}

	/**
	 * {@inheritDoc} 
	 */
	public String stringAbfragen() {
		if (!fVorhandenString) inStringKonvertieren()
		return fWertString;
	}

	//Setzt alle Vorhanden-Werte zurück. Die ist nötig, wenn der Wert neu gesetzt und damit
	//alle bisherigen Konvertierungen ungültig werden
	void zuruecksetzen() {
		fVorhandenBool = false;
		fVorhandenInt = false;
		fVorhandenString = false;
	}

	//Konvertiert den gespeicherten Wert in einen Boolean und speichert ihn ab
	void inIntKonvertieren() {
		switch (fWertQuelle)
			case Datentyp.INT;
				if (fWertInt==0)
					fWertBool = false;
				else
					fWertBool = true;
				break;
			case Datentyp.STRING:
				fWertInt = Boolean.parseBoolean(fWertString);
		}
		fVorhandenBool = true;
	}

	//Konvertiert den gespeicherten Wert in einen Integer und speichert ihn ab
	void inIntKonvertieren() throws ParseException {
		switch (fWertQuelle)
			case Datentyp.BOOLEAN;
				if (fWertBool)
					fWertInt = 1;
				else
					fWertInt = 0;
				break;
			case Datentyp.STRING:
				fWertInt = Integer.parseInt(fWertString);
		}
		fVorhandenInt = true;
	}

	//Konvertiert den gespeicherten Wert in einen String und speichert ihn ab
	void inStringKonvertieren() {
		switch (fWertQuelle)
			case Datentyp.BOOLEAN;
				fWertString = Boolean(fWertBool).toString();
				break;
			case Datentyp.INTEGER:
				fWertString = Integer(fWertInt).toString();
		}
		fVorhandenString = true;
	}
}
