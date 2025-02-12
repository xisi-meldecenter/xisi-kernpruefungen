package de.xisi.kernpruefung.fachlichkeit

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object AllgemeinePruefungen {
  private val uuidPattern = "([0-9]|[a-f]|[A-F]){8}(-([0-9]|[a-f]|[A-F]){4})(-([0-9]|[a-f]|[A-F]){4})(-([0-9]|[a-f]|[A-F]){4})(-([0-9]|[a-f]|[A-F]){12})".toRegex()
  fun valideUUID(uuid: String?) = uuid != null && uuid.matches(uuidPattern)

  // yyyy-MM-ddTHH:mm:ss.SSS
  private val validesDatum1 = "20[0-9]{2}-(12|11|10|0[1-9])-((3)(0|1)|(1|2)[0-9]|(0)[1-9])T([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].[0-9]{3}".toRegex()
  fun validesDatum1(datum: String?) = datum != null && datum.matches(validesDatum1)

  // yyyyMMddT
  private val validesDatumOhneTrenner = "^20[0-9]{2}(12|11|10|0[1-9])((3)(0|1)|(1|2)[0-9]|(0)[1-9])$".toRegex()
  val datumOhneTrennerPattern = "yyyyMMdd"
  fun validesDatumOhneTrenner(datum: String?): Boolean {
    if (datum == null) return false
    if (!datum.matches(validesDatumOhneTrenner)) return false
    val parsed = try {
      LocalDate.parse(datum.toString(), DateTimeFormatter.ofPattern(datumOhneTrennerPattern))
    } catch (e: DateTimeParseException) {
      return false
    }
    if (parsed.format(DateTimeFormatter.ofPattern(datumOhneTrennerPattern)) != datum) return false
    return true
  }

  // HHmmssSSS
  private val valideUhrzeitOhneTrenner = "^([0-1][0-9]|2[0-3])[0-5][0-9][0-5][0-9][0-9]{3}$".toRegex()
  fun valideUhrzeitOhneTrenner(uhrzeit: String?) = uhrzeit != null && uhrzeit.matches(valideUhrzeitOhneTrenner)
}
