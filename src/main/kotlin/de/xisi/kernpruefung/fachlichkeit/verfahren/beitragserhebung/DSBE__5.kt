package de.xisi.kernpruefung.fachlichkeit.verfahren.beitragserhebung

import de.xisi.kernpruefung.utils.StringWalker

class DSBE__5 : DSBE_Basis() {
  override val version: String = "5"
  override val gueltigAb = "2024-01-01"

  override val verwendeteSpezifikation = "TBD"
  override val releaseNotes = mapOf("2025-01-01" to "Initiale Erstellung")

  override fun versionMatches(datensatz: StringWalker) = datensatz.takeIfPresent(5, 6) == "05"

  override fun pruefeDatensatz(datensatz: StringWalker) {
  }
}