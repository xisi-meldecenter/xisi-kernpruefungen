package de.xisi.kernpruefung;

import de.xisi.kernpruefung.api.Resultat;

public class AufrufeInJava {
  public static void main(String[] args) {
    System.out.println("start");
    XisiKernpruefungsService xisiKernpruefung = new XisiKernpruefungsService();

    // ***** konfiguration
    xisiKernpruefung.konfiguriere().logLevelInfo();
    //xisiKernpruefung.konfiguriere().logsAus();


    // ***** hinterlegte Meldungen
    xisiKernpruefung.loggeAktiveKernpruefungen();
    xisiKernpruefung.konfiguriere().wennKeineKPExistiertLoggeWarnung();

    // eigene KP hinzufügen
    //xisiKernpruefung.fuegeImplementationHinzu()

    // ***** Prüfen
    String dsme = "DSME...";
    Resultat resultat = xisiKernpruefung.pruefeMeldung(dsme);
    resultat.exceptionBeiFehlern();
    resultat.getFehler().forEach(fehler -> {
      System.out.println(fehler.getFeld() + " " + fehler.getCode() + " " + fehler.getMessage());
    });
    resultat.getHinweise(); //...
    resultat.getTechnischeFehler(); //...
  }
}
