package de.hhu.propra16.avaders.projekt;

import java.io.IOException;
import de.hhu.propra16.avaders.konfig.IWerteTabelle;

/**
 * Ermöglicht das Speichern des Projekts mit seinen Klassen und Statuswerten in eine Datei
 * und umgekehrt das Laden aus einer Datei. Dieses Interface wird von der Klasse {@link Datei}
 * implementiert.
 *
 * @author Florian-Dt
 */

public interface IDatei {
	/**
	 * Speichert das übergebene Projekt mit den übergebenen Klassen.
	 *
	 * @param pKlassen Klassenstruktur, die alle zu speichernden Klassen verwaltet
	 * @param pStatusWerte Wertetabelle, die alle zu speichernden Statuswerte enthält
	 * @throws IOException Falls es zu Schreibfehlern im Zusammenhang mit der Datei kommt
	 * @throws KlasseNichtGefunden Falls ein interner Fehler aufgetreten ist
	 */
	public void Speichern(IKlassen pKlassen, IWerteTabelle pStatusWerte) throws IOException, KlasseNichtGefunden;

	/**
	 * Lädt ein Projekt aus einer Datei in die übergebene Klassenstruktur und die übergebene
	 * Wertetabelle
	 *
	 * @param pKlassen Klassenstruktur, in die alle geladenen Klassen eingetragen werden sollen
	 * @param pStatusWerte Wertetabelle, in der alle geladenen Statuswerte gespeichert werden
	 * @throws IOException Falls es zu LEsefehlern im Zusammenhang mit der Datei kommt
	 * @throws EOFAufgetreten Falls die Datei fehlerhaft oder unvollständig ist
	 * @throws KlasseNichtGefunden Falls ein interner Fehler aufgetreten ist
	 */
	public void Laden(IKlassen pKlassen, IWerteTabelle pStatusWerte) throws IOException, EOFaufgetreten, KlasseNichtGefunden;
}
