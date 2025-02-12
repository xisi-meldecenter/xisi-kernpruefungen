package de.xisi.kernpruefung.fachlichkeit.verfahren.dsko

import de.xisi.kernpruefung.XisiKernpruefungsService
import de.xisi.kernpruefung.expectKPFehler
import org.junit.jupiter.api.Test

class DSKO_4Test {
  private val dsko_gueltig = "DSKODEUEV02233442       15451439       04202502121440010000000002233442       123456712345000Xisi Meldecenter                                                                          12345     Berlin                            Hauptstr.                        32       MMax Mustermann                012345678912        012345678901        info@example.org                                                           "

  private val xkps = XisiKernpruefungsService()

  @Test
  fun `dsko gueltig`() {
    xkps.pruefeMeldung(dsko_gueltig)
      .exceptionBeiFehlern()
  }

  @Test
  fun `dsko vorn klein geschrieben`() {
    {
      xkps.pruefeMeldung(dsko_gueltig.replace("DSKO", "dsko"), "DSKO", "4")
    } expectKPFehler """
      [FEHLER]        DSKOv01     Zulässig ist nur 'DSKO'. (Feld: DSKO)
    """.trimIndent()
  }

  @Test
  fun `deuev vorn klein geschrieben`() {
    {
      xkps.pruefeMeldung(dsko_gueltig.replace("DSKO", "dsko").replace("DEUEV", "abcde"), "DSKO", "4")
    } expectKPFehler """
      [FEHLER]        DSKOv01     Zulässig ist nur 'DSKO'. (Feld: DSKO)
      [FEHLER]        DSKOv05     Zulässig ist [DEUEV, UVELN, UVSDD, BWNAC, AGAAG, EUBP ] (Feld: VF)
    """.trimIndent()
  }

  @Test
  fun `absn mit a`() {
    xkps.pruefeMeldung(dsko_gueltig.replace("02233442", "A2233442"), "DSKO", "4")
      .exceptionBeiFehlern()
  }

  @Test
  fun `absn mit b`() {
    {
      xkps.pruefeMeldung(dsko_gueltig.replace("02233442", "B2233442"), "DSKO", "4")
    } expectKPFehler """
      [FEHLER]        ABSN-X001   Absendernummer >B2233442< ist ungültig (Feld: ABSN)
    """.trimIndent()
  }
}