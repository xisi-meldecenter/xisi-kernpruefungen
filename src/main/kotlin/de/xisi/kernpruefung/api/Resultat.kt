package de.xisi.kernpruefung.api

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
    val fehler: List<String> =
      fehler.map { "[FEHLER]        " + it.code.padEnd(12) + it.message + " (Feld: ${it.feld})" } +
        technischeFehler.map { "[Tech. FEHLER]  " + it.code.padEnd(12) + it.message } +
        hinweise.map { "[HINWEIS]       " + it.code.padEnd(12) + it.message }


    return if (fehler.isEmpty()) "- fehlerfrei -" else fehler.joinToString("\n")
  }
}

// ******************************************************************************************************************
// ******************************************************************************************************************
// ******************************************************************************************************************

class Fehler(val code: String, val message: String, val feld: String)

class Hinweis(val code: String, val message: String)

class TechnischerFehler(val code: String, val message: String)