package de.xisi.kernpruefung.fachlichkeit.verfahren.beitragserhebung

import de.xisi.kernpruefung.utils.StringWalker

class DSBE__3 : DSBE_Basis() {
  override val version: String = "3"
  override val gueltigAb = "2018-01-01"

  override val verwendeteSpezifikation = "TBD"
  override val releaseNotes = mapOf("2025-01-01" to "Initiale Erstellung")

  override fun versionMatches(datensatz: StringWalker) = datensatz.takeIfPresent(5, 6) == "03"

  override fun pruefeDatensatz(datensatz: StringWalker) {
  }
}