package de.xisi.kernpruefung

import de.xisi.kernpruefung.api.Resultat
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.test.fail


// ******************************************************************************************************************
// ******************************************************************************************************************
// ***** Any
infix fun <T> T.assert(method: T.() -> Unit): Unit {
  this.apply(method)
}

infix fun Any?.mustBe(expected: Any?) = assertEquals(expected, this)

infix fun Any?.mustNotBe(expected: Any?) = assertNotEquals(expected, this)
infix fun Any?.mustBeType(expected: Class<*>) {
  this mustNotBe null
  this!!::class.java mustBe expected
}

infix fun Any?.mustBeAsString(expected: String) {
  this mustNotBe null
  this!!.toString() mustBe expected
}

// ******************************************************************************************************************
// ******************************************************************************************************************
// ***** String

infix fun String?.mustContain(expectedPart: String) = assertTrue("'$this' doesn't contain '$expectedPart'") { this != null && this.contains(expectedPart) }

infix fun (() -> Any).mustThrowException(exceptionHandler: (Exception) -> Unit) = try {
  this.invoke()
  fail("Exception expected")
} catch (e: Exception) {
  exceptionHandler.invoke(e)
}

infix fun (() -> Resultat).expectKPFehler(msg: String) {
  val res = this.invoke()
  res.toString() mustBe msg
}