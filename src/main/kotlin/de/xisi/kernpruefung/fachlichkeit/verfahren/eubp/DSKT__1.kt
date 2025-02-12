@file:Suppress("UNUSED_VARIABLE")

package de.xisi.kernpruefung.fachlichkeit.verfahren.eubp

import de.xisi.kernpruefung.api.XisiKKSKernpruefung
import de.xisi.kernpruefung.fachlichkeit.AllgemeinePruefungen
import de.xisi.kernpruefung.fachlichkeit.SvPruefungen
import de.xisi.kernpruefung.utils.StringWalker

class DSKT__1 : XisiKKSKernpruefung() {
  override val type = "DSKT"
  override val version = "1"

  override val verwendeteSpezifikation = """
    - euBP_Anlage 2 - Datensätze und Datenbausteine im euBP-Verfahren - Finanzbuchhaltung (Version 3.5.0, Stand: 17.05.2024, gültig ab: 01.01.2025)
  """.trimIndent()
  override val releaseNotes = mapOf(
    "2025-04-14" to "Initiale Erfassung"
  )

  override fun typeMatches(datensatz: StringWalker) = datensatz.takeIfPresent(1, 4) == "DSKT"
  override fun versionMatches(datensatz: StringWalker) = datensatz.takeIfPresent(40, 41) == "01"

  override fun pruefeDatensatz(datensatz: StringWalker) {
    val laenge = datensatz.laenge
    if (laenge != 717 + 1) {
      addFehler("DSKT910", "Zulässig ist nur die Datenlänge 717 + 1.")
      return
    }


    pruefe("KE")
    val ke = datensatz.naechsteZeichen(4)
    if (ke != "DSKT")
      addFehler("DSKTv01", "Zulässig ist nur 'DSKT'.")

    // DSKT004

    pruefe("VF")
    val vf = datensatz.naechsteZeichen(5)
    if (vf != "EUBP ")
      addFehler("DSKTv05", "Zulässig ist 'EUBP '")


    pruefe("ABSN")
    val absn = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(absn))
      addFehler("DSKT020, DSKT024", "Absendernummer >$absn< ist ungültig")

    // DSKTv15, DSKTv17, DSKTv18

