package de.xisi.kernpruefung

import org.junit.jupiter.api.Test

class XisiKernpruefungsServiceTest {
  private val dsko_gueltig = "DSKODEUEV02233442       15451439       04202502121440010000000002233442       123456712345000Xisi Meldecenter                                                                          12345     Berlin                            Hauptstr.                        32       MMax Mustermann                012345678912        012345678901        info@example.org                                                           "

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

  @Test
  fun `existiertKernpruefungFuer - 1`() {
    xkps.existiertPruefungFuer(dsko_gueltig) mustBe true
  }

  @Test
  fun `existiertKernpruefungFuer - 2`() {
    xkps.existiertPruefungFuer(dsko_gueltig, "DSKO", version = "3") mustBe false
  }

  @Test
  fun `Keine KP gefunden - Handling`() {
    xkps.konfiguriere().wennKeineKPExistiertTechnischerFehler()

    var res = xkps.pruefeMeldung(dsko_gueltig.replace("DSKO", "DSKU"))
    res.getTechnischeFehler().size mustBe 1
    res.getTechnischeFehler()[0].code mustBe "XISI-010"
    res.getTechnischeFehler()[0].message mustBe "Fehler beim Ermitteln der Kernpruefung: Es konnte keine passende Kernprüfung automatisch ermittelt werden. (DSKUDEUEV02233442   ..)"


    xkps.konfiguriere().wennKeineKPExistiertLoggeWarnung()
    res = xkps.pruefeMeldung(dsko_gueltig.replace("DSKO", "DSKU"))
    res.getTechnischeFehler().size mustBe 0

    xkps.konfiguriere().wennKeineKPExistiertIgnoriere()
    res = xkps.pruefeMeldung(dsko_gueltig.replace("DSKO", "DSKU"))
    res.getTechnischeFehler().size mustBe 0
  }


  @Test
  fun `mehrere Meldungen gehen`() {
    xkps.pruefeMeldungen(
      listOf(
        dsko_gueltig,
        dsko_gueltig,
        dsko_gueltig
      )
    ).exceptionBeiFehlern()
  }

  @Test
  fun `mehrere Meldungen gehen - 2`() {
    {
      xkps.konfiguriere().wennKeineKPExistiertTechnischerFehler()
      xkps.pruefeMeldungen(
        listOf(
          dsko_gueltig.replace("02233442", "0223344A"),
          dsko_gueltig.replace("DSKO", "DSKU"),
          dsko_gueltig
        )
      ).exceptionBeiFehlern()
    } mustThrowException {
      it mustBeType IllegalStateException::class.java
      it.message mustBe """
        [FEHLER]        ABSN-X001   Absendernummer >0223344A< ist ungültig (Feld: ABSN)
        [Tech. FEHLER]  XISI-010    Fehler beim Ermitteln der Kernpruefung: Es konnte keine passende Kernprüfung automatisch ermittelt werden. (DSKUDEUEV02233442   ..)
      """.trimIndent()
    }
  }

  @Test
  fun `mehrere Meldungen gehen - 3`() {
    {
      xkps.konfiguriere().wennKeineKPExistiertIgnoriere()
      xkps.pruefeMeldungen(
        listOf(
          dsko_gueltig.replace("02233442", "0223344A"),
          dsko_gueltig.replace("DSKO", "DSKU"),
          dsko_gueltig
        )
      ).exceptionBeiFehlern()
    } mustThrowException {
      it mustBeType IllegalStateException::class.java
      it.message mustBe """
        [FEHLER]        ABSN-X001   Absendernummer >0223344A< ist ungültig (Feld: ABSN)
      """.trimIndent()
    }
  }


}

