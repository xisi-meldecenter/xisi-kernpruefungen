package de.xisi.kernpruefung.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class XisiLogger() {
  private val dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")

  // ******************************************************************************************************************
  private enum class LogLevel { DEBUG, INFO, WARN, ERROR, OFF }

  private var logLevel = LogLevel.INFO
  fun log_level_debug() = run { logLevel = LogLevel.DEBUG }
  fun log_level_info() = run { logLevel = LogLevel.INFO }
  fun log_level_warn() = run { logLevel = LogLevel.WARN }
  fun log_level_error() = run { logLevel = LogLevel.ERROR }
  fun log_off() = run { logLevel = LogLevel.OFF }
  // ******************************************************************************************************************


  fun debug(any: Any?) = log(LogLevel.DEBUG, any)
  fun info(any: Any?) = log(LogLevel.INFO, any)
  fun warn(any: Any?) = log(LogLevel.WARN, any)
  fun error(any: Any?) = log(LogLevel.ERROR, any)


  private fun log(level: LogLevel, any: Any?) {
    if (logLevel.ordinal > level.ordinal) return
    println(LocalDateTime.now().format(dateFormat) + " [${level.name.padEnd(5)}] " + (any ?: ""))
  }
}
