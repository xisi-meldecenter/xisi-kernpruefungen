package de.xisi.kernpruefung.api

import de.xisi.kernpruefung.fachlichkeit.emptyToNull

class Resultat {
  private val technischeFehler = ArrayList<TechnischerFehler>()
  private val fehler = ArrayList<Fehler>()
  private val hinweise = ArrayList<Hinweis>()

  fun addFehler(fehler: Fehler): Resultat {
    this.fehler.add(fehler)
    return this
  }

  fun addHinweis(hinweis: Hinweis): Resultat {
    hinweise.add(hinweis)
    return this
  }

  fun addTechnischenFehler(technischerFehler: TechnischerFehler): Resultat {
    technischeFehler.add(technischerFehler)
    return this
  }

  // ******************************************************************************************************************

  fun getTechnischeFehler(): List<TechnischerFehler> = technischeFehler
  fun getFehler(): List<Fehler> = fehler
  fun getHinweise(): List<Hinweis> = hinweise

  fun exceptionBeiFehlern() {
    if (technischeFehler.isNotEmpty() || fehler.isNotEmpty())
      error(toString())
  }

  // ******************************************************************************************************************

  override fun toString(): String {
    val msg = fehler.joinToString("\n") { "[FEHLER]        " + it.code.padEnd(12) + it.message + " (Feld: ${it.feld})" } +
      technischeFehler.joinToString("\n") { "[Tech. FEHLER]  " + it.code.padEnd(12) + it.message } +
      hinweise.joinToString("\n") { "[HINWEIS]       " + it.code.padEnd(12) + it.message }

    return msg.emptyToNull() ?: "- fehlerfrei -"
  }
}

// ******************************************************************************************************************
// ******************************************************************************************************************
// ******************************************************************************************************************

class Fehler(val code: String, val message: String, val feld: String)

class Hinweis(val code: String, val message: String)

class TechnischerFehler(val code: String, val message: String)