package de.xisi.kernpruefung;

public class AufrufeInJava {
  public static void main(String[] args) {
    System.out.println("start");
    XisiKernpruefungsService xisiKernpruefung = new XisiKernpruefungsService();

    // ***** konfiguration
    xisiKernpruefung.konfiguriere().logsEin();
    //xisiKernpruefung.konfiguriere().logsAus();


    // ***** hinterlegte Meldungen
    xisiKernpruefung.loggeAktiveKernpruefungen();

    // eigene KP hinzufügen
    //xisiKernpruefung.fuegeImplementationHinzu()

    // ***** Prüfen
    String dsme = "DSME...";
    xisiKernpruefung.pruefeMeldung(dsme);
  }
}
