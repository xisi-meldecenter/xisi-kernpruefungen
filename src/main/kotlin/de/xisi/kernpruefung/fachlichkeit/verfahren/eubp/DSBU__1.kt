@file:Suppress("UNUSED_VARIABLE")

package de.xisi.kernpruefung.fachlichkeit.verfahren.eubp

import de.xisi.kernpruefung.api.XisiKKSKernpruefung
import de.xisi.kernpruefung.fachlichkeit.AllgemeinePruefungen
import de.xisi.kernpruefung.fachlichkeit.SvPruefungen
import de.xisi.kernpruefung.utils.StringWalker

class DSBU__1 : XisiKKSKernpruefung() {
  override val type = "DSBU"
  override val version = "1"

  override val verwendeteSpezifikation = """
    - euBP_Anlage 2 - Datensätze und Datenbausteine im euBP-Verfahren - Finanzbuchhaltung (Version 3.5.0, Stand: 17.05.2024, gültig ab: 01.01.2025)
  """.trimIndent()
  override val releaseNotes = mapOf(
    "2025-04-14" to "Initiale Erfassung"
  )

  override fun typeMatches(datensatz: StringWalker) = datensatz.takeIfPresent(1, 4) == "DSBU"
  override fun versionMatches(datensatz: StringWalker) = datensatz.takeIfPresent(40, 41) == "01"

  override fun pruefeDatensatz(datensatz: StringWalker) {
    val laenge = datensatz.laenge
    if ((laenge - (454 + 1)) % 389 != 0) {
      addFehler("DSBU910", "Zulässig ist nur die Datenlänge 454 + (ANKTO * 389) + 1.")
      return
    }


    pruefe("KE")
    val ke = datensatz.naechsteZeichen(4)
    if (ke != "DSBU")
      addFehler("DSBUv01", "Zulässig ist nur 'DSBU'.")

    // DSBU004

    pruefe("VF")
    val vf = datensatz.naechsteZeichen(5)
    if (vf != "EUBP ")
      addFehler("DSBUv05", "Zulässig ist 'EUBP '")


    pruefe("ABSN")
    val absn = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(absn))
      addFehler("DSBU020, DSBU024", "Absendernummer >$absn< ist ungültig")

    // DSBUv15, DSBUv17, DSBUv18

