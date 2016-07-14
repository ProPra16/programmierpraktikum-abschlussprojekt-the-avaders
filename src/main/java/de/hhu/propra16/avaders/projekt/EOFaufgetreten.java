package de.hhu.propra16.avaders.projekt;

/**
 * Exception, die geworfen wird, wenn eine Datenabfrage in einer Datei den zulässigen Addressbereich überschreitet
 * @author Florian-Dt
 */

public class EOFaufgetreten extends Exception {
	/**
	 * Wirft eine EOFaufgetreten-Exception
	 * @param pPosition Die angeforderte Datenposition, die außerhalb des zulässigen Addressbereichs liegt
	 */
	public EOFaufgetreten(int pPosition) {
		super(new Integer(pPosition).toString());
	}
}
