//Test-Dummy, der das Interface ILogik implementiert,
//beim Aufruf von weiter() zurückgibt,
//dass der aktuelle Test nicht bestanden wurde

package de.hhu.propra16.avaders.logik;

import de.hhu.propra16.avaders.testen.TestenRueckgabeDummyTrue;
import de.hhu.propra16.avaders.testen.ITestenRueckgabe;
import de.hhu.propra16.avaders.testen.ITestenUebergabe;

public LogikDummyFalse implements ILogik {
    public ITestenRueckgabe weiter(ITestenUebergabe uebergabe) {
      return new TestenRueckgabeDummyFalse();
    }

    public void abbrechen() { }
    public void zuruecksetzen() { }
}
