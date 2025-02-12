@file:Suppress("UNUSED_VARIABLE", "NAME_SHADOWING")

package de.xisi.kernpruefung.fachlichkeit.verfahren.eubp

import de.xisi.kernpruefung.api.XisiKKSKernpruefung
import de.xisi.kernpruefung.fachlichkeit.AllgemeinePruefungen
import de.xisi.kernpruefung.fachlichkeit.SvPruefungen
import de.xisi.kernpruefung.utils.StringWalker

class DSKB__6 : XisiKKSKernpruefung() {
  override val type = "DSKB"
  override val version = "6"

  override val verwendeteSpezifikation = """
    - euBP_Anlage 2 - Datensätze und Datenbausteine im euBP-Verfahren - Finanzbuchhaltung (Version 3.5.0, Stand: 17.05.2024, gültig ab: 01.01.2025)
  """.trimIndent()
  override val releaseNotes = mapOf(
    "2025-04-14" to "Initiale Erfassung"
  )

  override fun typeMatches(datensatz: StringWalker) = datensatz.takeIfPresent(1, 4) == "DSKB"
  override fun versionMatches(datensatz: StringWalker) = datensatz.takeIfPresent(40, 41) == "06"

  override fun pruefeDatensatz(datensatz: StringWalker) {
    val laenge = datensatz.laenge
    if ((laenge - (475 + 1)) % 315 != 0) {
      addFehler("DSKB910", "Zulässig ist nur die Datenlänge 475 + (ANBUCH * 315) + 1.")
      return
    }


    pruefe("KE")
    val ke = datensatz.naechsteZeichen(4)
    if (ke != "DSKB")
      addFehler("DSKBv01", "Zulässig ist nur 'DSKB'.")

    // DSKB004

    pruefe("VF")
    val vf = datensatz.naechsteZeichen(5)
    if (vf != "EUBP ")
      addFehler("DSKBv05", "Zulässig ist 'EUBP '")


    pruefe("ABSN")
    val absn = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(absn))
      addFehler("DSKB020, DSKB024", "Absendernummer >$absn< ist ungültig")

    // DSKBv15, DSKBv17, DSKBv18

    pruefe("EPNR")
    val epnr = datensatz.naechsteZeichen(15).trimEnd()
    if (!SvPruefungen.istValideBetriebsOderAbsendernummer(epnr))
      addFehler("DSKB030, DSKB034", "Empfängernummer >$epnr< ist ungültig")

    // DSKB032, DSKBv20, DSKBv22

    pruefe("VERNR")
    val vernr = datensatz.naechsteZeichen(2)
    if (vernr != "06")
      addFehler("DSKB042", "Zulässig ist nur der Wert „06“ bis zur Bekanntgabe einer neuen Versionsnummer.")


