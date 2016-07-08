package de.hhu.propra16.avaders.logik;

import java.util.Arrays;

import de.hhu.propra16.avaders.konfig.EinstellungNichtGefundenException;
import de.hhu.propra16.avaders.konfig.IKonfigWerte;
import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import de.hhu.propra16.avaders.testen.ITester;
import vk.core.api.CompilationUnit;

/**
 * Implementiert die Logik hinter dem Testzyklus.
 */
public class Logik implements ILogik {
	private final ITester tester;
	private final boolean atdd;
	private Step schritt;
	
	/**
	 * Erstellt eine neue Logik-Instanz.
	 * 
	 * @param tester der zu verwendende {@link ITester}
	 * @param konfig die zu verwendenden {@link IKonfigWerte}
	 */
	public Logik(ITester tester, IKonfigWerte konfig) {
		this.tester = tester;
		boolean atdd;
		try {
			atdd = konfig.einstellungAbfragen("ATDD").booleanAbfragen();
		} catch (EinstellungNichtGefundenException exc) {
			atdd = false;
		}
		this.atdd = atdd;
		schritt = this.atdd ? Step.ACCEPTANCE_RED : Step.RED;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Step getSchritt() {
		return schritt;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ITestenRueckgabe weiter(CompilationUnit... sources) {
		CompilationUnit[] normalSources;
		if (atdd) {
			normalSources = Arrays.copyOfRange(sources, 0, sources.length-1);
		} else {
			normalSources = sources;
		}
		ITestenRueckgabe rueckgabe = tester.testen(normalSources);
		ITestenRueckgabe acceptanceRueckgabe;
		switch (schritt) {
			case ACCEPTANCE_RED:
				acceptanceRueckgabe = tester.testen(sources);
				if (!acceptanceRueckgabe.isSuccessful()) {
					schritt = Step.RED;
				}
				break;
			
			case RED:
				if (!rueckgabe.isSuccessful()) {
					schritt = Step.GREEN;
				}
				break;
			
			case GREEN:
				if (rueckgabe.isSuccessful()) {
					schritt = Step.CODE_REFACTOR;
				}
				break;
			
			case CODE_REFACTOR:
				if (rueckgabe.isSuccessful()) {
					schritt = Step.TEST_REFACTOR;
				}
				break;
			
			case TEST_REFACTOR:
				if (rueckgabe.isSuccessful()) {
					if (atdd) {
						acceptanceRueckgabe = tester.testen(sources);
						schritt = acceptanceRueckgabe.isSuccessful() ? Step.ACCEPTANCE_RED : Step.RED;
					} else {
						schritt = Step.RED;
					}
				}
				break;
			
			default:
				throw new IllegalStateException(String.format("Schritt %s wurde nicht behandelt", schritt));
		}
		return rueckgabe;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void abbrechen() {
		if (schritt != Step.GREEN) {
			throw new IllegalStateException("Es darf nur während dem GREEN-Schritt abgebrochen werden");
		}
		schritt = Step.RED;
	}
}