    pruefe("EPNR")
    val epnr = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(epnr))
      addFehler("DSKT030, DSKT034", "Empfängernummer >$epnr< ist ungültig")

    // DSKT032, DSKTv20, DSKTv22

    pruefe("VERNR")
    val vernr = datensatz.naechsteZeichen(2)
    if (vernr != "01")
      addFehler("DSKT042", "Zulässig ist nur der Wert „01“ bis zur Bekanntgabe einer neuen Versionsnummer.")


    pruefe("ED")
    val edDatum = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(edDatum))
      addFehler("DSKT052", "Das Erstellungsdatum muss logisch richtig sein.")
    val edUhrzeit = datensatz.naechsteZeichen(9)
    if (!AllgemeinePruefungen.valideUhrzeitOhneTrenner(edUhrzeit))
      addFehler("DSKT056", "Die Uhrzeit muss logisch richtig sein.")


    pruefe("FEKZ")
    val fekz = datensatz.naechsteZeichen(1)
    if (fekz != "0")
      addFehler("DSKTv35", "Bei Meldungen vom Arbeitgeber zur DSRV (VFMM = 'AGBPL' oder 'AGBPF') ist nur der Wert '0' zulässig.")

    // DSKT064

    pruefe("FEAN")
    val fean = datensatz.naechsteZeichen(1)
    if (fean != "0")
      addFehler("DSKT072", "Ist im Feld FEKZ der Wert „0“ angegeben, ist hier nur der Wert „0“ zulässig.")

    // DSKTv50, DSKTv52

    pruefe("SYS-ID")
    val sysid = datensatz.naechsteZeichen(100)
    // keine Prüfung

    pruefe("ORG-ID")
    val orgid = datensatz.naechsteZeichen(100)
    if (SvPruefungen.istGrundstellungAN(orgid))
      addFehler("DSKT080", "Feldinhalt ist leer.")

    pruefe("ABSCHLART")
    val gueltigeAbschlart = listOf("0", "1")
    val abschlart = datensatz.naechsteZeichen(1)
    if (abschlart !in gueltigeAbschlart)
      addFehler("DSKT085", "Zulässig sind \"0\" und \"1\".")

    pruefe("KONTOART")
    val gueltigeKontoart = listOf("0", "1")
    val kontoart = datensatz.naechsteZeichen(1)
    if (kontoart !in gueltigeKontoart)
      addFehler("DSKT087", "Zulässig sind \"0\" und \"1\".")

    pruefe("KTONR")
    val ktonr = datensatz.naechsteZeichen(35)
    if (SvPruefungen.istGrundstellungAN(ktonr))
      addFehler("DSKT092", "Feldinhalt ist leer.")

    pruefe("GLTAB")
    val gltab = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(gltab))
      addFehler("DSKT102", "Das Datum muss logisch richtig sein.")

    pruefe("GLTBIS")
    val gltbis = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(gltbis))
      addFehler("DSKT112", "Das Datum muss logisch richtig sein.")


    pruefe("KTONAME")
    val ktoname = datensatz.naechsteZeichen(150)
    if (SvPruefungen.istGrundstellungAN(ktoname))
      addFehler("DSKT120", "Feldinhalt ist leer.")


    pruefe("WAKREDITOR")
    val wakreditor = datensatz.naechsteZeichen(150)
    // keine Prüfung


    pruefe("VVWERTSOLL")
    val vvwertsoll = datensatz.naechsteZeichen(1)
    if (!SvPruefungen.istPlusMinusOderGrundstellung(vvwertsoll))
      addFehler("DSKT134", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


    pruefe("VWERTSOLL")
    val vwertsoll = datensatz.naechsteZeichen(25)
    if (!SvPruefungen.istNumerisch(vwertsoll))
      addFehler("DSKT135", "Zulässig sind nur numerische Zeichen.")


    pruefe("VVWERTHABEN")
    val vvwerthaben = datensatz.naechsteZeichen(1)
    if (!SvPruefungen.istPlusMinusOderGrundstellung(vvwerthaben))
      addFehler("DSKT139", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


    pruefe("VWERTHABEN")
    val vwerthaben = datensatz.naechsteZeichen(25)
    if (!SvPruefungen.istNumerisch(vwerthaben))
      addFehler("DSKT140", "Zulässig sind nur numerische Zeichen.")


    pruefe("VSOLL")
    val vsoll = datensatz.naechsteZeichen(1)
    if (!SvPruefungen.istPlusMinusOderGrundstellung(vsoll))
      addFehler("DSKT144", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


    pruefe("SOLL")
    val soll = datensatz.naechsteZeichen(25)
    if (!SvPruefungen.istNumerischOderGrundstellung(soll))
      addFehler("DSKT145", "Zulässig sind nur numerische Zeichen oder Grundstellung.")


    pruefe("VHABEN")
    val vhaben = datensatz.naechsteZeichen(1)
    if (!SvPruefungen.istPlusMinusOderGrundstellung(vhaben))
      addFehler("DSKT149", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


    pruefe("HABEN")
    val haben = datensatz.naechsteZeichen(25)
    if (!SvPruefungen.istNumerischOderGrundstellung(haben))
      addFehler("DSKT150", "Zulässig sind nur numerische Zeichen oder Grundstellung.")


    pruefe("DSENDE")
    val gueltigeDsende = listOf("E", " ")
    val dsende = datensatz.naechsteZeichen(1)
    if (dsende !in gueltigeDsende)
      addFehler("DSKT850", "Zulässig sind \"E\" oder Grundstellung.")
  }
}