    pruefe("ED")
    val edDatum = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(edDatum))
      addFehler("DSKB052", "Das Erstellungsdatum muss logisch richtig sein.")
    val edUhrzeit = datensatz.naechsteZeichen(9)
    if (!AllgemeinePruefungen.valideUhrzeitOhneTrenner(edUhrzeit))
      addFehler("DSKB056", "Die Uhrzeit muss logisch richtig sein.")


    pruefe("FEKZ")
    val fekz = datensatz.naechsteZeichen(1)
    if (fekz != "0")
      addFehler("DSKBv35", "Bei Meldungen vom Arbeitgeber zur DSRV (VFMM = 'AGBPL' oder 'AGBPF') ist nur der Wert '0' zulässig.")

    // DSKB064

    pruefe("FEAN")
    val fean = datensatz.naechsteZeichen(1)
    if (fean != "0")
      addFehler("DSKB072", "Ist im Feld FEKZ der Wert „0“ angegeben, ist hier nur der Wert „0“ zulässig.")

    // DSKBv50, DSKBv52

    pruefe("ORG-ID")
    val orgid = datensatz.naechsteZeichen(100)
    if (SvPruefungen.istGrundstellungAN(orgid))
      addFehler("DSKB080", "Feldinhalt ist leer.")

    pruefe("ABSCHLART")
    val gueltigeAbschlart = listOf("0", "1")
    val abschlart = datensatz.naechsteZeichen(1)
    if (abschlart !in gueltigeAbschlart)
      addFehler("DSKB085", "Zulässig sind \"0\" und \"1\".")

    pruefe("KONTOART")
    val gueltigeKontoart = listOf("0", "1")
    val kontoart = datensatz.naechsteZeichen(1)
    if (kontoart !in gueltigeKontoart)
      addFehler("DSKB087", "Zulässig sind \"0\" und \"1\".")

    pruefe("KTONR")
    val ktonr = datensatz.naechsteZeichen(35)
    if (SvPruefungen.istGrundstellungAN(ktonr))
      addFehler("DSKB090", "Feldinhalt ist leer.")

    pruefe("GLTAB")
    val gltab = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(gltab))
      addFehler("DSKB102", "Das Datum muss logisch richtig sein.")

    pruefe("GLTBIS")
    val gltbis = datensatz.naechsteZeichen(8)
    if (!AllgemeinePruefungen.validesDatumOhneTrenner(gltbis))
      addFehler("DSKB112", "Das Datum muss logisch richtig sein.")


    pruefe("KTONAME")
    val ktoname = datensatz.naechsteZeichen(150)
    if (SvPruefungen.istGrundstellungAN(ktoname))
      addFehler("DSKB120", "Feldinhalt ist leer.")


    pruefe("VVWERTSOLL")
    val vvwertsoll = datensatz.naechsteZeichen(1)
    if (!SvPruefungen.istPlusMinusOderGrundstellung(vvwertsoll))
      addFehler("DSKB134", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


    pruefe("VWERTSOLL")
    val vwertsoll = datensatz.naechsteZeichen(25)
    if (!SvPruefungen.istNumerisch(vwertsoll))
      addFehler("DSKB135", "Zulässig sind nur numerische Zeichen.")


    pruefe("VVWERTHABEN")
    val vvwerthaben = datensatz.naechsteZeichen(1)
    if (!SvPruefungen.istPlusMinusOderGrundstellung(vvwerthaben))
      addFehler("DSKB139", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


    pruefe("VWERTHABEN")
    val vwerthaben = datensatz.naechsteZeichen(25)
    if (!SvPruefungen.istNumerisch(vwerthaben))
      addFehler("DSKB140", "Zulässig sind nur numerische Zeichen.")


    pruefe("VSOLL")
    val vsoll = datensatz.naechsteZeichen(1)
    if (!SvPruefungen.istPlusMinusOderGrundstellung(vsoll))
      addFehler("DSKB144", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


    pruefe("SOLL")
    val soll = datensatz.naechsteZeichen(25)
    if (!SvPruefungen.istNumerischOderGrundstellung(soll))
      addFehler("DSKB145", "Zulässig sind nur numerische Zeichen oder Grundstellung.")


    pruefe("VHABEN")
    val vhaben = datensatz.naechsteZeichen(1)
    if (!SvPruefungen.istPlusMinusOderGrundstellung(vhaben))
      addFehler("DSKB149", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


    pruefe("HABEN")
    val haben = datensatz.naechsteZeichen(25)
    if (!SvPruefungen.istNumerischOderGrundstellung(haben))
      addFehler("DSKB150", "Zulässig sind nur numerische Zeichen oder Grundstellung.")


    pruefe("ANBUCH")
    val anbuch = datensatz.naechsteZeichen(8)
    if (!SvPruefungen.istNumerisch(anbuch))
      addFehler("DSKB160", "Zulässig sind nur numerische Zeichen.")

    val anzahlBuchungen = (laenge - (475 + 1)) / 315
    if (anzahlBuchungen.toString().padStart(8, '0') != anbuch)
      addFehler("XISI", "Feld 'ANBUCH' entspricht nicht der Anzahl an Buchungsbausteinen.")

    for (index in 1..anzahlBuchungen) {
      pruefe("BELEGDT ($index. Datenbaustein)")
      val belegdt = datensatz.naechsteZeichen(8)
      if (!AllgemeinePruefungen.validesDatumOhneTrenner(belegdt))
        addFehler("DSKB172", "Das Datum muss logisch richtig sein.")


      pruefe("BUCHDT ($index. Datenbaustein)")
      val buchdt = datensatz.naechsteZeichen(8)
      if (!SvPruefungen.istGrundstellungAN(buchdt) && !AllgemeinePruefungen.validesDatumOhneTrenner(buchdt))
          addFehler("DSKB182", "Wenn nicht Grundstellung, dann muss das Buchungsdatum logisch richtig sein.")


      pruefe("GKTONR")
      val gktonr = datensatz.naechsteZeichen(35)
      if (SvPruefungen.istGrundstellungAN(gktonr))
        addFehler("DSKB190", "Feldinhalt ist leer.")


      pruefe("BUCHTEXT")
      val buchtext = datensatz.naechsteZeichen(100)
      // keine Prüfung


      pruefe("BELEGNR")
      val belegnr = datensatz.naechsteZeichen(36)
      // keine Prüfung


      pruefe("BELEG")
      val gueltigeBeleg = listOf("0", "1")
      val beleg = datensatz.naechsteZeichen(1)
      if (beleg !in gueltigeBeleg)
        addFehler("DSKB210", "Zulässig sind \"0\" und \"1\".")


      pruefe("VSOLL")
      val vsoll = datensatz.naechsteZeichen(1)
      if (!SvPruefungen.istPlusMinusOderGrundstellung(vsoll))
        addFehler("DSKB214", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


      pruefe("SOLL")
      val soll = datensatz.naechsteZeichen(25)
      if (!SvPruefungen.istNumerischOderGrundstellung(soll))
        addFehler("DSKB215", "Zulässig sind nur numerische Zeichen oder Grundstellung.")


      pruefe("VHABEN")
      val vhaben = datensatz.naechsteZeichen(1)
      if (!SvPruefungen.istPlusMinusOderGrundstellung(vhaben))
        addFehler("DSKB219", "Zulässig sind nur die Zeichen „+“, „-“ oder Grundstellung.")


      pruefe("HABEN")
      val haben = datensatz.naechsteZeichen(25)
      if (!SvPruefungen.istNumerischOderGrundstellung(haben))
        addFehler("DSKB220", "Zulässig sind nur numerische Zeichen oder Grundstellung.")


      pruefe("STSATZ")
      val stsatz = datensatz.naechsteZeichen(5)
      if (!SvPruefungen.istNumerischOderGrundstellung(stsatz))
        addFehler("DSKB240", "Zulässig sind nur numerische Zeichen oder Grundstellung.")


      pruefe("STKZ")
      val stkz = datensatz.naechsteZeichen(70)
      // keine Prüfung
    }

    pruefe("DSENDE")
    val gueltigeDsende = listOf("E", " ")
    val dsende = datensatz.naechsteZeichen(1)
    if (dsende !in gueltigeDsende)
      addFehler("DSKB850", "Zulässig sind \"E\" oder Grundstellung.")
  }
}
