package de.hhu.propra16.avaders.konfig;

import java.util.HashMap;

/**
 * Implementierung von {@link IKonfigWerte}.
 */
public class KonfigWerte implements IKonfigWerte {
	private HashMap<String, IKonfigEinstellung> einstellungen;

	/**
	 * Erstellt eine leere Instanz von {@link KonfigWerte}.
	 */
	public KonfigWerte() {
		einstellungen = new HashMap<>();
	}

	/**
	 * {@inheritDoc}
	 */
	public void einstellungEintragen(String einstellung, IKonfigEinstellung wert) {
		einstellungen.put(einstellung, wert);
	}

	/**
	 * {@inheritDoc}
	 */
	public IKonfigEinstellung einstellungAbfragen(String einstellung) throws EinstellungNichtGefundenException {
		if (!einstellungen.containsKey(einstellung)) {
			throw new EinstellungNichtGefundenException(einstellung);
		}
		return einstellungen.get(einstellung);
	}
}
