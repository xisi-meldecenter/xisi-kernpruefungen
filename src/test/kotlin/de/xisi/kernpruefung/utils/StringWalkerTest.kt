package de.xisi.kernpruefung.utils

import de.xisi.kernpruefung.mustBe
import org.junit.jupiter.api.Test


class StringWalkerTest {
  @Test
  fun `Das ist ein Test`() {
    val sw = StringWalker("123")

    sw.takeIfPresent(1, 2) mustBe "12"
    sw.takeIfPresent(1, 3) mustBe "123"
    sw.takeIfPresent(1, 4) mustBe ""
  }

  @Test
  fun `laufen geht`() {
    val sw = StringWalker("1234567890")

    sw.naechsteZeichen(2) mustBe "12"
    sw.naechsteZeichen(2) mustBe "34"
    sw.naechsteZeichen(4) mustBe "5678"
  }
}