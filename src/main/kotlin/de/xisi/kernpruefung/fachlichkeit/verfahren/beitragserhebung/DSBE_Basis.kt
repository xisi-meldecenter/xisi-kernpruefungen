package de.xisi.kernpruefung.fachlichkeit.verfahren.beitragserhebung

import de.xisi.kernpruefung.api.XisiKKSKernpruefung
import de.xisi.kernpruefung.utils.StringWalker

abstract class DSBE_Basis : XisiKKSKernpruefung() {
  override val type = "DSBE"
  override fun typeMatches(datensatz: StringWalker) = datensatz.takeIfPresent(1, 4) == type
}