package de.xisi.kernpruefung.api

import de.xisi.kernpruefung.fachlichkeit.hasContent
import de.xisi.kernpruefung.utils.xml.XmlNode

abstract class XisiXMLKernpruefung : XisiKernpruefung() {
  abstract fun typeMatches(xml: XmlNode): Boolean
  abstract fun versionMatches(xml: XmlNode): Boolean

  // ******************************************************************************************************************

  abstract fun pruefeDatensatz(xml: XmlNode)

  // ******************************************************************************************************************


  private var currentElement: XmlNode? = null


  fun pruefe(element: XmlNode?) {
    currentElement = element
  }

  override fun currentFeld() = currentElement?.let {
    if (it.namespace.hasContent()) "${it.namespace}:${it.name}" else it.name
  } ?: ""

  fun noAttributes(xmlNode: XmlNode, ignoring: (String) -> Boolean = { false }) {

  }

  fun noChildren(xmlNode: XmlNode) {

  }

  fun addFehlerExpectedElement(xml: XmlNode) {
  }

}