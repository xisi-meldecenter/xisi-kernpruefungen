package de.xisi.kernpruefung.fachlichkeit.verfahren.eubp

import de.xisi.kernpruefung.XisiKernpruefungsService
import de.xisi.kernpruefung.expectKPFehler
import org.junit.jupiter.api.Test

class DSST__4Test {
    private val dsst_gueltig = "DSSTEUBP 12345678       66667777       04202501010112111000012345678       12345678       12345678       20240101202412311JN3.5.0                                                     E"

    private val xkps = XisiKernpruefungsService()

    @Test
    fun `dsst gueltig`() {
        xkps.pruefeMeldung(dsst_gueltig, "DSST", "4")
            .exceptionBeiFehlern()
    }

    @Test
    fun `ed ungueltig`() {
        {
            xkps.pruefeMeldung(dsst_gueltig.replace("20250101", "20250231"), "DSST", "4")
        } expectKPFehler """
      [FEHLER]        DSST052     Das Erstellungsdatum muss logisch richtig sein. (Feld: ED)
    """.trimIndent()
    }
}
