package de.xisi.kernpruefung.api

import de.xisi.kernpruefung.utils.StringWalker

abstract class XisiKKSKernpruefung : XisiKernpruefung() {
  abstract fun typeMatches(datensatz: StringWalker): Boolean
  abstract fun versionMatches(datensatz: StringWalker): Boolean

  // ******************************************************************************************************************

  abstract fun pruefeDatensatz(datensatz: StringWalker)

  // ******************************************************************************************************************

  var currentField = ""
  fun pruefe(feldname: String) {
    currentField = feldname
  }

  override fun currentFeld() = currentField

}