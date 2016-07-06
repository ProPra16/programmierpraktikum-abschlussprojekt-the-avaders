package de.hhu.propra16.avaders.konfig;

/**
 * Implementierung von {@link IKonfigEinstellung}.
 */
public class KonfigEinstellung implements IKonfigEinstellung {
	private String wert;

	/**
	 * Erstellt eine leere {@link KonfigEinstellung}.
	 */
	public KonfigEinstellung() {
		wert = null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void wertSetzen(String wert) {
		this.wert = wert;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean booleanAbfragen() throws EinstellungKonvertierenException {
		if (wert == null) {
			throw new EinstellungKonvertierenException(new NullPointerException());
		} else if (wert.equals("True")) {
			return true;
		} else if (wert.equals("False")) {
			return false;
		} else {
			throw new EinstellungKonvertierenException(wert);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int integerAbfragen() throws EinstellungKonvertierenException {
		try {
			return Integer.parseInt(wert);
		} catch (NumberFormatException e) {
			throw new EinstellungKonvertierenException(wert, e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String stringAbfragen() throws EinstellungKonvertierenException {
		if (wert == null) {
			throw new EinstellungKonvertierenException(new NullPointerException());
		} else {
			return wert;
		}
	}
}
