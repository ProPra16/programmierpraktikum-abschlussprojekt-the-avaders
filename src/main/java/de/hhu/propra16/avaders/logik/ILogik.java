package de.hhu.propra16.avaders.logik;

import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import de.hhu.propra16.avaders.testen.ITester;
import vk.core.api.CompilationUnit;

public interface ILogik {
	/**
	 * Gibt den aktuellen Schritt im Testzyklus zurück.
	 * 
	 * @return der aktuelle Schritt
	 */
	public Step getSchritt();
	
	/**
	 * Kompiliert und testet die gegebenen {@link CompilationUnit}s und geht zum nächsten Schritt über,
	 * sofern alle Bedingungen erfüllt sind (siehe {@link Step} für Details).
	 * 
	 * @param sources die zu kompilierenden {@link CompilationUnit}s
	 * @return die Testergebnisse wie von {@link ITester#testen(CompilationUnit[])} zurückgegeben
	 */
	public ITestenRueckgabe weiter(CompilationUnit[] sources);
	
	/**
	 * Bricht den {@link Step#GREEN}-Schritt ab und geht zurück zu {@link Step#RED}.
	 * Dies wird vom Nutzer über die GUI veranlasst, wenn der vorher geschriebene Test nicht erfüllbar ist,
	 * oder von der Babysteps-Erweiterung, wenn der Test nicht innerhalb der vorgegebenen Zeit erfüllt wurde.
	 * 
	 * @throws IllegalStateException wenn der aktuelle Schritt nicht {@link Step#GREEN} ist
	 */
	public void abbrechen();
}
