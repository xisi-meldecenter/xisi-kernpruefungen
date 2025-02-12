package de.xisi.kernpruefung.fachlichkeit.verfahren.beitragserhebung

import de.xisi.kernpruefung.utils.StringWalker

class DSBE__4 : DSBE_Basis() {
  override val version: String = "4"
  override val gueltigAb = "2019-07-01"

  override val verwendeteSpezifikation = "TBD"
  override val releaseNotes = mapOf("2025-01-01" to "Initiale Erstellung")

  override fun versionMatches(datensatz: StringWalker) = datensatz.takeIfPresent(5, 6) == "04"

  override fun pruefeDatensatz(datensatz: StringWalker) {
  }
}