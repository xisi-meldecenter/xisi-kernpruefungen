@file:Suppress("UNUSED_VARIABLE")

package de.xisi.kernpruefung.fachlichkeit.verfahren.eubp

import de.xisi.kernpruefung.api.XisiKKSKernpruefung
import de.xisi.kernpruefung.fachlichkeit.AllgemeinePruefungen
import de.xisi.kernpruefung.fachlichkeit.SvPruefungen
import de.xisi.kernpruefung.utils.StringWalker

class DSST__4 : XisiKKSKernpruefung() {
  override val type = "DSST"
  override val version = "4"

  override val verwendeteSpezifikation = """
    - euBP_Anlage 2 - Datensätze und Datenbausteine im euBP-Verfahren - Finanzbuchhaltung (Version 3.5.0, Stand: 17.05.2024, gültig ab: 01.01.2025)
  """.trimIndent()
  override val releaseNotes = mapOf(
    "2025-04-14" to "Initiale Erfassung"
  )

  override fun typeMatches(datensatz: StringWalker) = datensatz.takeIfPresent(1, 4) == "DSST"
  override fun versionMatches(datensatz: StringWalker) = datensatz.takeIfPresent(40, 41) == "04"

  override fun pruefeDatensatz(datensatz: StringWalker) {
    val laenge = datensatz.laenge
    if (laenge != 183) {
      addFehler("DSST910", "Zulässig ist nur die Datenlänge 183.")
      return
    }


    pruefe("KE")
    val ke = datensatz.naechsteZeichen(4)
    if (ke != "DSST")
      addFehler("DSSTv01", "Zulässig ist nur 'DSST'.")

    // DSST004

    pruefe("VF")
    val vf = datensatz.naechsteZeichen(5)
    if (vf != "EUBP ")
      addFehler("DSSTv05", "Zulässig ist 'EUBP '")


    pruefe("ABSN")
    val absn = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(absn))
      addFehler("DSST020, DSST024", "Absendernummer >$absn< ist ungültig")

    // DSSTv15, DSSTv17, DSSTv18

    pruefe("EPNR")
    val epnr = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(epnr))
      addFehler("DSST030, DSST034", "Empfängernummer >$epnr< ist ungültig")

    // DSST032, DSSTv20, DSSTv22

    pruefe("VERNR")
    val vernr = datensatz.naechsteZeichen(2)
    if (vernr != "04")
      addFehler("DSST042", "Zulässig ist nur der Wert „04“ bis zur Bekanntgabe einer neuen Versionsnummer.")


    pruefe("ED")
    val edDatum = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(edDatum))
      addFehler("DSST052", "Das Erstellungsdatum muss logisch richtig sein.")
    val edUhrzeit = datensatz.naechsteZeichen(9)
    if (!AllgemeinePruefungen.valideUhrzeitOhneTrenner(edUhrzeit))
      addFehler("DSST056", "Die Uhrzeit muss logisch richtig sein.")


    pruefe("FEKZ")
    val fekz = datensatz.naechsteZeichen(1)
    if (fekz != "0")
      addFehler("DSSTv35", "Bei Meldungen vom Arbeitgeber zur DSRV (VFMM = 'AGBPL' oder 'AGBPF') ist nur der Wert '0' zulässig.")

    // DSST064

    pruefe("FEAN")
    val fean = datensatz.naechsteZeichen(1)
    if (fean != "0")
      addFehler("DSST072", "Ist im Feld FEKZ der Wert „0“ angegeben, ist hier nur der Wert „0“ zulässig.")

    // DSSTv50, DSSTv52

    pruefe("BBNRVU")
    val bbnrvu = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(bbnrvu))
      addFehler("DSST080", "Betriebsnummer des Verursachers >$bbnrvu< ist ungültig")

    pruefe("BBNRAS")
    val bbnras = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(bbnras))
      addFehler("DSST090", "Betriebsnummer der Abrechnungsstelle >$bbnras< ist ungültig")

    pruefe("BBNRMS")
    val bbnrms = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(bbnrms))
      addFehler("DSST100", "Betriebsnummer der meldenden Stelle >$bbnrms< ist ungültig")

    pruefe("ZRVON")
    val zrvon = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(zrvon))
      addFehler("DSST112", "Das Datum muss logisch richtig sein.")

    pruefe("ZRBIS")
    val zrbis = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(zrbis))
      addFehler("DSST122", "Das Datum muss logisch richtig sein.")


    pruefe("GDDUE")
    val gueltigeGddue = listOf("1", "2", "3")
    val gddue = datensatz.naechsteZeichen(1)
    if (gddue !in gueltigeGddue)
      addFehler("DSST125", "Zulässig sind \"1\", \"2\" und \"3\".")


    pruefe("KENNZSEKO")
    val kennzseko = datensatz.naechsteZeichen(1)
    if (!SvPruefungen.istJOderN(kennzseko))
      addFehler("DSST130", "Zulässig ist „N“ oder „J“.")


    pruefe("KENNZST")
    val kennzst = datensatz.naechsteZeichen(1)
    if (!SvPruefungen.istJOderN(kennzst))
      addFehler("DSST130", "Zulässig ist „N“ oder „J“.")


    pruefe("VERGES")
    val verges = datensatz.naechsteZeichen(8).trimEnd()
    if (verges != "3.5.0")
      addFehler("DSST150", "Prüfung, ob es sich um eine zulässige Versionsnummer der Schnittstelle handelt. Bei Dateien der Arbeitgeber (VFMM = „AGBPL“ oder \"AGBPF\") ist \"3.5.0\" zulässig.")


    pruefe("AKAB")
    val _akab = datensatz.naechsteZeichen(50)
    // keine Prüfung


    pruefe("DSENDE")
    val gueltigeDsende = listOf("E", " ")
    val dsende = datensatz.naechsteZeichen(1)
    if (dsende !in gueltigeDsende)
      addFehler("DSST850", "Zulässig sind \"E\" oder Grundstellung.")
  }
}
