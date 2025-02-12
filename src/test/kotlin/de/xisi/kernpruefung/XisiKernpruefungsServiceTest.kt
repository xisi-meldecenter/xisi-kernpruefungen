package de.xisi.kernpruefung

import org.junit.jupiter.api.Test


class XisiKernpruefungsServiceTest {
  private val dsko_gueltig = "DSKODEUEV02221983       15451439       04202502121440010000000002221983       139696592028000Quick-Lohn Meldecenter                                                                    16230     Britz                             Wiesenstr.                       32       MMarcel Berschneider           030809520738        030809520721        meldecenter@quick-lohn.de                                                  "

  private val xkps = XisiKernpruefungsService()

  @Test
  fun `kann verfuegbare Kernpruefungen anzeigen`() {
    xkps.loggeAktiveKernpruefungen()
  }

  @Test
  fun `einfacher Aufruf geht`() {
    xkps.pruefeMeldung(dsko_gueltig)
      .exceptionBeiFehlern()
  }

  @Test
  fun `kp aufrufen - mit type und version`() {
    xkps.pruefeMeldung(dsko_gueltig, "DSKO", "4")
      .exceptionBeiFehlern()
  }

  @Test
  fun `kp aufrufen - falscher type`() {
    {
      xkps.pruefeMeldung(dsko_gueltig, "DSZZ")
    } expectKPFehler """
      [Tech. FEHLER]  XISI-010    Fehler beim Ermitteln der Kernpruefung: Es existieren keine Kernpruefungen mit dem Type: >DSZZ<
    """.trimIndent();


    {
      xkps.pruefeMeldung(dsko_gueltig, "DSKO", "3")
    } expectKPFehler """
      [Tech. FEHLER]  XISI-010    Fehler beim Ermitteln der Kernpruefung: Es existiert keine Kernpruefung mit dem Type: >DSKO< und Version: >3<
      """.trimIndent()
  }
}

