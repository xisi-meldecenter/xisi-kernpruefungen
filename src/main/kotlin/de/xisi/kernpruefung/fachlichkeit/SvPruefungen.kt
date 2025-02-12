package de.xisi.kernpruefung.fachlichkeit

object SvPruefungen {
  private val bbnrOrAbbnrRegex = "[A0-9][0-9]{7}".toRegex()
  fun istValideBetriebsOderAbsendernummer(bbnr: String?) = bbnr != null && bbnr.matches(bbnrOrAbbnrRegex)
  fun istGrundstellungAN(string: String?) = string != null && string == "".padEnd(string.length, ' ')
  fun istGrundstellungN(string: String?) = string != null && string == "".padEnd(string.length, '0')
  fun istJOderN(string: String?) = string != null && string in listOf("J", "N")
  fun istPlusMinusOderGrundstellung(string: String?) = string != null && string in listOf("+", "-", " ")
  private val numerischRegex = "^[0-9]*$".toRegex()
  fun istNumerisch(string: String?) = string != null && string.matches(numerischRegex)
  fun istNumerischOderGrundstellung(string: String?) = string != null && (string.matches(numerischRegex) || istGrundstellungAN(string))
}
