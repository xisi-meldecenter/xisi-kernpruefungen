package de.xisi.kernpruefung.api

import de.xisi.kernpruefung.utils.StringWalker
import de.xisi.kernpruefung.utils.xml.XMLReader

sealed class XisiKernpruefung {
  abstract val type: String
  abstract val version: String

  open val gueltigAb: String = "0000-00-00"

  val meldungsformat: String
    get() = when (this) {
      is XisiXMLKernpruefung -> "XML"
      is XisiKKSKernpruefung -> "KKS"
    }


  abstract val verwendeteSpezifikation: String
  abstract val releaseNotes: Map<String, String>

  private val resultat = Resultat()

  fun matchedName(datensatz: String): Boolean = when (this) {
    is XisiXMLKernpruefung -> typeMatches(XMLReader.read(datensatz))
    is XisiKKSKernpruefung -> typeMatches(StringWalker(datensatz))
  }

  fun typeMatch(datensatz: String): Boolean = when (this) {
    is XisiXMLKernpruefung -> versionMatches(XMLReader.read(datensatz))
    is XisiKKSKernpruefung -> versionMatches(StringWalker(datensatz))
  }

  fun pruefeMeldung(meldung: String): Resultat {
    when (this) {
      is XisiXMLKernpruefung -> pruefeDatensatz(XMLReader.read(meldung))
      is XisiKKSKernpruefung -> pruefeDatensatz(StringWalker(meldung))
    }
    return resultat
  }

  abstract fun currentFeld(): String

  fun addFehler(code: String, message: String) = resultat.addFehler(Fehler(code, message, currentFeld()))

  override fun toString() = "$type ($version)"
}