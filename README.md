# xisi-kernpruefungen

Ziel dieser Bibliothek ist es, möglichst alle gängigen elektronischen Meldungen im Bereich des deutschen Payroll-Wesens auf einfache und umfassende Art zu validieren.

## Aufruf

### In Java (ganz simpel)

```java
package de.my.software;

import de.xisi.kernpruefung;
import de.xisi.kernpruefung.api.Resultat;

public class AufrufeInJava {
  public static void main(String[] args) {
    // Initialisiert die Kernprüfung
    XisiKernpruefungsService xisiKernpruefung = new XisiKernpruefungsService();

    // Führt Prüfung aus
    String dsme = "DSME...";
    Resultat resultat = xisiKernpruefung.pruefeMeldung(dsme);
    
    // Wenn im Resultat ein Fehler steckt, wird an dieser Stelle eine Excetion geworfen
    resultat.exceptionBeiFehlern();
  }
}
```

### In Java (mit Konfiguration)

```java
package de.my.software;

import de.xisi.kernpruefung;
import de.xisi.kernpruefung.api.Resultat;

public class AufrufeInJava {
  public static void main(String[] args) {
    // Initialisiert die Kernprüfung
    XisiKernpruefungsService xisiKernpruefung = new XisiKernpruefungsService();

    // ***** konfiguration
    xisiKernpruefung.konfiguriere().logLevelWarn();
    xisiKernpruefung.konfiguriere().wennKeineKPExistiertLoggeWarnung();

    // ***** hinterlegte Meldungen anzeigen
    xisiKernpruefung.loggeAktiveKernpruefungen();

    // ***** eigene KP hinzufügen
    // xisiKernpruefung.fuegeImplementationHinzu(...)

    // ***** Prüfen
    String dsme = "DSME...";
    Resultat resultat = xisiKernpruefung.pruefeMeldung(dsme);
    
    // ***** Fehler auf Konsole schreiben
    resultat.getFehler().forEach(fehler -> {
      System.out.println(fehler.getFeld() + " " + fehler.getCode() + " " + fehler.getMessage());
    });
    resultat.getTechnischeFehler(); //...
    resultat.getHinweise(); //...
  }
}
```

### Verschiedene Aufrufe

```java
String dsme = "DSME...";

xisiKernpruefung.pruefeMeldung(dsme);                 // ermittelt den Typ und die Version
                                                      // der Meldung selbstständig
                                                      
xisiKernpruefung.pruefeMeldung(dsme, "DSME");         // ermittelt die Version für den übergebenen
                                                      // Typen der Meldung selbstständig

xisiKernpruefung.pruefeMeldung(dsme, "DSME", "11");   // nutzt zum Prüfen den übergebenen Typen
                                                      // und die übergebene Version
```
