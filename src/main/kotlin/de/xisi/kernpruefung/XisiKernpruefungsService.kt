package de.xisi.kernpruefung

import de.xisi.kernpruefung.api.Resultat
import de.xisi.kernpruefung.api.TechnischerFehler
import de.xisi.kernpruefung.api.XisiKernpruefung
import de.xisi.kernpruefung.fachlichkeit.hasContent

class XisiKernpruefungsService {

  // ******************************************************************************************************************
  // ***** Konfiguration
  private val konfiguration = XisiKernpruefungKonfiguration()

  fun konfiguriere() = konfiguration

  fun log(any: Any? = null) {
    println(any)
  }


  // ******************************************************************************************************************
  // ***** Kernprüfungslisten
  private val kernpruefungen = ArrayList<XisiKernpruefung>()

  init {
    XisiKernpruefungRegister.mitgelieferte_verfahren.forEach(this::fuegeKernpruefungHinzu)
  }

  fun fuegeKernpruefungHinzu(cls: Class<out XisiKernpruefung>) {
    val instance = cls.newInstance()
    kernpruefungen.add(instance)
  }

  fun loggeAktiveKernpruefungen() {
    kernpruefungen
      .sortedBy { it.type + it.version }
      .forEach { println(it.type.padEnd(10) + it.version.padEnd(7) + it.meldungsformat) }
  }

  // ******************************************************************************************************************

  @JvmOverloads
  fun pruefeMeldung(meldung: String, type: String? = null, version: String? = null, datum: String? = null): Resultat {
    val kp = try {
      ermittleKernpruefung(type, version, meldung)
    } catch (e: Exception) {
      return Resultat().addTechnischenFehler(TechnischerFehler("XISI-010", "Fehler beim Ermitteln der Kernpruefung: " + e.message))
    }

    return kp::class.java.newInstance().pruefeMeldung(meldung)
  }

  private fun ermittleKernpruefung(
    type: String?,
    version: String?,
    meldung: String
  ) = when {
    type.hasContent() && version.hasContent() -> {
      kernpruefungen.firstOrNull { it.type == type && it.version == version } ?: error("Es existiert keine Kernpruefung mit dem Type: >$type< und Version: >$version<")
    }

    type.hasContent() -> {
      val kpList = kernpruefungen.filter { it.type == type }
      if (kpList.isEmpty()) error("Es existieren keine Kernpruefungen mit dem Type: >$type<")
      kpList.firstOrNull { it.matchedName(meldung) } ?: error("Keine der Kernpruefungen $kpList erkennt die Version")
    }

    else -> {
      kernpruefungen.firstOrNull { it.typeMatch(meldung) && it.matchedName(meldung) } ?: error("Es konnte keine passende Kernprüfung automatisch ermittelt werden.")
    }
  }

}