    pruefe("EPNR")
    val epnr = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(epnr))
      addFehler("DSBU030, DSBU034", "Empfängernummer >$epnr< ist ungültig")

    // DSBU032, DSBUv20, DSBUv22

    pruefe("VERNR")
    val vernr = datensatz.naechsteZeichen(2)
    if (vernr != "01")
      addFehler("DSBU042", "Zulässig ist nur der Wert „01“ bis zur Bekanntgabe einer neuen Versionsnummer.")


    pruefe("ED")
    val edDatum = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(edDatum))
      addFehler("DSBU052", "Das Erstellungsdatum muss logisch richtig sein.")
    val edUhrzeit = datensatz.naechsteZeichen(9)
    if (!AllgemeinePruefungen.valideUhrzeitOhneTrenner(edUhrzeit))
      addFehler("DSBU056", "Die Uhrzeit muss logisch richtig sein.")


    pruefe("FEKZ")
    val fekz = datensatz.naechsteZeichen(1)
    if (fekz != "0")
      addFehler("DSBUv35", "Bei Meldungen vom Arbeitgeber zur DSRV (VFMM = 'AGBPL' oder 'AGBPF') ist nur der Wert '0' zulässig.")

    // DSBU064

    pruefe("FEAN")
    val fean = datensatz.naechsteZeichen(1)
    if (fean != "0")
      addFehler("DSBU072", "Ist im Feld FEKZ der Wert „0“ angegeben, ist hier nur der Wert „0“ zulässig.")

    // DSBUv50, DSBUv52

    pruefe("SYS-ID")
    val sysid = datensatz.naechsteZeichen(100)
    // keine Prüfung

    pruefe("ORG-ID")
    val orgid = datensatz.naechsteZeichen(100)
    if (SvPruefungen.istGrundstellungAN(orgid))
      addFehler("DSBU080", "Feldinhalt ist leer.")

    pruefe("BELEGNR")
    val belegnr = datensatz.naechsteZeichen(36)
    // keine Prüfung

    pruefe("BELEGDT")
    val belegdt = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(belegdt))
      addFehler("DSBU90", "Das Datum muss logisch richtig sein.")

    pruefe("BUCHDT")
    val buchdt = datensatz.naechsteZeichen(8)
    if (!SvPruefungen.istGrundstellungAN(buchdt) && !AllgemeinePruefungen.validesDatumOhneTrenner(buchdt))
      addFehler("DSBU100", "Wenn nicht Grundstellung, dann muss das Buchungsdatum logisch richtig sein.")

    pruefe("BUCHTEXT")
    val buchtext = datensatz.naechsteZeichen(100)
    // keine Prüfung

    pruefe("BELEG")
    val gueltigeBeleg = listOf("0", "1")
    val beleg = datensatz.naechsteZeichen(1)
    if (beleg !in gueltigeBeleg)
      addFehler("DSBU110", "Zulässig sind \"0\" und \"1\".")

    pruefe("GRUPPSCHL")
    val gruppschl = datensatz.naechsteZeichen(35)
    // keine Prüfung


    pruefe("ANPOS")
    val anpos = datensatz.naechsteZeichen(6)
    if (!SvPruefungen.istNumerisch(anpos))
      addFehler("DSBU120", "Zulässig sind nur numerische Zeichen.")

    val anzahlBuchungspositionen = (laenge - (453 + 1)) / 389
    if (anzahlBuchungspositionen.toString().padStart(6, '0') != anpos)
      addFehler("XISI", "Feld 'ANPOS' entspricht nicht der Anzahl an Buchungsbausteinen.")

    for (index in 1..anzahlBuchungspositionen) {

      pruefe("POSNR ($index. Datenbaustein)")
      val posnr = datensatz.naechsteZeichen(6)
      if (SvPruefungen.istGrundstellungAN(posnr))
        addFehler("DSBU130", "Feldinhalt ist leer.")

      pruefe("KONTOART ($index. Datenbaustein)")
      val gueltigeKontoart = listOf("0", "1", "2")
      val kontoart = datensatz.naechsteZeichen(1)
      if (kontoart !in gueltigeKontoart)
        addFehler("DSBU130", "Zulässig sind \"0\", \"1\" und \"2\".") // todo: sollte das nicht DSBU140 sein? laut doku ist es genauso, wie POSNR Fehlerprüfung genannt...

      pruefe("KTONR ($index. Datenbaustein)")
      val ktonr = datensatz.naechsteZeichen(35)
      if (SvPruefungen.istGrundstellungAN(ktonr))
        addFehler("DSBU150", "Feldinhalt ist Grundstellung.")


      pruefe("VSOLL ($index. Datenbaustein)")
      val vsoll = datensatz.naechsteZeichen(1)
      if (!SvPruefungen.istPlusMinusOderGrundstellung(vsoll))
        addFehler("DSBU160", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


      pruefe("SOLL ($index. Datenbaustein)")
      val soll = datensatz.naechsteZeichen(25)
      if (!SvPruefungen.istNumerischOderGrundstellung(soll))
        addFehler("DSBU170", "Zulässig sind nur numerische Zeichen oder Grundstellung.")


      pruefe("VHABEN ($index. Datenbaustein)")
      val vhaben = datensatz.naechsteZeichen(1)
      if (!SvPruefungen.istPlusMinusOderGrundstellung(vhaben))
        addFehler("DSBU180", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


      pruefe("HABEN ($index. Datenbaustein)")
      val haben = datensatz.naechsteZeichen(25)
      if (!SvPruefungen.istNumerischOderGrundstellung(haben))
        addFehler("DSBU190", "Zulässig sind nur numerische Zeichen oder Grundstellung.")

      pruefe("ERWBUCHTEXT ($index. Datenbaustein)")
      val erwbuchtext = datensatz.naechsteZeichen(70)
      // keine Prüfung

      pruefe("STSATZ ($index. Datenbaustein)")
      val stsatz = datensatz.naechsteZeichen(5)
      if (!SvPruefungen.istNumerischOderGrundstellung(stsatz))
        addFehler("DSBU200", "Zulässig sind nur numerische Zeichen oder Grundstellung.")

      pruefe("STKZ ($index. Datenbaustein)")
      val stkz = datensatz.naechsteZeichen(70)
      // keine Prüfung

      pruefe("ORGEINHEIT1 ($index. Datenbaustein)")
      val orgeinheit1 = datensatz.naechsteZeichen(50)
      // keine Prüfung

      pruefe("ORGEINHEIT2 ($index. Datenbaustein)")
      val orgeinheit2 = datensatz.naechsteZeichen(50)
      // keine Prüfung

      pruefe("ORGEINHEIT3 ($index. Datenbaustein)")
      val orgeinheit3 = datensatz.naechsteZeichen(50)
      // keine Prüfung
    }

    pruefe("DSENDE")
    val gueltigeDsende = listOf("E", " ")
    val dsende = datensatz.naechsteZeichen(1)
    if (dsende !in gueltigeDsende)
      addFehler("DSBU850", "Zulässig sind \"E\" oder Grundstellung.")
  }
}
