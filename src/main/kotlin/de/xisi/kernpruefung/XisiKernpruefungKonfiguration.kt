package de.xisi.kernpruefung

import de.xisi.kernpruefung.utils.XisiLogger
import java.util.*

class XisiKernpruefungKonfiguration(private val log: XisiLogger) {

  // ******************************************************************************************************************
  // ***** logging
  fun logLevelDebug() = log.log_level_debug()
  fun logLevelInfo() = log.log_level_info()
  fun logLevelWarn() = log.log_level_warn()
  fun logLevelError() = log.log_level_error()
  fun logOff() = log.log_off()

  // ******************************************************************************************************************

  internal enum class WennKeineKPOptionen { TF, LOG, IGNORE }

  internal var wennKeineKP = WennKeineKPOptionen.TF
  fun wennKeineKPExistiertTechnischerFehler() = run { wennKeineKP = WennKeineKPOptionen.TF }
  fun wennKeineKPExistiertLoggeWarnung() = run { wennKeineKP = WennKeineKPOptionen.LOG }
  fun wennKeineKPExistiertIgnoriere() = run { wennKeineKP = WennKeineKPOptionen.IGNORE }

  // ******************************************************************************************************************

  fun nutzeDatum(yyyyMMdd: String) {}
  fun nutzeDatum(date: Date) {}

  // verhalten, wenn meldung noch ungültig
}