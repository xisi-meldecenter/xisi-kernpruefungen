package de.xisi.kernpruefung.fachlichkeit

fun String?.hasContent() = this.isNullOrBlank().not()
fun String?.emptyToNull() = if (this.isNullOrBlank()) null else this 