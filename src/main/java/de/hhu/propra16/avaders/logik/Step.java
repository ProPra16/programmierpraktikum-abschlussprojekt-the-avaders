package de.hhu.propra16.avaders.logik;

/**
 * Gibt einen Schritt im Testzyklus an.
 */
public enum Step {
	/**
	 * <p>Es muss ein fehlschlagender Akzeptanztest geschrieben werden.</p>
	 * <p>In diesem Schritt darf nur der Akzeptanztest bearbeitet werden.</p>
	 * <p>Wird nur verwendet, wenn ATDD eingeschaltet ist.</p>
	 */
	ACCEPTANCE_RED,
	
	/**
	 * <p>Es muss ein fehlschlagender Test geschrieben werden.</p>
	 * <p>In diesem Schritt darf nur der Unit-Test bearbeitet werden.</p>
	 * <p>Es wird zu {@link #GREEN} übergegangen, sobald es (mindestens) einen fehlschlagenden Test gibt,
	 * oder wenn ein Compilerfehler auftritt - da die Tests vor der Implementierung geschrieben werden,
	 * existieren die zu testenden Methoden möglicherweise noch nicht.</p>
	 */
	RED,
	
	/**
	 * <p>Alle Tests müssen erfüllt werden.</p>
	 * <p>In diesem Schritt darf nur der normale Code bearbeitet werden.</p>
	 * <p>Es wird zu {@link #REFACTOR1} übergegangen, sobald kein Test mehr fehlschlägt.</p>
	 */
	GREEN,
	
	/**
	 * <p>Der geschriebene Code darf verbessert werden.</p>
	 * <p>In diesem Schritt darf nur der normale Code bearbeitet werden.</p>
	 * <p>Der Nutzer bestimmt, wann zum nächsten Schritt weitergegangen wird.
	 * Ist die REFACTOR2-Erweiterung aktiviert, wird mit {@link #REFACTOR2} fortgefahren, ansonsten mit {@link #RED}.</p>
	 */
	REFACTOR1,
	
	/**
	 * <p>Die geschriebenen Tests dürfen verbessert werden.</p>
	 * <p>In diesem Schritt darf nur der Unit-Test bearbeitet werden.</p>
	 * <p>Der Nutzer bestimmt, wann wieder zu {@link #RED} weitergegangen wird.
	 * Ist ATDD eingeschaltet und sind hiernach alle Akzeptanztests erfüllt, wird stattdessen zu {@link #ACCEPTANCE_RED} weitergegangen.</p>
	 */
	REFACTOR2,
}
