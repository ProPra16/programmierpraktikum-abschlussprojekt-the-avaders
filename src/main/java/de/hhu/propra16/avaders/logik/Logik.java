package de.hhu.propra16.avaders.logik;

import de.hhu.propra16.avaders.konfig.IKonfigWerte;
import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import de.hhu.propra16.avaders.testen.ITester;
import vk.core.api.CompilationUnit;

/**
 * Implementiert die Logik hinter dem Testzyklus.
 */
public class Logik implements ILogik {
	private final ITester tester;
	private Step schritt;
	
	/**
	 * Erstellt eine neue Logik-Instanz.
	 * 
	 * @param tester der zu verwendende {@link ITester}
	 * @param konfig die zu verwendenden {@link IKonfigWerte}
	 */
	public Logik(ITester tester, IKonfigWerte konfig) {
		this.tester = tester;
		schritt = Step.RED;
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
	public ITestenRueckgabe weiter(CompilationUnit[] sources) {
		ITestenRueckgabe rueckgabe = tester.testen(sources);
		switch (schritt) {
			case RED:
				if (!rueckgabe.isSuccessful()) {
					schritt = Step.GREEN;
				}
				break;
			
			case GREEN:
				if (rueckgabe.isSuccessful()) {
					schritt = Step.REFACTOR1;
				}
				break;
			
			case REFACTOR1:
				if (rueckgabe.isSuccessful()) {
					schritt = Step.REFACTOR2;
				}
				break;
			
			case REFACTOR2:
				if (rueckgabe.isSuccessful()) {
					schritt = Step.RED;
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
