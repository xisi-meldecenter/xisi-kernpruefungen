package de.xisi.kernpruefung.fachlichkeit

import de.xisi.kernpruefung.mustBe
import org.junit.jupiter.api.Test

class SvPruefungenTest {
  @Test fun `istValideBetriebsOderAbsendernummer funktioniert`() {
    SvPruefungen.istValideBetriebsOderAbsendernummer("02221983") mustBe true
    SvPruefungen.istValideBetriebsOderAbsendernummer("A2221983") mustBe true
    SvPruefungen.istValideBetriebsOderAbsendernummer("B2221983") mustBe false
  }
}