package de.hhu.propra16.avaders.konfig;

import java.util.HashMap;
import java.text.ParseException;

/**
 * Eine WerteTabelle Verwaltet eine Liste mit {@link WerteEintrag}. So können Einstellungen
 * und Statuswerte des Programms in einer Datenstruktur gespeichert werden, unabhängig
 * von ihrem {@link Datentyp}. Als Datentypen sind zur Zeit String und Integer möglich.
 * Diese Klasse ersetzt KonfigWerte, eine Klasse mit gleichem Zweck, die jedoch auf die
 * Speicherung von Einstellungsdaten spezialisiert war.
 *
 * @author Florian-Dt
 * 
 */

public class WerteTabelle implements IWerteTabelle {
	private HashMap<String, WerteEintrag> fTabelle;

	/**
	 * Erstellt eine neue, leere Statuswertetabelle
	 *
	 * @return Neue Instanz von WerteTabelle
	 */
	public WerteTabelle() {
		fTabelle = new HashMap<>();
	}

	/**
	 * {@inheritDoc} 
	 */
	public void boolSetzen(String pName, boolean pWert) {
		if (fTabelle.containsKey(pName))
			fTabelle.get(pName).boolSetzen(pWert);
		else
			fTabelle.put(pName, new WerteEintrag(pWert));
	}

	/**
	 * {@inheritDoc} 
	 */
	public void intSetzen(String pName, int pWert) {
		if (fTabelle.containsKey(pName))
			fTabelle.get(pName).intSetzen(pWert);
		else
			fTabelle.put(pName, new WerteEintrag(pWert));
	}

	/**
	 * {@inheritDoc} 
	 */
	public void stringSetzen(String pName, String pWert) {
		if (fTabelle.containsKey(pName))
			fTabelle.get(pName).stringSetzen(pWert);
		else
			fTabelle.put(pName, new WerteEintrag(pWert));
	}

	/**
	 * {@inheritDoc} 
	 */
	public boolean boolAbfragen(String pName) throws EintragNichtGefunden {
		if (fTabelle.containsKey(pName))
			return fTabelle.get(pName).boolAbfragen();
		else
			throw new EintragNichtGefunden(pName);
	}

	/**
	 * {@inheritDoc} 
	 */
	public int intAbfragen(String pName) throws EintragNichtGefunden, ParseException {
		if (fTabelle.containsKey(pName))
			return fTabelle.get(pName).intAbfragen();
		else
			throw new EintragNichtGefunden(pName);
	}

	/**
	 * {@inheritDoc} 
	 */
	public String stringAbfragen(String pName) throws EintragNichtGefunden {
		if (fTabelle.containsKey(pName))
			return fTabelle.get(pName).stringAbfragen();
		else
			throw new EintragNichtGefunden(pName);
	}

}
