package de.xisi.kernpruefung

import de.xisi.kernpruefung.api.Resultat
import de.xisi.kernpruefung.api.TechnischerFehler
import de.xisi.kernpruefung.api.XisiKernpruefung
import de.xisi.kernpruefung.fachlichkeit.hasContent
import de.xisi.kernpruefung.utils.XisiLogger

class XisiKernpruefungsService {

  // ******************************************************************************************************************
  // ***** Konfiguration
  private val log = XisiLogger()
  private val konfiguration = XisiKernpruefungKonfiguration(log)

  fun konfiguriere() = konfiguration

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

  /**
   * Log Level muss Info sein
   */
  fun loggeAktiveKernpruefungen() {
    kernpruefungen
      .sortedBy { it.type + it.version }
      .forEach { log.info(it.type.padEnd(10) + it.version.padEnd(7) + it.meldungsformat) }
  }

  // ******************************************************************************************************************

  fun existiertPruefungFuer(meldung: String) = existiertPruefungFuerIntern(meldung, null, null)
  fun existiertPruefungFuer(meldung: String, type: String) = existiertPruefungFuerIntern(meldung, type, null)
  fun existiertPruefungFuer(meldung: String, type: String, version: String) = existiertPruefungFuerIntern(meldung, type, version)
  private fun existiertPruefungFuerIntern(meldung: String, type: String?, version: String?): Boolean = try {
    ermittleKernpruefung(meldung, type, version)
    true
  } catch (e: Exception) {
    false
  }


  fun pruefeMeldung(meldung: String) = pruefeMeldungIntern(meldung, null, null, Resultat())
  fun pruefeMeldung(meldung: String, type: String) = pruefeMeldungIntern(meldung, type, null, Resultat())
  fun pruefeMeldung(meldung: String, type: String, version: String) = pruefeMeldungIntern(meldung, type, version, Resultat())

  private fun pruefeMeldungIntern(meldung: String, type: String?, version: String?, resultat: Resultat): Resultat {
    val kp = try {
      ermittleKernpruefung(meldung, type, version)
    } catch (e: Exception) {
      when (konfiguration.wennKeineKP) {
        XisiKernpruefungKonfiguration.WennKeineKPOptionen.TF -> resultat.addTechnischenFehler(TechnischerFehler("XISI-010", "Fehler beim Ermitteln der Kernpruefung: " + e.message))
        XisiKernpruefungKonfiguration.WennKeineKPOptionen.LOG -> log.warn(e.message)
        XisiKernpruefungKonfiguration.WennKeineKPOptionen.IGNORE -> Unit // do nothing
      }
      return resultat
    }

    return kp::class.java.newInstance().pruefeMeldung(meldung, resultat)
  }


  fun pruefeMeldungen(meldungen: List<String>) = pruefeMeldungenIntern(meldungen, null, null, Resultat())
  fun pruefeMeldungen(meldungen: List<String>, type: String) = pruefeMeldungenIntern(meldungen, type, null, Resultat())
  fun pruefeMeldungen(meldungen: List<String>, type: String, version: String) = pruefeMeldungenIntern(meldungen, type, version, Resultat())

  private fun pruefeMeldungenIntern(meldungen: List<String>, type: String?, version: String?, resultat: Resultat): Resultat {
    meldungen.forEach { meldung ->
      pruefeMeldungIntern(meldung, type, version, resultat)
    }
    return resultat 
  }


  private fun ermittleKernpruefung(
    meldung: String,
    type: String?,
    version: String?,
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