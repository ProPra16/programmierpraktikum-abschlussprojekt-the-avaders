## Berichte und Dokumentationen

In diesem Ordner sind Berichte und Dokumentationen zu finden. Das
[Nutzerhandbuch](https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-the-avaders/blob/master/doc/TDDT_Handbuch.pdf)
beschreibt dabei die Verwendung des Programms und die
[Spezifikation](https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-the-avaders/blob/master/doc/User%20Guide%20-%20Exercise%20catalogues%20in%20XML.pdf)
das Erstellen neuer Konfigurationsdateien.

In den Unterordnern sind Dokumentationen zu den einzelnen packages zu finden. Diese Unterteilen das Programm in folgende Bereiche:

###	catalogueLoader
Dieses Package ist für das laden und interpretieren der XML-Dateien zuständig und verwaltet auch die Programmeinstellungen.
[Spezifikation](https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-the-avaders/blob/master/doc/User%20Guide%20-%20Exercise%20catalogues%20in%20XML.pdf)

### konfig
Dieses Package sollte eingetlich die Programmeinstellungen verwalten.
Zur besseren Verwendbarkeit wurde es zur Wertetabelle verallgemeinert.
in der aktuellen Implementation wird zwar eine Instanz erstellt, diese jedoch noch nicht vollständig verwendet.
[Dokumentation](https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-the-avaders/blob/master/doc/konfig/Projekt7_Bericht_konfig.pdf)

### extensions
Alle Erweiterungen, beispielsweise BabySteps, wurden in diesem Package isoliert. Ihre Verwendung ist dem
[Nutzerhandbuch](https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-the-avaders/blob/master/doc/TDDT_Handbuch.pdf)
zu entnehmen.

### gui
Neben dem Hauptprogramm mit dem Controller und den ausgelagerten .fxml- und .css-Dateien sind die Bestandteile der
grafischen Benutzeroberfläche in diesem Package zu finden. Auch hier ist die Verwendung dem
[Nutzerhandbuch](https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-the-avaders/blob/master/doc/TDDT_Handbuch.pdf)
zu entnehmen.

### projekt und statistik
Diese beiden Packages sind bisher nur Entwürfe und noch nicht in das Programm eingebunden.
Sie sollen zur weiteren Entkopplung der einzelnen Komponenten beitragen.
[Dokumentation](https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-the-avaders/blob/master/doc/statistik/Projekt7_Entwurf_Statistik.pdf)

### testen
Dieses Package ist für das Testen zuständig und damit die Schnittstelle zur Virtual-Kata-Lib. Es erhält die Quelltexte,
lässt diese kompilieren und ausführen und gibt die Ergebnisse zurück.
[Dokumentation](https://github.com/ProPra16/programmierpraktikum-abschlussprojekt-the-avaders/blob/master/doc/testen/Projekt7_Bericht_Testen.pdf)
