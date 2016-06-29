package de.hhu.propra16.avaders.logik;

/**
 * Gibt einen Schritt im Testzyklus an.
 */
public enum Step {
	/**
	 * Es muss ein fehlschlagender Test geschrieben werden.
	 * Es wird zu {@link #GREEN} übergegangen, sobald es (mindestens) einen fehlschlagenden Test gibt,
	 * oder wenn ein Compilerfehler auftritt - da die Tests vor der Implementierung geschrieben werden,
	 * existieren die zu testenden Methoden möglicherweise noch nicht.
	 */
	RED,
	
	/**
	 * Alle Tests müssen erfüllt werden.
	 * Es wird zu {@link #REFACTOR1} übergegangen, sobald kein Test mehr fehlschlägt.
	 */
	GREEN,
	
	/**
	 * Der geschriebene Code darf verbessert werden.
	 * Der Nutzer bestimmt, wann zum nächsten Schritt weitergegangen wird.
	 * Ist die REFACTOR2-Erweiterung aktiviert, wird mit {@link #REFACTOR2} fortgefahren, ansonsten mit {@link #RED}.
	 */
	REFACTOR1,
	
	/**
	 * Die geschriebenen Tests dürfen verbessert werden.
	 * Der Nutzer bestimmt, wann wieder zu {@link #RED} weitergegangen wird.
	 */
	REFACTOR2
}
