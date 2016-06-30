package de.hhu.propra16.avaders.logik;

import de.hhu.propra16.avaders.testen.ITester;
import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import vk.core.api.CompilationUnit;

/**
 * Implementiert die Logik hinter dem Testzyklus.
 */
public class Logik implements ILogik {
	private final ITester tester;
	private final boolean refactor2;
	private Step schritt;
	
	/**
	 * Erstellt eine neue Logik-Instanz.
	 * 
	 * @param tester der zu verwendende {@link ITester}
	 * @param konfig die zu verwendende {@link ILogikKonfig}
	 */
	public Logik(ITester tester, ILogikKonfig konfig) {
		this.tester = tester;
		refactor2 = konfig.einstellung("REFACTOR2").toBoolean();
		schritt = Step.RED;
	}
	
	@Override
	public Step getSchritt() {
		return schritt;
	}
	
	@Override
	public ITestenRueckgabe weiter(CompilationUnit[] sources) {
		ITestenRueckgabe rueckgabe = tester.testen(sources);
		switch (schritt) {
			case RED:
				redWeiter(rueckgabe);
				break;
			
			case GREEN:
				greenWeiter(rueckgabe);
				break;
			
			case REFACTOR1:
				refactor1Weiter(rueckgabe);
				break;
			
			case REFACTOR2:
				refactor2Weiter(rueckgabe);
				break;
			
			default:
				throw new IllegalStateException(String.format("Schritt %s wurde nicht behandelt", schritt));
		}
		return rueckgabe;
	}

	private void redWeiter(ITestenRueckgabe rueckgabe) {
		if (!rueckgabe.isSuccessful()) {
			schritt = Step.GREEN;
		}
	}

	private void greenWeiter(ITestenRueckgabe rueckgabe) {
		if (rueckgabe.isSuccessful()) {
			schritt = Step.REFACTOR1;
		}
	}

	private void refactor1Weiter(ITestenRueckgabe rueckgabe) {
		if (rueckgabe.isSuccessful()) {
			if (refactor2) {
				schritt = Step.REFACTOR2;
			} else {
				endeAufrufen(rueckgabe);
			}
		}
	}

	private void refactor2Weiter(ITestenRueckgabe rueckgabe) {
		if (rueckgabe.isSuccessful()) {
			endeAufrufen(rueckgabe);
		}
	}

	private void endeAufrufen(ITestenRueckgabe rueckgabe) {
		schritt = Step.RED;
	}

	@Override
	public void abbrechen() {
		if (schritt != Step.GREEN) {
			throw new IllegalStateException("Es darf nur während dem GREEN-Schritt abgebrochen werden");
		}
		schritt = Step.RED;
	}
}
