package de.xisi.kernpruefung.fachlichkeit.verfahren.dsko

import de.xisi.kernpruefung.api.XisiKKSKernpruefung
import de.xisi.kernpruefung.fachlichkeit.SvPruefungen
import de.xisi.kernpruefung.utils.StringWalker

class DSKO__4 : XisiKKSKernpruefung() {
  override val type = "DSKO"
  override val version = "4"

  override val verwendeteSpezifikation = """
    - 03_Anlage 9.2_Vers 8.00.pdf (Stand: 30.03.2022 Version: 8.00)
  """.trimIndent()
  override val releaseNotes = mapOf(
    "2025-02-12" to "Initiale Erfassung"
  )

  override fun typeMatches(datensatz: StringWalker) = datensatz.takeIfPresent(1, 4) == "DSKO"
  override fun versionMatches(datensatz: StringWalker) = datensatz.takeIfPresent(40, 41) == "04"

  override fun pruefeDatensatz(datensatz: StringWalker) {
    val laenge = datensatz.laenge
    if (laenge != 415) {
      addFehler("DSKO910", "Zulässig ist nur die Datenlänge 415.")
      return
    }


    pruefe("DSKO")
    val dsko = datensatz.naechsteZeichen(4)
    if (dsko != "DSKO")
      addFehler("DSKOv01", "Zulässig ist nur 'DSKO'.")


    pruefe("VF")
    val gueltigeVF = listOf("DEUEV", "UVELN", "UVSDD", "BWNAC", "AGAAG", "EUBP ")
    val vf = datensatz.naechsteZeichen(5)
    if (vf !in gueltigeVF)
      addFehler("DSKOv05", "Zulässig ist $gueltigeVF")


    pruefe("ABSN")
    val absn = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(absn))
      addFehler("ABSN-X001", "Absendernummer >$absn< ist ungültig")


    pruefe("EPNR")
    val epnr = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(epnr))
      addFehler("DSKOv20", "Empfängernummer >$epnr< ist ungültig")


    pruefe("VERNR")
    val vernr = datensatz.naechsteZeichen(2)
    if (vernr != "04")
      addFehler("DSKO042", "Gültig ist die Version \"04\" bis zur Bekanntgabe einer neuen Versionsnummer.")


    pruefe("ED")
    val ed = datensatz.naechsteZeichen(20)
  }
}