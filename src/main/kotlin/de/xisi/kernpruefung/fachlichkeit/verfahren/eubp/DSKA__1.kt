package de.xisi.kernpruefung.fachlichkeit.verfahren.eubp

import de.xisi.kernpruefung.api.XisiKKSKernpruefung
import de.xisi.kernpruefung.fachlichkeit.AllgemeinePruefungen
import de.xisi.kernpruefung.fachlichkeit.SvPruefungen
import de.xisi.kernpruefung.utils.StringWalker

class DSKA__1 : XisiKKSKernpruefung() {
  override val type = "DSKA"
  override val version = "1"

  override val verwendeteSpezifikation = """
    - euBP_Anlage 2 - Datensätze und Datenbausteine im euBP-Verfahren - Finanzbuchhaltung (Version 3.5.0, Stand: 17.05.2024, gültig ab: 01.01.2025)
  """.trimIndent()
  override val releaseNotes = mapOf(
    "2025-04-14" to "Initiale Erfassung"
  )

  override fun typeMatches(datensatz: StringWalker) = datensatz.takeIfPresent(1, 4) == "DSKA"
  override fun versionMatches(datensatz: StringWalker) = datensatz.takeIfPresent(40, 41) == "01"

  override fun pruefeDatensatz(datensatz: StringWalker) {
    val laenge = datensatz.laenge
    // Prüfung der Länger weiter unten.

    pruefe("KE")
    val ke = datensatz.naechsteZeichen(4)
    if (ke != "DSKA")
      addFehler("DSKAv01", "Zulässig ist nur 'DSKA'.")

    // DSKA004

    pruefe("VF")
    val vf = datensatz.naechsteZeichen(5)
    if (vf != "EUBP ")
      addFehler("DSKAv05", "Zulässig ist 'EUBP '")


    pruefe("ABSN")
    val absn = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(absn))
      addFehler("DSKA020, DSKA024", "Absendernummer >$absn< ist ungültig")

    // DSKAv15, DSKAv17, DSKAv18

