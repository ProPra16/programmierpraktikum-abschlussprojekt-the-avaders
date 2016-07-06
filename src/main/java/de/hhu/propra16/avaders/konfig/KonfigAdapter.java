package de.hhu.propra16.avaders.konfig;

import de.hhu.propra16.avaders.catalogueLoader.exercises.ExerciseConfig;

/**
 * Konvertiert Einstellungen aus einer {@link ExerciseConfig} in {@link IKonfigWerte}.
 * 
 * @author Florian-Dt
 */
public class KonfigAdapter {
	private IKonfigWerte werte;

	/**
	 * Erstellt einen neuen {@link KonfigAdapter}.
	 * 
	 * @param config die {@link ExerciseConfig}, aus der die Einstellungen gelesen werden
	 */
	public KonfigAdapter(ExerciseConfig config) {
		werte = new KonfigWerte();
		setzeATDD(config.isAtdd());
	}
	
	private String booleanZuString(boolean wert) {
		return wert ? "True" : "False";
	}

	private void setzeATDD(boolean wert) {
		IKonfigEinstellung einstellung = new KonfigEinstellung();
		einstellung.wertSetzen(booleanZuString(wert));
		werte.einstellungEintragen("ATDD", einstellung);
	}

	/**
	 * Gibt die {@link IKonfigWerte} zurück, die aus der {@link ExerciseConfig} erstellt wurden.
	 * @return die {@link IKonfigWerte} zurück, die aus der {@link ExerciseConfig} erstellt wurden
	 */
	public IKonfigWerte konfigWerte() {
		return werte;
	}
}
