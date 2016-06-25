//Interface, dass von der Klasse Logik implementiert wird.

package de.hhu.propra16.avaders.logik;

import de.hhu.propra16.avaders.testen.ITester;
import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import de.hhu.propra16.avaders.testen.ITestenUebergabe;

interface ILogik {
    public ITestenRueckgabe weiter(ITestenUebergabe uebergabe);
    public void abbrechen();
    public void zuruecksetzen();
}