    pruefe("EPNR")
    val epnr = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(epnr))
      addFehler("DSKA030, DSKA034", "Empfängernummer >$epnr< ist ungültig")

    // DSKA032, DSKAv20, DSKAv22

    pruefe("VERNR")
    val vernr = datensatz.naechsteZeichen(2)
    if (vernr != "01")
      addFehler("DSKA042", "Zulässig ist nur der Wert „01“ bis zur Bekanntgabe einer neuen Versionsnummer.")


    pruefe("ED")
    val edDatum = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(edDatum))
      addFehler("DSKA052", "Das Erstellungsdatum muss logisch richtig sein.")
    val edUhrzeit = datensatz.naechsteZeichen(9)
    if (!AllgemeinePruefungen.valideUhrzeitOhneTrenner(edUhrzeit))
      addFehler("DSKA056", "Die Uhrzeit muss logisch richtig sein.")


    pruefe("FEKZ")
    val fekz = datensatz.naechsteZeichen(1)
    if (fekz != "0")
      addFehler("DSKAv35", "Bei Meldungen vom Arbeitgeber zur DSRV (VFMM = 'AGBPL' oder 'AGBPF') ist nur der Wert '0' zulässig.")

    // DSKA064

    pruefe("FEAN")
    val fean = datensatz.naechsteZeichen(1)
    if (fean != "0")
      addFehler("DSKA072", "Ist im Feld FEKZ der Wert „0“ angegeben, ist hier nur der Wert „0“ zulässig.")

    // DSKAv50, DSKAv52

    pruefe("ORG-ID")
    val orgid = datensatz.naechsteZeichen(100)
    if (SvPruefungen.istGrundstellungAN(orgid))
      addFehler("DSKA080", "Feldinhalt ist leer.")


    pruefe("ANZGLS")
    val anzgls = datensatz.naechsteZeichen(2)
    if (!SvPruefungen.istNumerisch(anzgls))
      addFehler("DSKA090", "Zulässig sind nur numerische Zeichen.")
    val anzglsInt = try {
      anzgls.toInt()
    } catch (e: NumberFormatException) {
      addFehler("XISI", "ANZGLS konnte nicht als Zahl interpretiert werden.")
      return
    }

    pruefe("DSKA")
    if ((laenge - 162 - 1 - anzglsInt * 211) % 289 != 0) {
      addFehler("DSKA910", "Zulässig ist nur die Datenlänge 162 + (ANZGLS * 211) + (ANBUCH * 289) + 1.")
      return
    }

    for (index in 1..anzglsInt) {

      pruefe("GLS ($index. Datenbaustein)")
      val gls = datensatz.naechsteZeichen(10)
      if (SvPruefungen.istGrundstellungN(gls))
        addFehler("DSKA095", "Feldinhalt ist Grundstellung.")
      if (!SvPruefungen.istNumerisch(gls))
        addFehler("XISI", "Feldinhalt darf nur aus Ziffern bestehen.")

      pruefe("GLSBEZ ($index. Datenbaustein)")
      val glsbez = datensatz.naechsteZeichen(150)
      if (SvPruefungen.istGrundstellungAN(glsbez))
        addFehler("DSKA096", "Feldinhalt ist Grundstellung.")

      pruefe("KENNZEA ($index. Datenbaustein)")
      val gueltigeKennzea = listOf(" ", "1", "2")
      val kennzea = datensatz.naechsteZeichen(1)
      if (kennzea !in gueltigeKennzea)
        addFehler("DSKA097", "Zulässig sind Grundstellung (Leerzeichen), \"1\" und \"2\".")

      pruefe("GLTAB ($index. Datenbaustein)")
      val gltab = datensatz.naechsteZeichen(8)
      if (!AllgemeinePruefungen.validesDatumOhneTrenner(gltab))
        addFehler("DSKA102", "Das Datum muss logisch richtig sein.")

      pruefe("GLTBIS ($index. Datenbaustein)")
      val gltbis = datensatz.naechsteZeichen(8)
      if (!AllgemeinePruefungen.validesDatumOhneTrenner(gltbis))
        addFehler("DSKA112", "Das Datum muss logisch richtig sein.")

      pruefe("VKIST ($index. Datenbaustein)")
      val vkist = datensatz.naechsteZeichen(1)
      if (!SvPruefungen.istPlusMinusOderGrundstellung(vkist))
        addFehler("DSKA144", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")

      pruefe("KIST ($index. Datenbaustein)")
      val kist = datensatz.naechsteZeichen(25)
      if (!SvPruefungen.istNumerischOderGrundstellung(kist))
        addFehler("DSKA145", "Zulässig sind nur numerische Zeichen oder Grundstellung.")

      pruefe("ANBUCH ($index. Datenbaustein)")
      val anbuch = datensatz.naechsteZeichen(8)
      if (!SvPruefungen.istNumerisch(anbuch))
        addFehler("DSKA160", "Zulässig sind nur numerische Zeichen.")

      val anbuchInt = try {
        anbuch.toInt()
      } catch (e: NumberFormatException) {
        addFehler("XISI", "ANBUCH konnte nicht als Zahl interpretiert werden.")
        return
      }

      for (anbuchIndex in 1..anbuchInt) {

        pruefe("BELEGDT ($index. Datenbaustein, $anbuchIndex. Unterbaustein)")
        val belegdt = datensatz.naechsteZeichen(8)
        if (!AllgemeinePruefungen.validesDatumOhneTrenner(belegdt))
          addFehler("DSKA172", "Das Datum muss logisch richtig sein.")

        pruefe("BUCHDT ($index. Datenbaustein, $anbuchIndex. Unterbaustein)")
        val buchdt = datensatz.naechsteZeichen(8)
        if (!SvPruefungen.istGrundstellungAN(buchdt) && !AllgemeinePruefungen.validesDatumOhneTrenner(buchdt))
          addFehler("DSKA182", "Wenn nicht Grundstellung, dann muss das Buchungsdatum logisch richtig sein.")

        pruefe("GKTONR ($index. Datenbaustein, $anbuchIndex. Unterbaustein)")
        val gktonr = datensatz.naechsteZeichen(35)
        // keine Prüfung

        pruefe("BUCHTEXT ($index. Datenbaustein, $anbuchIndex. Unterbaustein)")
        val buchtext = datensatz.naechsteZeichen(100)
        // keine Prüfung

        pruefe("BELEGNR ($index. Datenbaustein, $anbuchIndex. Unterbaustein)")
        val belegnr = datensatz.naechsteZeichen(36)
        // keine Prüfung

        pruefe("BELEG ($index. Datenbaustein, $anbuchIndex. Unterbaustein)")
        val gueltigeBeleg = listOf("0", "1")
        val beleg = datensatz.naechsteZeichen(1)
        if (beleg !in gueltigeBeleg)
          addFehler("DSKA210", "Zulässig sind \"0\" und \"1\".")

        pruefe("VIST ($index. Datenbaustein, $anbuchIndex. Unterbaustein)")
        val vist = datensatz.naechsteZeichen(1)
        if (!SvPruefungen.istPlusMinusOderGrundstellung(vist))
          addFehler("DSKA219", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")

        pruefe("IST ($index. Datenbaustein, $anbuchIndex. Unterbaustein)")
        val ist = datensatz.naechsteZeichen(25)
        if (!SvPruefungen.istNumerischOderGrundstellung(ist))
          addFehler("DSKA220", "Zulässig sind nur numerische Zeichen oder Grundstellung.")

        pruefe("STSATZ ($index. Datenbaustein, $anbuchIndex. Unterbaustein)")
        val stsatz = datensatz.naechsteZeichen(5)
        if (!SvPruefungen.istNumerischOderGrundstellung(stsatz))
          addFehler("DSKA230", "Zulässig sind nur numerische Zeichen oder Grundstellung.")

        pruefe("STKZ ($index. Datenbaustein, $anbuchIndex. Unterbaustein)")
        val stkz = datensatz.naechsteZeichen(70)
        // keine Prüfung
      }
    }

    pruefe("DSENDE")
    val gueltigeDsende = listOf("E", " ")
    val dsende = datensatz.naechsteZeichen(1)
    if (dsende !in gueltigeDsende)
      addFehler("DSKA850", "Zulässig sind \"E\" oder Grundstellung.")
  }
}
