package de.xisi.kernpruefung.fachlichkeit.verfahren.dabpv

import de.xisi.kernpruefung.XisiKernpruefungsService
import de.xisi.kernpruefung.expectKPFehler
import org.junit.jupiter.api.Test

class DABPV__1_0_0Test {
  val xml = """
      <PuegRequest xmlns="http://www.itzbund.de/elstam/pueg/request/v1"
                   xmlns:common="http://www.itzbund.de/elstam/pueg/common/v1">
          <Anfrage>
              <MessageId>7f72bc27-1879-457e-bf35-d2e7d69c5e54</MessageId>
              <DatumAnfrage>2025-07-01T13:30:47.123</DatumAnfrage>
              <Kunde>
                  <common:Kundennummer>0321404469</common:Kundennummer>
                  <common:Zuordnungsmerkmal>55151555-12345678-87654321</common:Zuordnungsmerkmal>
                  <common:Ordnungsbegriff>OB-2</common:Ordnungsbegriff>
              </Kunde>
              <IdNr>47110000017</IdNr>
              <Geburtsdatum>1970-00-00</Geburtsdatum>
              <AbDatum>2025-07-02</AbDatum>
              <BisDatum>2025-07-03</BisDatum>
              <Abo>false</Abo>
          </Anfrage>
      </PuegRequest>
    """.trimIndent()

  @Test
  fun `Beispiel 1`() {
    val xml = """
      <puegRequest:PuegRequest xmlns:puegRequest="http://www.itzbund.de/elstam/pueg/request/v1"
                               xmlns:common="http://www.itzbund.de/elstam/pueg/common/v1">
          <puegRequest:Anfrage>
              <puegRequest:MessageId>7f72bc27-1879-457e-bf35-d2e7d69c5e54</puegRequest:MessageId>
              <puegRequest:DatumAnfrage>2025-07-01T13:30:47.123</puegRequest:DatumAnfrage>
              <puegRequest:Kunde>
                  <common:Kundennummer>0321404469</common:Kundennummer>
                  <common:Zuordnungsmerkmal>55151555-12345678-87654321</common:Zuordnungsmerkmal>
                  <common:Ordnungsbegriff>OB-2</common:Ordnungsbegriff>
              </puegRequest:Kunde>
              <puegRequest:IdNr>47110000017</puegRequest:IdNr>
              <puegRequest:Geburtsdatum>1970-00-00</puegRequest:Geburtsdatum>
              <puegRequest:AbDatum>2025-07-02</puegRequest:AbDatum>
              <puegRequest:BisDatum>2025-07-03</puegRequest:BisDatum>
              <puegRequest:Abo>false</puegRequest:Abo>
          </puegRequest:Anfrage>
      </puegRequest:PuegRequest>
    """.trimIndent()

    XisiKernpruefungsService().pruefeMeldung(xml)
      .exceptionBeiFehlern()
  }

  @Test
  fun `Beispiel 2`() {


    XisiKernpruefungsService().pruefeMeldung(xml)
      .exceptionBeiFehlern()
  }

  @Test
  fun `Beispiel 3`() {
    {

      val xml = """
      <PuegRequest xmlns="http://www.itzbund.de/elstam/pueg/request/v1"
                   xmlns:common="http://www.itzbund.de/elstam/pueg/common/v1">
      </PuegRequest>
    """.trimIndent()
      XisiKernpruefungsService().pruefeMeldung(xml)
    } expectKPFehler """
      [FEHLER]        1-100 Elemente (Feld: http://www.itzbund.de/elstam/pueg/request/v1:PuegRequest)
    """.trimIndent()
  }

  @Test
  fun `Beispiel 4`() {
    {
      val falsche_messageid = xml.replace("7f72bc27-1879-457e-bf35-d2e7d69c5e54", "abc")
      XisiKernpruefungsService().pruefeMeldung(falsche_messageid)
    } expectKPFehler """
      [FEHLER]        keine gültige UUID (Feld: http://www.itzbund.de/elstam/pueg/request/v1:MessageId)
    """.trimIndent()
  }
}