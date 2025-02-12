package de.xisi.kernpruefung.fachlichkeit.verfahren.dabpv

import de.xisi.kernpruefung.api.XisiXMLKernpruefung
import de.xisi.kernpruefung.fachlichkeit.AllgemeinePruefungen
import de.xisi.kernpruefung.utils.xml.XmlNode

class DABPV__1_0_0 : XisiXMLKernpruefung() {
  override val type = "DABPV"
  override val version = "1.0.0"

  override val verwendeteSpezifikation = """
    - ..
  """.trimIndent()
  override val releaseNotes = mapOf(
    "2025-02-12" to "Initiale Erfassung"
  )

  // ******************************************************************************************************************

  private val ROOT_NS = "http://www.itzbund.de/elstam/pueg/request/v1"
  private val element_root = XmlNode(ROOT_NS, "PuegRequest")
  private val element_anfrage = XmlNode(ROOT_NS, "Anfrage")
  private val element_aboKuendigung = XmlNode(ROOT_NS, "AboKuendigung")

  // ******************************************************************************************************************

  override fun typeMatches(xml: XmlNode): Boolean = xml == element_root
  override fun versionMatches(xml: XmlNode) = true // kein Versions-Attribut

  override fun pruefeDatensatz(xml: XmlNode) {
    if (xml != XmlNode(ROOT_NS, "PuegRequest"))
      addFehler("", "")


    pruefe(xml)
    if (xml.children.size !in 1..100)
      addFehler("1-100 Elemente", "")



    xml.children.forEachIndexed { index, anfrageOrAboStornierung ->
      if (anfrageOrAboStornierung !in listOf(element_anfrage, element_aboKuendigung))
        addFehler("Element ${index + 1} ist weder Anfrage noch Kündigung", "")

      pruefe(anfrageOrAboStornierung)
      noAttributes(anfrageOrAboStornierung)


      val childrenInside = anfrageOrAboStornierung.childrenIterator
      
      
      childrenInside.expectNextChild(XmlNode(ROOT_NS, "MessageId"),
        notFound = { addFehlerExpectedElement(XmlNode(ROOT_NS, "MessageId")) },
        found = { messageId ->
          pruefe(messageId) // move up
          noAttributes(messageId); noChildren(messageId)
          if (AllgemeinePruefungen.valideUUID(messageId.value).not())
            addFehler("keine gültige UUID", "")
        }
      )


      childrenInside.expectNextChild(XmlNode(ROOT_NS, "DatumAnfrage"),
        notFound = { addFehlerExpectedElement(XmlNode(ROOT_NS, "DatumAnfrage")) },
        found = { datumAnfrage ->
          pruefe(datumAnfrage) // move up
          noAttributes(datumAnfrage); noChildren(datumAnfrage)
          if (AllgemeinePruefungen.validesDatum1(datumAnfrage.value).not())
            addFehler("Datum nicht valide", "")
        }
      )


    }
  }
}