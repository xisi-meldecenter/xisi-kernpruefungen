package de.xisi.kernpruefung.utils

class StringWalker(
  private val meldung: String
) {
  val laenge: Int get() = meldung.length
  private var currentIndex = 0

  fun naechsteZeichen(anzahl: Int): String {
    val res = meldung.substring(currentIndex, currentIndex + anzahl)
    currentIndex += anzahl
    return res
  }

  fun takeIfPresent(from: Int, to: Int): String {
    return if (meldung.length >= to)
      meldung.substring(from - 1, to)
    else ""
  }
}
